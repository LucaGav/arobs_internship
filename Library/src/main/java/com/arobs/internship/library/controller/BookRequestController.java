package com.arobs.internship.library.controller;

import com.arobs.internship.library.business.BookRequestService;
import com.arobs.internship.library.converters.BookReqDTOConverter;
import com.arobs.internship.library.dtos.operations.BookRequestDTO;
import com.arobs.internship.library.entities.operations.BookRequest;
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
@RequestMapping(path = "/bookRequest")
public class BookRequestController {

    @Autowired
    private BookRequestService bookRequestService;

    @Autowired
    private BookReqDTOConverter bookReqDTOConverter;

    private static final Logger logger = LoggerFactory.getLogger(BookRequestController.class);

    @PostMapping("/addBookRequest")
    public ResponseEntity<?> addBookRequest(@RequestBody @Valid BookRequestDTO bookRequestDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.error("addBookRequest: RequestBody has invalid information");
            return new ResponseEntity<>("Invalid information in fields", HttpStatus.BAD_REQUEST);
        }
        try {
            BookRequest bookRequest = bookRequestService.insertBookRequest(bookReqDTOConverter.dtoToBookRequest(bookRequestDTO));
            logger.info("addBookRequest: BookRequest with id " + bookRequest.getBookreqID() + " inserted successfully");
            return new ResponseEntity<>(bookReqDTOConverter.bookRequestToDTO(bookRequest), HttpStatus.OK);
        } catch (ValidationException ex) {
            logger.error("addBookRequest: " + ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/bookRequests")
    public ResponseEntity<?> findBookRequests() {
        List<BookRequest> bookRequests = bookRequestService.findBookRequests();
        if (bookRequests.isEmpty()) {
            logger.info("findBookRequests: No book requests in the database");
            return new ResponseEntity<>("No book requests present in the db", HttpStatus.NO_CONTENT);
        }
        List<BookRequestDTO> bookRequestDTOS = bookReqDTOConverter.listBookRequestToDTO(bookRequests);
        logger.info("findBookRequests: List of book requests sent as response");
        return new ResponseEntity<>(bookRequestDTOS, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getBookRequest(@RequestParam("bookRequestID") int id) {
        BookRequest bookRequest = bookRequestService.findBookRequestById(id);
        if (bookRequest == null) {
            logger.info("getBookRequest: No book request with id + " + id + " in the database");
            return new ResponseEntity<>("No book request with this id found", HttpStatus.NO_CONTENT);
        }
        logger.info("getBookRequest: Book request with id " + id + " found and sent in response");
        return new ResponseEntity<>(bookReqDTOConverter.bookRequestToDTO(bookRequest), HttpStatus.OK);
    }

    @PatchMapping("/updateBookRequest/{id}")
    public ResponseEntity<?> updateBookRequest(@RequestParam("status") String status,
                                               @PathVariable int id) {
        try {
            BookRequest bookRequest = bookRequestService.updateBookRequest(status, id);
            logger.info("updateBookRequest: Book request with id: " + bookRequest.getBookreqID() + " updated successfully");
            return new ResponseEntity<>("BookRequest with id: " + id + " updated successfully.", HttpStatus.OK);
        } catch (ValidationException ex) {
            logger.error("updateBookRequest: " + ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
