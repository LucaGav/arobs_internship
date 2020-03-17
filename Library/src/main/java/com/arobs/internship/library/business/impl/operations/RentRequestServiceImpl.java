package com.arobs.internship.library.business.impl.operations;

import com.arobs.internship.library.business.RentRequestService;
import com.arobs.internship.library.dao.BookRentDao;
import com.arobs.internship.library.dao.RentRequestDao;
import com.arobs.internship.library.dao.factory.DaoFactory;
import com.arobs.internship.library.entities.Employee;
import com.arobs.internship.library.entities.book.Book;
import com.arobs.internship.library.entities.operations.BookRent;
import com.arobs.internship.library.entities.operations.RentRequest;
import com.arobs.internship.library.util.handler.ValidationException;
import com.arobs.internship.library.util.status.RentRequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class RentRequestServiceImpl implements RentRequestService {

    @Autowired
    private DaoFactory daoFactory;

    private RentRequestDao rentRequestDao;

    @PostConstruct
    public void init(){
        DaoFactory factory = daoFactory.getInstance();
        rentRequestDao = factory.getRentRequestDao();
    }

    @Override
    @Transactional
    public RentRequest insertRentRequest(RentRequest rentRequest) throws ValidationException {
        Book book = rentRequest.getBook();
        Employee employee = rentRequest.getEmployee();
        this.verifyRentRequest(book,employee);
        return rentRequestDao.insert(rentRequest);
    }

    private void verifyRentRequest(Book book,Employee employee) throws ValidationException {
        RentRequest rrCheck = this.findRentRequestByEmployeeAndBookID(employee.getEmployeeID(),
                book.getBookID());
        if(rrCheck != null){
            System.out.println("This employee has already requested this book");
            throw new ValidationException("This employee has already requested this book");
        }
    }
    @Override
    @Transactional
    public List<RentRequest> findRentRequests() {
        return null;
    }

    @Override
    @Transactional
    public RentRequest findRentRequestById(int id) {
        return null;
    }

    @Override
    @Transactional
    public RentRequest updateRentRequest(String status, int id) {
        return null;
    }

    @Override
    @Transactional
    public List<RentRequest> findRentRequestByBookID(int bookID) {
        return rentRequestDao.findByBookId(bookID);
    }

    @Override
    @Transactional
    public RentRequest findRentRequestByEmployeeAndBookID(int employeeID, int bookID) {
        return rentRequestDao.findByEmployeeAndBookID(employeeID,bookID);
    }

    @Override
    @Transactional
    public int deleteRentRequest(int id) {
        return rentRequestDao.delete(id);
    }
}
