package com.arobs.internship.library.business;

import com.arobs.internship.library.entities.operations.RentRequest;
import com.arobs.internship.library.util.handler.ValidationException;

import java.util.List;

public interface RentRequestService {

    RentRequest insertRentRequest(RentRequest rentRequest) throws ValidationException;

    List<RentRequest> findRentRequests();

    RentRequest findRentRequestById(int id);

    RentRequest updateRentRequest(String status, int id) throws ValidationException;

    int deleteRentRequest(int id);
}
