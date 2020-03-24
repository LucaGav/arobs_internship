package com.arobs.internship.library.business.impl.operations;

import com.arobs.internship.library.business.BookRentService;
import com.arobs.internship.library.business.CopyService;
import com.arobs.internship.library.business.RentRequestService;
import com.arobs.internship.library.business.SuspendedEmployeeService;
import com.arobs.internship.library.dao.BookRentDao;
import com.arobs.internship.library.dao.factory.DaoFactory;
import com.arobs.internship.library.entities.employee.Employee;
import com.arobs.internship.library.entities.book.Book;
import com.arobs.internship.library.entities.book.Copy;
import com.arobs.internship.library.entities.employee.SuspendedEmployee;
import com.arobs.internship.library.entities.operations.BookRent;
import com.arobs.internship.library.entities.operations.RentRequest;
import com.arobs.internship.library.util.date.DateUtil;
import com.arobs.internship.library.util.handler.ValidationException;
import com.arobs.internship.library.util.status.BookRentStatus;
import com.arobs.internship.library.util.status.CopyStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class BookRentServiceImpl implements BookRentService {

    @Autowired
    private RentRequestService rentRequestService;

    @Autowired
    private SuspendedEmployeeService suspendedEmployeeService;

    @Autowired
    private CopyService copyService;

    @Autowired
    private DaoFactory daoFactory;

    private BookRentDao bookRentDao;

    @PostConstruct
    public void init() {
        DaoFactory factory = daoFactory.getInstance();
        bookRentDao = factory.getBookRentDao();
    }

    @Override
    @Transactional
    public BookRent insertBookRent(BookRent bookRent) throws ValidationException {
        Book book = bookRent.getBook();
        Employee employee = bookRent.getEmployee();
        validateEmployeeRent(employee, book);

        if (bookRent.getCopy() != null) {
            return bookRentDao.insert(bookRent);
        }

        Copy copy = copyService.findAvailableCopyOfBook(book.getBookID());
        if (this.verifyBookRentFactors(book, employee, copy)) {
            bookRent.setCopy(copy);
            copy.setStatus(CopyStatus.RENTED.name());
            return bookRentDao.insert(bookRent);
        } else {
            return bookRent;
        }
    }

    private void validateEmployeeRent(Employee employee, Book book) throws ValidationException {
        SuspendedEmployee suspendedEmployee = suspendedEmployeeService.findSuspendedEmployeeByEmployeeID(employee.getEmployeeID());
        if(suspendedEmployee != null){
            throw new ValidationException("This employee is suspended from renting books");
        }
        BookRent brCheck = this.findBookRentByEmployeeAndBookID(employee.getEmployeeID(),
                book.getBookID());
        if (brCheck != null) {
            throw new ValidationException("This employee has already rented this book");
        }
    }

    private boolean verifyBookRentFactors(Book book, Employee employee, Copy copy) throws ValidationException {
        List<RentRequest> rrCheck = rentRequestService.findListWaitingForCopyRentRequest(book.getBookID());
        if (copy == null || !rrCheck.isEmpty()) {
            RentRequest rentRequest = new RentRequest(book, employee);
            rentRequestService.insertRentRequest(rentRequest);
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public List<BookRent> findBookRents() {
        return bookRentDao.findBookRents();
    }

    @Override
    @Transactional
    public BookRent findBookRentById(int id) {
        return bookRentDao.findById(id);
    }

    @Override
    @Transactional
    public BookRent findBookRentByEmployeeAndBookID(int employeeID, int bookID) {
        return bookRentDao.findByEmployeeAndBookId(employeeID, bookID);
    }

    @Override
    @Transactional
    public BookRent updateBookRentOnReturn(float grade, int id) throws ValidationException {
        BookRent bookRent = bookRentDao.findById(id);
        if(bookRent == null){
            throw new ValidationException("This is not a valid book rent");
        }
        if(grade < 1 || grade > 5) {
            throw new ValidationException("Not a valid grade");
        }
        bookRent.setGrade(grade);
        bookRent.setStatus(BookRentStatus.RETURNED.name());
        suspendedEmployeeService.updateOnBookReturn(bookRent.getEmployee(), bookRent.getReturnDate());
        Copy copy = bookRent.getCopy();
        copyService.updateCopyStatus(copy.getCopyID(),CopyStatus.AVAILABLE.name());
        return bookRent;
    }

    @Override
    @Transactional
    public BookRent updateBookRentOnExtension(int id) throws ValidationException {
        BookRent bookRent = bookRentDao.findById(id);
        if(bookRent == null){
            throw new ValidationException("This is not a valid book rent");
        }
        if(!bookRent.getStatus().equals(BookRentStatus.ON_GOING.name())){
            throw new ValidationException("This book rent is not available for extension");
        }
        this.extendBookRent(bookRent);
        return bookRent;
    }

    private void extendBookRent(BookRent bookRent) throws ValidationException {
        Date rentalDate = bookRent.getRentalDate();
        System.out.println(DateUtil.getDaysBetween(bookRent.getRentalDate(),bookRent.getReturnDate()));
        if(DateUtil.getDaysBetween(rentalDate,bookRent.getReturnDate()) >= 90) {
            throw new ValidationException("This book can no longer be extended");
        }
        Date newReturnDate = DateUtil.addDays(bookRent.getReturnDate(),15);
        if(DateUtil.getDaysBetween(rentalDate,newReturnDate) > 90) {
            newReturnDate = DateUtil.addDays(rentalDate,90);
        }
        bookRent.setReturnDate(newReturnDate);
        System.out.println(DateUtil.getDaysBetween(bookRent.getRentalDate(),bookRent.getReturnDate()));
    }

    @Override
    @Transactional
    public int deleteBookRent(int id) {
        return 0;
    }

    @Transactional
    public void checkLateBookRents(){
        List<BookRent> bookRents = bookRentDao.findBookRents();
        for(BookRent b : bookRents){
            if(b.getReturnDate().compareTo(new Date()) < 0){
                b.setStatus(BookRentStatus.LATE.name());
                if(suspendedEmployeeService.findSuspendedEmployeeByEmployeeID(b.getEmployee().getEmployeeID()) == null) {
                    SuspendedEmployee suspendedEmployee = new SuspendedEmployee();
                    suspendedEmployee.setEmployee(b.getEmployee());
                    suspendedEmployeeService.insertSuspendedEmployee(suspendedEmployee);
                }
            }
        }
    }

}
