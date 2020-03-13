package com.arobs.internship.library.business.impl.operations;

import com.arobs.internship.library.business.RentRequestService;
import com.arobs.internship.library.entities.operations.RentRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentRequestServiceImpl implements RentRequestService {
    @Override
    public RentRequest insertRentRequest(RentRequest rentRequest) {
        return null;
    }

    @Override
    public List<RentRequest> findRentRequests() {
        return null;
    }

    @Override
    public RentRequest findRentRequestById(int id) {
        return null;
    }

    @Override
    public RentRequest updateRentRequest() {
        return null;
    }

    @Override
    public int deleteRentRequest(int id) {
        return 0;
    }
}
