package com.arobs.internship.library.converters.impl;

import com.arobs.internship.library.business.EmployeeService;
import com.arobs.internship.library.converters.BookReqDTOConverter;
import com.arobs.internship.library.dtos.BookRequestDTO;
import com.arobs.internship.library.entities.operations.BookRequest;
import com.arobs.internship.library.handler.ValidationException;
import com.arobs.internship.library.util.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookReqDTOConverterImpl implements BookReqDTOConverter {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmployeeService employeeService;

    @Override
    public List<BookRequestDTO> listBookRequestToDTO(List<BookRequest> bookRequests) {
        ModelMapper modelMapper = objectMapper.getMapper();
        BookRequestDTO bookRequestDTO;
        List<BookRequestDTO> bookRequestDTOS = new ArrayList<>();
        for (BookRequest bookRequest : bookRequests) {
            bookRequestDTO = modelMapper.map(bookRequest, BookRequestDTO.class);
            bookRequestDTOS.add(bookRequestDTO);
        }
        return bookRequestDTOS;
    }

    @Override
    public BookRequest dtoToBookRequest(BookRequestDTO bookRequestDTO) throws ValidationException {
        ModelMapper modelMapper = objectMapper.getMapper();
        BookRequest bookRequest = modelMapper.map(bookRequestDTO, BookRequest.class);
        bookRequest.setEmployee(employeeService.findEmployeeById(bookRequestDTO.getEmployeeID()));
        return bookRequest;
    }

    @Override
    public BookRequestDTO bookRequestToDTO(BookRequest bookRequest) {
        ModelMapper modelMapper = objectMapper.getMapper();
        return modelMapper.map(bookRequest, BookRequestDTO.class);
    }
}
