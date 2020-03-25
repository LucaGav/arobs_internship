package com.arobs.internship.library.controller;

import com.arobs.internship.library.business.CopyService;
import com.arobs.internship.library.converters.CopyDTOConverter;
import com.arobs.internship.library.dtos.book.CopyDTO;
import com.arobs.internship.library.entities.book.Copy;
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
@RequestMapping(path = "/copy")
public class CopyController {

    @Autowired
    private CopyService copyService;

    @Autowired
    private CopyDTOConverter copyDTOConverter;

    private static final Logger logger = LoggerFactory.getLogger(CopyController.class);

    @PostMapping("/addCopy")
    public ResponseEntity<?> addCopy(@RequestBody @Valid CopyDTO copyDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.error("addCopy: RequestBody has invalid information");
            return new ResponseEntity<>("Invalid information in fields", HttpStatus.BAD_REQUEST);
        }
        try {
            Copy copy = copyService.insertCopy(copyDTOConverter.dtoToCopy(copyDTO));
            logger.info("addCopy: Copy with id " + copy.getCopyID() + " inserted successfully");
            return new ResponseEntity<>(copyDTOConverter.copyToDTO(copy), HttpStatus.OK);
        } catch (ValidationException ex) {
            logger.error("addCopy: " + ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/copies")
    public ResponseEntity<?> findCopies() {
        List<Copy> copies = copyService.findCopies();
        if (copies.isEmpty()) {
            logger.info("findCopies: No copies in the database");
            return new ResponseEntity<>("No copies present in the db", HttpStatus.NO_CONTENT);
        }
        List<CopyDTO> copyDTOS = copyDTOConverter.listCopyToDTO(copies);
        logger.info("findCopies: List of copies sent as response");
        return new ResponseEntity<>(copyDTOS, HttpStatus.OK);
    }

    @GetMapping("/copies/{bookId}")
    public ResponseEntity<?> findCopiesOfBook(@PathVariable int bookId) {
        List<Copy> copies = copyService.findCopiesOfBook(bookId);
        if (copies.isEmpty()) {
            logger.info("findCopiesOfBook: No copies of this book in the database");
            return new ResponseEntity<>("No copies of this book present in the db", HttpStatus.NO_CONTENT);
        }
        List<CopyDTO> copyDTOS = copyDTOConverter.listCopyToDTO(copies);
        logger.info("findCopiesOfBook: List of copies sent as response");
        return new ResponseEntity<>(copyDTOS, HttpStatus.OK);
    }

    @GetMapping("/copies/available/{bookId}")
    public ResponseEntity<?> checkAvailableCopy(@PathVariable int bookId) {
        Copy copy = copyService.findAvailableCopyOfBook(bookId);
        if (copy == null) {
            logger.info("checkAvailableCopy: No available copies of book with id " + bookId + " in the database");
            return new ResponseEntity<>("No available copies of this book present in the db", HttpStatus.NO_CONTENT);
        } else {
            logger.info("checkAvailableCopy: At least one available copy of book with id " + bookId + " in the database");
            return new ResponseEntity<>("Book with this id has at least one available copy", HttpStatus.OK);
        }
    }

    @GetMapping
    public ResponseEntity<?> getCopy(@RequestParam("copyID") int id) {
        Copy copy = copyService.findCopyById(id);
        if (copy == null) {
            logger.info("getCopy: No copy with id " + id + " in the database");
            return new ResponseEntity<>("No copy with this id found", HttpStatus.NO_CONTENT);
        }
        logger.info("getCopy: Copy with id " + id + " found and sent in response");
        return new ResponseEntity<>(copyDTOConverter.copyToDTO(copy), HttpStatus.OK);
    }

    @DeleteMapping("/deleteCopy")
    public ResponseEntity<?> deleteCopy(@RequestParam("id") int id) {
        try {
            Copy copy = copyService.deleteCopy(id);
            logger.info("deleteCopy: Copy with id " + copy.getCopyID() + " deleted successfully");
            return new ResponseEntity<>("Copy with id " + copy.getCopyID() + " deleted successfully.", HttpStatus.OK);
        } catch (ValidationException ex) {
            logger.error("deleteCopy: " + ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/updateCopy/{id}")
    public ResponseEntity<?> updateCopy(@RequestParam("isRentable") Boolean rentable,
                                        @PathVariable int id) {
        try {
            copyService.updateCopyRentable(rentable, id);
            logger.info("updateCopy: Copy with id: " + id + " updated successfully");
            return new ResponseEntity<>("Copy with id: " + id + " updated successfully", HttpStatus.OK);
        } catch (ValidationException ex) {
            logger.error("updateCopy: " + ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
