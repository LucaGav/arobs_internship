package com.arobs.internship.library.business.impl.book;

import com.arobs.internship.library.business.*;
import com.arobs.internship.library.dao.CopyDao;
import com.arobs.internship.library.dao.factory.DaoFactory;
import com.arobs.internship.library.entities.auxiliary.PendingRequest;
import com.arobs.internship.library.entities.book.Book;
import com.arobs.internship.library.entities.book.Copy;
import com.arobs.internship.library.entities.operations.RentRequest;
import com.arobs.internship.library.util.status.ActiveStatus;
import com.arobs.internship.library.util.status.CopyStatus;
import com.arobs.internship.library.util.handler.ValidationException;
import com.arobs.internship.library.util.status.RentRequestStatus;
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

    @Autowired
    private PendingRequestService pendingRequestService;

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
        if (copyIns.isRentable()) {
            this.updateOnNewAvailableCopy(copyIns, book);
        }
        return copyIns;
    }


    @Override
    @Transactional
    public void updateCopyStatus(int id, String status) throws ValidationException {
        Copy copy = this.findCopyById(id);
        copy.setStatus(status);
        if (status.equals(CopyStatus.AVAILABLE.name())) {
            this.updateOnNewAvailableCopy(copy, copy.getBook());
        }
    }

    private void updateOnNewAvailableCopy(Copy copy, Book book) throws ValidationException {
        RentRequest rentRequest = rentRequestService.findWaitingForCopyRentRequest(book.getBookID());
        if (rentRequest != null) {
            copy.setStatus(CopyStatus.PENDING.name());
            rentRequest.setStatus(RentRequestStatus.WAITING_CONFIRMATION.name());
            PendingRequest pendingRequest = new PendingRequest(copy, rentRequest);
            pendingRequestService.insertPendingRequest(pendingRequest);
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
    public Copy updateCopyRentable(Boolean rentable, int id) throws ValidationException {
        Copy copy = this.findCopyById(id);
        if (copy == null) {
            throw new ValidationException("No copy with this id found.");
        }
        if (copy.isRentable() != rentable) {
            copy.setRentable(rentable);
        } else {
            throw new ValidationException("Rentable field not updated");
        }
        return copy;
    }

    @Override
    @Transactional
    public Copy deleteCopy(int id) throws ValidationException {
        Copy copy = this.findCopyById(id);
        if (copy == null) {
            throw new ValidationException("No copy with this id found");
        }
        if (!copy.getStatus().equals(CopyStatus.AVAILABLE.name())) {
            throw new ValidationException("Copy cannot be deleted, it has unfinished operations");
        }
        copy.setStatus(ActiveStatus.INACTIVE.name());
        return copy;
    }
}
