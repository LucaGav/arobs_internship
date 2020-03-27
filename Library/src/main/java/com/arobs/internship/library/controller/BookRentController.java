package com.arobs.internship.library.controller;

import com.arobs.internship.library.business.BookRentService;
import com.arobs.internship.library.converters.BookRentDTOConverter;
import com.arobs.internship.library.dtos.operations.BookRentDTO;
import com.arobs.internship.library.entities.book.Book;
import com.arobs.internship.library.entities.operations.BookRent;
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

@RestController
@RequestMapping(path = "/bookrent")
public class BookRentController {

    @Autowired
    private BookRentService bookRentService;

    @Autowired
    private BookRentDTOConverter bookRentDTOConverter;

    private static final Logger logger = LoggerFactory.getLogger(BookRentController.class);

    @PostMapping("/addBookRent")
    public ResponseEntity<?> addBookRent(@RequestBody @Valid BookRentDTO bookRentDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.error("addBookRent: RequestBody has invalid information");
            return new ResponseEntity<>("Invalid information in fields", HttpStatus.BAD_REQUEST);
        }
        try {
            BookRent bookRent = bookRentService.insertBookRent(bookRentDTOConverter.dtoToBookRent(bookRentDTO));
            if (bookRent.getCopy() == null) {
                logger.info("addBookRent: Rent request created, no available or rentable copies");
                return new ResponseEntity<>("Rent request created, no available or rentable copies", HttpStatus.ACCEPTED);
            }
            logger.info("addBookRent: BookRent of book with id: " + bookRent.getBook().getBookID() + " inserted successfully");
            return new ResponseEntity<>("Book rent of book with id: " + bookRent.getBook().getBookID() + " created for employee with id: " +
                    bookRent.getEmployee().getEmployeeID(), HttpStatus.OK);
            //return new ResponseEntity<>(bookRentDTOConverter.bookRentToDTO(bookRent), HttpStatus.OK);
        } catch (ValidationException ex) {
            logger.error("addBookRent: " + ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/bookRents")
    public ResponseEntity<?> findBookRents() {
        List<BookRent> bookRents = bookRentService.findBookRents();
        if (bookRents.isEmpty()) {
            logger.info("findBookRents: No book rents found in the database");
            return new ResponseEntity<>("No book rents present in the db", HttpStatus.ACCEPTED);
        }
        List<BookRentDTO> rentDTOS = bookRentDTOConverter.listBookRentToDTO(bookRents);
        logger.info("findBookRents: List of book rents sent as response");
        return new ResponseEntity<>(rentDTOS, HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<?> getBookRent(@RequestParam("bookRentID") int id) {
        BookRent bookRent = bookRentService.findBookRentById(id);
        if (bookRent == null) {
            logger.info("getBookRent: No book rent with id " + id + " in the database");
            return new ResponseEntity<>("No book rent with this id found", HttpStatus.ACCEPTED);
        }
        logger.info("getBookRent: Book rent with id " + id + " found and sent in response");
        return new ResponseEntity<>(bookRentDTOConverter.bookRentToDTO(bookRent), HttpStatus.OK);
    }

    @PatchMapping("/updateBookRentOnReturn/{id}")
    public ResponseEntity<?> updateBookRentReturn(@RequestParam("Grade") float grade,
                                                  @PathVariable int id) {
        try {
            BookRent bookRent = bookRentService.updateBookRentOnReturn(grade, id);
            logger.info("updateBookRentReturn: Book with id: " + bookRent.getRentID() + " returned successfully");
            return new ResponseEntity<>("Book Rent with id: " + bookRent.getRentID() + " returned successfully.", HttpStatus.OK);
        } catch (ValidationException ex) {
            logger.error("updateBookRentReturn: " + ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/updateBookRentOnExtension/{id}")
    public ResponseEntity<?> updateBookRentExtension(@PathVariable int id) {
        try {
            BookRent bookRent = bookRentService.updateBookRentOnExtension(id);
            logger.info("updateBookRentReturn: Book with id: " + bookRent.getRentID() + " extended successfully");
            return new ResponseEntity<>("Book Rent with id: " + bookRent.getRentID() + " extended successfully.", HttpStatus.OK);
        } catch (ValidationException ex) {
            logger.error("updateBookRentExtension: " + ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
