package com.arobs.internship.library.controller;

import com.arobs.internship.library.business.BookService;
import com.arobs.internship.library.converters.BookDTOConverter;
import com.arobs.internship.library.entities.book.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    @Mock
    private BookDTOConverter bookDTOConverter;

    private Book book;

    @BeforeEach
    public void setUp() {
        book = new Book(10, "Pratatata", "String", "Priasac", new Date());
    }

    @Test
    public void whenFindBook_givenBookID_returnResponseEntity() {
        when(bookService.findBookById(10)).thenReturn(book);
        ResponseEntity responseEntity = bookController.getBook(10);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
    }
}
