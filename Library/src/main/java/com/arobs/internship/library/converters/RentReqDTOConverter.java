package com.arobs.internship.library.converters;

import com.arobs.internship.library.dtos.operations.RentRequestDTO;
import com.arobs.internship.library.entities.operations.RentRequest;

import java.util.List;

public interface RentReqDTOConverter {

    RentRequest dtoToRentRequest(RentRequestDTO rentRequestDTO);

    RentRequestDTO rentRequestToDTO(RentRequest rentRequest);

    List<RentRequestDTO> listRentRequestToDTO(List<RentRequest> rentRequests);
}
