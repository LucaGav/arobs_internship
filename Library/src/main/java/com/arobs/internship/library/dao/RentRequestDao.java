package com.arobs.internship.library.dao;

import com.arobs.internship.library.entities.operations.RentRequest;

import java.util.List;

public interface RentRequestDao {

    RentRequest insert(RentRequest rentRequest);

    List<RentRequest> findRentRequests();

    RentRequest findById(int id);

    List<RentRequest> findByBookId(int bookID);

    RentRequest findByEmployeeAndBookID(int employeeID,int bookID);

    int delete(int id);
}
