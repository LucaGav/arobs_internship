package com.arobs.internship.library.controller;

import com.arobs.internship.library.business.BookRentService;
import com.arobs.internship.library.converters.BookRentDTOConverter;
import com.arobs.internship.library.dtos.operations.BookRentDTO;
import com.arobs.internship.library.entities.operations.BookRent;
import com.arobs.internship.library.util.handler.ValidationException;
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

    @PostMapping("/addBookRent")
    public ResponseEntity<?> addBookRent(@RequestBody @Valid BookRentDTO bookRentDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>("Invalid information in fields", HttpStatus.BAD_REQUEST);
        }
        try {
            BookRent bookRent = bookRentService.insertBookRent(bookRentDTOConverter.dtoToBookRent(bookRentDTO));
            if (bookRent.getCopy() == null) {
                return new ResponseEntity<>("Rent request created, no available or rentable copies", HttpStatus.ACCEPTED);
            }
            return new ResponseEntity<>(bookRentDTOConverter.bookRentToDTO(bookRent), HttpStatus.OK);
        } catch (ValidationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } //2ndcatch Exception 500
    }

    @GetMapping("/bookRents")
    public ResponseEntity<?> findBookRents() {
        List<BookRent> bookRents = bookRentService.findBookRents();
        if (bookRents.isEmpty()) {
            return new ResponseEntity<>("No book rents present in the db", HttpStatus.BAD_REQUEST);
        }
        List<BookRentDTO> rentDTOS = bookRentDTOConverter.listBookRentToDTO(bookRents);
        return new ResponseEntity<>(rentDTOS, HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<?> getBookRent(@RequestParam("bookRentID") int id) {
        BookRent bookRent = bookRentService.findBookRentById(id);
        if (bookRent == null) {
            return new ResponseEntity<>("No book rent with this id found", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(bookRentDTOConverter.bookRentToDTO(bookRent), HttpStatus.OK);
    }

    @DeleteMapping("/deleteBookRent")
    public ResponseEntity<?> deleteBookRent(@RequestParam("id") int id) {
        if (bookRentService.deleteBookRent(id) == 1) {
            return new ResponseEntity<>("Book Rent with id: " + id + " deleted successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No book rent with this id found", HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/updateBookRentOnReturn/{id}")
    public ResponseEntity<?> updateBookRentReturn(@RequestParam("Grade") float grade,
                                                  @PathVariable int id) {
        try {
            bookRentService.updateBookRentOnReturn(grade, id);
        } catch (ValidationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Book Rent with id: " + id + " returned successfully.", HttpStatus.OK);
    }

    @PatchMapping("/updateBookRentOnExtension/{id}")
    public ResponseEntity<?> updateBookRentExtension(@PathVariable int id) {
        try {
            bookRentService.updateBookRentOnExtension(id);
        } catch (ValidationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Book Rent with id: " + id + " extended successfully.", HttpStatus.OK);
    }
}
