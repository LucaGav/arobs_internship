package com.arobs.internship.library.controller;

import com.arobs.internship.library.business.BookService;
import com.arobs.internship.library.converters.BookDTOConverter;
import com.arobs.internship.library.converters.TagDTOConverter;
import com.arobs.internship.library.dtos.book.BookDTO;
import com.arobs.internship.library.dtos.book.TagDTO;
import com.arobs.internship.library.entities.book.Book;
import com.arobs.internship.library.util.handler.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookDTOConverter bookDTOConverter;

    @PostMapping("/addBook")
    public ResponseEntity<?> addBook(@RequestBody @Valid BookDTO bookDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>("Invalid information in fields", HttpStatus.BAD_REQUEST);
        }
        try {
            Book book = bookService.insertBook(bookDTOConverter.dtoToBook(bookDTO));
            return new ResponseEntity<>(bookDTOConverter.bookToDTO(book), HttpStatus.OK);
        } catch (ValidationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/books")
    public ResponseEntity<?> findBooks() {
        List<Book> books = bookService.findBooks();
        if (books.isEmpty()) {
            return new ResponseEntity<>("No books present in the db", HttpStatus.BAD_REQUEST);
        }
        List<BookDTO> bookDTOS = bookDTOConverter.listBookToDTO(books);
        return new ResponseEntity<>(bookDTOS, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getBook(@RequestParam("bookID") int id) {
        Book book = bookService.findBookById(id);
        if (book == null) {
            return new ResponseEntity<>("No book with this id found", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(bookDTOConverter.bookToDTO(book), HttpStatus.OK);
    }

    @DeleteMapping("/deleteBook")
    public ResponseEntity<?> deleteBook(@RequestParam("title") String title, @RequestParam("author") String author) {
       if(bookService.deleteBook(title, author)==1) {
           return new ResponseEntity<>("Book " + title + " of author: " + author + " deleted successfully.", HttpStatus.OK);
       } else {
            return new ResponseEntity<>("No book with title " + title + " and author " + author + " found", HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/updateBook/{id}")
    public ResponseEntity<?> updateBook(@RequestParam("bookDescription") String description, @RequestBody Set<TagDTO> tagDTOSet, @PathVariable int id) {
        try {
            Book book = bookService.updateBook(description, tagDTOSet, id);
            return new ResponseEntity<>("Book with id: " + book.getBookID() + " updated successfully", HttpStatus.OK);
        } catch (ValidationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
