package com.arobs.internship.library.controller;

import com.arobs.internship.library.business.BookService;
import com.arobs.internship.library.converters.BookDTOConverter;
import com.arobs.internship.library.dtos.book.BookDTO;
import com.arobs.internship.library.dtos.book.TagDTO;
import com.arobs.internship.library.entities.book.Book;
import com.arobs.internship.library.util.handler.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @PostMapping("/addBook")
    public ResponseEntity<?> addBook(@RequestBody @Valid BookDTO bookDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.error("addBook: RequestBody has invalid information");
            return new ResponseEntity<>("Invalid information in fields", HttpStatus.BAD_REQUEST);
        }
        try {
            Book book = bookService.insertBook(bookDTOConverter.dtoToBook(bookDTO));
            logger.info("addBook: Book " + book.getTitle() + "of author " + book.getAuthor() + " inserted successfully");
            return new ResponseEntity<>(bookDTOConverter.bookToDTO(book), HttpStatus.OK);
        } catch (ValidationException ex) {
            logger.error("addBook: " + ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/books")
    public ResponseEntity<?> findBooks() {
        List<Book> books = bookService.findBooks();
        if (books.isEmpty()) {
            logger.info("findBooks: No books in the database");
            return new ResponseEntity<>("No books present in the db", HttpStatus.NO_CONTENT);
        }
        List<BookDTO> bookDTOS = bookDTOConverter.listBookToDTO(books);
        logger.info("findBooks: List of books sent as response");
        return new ResponseEntity<>(bookDTOS, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getBook(@RequestParam("bookID") int id) {
        Book book = bookService.findBookById(id);
        if (book == null) {
            logger.info("getBook: No book with id " + id + " in the database");
            return new ResponseEntity<>("No book with this id found", HttpStatus.NO_CONTENT);
        }
        logger.info("getBook: Book with id " + id + " found and sent in response");
        return new ResponseEntity<>(bookDTOConverter.bookToDTO(book), HttpStatus.OK);
    }

    @DeleteMapping("/deleteBook")
    public ResponseEntity<?> deleteBook(@RequestParam("id") int id) {
        try {
            Book book = bookService.deleteBook(id);
            logger.info("deleteBook: Book with title " + book.getTitle() + " of author: " + book.getAuthor() + " deleted successfully");
            return new ResponseEntity<>("Book " + book.getTitle() + " of author: " + book.getAuthor() + " deleted successfully.", HttpStatus.OK);
        } catch (ValidationException ex) {
            logger.error("deleteBook: " + ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/updateBook/{id}")
    public ResponseEntity<?> updateBook(@RequestParam("bookDescription") String description, @RequestBody Set<TagDTO> tagDTOSet, @PathVariable int id) {
        try {
            Book book = bookService.updateBook(description, tagDTOSet, id);
            logger.info("updateBook: Book with id: " + book.getBookID() + " updated successfully");
            return new ResponseEntity<>("Book with id: " + book.getBookID() + " updated successfully", HttpStatus.OK);
        } catch (ValidationException ex) {
            logger.error("updateBook: " + ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
