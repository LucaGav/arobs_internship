package com.arobs.internship.library.converters.impl.operations;

import com.arobs.internship.library.converters.BookRentDTOConverter;
import com.arobs.internship.library.dtos.operations.BookRentDTO;
import com.arobs.internship.library.entities.book.Book;
import com.arobs.internship.library.entities.operations.BookRent;
import com.arobs.internship.library.util.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookRentDTOConverterImpl implements BookRentDTOConverter {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public BookRent dtoToBookRent(BookRentDTO bookRentDTO) {
        ModelMapper modelMapper = objectMapper.getMapper();
        return modelMapper.map(bookRentDTO, BookRent.class);
    }

    @Override
    public BookRentDTO bookRentToDTO(BookRent bookRent) {
        ModelMapper modelMapper = objectMapper.getMapper();
        return modelMapper.map(bookRent, BookRentDTO.class);
    }

    @Override
    public List<BookRentDTO> listBookRentToDTO(List<BookRent> bookRents) {
        ModelMapper modelMapper = objectMapper.getMapper();
        BookRentDTO bookRentDTO;
        List<BookRentDTO> bookRentDTOS = new ArrayList<>();
        for (BookRent bookRent : bookRents){
            bookRentDTO = modelMapper.map(bookRent, BookRentDTO.class);
            bookRentDTOS.add(bookRentDTO);
        }
        return bookRentDTOS;
    }
}
