package com.arobs.internship.library.business;

import com.arobs.internship.library.entities.auxiliary.PendingRequest;
import com.arobs.internship.library.util.handler.ValidationException;

import java.util.List;

public interface PendingRequestService {

    PendingRequest insertPendingRequest(PendingRequest pendingRequest);

    int deletePendingRequest(int id);

    void checkPendingRentRequests() throws ValidationException;

}
