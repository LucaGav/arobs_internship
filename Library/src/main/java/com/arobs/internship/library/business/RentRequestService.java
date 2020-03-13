package com.arobs.internship.library.business;

import com.arobs.internship.library.entities.operations.RentRequest;

import java.util.List;

public interface RentRequestService {

    RentRequest insertRentRequest(RentRequest rentRequest);

    List<RentRequest> findRentRequests();

    RentRequest findRentRequestById(int id);

    RentRequest updateRentRequest();

    int deleteRentRequest(int id);
}
