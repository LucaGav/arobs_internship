package com.arobs.internship.library.controller;

import com.arobs.internship.library.business.BookService;
import com.arobs.internship.library.dtos.BookDTO;
import com.arobs.internship.library.dtos.TagDTO;
import com.arobs.internship.library.handler.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/addBook")
    public ResponseEntity<?> addBook(@RequestBody @Valid BookDTO bookDTO) {
        try {
            bookService.insertBook(bookService.dtoToBook(bookDTO));
            return new ResponseEntity<>("Inserted book", HttpStatus.OK);
        } catch (ValidationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/books")
    public ResponseEntity<?> findBooks() {
        List<BookDTO> books = bookService.findBooks();
        if (books == null) {
            return new ResponseEntity<>("No books present in the db", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getBook(@RequestParam("bookID") int id) {
        BookDTO bookDTO;
        try {
            bookDTO = bookService.findBookById(id);
        } catch (ValidationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(bookDTO, HttpStatus.OK);
    }

    @DeleteMapping("/deleteBook")
    public ResponseEntity<?> deleteBook(@RequestParam("title") String title, @RequestParam("author") String author) {
        try {
            bookService.deleteBook(title, author);
        } catch (ValidationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Book " + title + " of author: " + author + " deleted successfully.", HttpStatus.OK);
    }

    @PatchMapping("/updateBook/{id}")
    public ResponseEntity<?> updateBook(@RequestParam("bookDescription") String description, @RequestBody Set<TagDTO> tagDTOSet, @PathVariable int id) {
        try {
            bookService.updateBook(description, tagDTOSet, id);
        } catch (ValidationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Book with id: " + id + " updated successfully", HttpStatus.OK);
    }
}
