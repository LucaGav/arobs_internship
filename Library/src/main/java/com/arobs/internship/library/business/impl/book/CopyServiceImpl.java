package com.arobs.internship.library.business.impl.book;

import com.arobs.internship.library.business.BookRentService;
import com.arobs.internship.library.business.BookService;
import com.arobs.internship.library.business.CopyService;
import com.arobs.internship.library.business.RentRequestService;
import com.arobs.internship.library.dao.CopyDao;
import com.arobs.internship.library.dao.factory.DaoFactory;
import com.arobs.internship.library.entities.book.Book;
import com.arobs.internship.library.entities.book.Copy;
import com.arobs.internship.library.entities.operations.BookRent;
import com.arobs.internship.library.entities.operations.RentRequest;
import com.arobs.internship.library.util.status.CopyStatus;
import com.arobs.internship.library.util.handler.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class CopyServiceImpl implements CopyService {

    @Autowired
    private BookService bookService;

    @Autowired
    private DaoFactory daoFactory;

    @Autowired
    private RentRequestService rentRequestService;

    @Autowired
    private BookRentService bookRentService;

    private CopyDao copyDao;

    @PostConstruct
    public void init() {
        DaoFactory factory = daoFactory.getInstance();
        copyDao = factory.getCopyDao();
    }

    @Override
    @Transactional
    public Copy insertCopy(Copy copy) throws ValidationException {
        Book book = copy.getBook();
        Copy copyIns = copyDao.insert(copy);
        if(copyIns.isRentable()) {
            this.updateOnCopyInsert(copyIns, book);
        }
        return copyIns;
    }

    private void updateOnCopyInsert(Copy copy, Book book) throws ValidationException {
        List<RentRequest> rentRequests = rentRequestService.findRentRequestByBookID(book.getBookID());
        if(!rentRequests.isEmpty()) {
            RentRequest rentRequest = rentRequests.get(0);
            BookRent bookRent = new BookRent(book, rentRequest.getEmployee());
            bookRent.setCopy(copy);
            copy.setStatus(CopyStatus.RENTED.name());
            rentRequestService.deleteRentRequest(rentRequest.getRentreqID());
            bookRentService.insertBookRent(bookRent);
        }
    }

    @Override
    @Transactional
    public List<Copy> findCopies() {
        return copyDao.findCopies();
    }

    @Override
    @Transactional
    public List<Copy> findCopiesOfBook(int id) {
        return copyDao.findByBookID(id);
    }

    @Override
    @Transactional
    public Copy findAvailableCopyOfBook(int id) {
        return copyDao.findAvailableByBookID(id);
    }

    @Override
    @Transactional
    public Copy findCopyById(int id) {
        return copyDao.findById(id);
    }

    @Override
    @Transactional
    public Copy updateCopy(Boolean rentable, int id) throws ValidationException {
        Copy copy = this.findCopyById(id);
        if (copy == null) {
            throw new ValidationException("No copy with this id found.");
        }
        if (copy.isRentable() != rentable) {
            copy.setRentable(rentable);
        }
        else {
            throw new ValidationException("Rentable field not updated");
        }
        return copy;
    }

    private boolean setCopyStatus(Copy copy, String status) {
        if(status.equals(CopyStatus.AVAILABLE.name())){
            copy.setStatus(status);
            return true;
        }
        if(status.equals(CopyStatus.PENDING.name()) || status.equals(CopyStatus.RENTED.name())){
            if(copy.isRentable()){
                copy.setStatus(status);
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional
    public int deleteCopy(int id){
        return copyDao.delete(id);
    }
}
