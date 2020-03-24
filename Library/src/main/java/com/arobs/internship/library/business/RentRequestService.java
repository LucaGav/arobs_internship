package com.arobs.internship.library.business;

import com.arobs.internship.library.entities.operations.RentRequest;
import com.arobs.internship.library.util.handler.ValidationException;

import java.util.List;

public interface RentRequestService {

    RentRequest insertRentRequest(RentRequest rentRequest) throws ValidationException;

    List<RentRequest> findRentRequests();

    RentRequest findRentRequestById(int id);

    RentRequest updateRentRequestFinalStatus(Boolean confirmation, int id) throws ValidationException;

    List<RentRequest> findRentRequestsByBookID(int bookID);

    RentRequest findRentRequestByEmployeeAndBookID(int employeeID, int bookID);

    RentRequest findWaitingForCopyRentRequest(int bookID);

    List<RentRequest> findListWaitingForCopyRentRequest(int bookID);

    int deleteRentRequest(int id);
}
