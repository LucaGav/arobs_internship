package com.arobs.internship.library.converters;

import com.arobs.internship.library.dtos.operations.BookRequestDTO;
import com.arobs.internship.library.entities.operations.BookRequest;
import com.arobs.internship.library.util.handler.ValidationException;

import java.util.List;

public interface BookReqDTOConverter {

    List<BookRequestDTO> listBookRequestToDTO(List<BookRequest> bookRequests);

    BookRequest dtoToBookRequest(BookRequestDTO bookRequestDTO) throws ValidationException;

    BookRequestDTO bookRequestToDTO(BookRequest bookRequest);

}
