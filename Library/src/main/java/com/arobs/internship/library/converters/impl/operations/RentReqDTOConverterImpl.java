package com.arobs.internship.library.converters.impl.operations;

import com.arobs.internship.library.converters.RentReqDTOConverter;
import com.arobs.internship.library.dtos.operations.RentRequestDTO;
import com.arobs.internship.library.entities.operations.RentRequest;
import com.arobs.internship.library.util.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RentReqDTOConverterImpl implements RentReqDTOConverter {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public RentRequest dtoToRentRequest(RentRequestDTO rentRequestDTO) {
        ModelMapper modelMapper = objectMapper.getMapper();
        return modelMapper.map(rentRequestDTO, RentRequest.class);

    }

    @Override
    public RentRequestDTO rentRequestToDTO(RentRequest rentRequest) {
        ModelMapper modelMapper = objectMapper.getMapper();
        return modelMapper.map(rentRequest, RentRequestDTO.class);
    }

    @Override
    public List<RentRequestDTO> listRentRequestToDTO(List<RentRequest> rentRequests) {
        ModelMapper modelMapper = objectMapper.getMapper();
        RentRequestDTO rentRequestDTO;
        List<RentRequestDTO> rentRequestDTOS = new ArrayList<>();
        for (RentRequest rentRequest : rentRequests) {
            rentRequestDTO = modelMapper.map(rentRequest, RentRequestDTO.class);
            rentRequestDTOS.add(rentRequestDTO);
        }
        return rentRequestDTOS;
    }
}
