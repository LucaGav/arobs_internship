package com.arobs.internship.library.dao.impl.hibernate.operations;

import com.arobs.internship.library.dao.RentRequestDao;
import com.arobs.internship.library.entities.operations.RentRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HibernateRentRequestDao implements RentRequestDao {
    @Override
    public RentRequest insert(RentRequest rentRequest) {
        return null;
    }

    @Override
    public List<RentRequest> findRentRequests() {
        return null;
    }

    @Override
    public RentRequest findById(int id) {
        return null;
    }

    @Override
    public int delete(int id) {
        return 0;
    }
}
