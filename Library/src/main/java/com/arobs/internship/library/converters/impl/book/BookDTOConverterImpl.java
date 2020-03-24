package com.arobs.internship.library.converters.impl.book;

import com.arobs.internship.library.converters.BookDTOConverter;
import com.arobs.internship.library.dtos.book.BookDTO;
import com.arobs.internship.library.entities.book.Book;
import com.arobs.internship.library.util.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookDTOConverterImpl implements BookDTOConverter {

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public List<BookDTO> listBookToDTO(List<Book> books) {
        ModelMapper modelMapper = objectMapper.getMapper();
        BookDTO bookDTO;
        List<BookDTO> bookDTOS = new ArrayList<>();
        for (Book book : books) {
            bookDTO = modelMapper.map(book, BookDTO.class);
            bookDTOS.add(bookDTO);
        }
        return bookDTOS;
    }

    @Override
    public Book dtoToBook(BookDTO bookDTO) {
        ModelMapper modelMapper = objectMapper.getMapper();
        return modelMapper.map(bookDTO, Book.class);
    }

    @Override
    public BookDTO bookToDTO(Book book) {
        ModelMapper modelMapper = objectMapper.getMapper();
        return modelMapper.map(book, BookDTO.class);
    }

}
