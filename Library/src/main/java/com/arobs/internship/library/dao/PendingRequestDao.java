package com.arobs.internship.library.dao;

import com.arobs.internship.library.entities.auxiliary.PendingRequest;

import java.util.List;

public interface PendingRequestDao {

    PendingRequest insert(PendingRequest pendingRequest);

    List<PendingRequest> findPendingRequests();

    PendingRequest findById(int id);

    int delete(int id);

}
