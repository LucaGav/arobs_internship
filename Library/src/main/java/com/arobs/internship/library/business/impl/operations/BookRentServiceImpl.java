package com.arobs.internship.library.business.impl.operations;

import com.arobs.internship.library.business.BookRentService;
import com.arobs.internship.library.business.CopyService;
import com.arobs.internship.library.business.RentRequestService;
import com.arobs.internship.library.dao.BookRentDao;
import com.arobs.internship.library.dao.factory.DaoFactory;
import com.arobs.internship.library.entities.Employee;
import com.arobs.internship.library.entities.book.Book;
import com.arobs.internship.library.entities.book.Copy;
import com.arobs.internship.library.entities.operations.BookRent;
import com.arobs.internship.library.entities.operations.RentRequest;
import com.arobs.internship.library.util.handler.ValidationException;
import com.arobs.internship.library.util.status.BookRentStatus;
import com.arobs.internship.library.util.status.CopyStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@EnableScheduling
public class BookRentServiceImpl implements BookRentService {

    @Autowired
    private RentRequestService rentRequestService;

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
        BookRent brCheck = this.findBookRentByEmployeeAndBookID(employee.getEmployeeID(),
                book.getBookID());
        if (brCheck != null) {
            System.out.println("This employee has already rented this book");
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
    public BookRent updateBookRent(float grade, String status, int id) {
        return null;
    }

    @Override
    @Transactional
    public int deleteBookRent(int id) {
        return 0;
    }

    @Scheduled(cron = "0 59 16 * * *")
    @Transactional
    public void checkLateBookRents(){
        List<BookRent> bookRents = bookRentDao.findBookRents();
        for(BookRent b : bookRents){
            if(b.getReturnDate().compareTo(new Date()) < 0){
                b.setStatus(BookRentStatus.LATE.name());
            }
        }
    }
}
