package com.arobs.internship.library.converters;

import com.arobs.internship.library.dtos.operations.BookRentDTO;
import com.arobs.internship.library.entities.operations.BookRent;
import org.modelmapper.ValidationException;

import java.util.List;

public interface BookRentDTOConverter {

    BookRent dtoToBookRent(BookRentDTO bookRentDTO) throws ValidationException, com.arobs.internship.library.util.handler.ValidationException;

    BookRentDTO bookRentToDTO(BookRent bookRent);

    List<BookRentDTO> listBookRentToDTO(List<BookRent> bookRents);
}
