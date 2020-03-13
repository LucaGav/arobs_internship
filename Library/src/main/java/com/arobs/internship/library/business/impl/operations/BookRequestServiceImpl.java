package com.arobs.internship.library.business.impl.operations;

import com.arobs.internship.library.business.BookRequestService;
import com.arobs.internship.library.dao.BookRequestDao;
import com.arobs.internship.library.dao.factory.DaoFactory;
import com.arobs.internship.library.entities.operations.BookRequest;
import com.arobs.internship.library.util.status.RequestStatus;
import com.arobs.internship.library.util.handler.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class BookRequestServiceImpl implements BookRequestService {

    @Autowired
    private DaoFactory daoFactory;

    private BookRequestDao bookRequestDao;

    @PostConstruct
    public void init() {
        DaoFactory factory = daoFactory.getInstance();
        bookRequestDao = factory.getBookRequestDao();
    }

    @Override
    @Transactional
    public BookRequest insertBookRequest(BookRequest bookRequest) {
        bookRequest.setStatus(RequestStatus.PENDING.name());
        return bookRequestDao.insert(bookRequest);
    }

    @Override
    @Transactional
    public List<BookRequest> findBookRequests() {
        return bookRequestDao.findBookRequests();
    }

    @Override
    @Transactional
    public BookRequest findBookRequestById(int id) {
        return bookRequestDao.findById(id);
    }

    @Override
    @Transactional
    public BookRequest updateBookRequest(String status, int id) throws ValidationException {
        BookRequest bookRequest = this.findBookRequestById(id);
        if (bookRequest == null) {
            throw new ValidationException("No book request with this id found.");
        }
        if (!RequestStatus.contains(status.toUpperCase())) {
            throw new ValidationException("No valid status for copy");
        }
        if (!status.equals(bookRequest.getStatus())) {
            bookRequest.setStatus(status.toUpperCase());
        } else {
            throw new ValidationException("No updated fields");
        }
        return bookRequest;
    }

    @Override
    @Transactional
    public int deleteBookRequest(int id) {
        return bookRequestDao.delete(id);
    }
}
