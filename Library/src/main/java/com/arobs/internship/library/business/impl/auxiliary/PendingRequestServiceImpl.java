package com.arobs.internship.library.business.impl.auxiliary;


import com.arobs.internship.library.business.BookRentService;
import com.arobs.internship.library.business.CopyService;
import com.arobs.internship.library.business.PendingRequestService;
import com.arobs.internship.library.dao.PendingRequestDao;
import com.arobs.internship.library.dao.factory.DaoFactory;
import com.arobs.internship.library.entities.auxiliary.PendingRequest;
import com.arobs.internship.library.entities.book.Copy;
import com.arobs.internship.library.entities.operations.BookRent;
import com.arobs.internship.library.entities.operations.RentRequest;
import com.arobs.internship.library.util.date.DateUtil;
import com.arobs.internship.library.util.handler.ValidationException;
import com.arobs.internship.library.util.status.CopyStatus;
import com.arobs.internship.library.util.status.RentRequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class PendingRequestServiceImpl implements PendingRequestService {

    @Autowired
    private BookRentService bookRentService;

    @Autowired
    private CopyService copyService;

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
    public int deletePendingRequest(int id) {
        return pendingRequestDao.delete(id);
    }


    @Transactional
    public void checkPendingRentRequests() throws ValidationException {
        List<PendingRequest> pendingRequests = pendingRequestDao.findPendingRequests();
        for (PendingRequest pendingRequest : pendingRequests) {
            Date pendingDate = DateUtil.addHours(pendingRequest.getPendingDate(), 24);
            RentRequest rentRequest = pendingRequest.getRentRequest();
            if (pendingDate.compareTo(new Date()) < 0) {
                rentRequest.setStatus(RentRequestStatus.DECLINED.name());
                copyService.updateCopyStatus(pendingRequest.getCopy().getCopyID(), CopyStatus.AVAILABLE.name());
                pendingRequestDao.delete(pendingRequest.getPendingreqID());

            } else {
                if (!rentRequest.getStatus().equals(RentRequestStatus.WAITING_CONFIRMATION.name())) {
                    this.finalizePending(pendingRequest, rentRequest);
                }
            }
        }
    }

    private void finalizePending(PendingRequest pendingRequest, RentRequest rentRequest) throws ValidationException {
        if (rentRequest.getStatus().equals(RentRequestStatus.GRANTED.name())) {
            BookRent bookRent = new BookRent(rentRequest.getBook(), rentRequest.getEmployee());
            Copy copy = pendingRequest.getCopy();
            copy.setStatus(CopyStatus.RENTED.name());
            bookRent.setCopy(copy);
            bookRentService.insertBookRent(bookRent);
        } else if (rentRequest.getStatus().equals(RentRequestStatus.DECLINED.name())) {
            copyService.updateCopyStatus(pendingRequest.getCopy().getCopyID(), CopyStatus.AVAILABLE.name());
        }
        this.deletePendingRequest(pendingRequest.getPendingreqID());
    }

}


