package com.arobs.internship.library.controller;

import com.arobs.internship.library.business.BookRequestService;
import com.arobs.internship.library.converters.BookReqDTOConverter;
import com.arobs.internship.library.dtos.BookRequestDTO;
import com.arobs.internship.library.entities.operations.BookRequest;
import com.arobs.internship.library.util.handler.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/bookRequest")
public class BookRequestController {

    @Autowired
    private BookRequestService bookRequestService;

    @Autowired
    private BookReqDTOConverter bookReqDTOConverter;

    @PostMapping("/addBookRequest")
    public ResponseEntity<?> addBookRequest(@RequestBody @Valid BookRequestDTO bookRequestDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>("Invalid information in fields", HttpStatus.BAD_REQUEST);
        }
        try {
            bookRequestService.insertBookRequest(bookReqDTOConverter.dtoToBookRequest(bookRequestDTO));
            return new ResponseEntity<>("Book Request inserted successfully", HttpStatus.OK);
        } catch (ValidationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/bookRequests")
    public ResponseEntity<?> findBookRequests() {
        List<BookRequest> bookRequests = bookRequestService.findBookRequests();
        if (bookRequests.isEmpty()) {
            return new ResponseEntity<>("No book requests present in the db", HttpStatus.BAD_REQUEST);
        }
        List<BookRequestDTO> bookRequestDTOS = bookReqDTOConverter.listBookRequestToDTO(bookRequests);
        return new ResponseEntity<>(bookRequestDTOS, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getBookRequest(@RequestParam("bookRequestID") int id) {
        BookRequest bookRequest = bookRequestService.findBookRequestById(id);
        if (bookRequest == null) {
            return new ResponseEntity<>("No book request with this id found", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(bookReqDTOConverter.bookRequestToDTO(bookRequest), HttpStatus.OK);
    }

    @DeleteMapping("/deleteBookRequest")
    public ResponseEntity<?> deleteBookRequest(@RequestParam("id") int id) {
        try {
            bookRequestService.deleteBookRequest(id);
        } catch (ValidationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("BookRequest with id: " + id + " deleted successfully.", HttpStatus.OK);
    }

    @PatchMapping("/updateBookRequest/{id}")
    public ResponseEntity<?> updateEmployee(@RequestParam("status") String status,
                                            @PathVariable int id) {
        try {
            bookRequestService.updateBookRequest(status, id);
        } catch (ValidationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("BookRequest with id: " + id + " updated successfully.", HttpStatus.OK);
    }

}
