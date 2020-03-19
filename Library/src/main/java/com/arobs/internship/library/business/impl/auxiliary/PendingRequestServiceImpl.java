package com.arobs.internship.library.business.impl.auxiliary;


import com.arobs.internship.library.business.BookRentService;
import com.arobs.internship.library.business.PendingRequestService;
import com.arobs.internship.library.dao.PendingRequestDao;
import com.arobs.internship.library.dao.factory.DaoFactory;
import com.arobs.internship.library.entities.auxiliary.PendingRequest;
import com.arobs.internship.library.entities.book.Copy;
import com.arobs.internship.library.entities.operations.BookRent;
import com.arobs.internship.library.entities.operations.RentRequest;
import com.arobs.internship.library.util.handler.ValidationException;
import com.arobs.internship.library.util.status.RentRequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class PendingRequestServiceImpl implements PendingRequestService {

    @Autowired
    private BookRentService bookRentService;

    @Autowired
    private DaoFactory daoFactory;

    private PendingRequestDao pendingRequestDao;

    @PostConstruct
    public void init() {
        DaoFactory factory = daoFactory.getInstance();
        pendingRequestDao = factory.getPendingRequestDao();
    }

    @Override
    @Transactional
    public PendingRequest insertPendingRequest(PendingRequest pendingRequest) {
        return pendingRequestDao.insert(pendingRequest);
    }

    @Override
    @Transactional
    public List<PendingRequest> findPendingRequests() {
        return pendingRequestDao.findPendingRequests();
    }

    @Override
    @Transactional
    public PendingRequest findPendingRequestById(int id) {
        return pendingRequestDao.findById(id);
    }

    @Override
    @Transactional
    public PendingRequest findPendingRequestByRentRequestId(int rentreqID) {
        return pendingRequestDao.findByRentRequestID(rentreqID);
    }

    @Override
    @Transactional
    public int deletePendingRequest(int id) {
        return pendingRequestDao.delete(id);
    }


    @Transactional
    public void checkPendingRentRequests(){
        this.findPendingRequests();
    }

    private void finalizePending(PendingRequest pendingRequest, RentRequest rentRequest) throws ValidationException {
        if(rentRequest.getStatus().equals(RentRequestStatus.GRANTED.name())){
            BookRent bookRent = new BookRent(rentRequest.getBook(),rentRequest.getEmployee());
            bookRent.setCopy(pendingRequest.getCopy());
            bookRentService.insertBookRent(bookRent);
        }
        this.deletePendingRequest(pendingRequest.getPendingreqID());
    }

}


