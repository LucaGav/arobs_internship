package com.arobs.internship.library.business.impl;

import com.arobs.internship.library.business.BookRequestService;
import com.arobs.internship.library.business.EmployeeService;
import com.arobs.internship.library.converters.BookReqDTOConverter;
import com.arobs.internship.library.dao.BookRequestDao;
import com.arobs.internship.library.dao.factory.DaoFactory;
import com.arobs.internship.library.dtos.BookRequestDTO;
import com.arobs.internship.library.entities.operations.BookRequest;
import com.arobs.internship.library.entities.util.RequestStatus;
import com.arobs.internship.library.handler.ValidationException;
import com.arobs.internship.library.util.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.ArrayList;
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
    public void insertBookRequest(BookRequest bookRequest){
        bookRequest.setStatus(RequestStatus.PENDING.name());
        bookRequestDao.save(bookRequest);
    }

    @Override
    @Transactional
    public List<BookRequest> findBookRequests() {
        return bookRequestDao.findBookRequests();
    }

    @Override
    @Transactional
    public BookRequest findBookRequestById(int id) throws ValidationException {
        BookRequest bookRequest = bookRequestDao.findById(id);
        if (bookRequest == null) {
            throw new ValidationException("No book request with this id found");
        }
        return bookRequest;
    }

    @Override
    @Transactional
    public void updateBookRequest(String status, int id) throws ValidationException {
        BookRequest bookRequest = this.findBookRequestById(id);
        if (bookRequest == null) {
            throw new ValidationException("No book request with this id found.");
        }
        if (!RequestStatus.contains(status.toUpperCase())) {
            System.out.println(status);
            throw new ValidationException("No valid status for copy");
        }
        if (!status.equals(bookRequest.getStatus())) {
            bookRequest.setStatus(status);
        } else {
            throw new ValidationException("No updated fields");
        }
    }

    @Override
    @Transactional
    public void deleteBookRequest(int id) throws ValidationException {
        boolean foundBR = false;
        List<BookRequest> bookRequests = this.findBookRequests();
        for (BookRequest bookRequest : bookRequests) {
            if (bookRequest.getBookreqID() == id) {
                foundBR = true;
                break;
            }
        }
        if (!foundBR) {
            throw new ValidationException("No book request with this id found");
        }
        bookRequestDao.delete(id);
    }
}
