package com.arobs.internship.library.business.impl;

import com.arobs.internship.library.business.BookService;
import com.arobs.internship.library.business.CopyService;
import com.arobs.internship.library.dao.CopyDao;
import com.arobs.internship.library.dao.factory.DaoFactory;
import com.arobs.internship.library.entities.book.Copy;
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

    private CopyDao copyDao;

    @PostConstruct
    public void init() {
        DaoFactory factory = daoFactory.getInstance();
        copyDao = factory.getCopyDao();
    }

    @Override
    @Transactional
    public void insertCopy(Copy copy) throws ValidationException {
        if (!CopyStatus.contains(copy.getStatus())) {
            throw new ValidationException("No valid status for copy");
        }
        copyDao.save(copy);
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
    public Copy findCopyById(int id) {
        return copyDao.findById(id);
    }

    @Override
    @Transactional
    public void updateCopy(String status, Boolean rentable, int id) throws ValidationException {
        Copy copy = this.findCopyById(id);
        if (copy == null) {
            throw new ValidationException("No copy with this id found.");
        }
        if (!CopyStatus.contains(status.toUpperCase())) {
            throw new ValidationException("No valid status for copy");
        }
        if (copy.isRentable() != rentable) {
            copy.setRentable(rentable);
        }
        if (!status.toUpperCase().equals(copy.getStatus())) {
            this.setCopyStatus(copy, status.toUpperCase());
            if(!status.toUpperCase().equals(copy.getStatus())){
                throw new ValidationException("Cannot update status, because the copy is not rentable");
            }
        }
        else if (status.equals(copy.getStatus()) && copy.isRentable() == rentable) {
            throw new ValidationException("No updated fields");
        }

    }

    @Override
    @Transactional
    public void deleteCopy(int id) throws ValidationException {
        boolean foundCopy = false;
        List<Copy> copies = this.findCopies();
        for (Copy copy : copies) {
            if (copy.getCopyID() == id) {
                foundCopy = true;
                break;
            }
        }
        if (!foundCopy) {
            throw new ValidationException("No copy with this id found");
        }
        copyDao.delete(id);
    }

    void setCopyStatus(Copy copy, String status) {
        if(status.equals(CopyStatus.PENDING.name()) || status.equals(CopyStatus.RENTED.name())){
            if(copy.isRentable()){
                copy.setStatus(status);
            }
        }
    }
}
