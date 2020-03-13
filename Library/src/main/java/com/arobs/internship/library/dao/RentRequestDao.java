package com.arobs.internship.library.dao;

import com.arobs.internship.library.entities.operations.RentRequest;

import java.util.List;

public interface RentRequestDao {

    RentRequest insert(RentRequest rentRequest);

    List<RentRequest> findRentRequests();

    RentRequest findById(int id);

    int delete(int id);
}
