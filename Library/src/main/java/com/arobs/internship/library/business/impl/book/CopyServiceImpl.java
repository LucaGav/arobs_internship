package com.arobs.internship.library.business.impl.book;

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
    public Copy insertCopy(Copy copy) throws ValidationException {
        return copyDao.insert(copy);
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
    public Copy updateCopy(String status, Boolean rentable, int id) throws ValidationException {
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
            boolean checkStatus = this.setCopyStatus(copy, status.toUpperCase());
            if(!checkStatus){
                throw new ValidationException("Cannot update status, because the copy is not rentable");
            }
        }
        else if (status.equals(copy.getStatus()) && copy.isRentable() == rentable) {
            throw new ValidationException("No updated fields");
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
