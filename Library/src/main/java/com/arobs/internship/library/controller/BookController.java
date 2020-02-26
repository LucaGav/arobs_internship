package com.arobs.internship.library.controller;

import com.arobs.internship.library.business.BookService;
import com.arobs.internship.library.dtos.BookDTO;
import com.arobs.internship.library.entities.book.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.ValidationException;

@RestController
@RequestMapping(path = "/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/addBook")
    public ResponseEntity<?> addBook(@RequestBody @Valid BookDTO bookDTO){
        try{
            bookService.insertBook(bookService.dtoToBook(bookDTO));
            return new ResponseEntity<>("Inserted book", HttpStatus.OK);
        } catch (ValidationException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
