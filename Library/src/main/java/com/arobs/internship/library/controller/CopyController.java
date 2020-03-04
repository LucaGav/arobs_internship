package com.arobs.internship.library.controller;

import com.arobs.internship.library.business.CopyService;
import com.arobs.internship.library.dtos.CopyDTO;
import com.arobs.internship.library.entities.book.Copy;
import com.arobs.internship.library.handler.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/copy")
public class CopyController {

    @Autowired
    private CopyService copyService;

    @PostMapping("/addCopy")
    public ResponseEntity<?> addCopy(@RequestBody @Valid CopyDTO copyDTO) {
        try {
            copyService.insertCopy(copyService.dtoToCopy(copyDTO));
            return new ResponseEntity<>("Copy inserted successfully", HttpStatus.OK);
        } catch (ValidationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/copies")
    public ResponseEntity<?> findCopies() {
        List<Copy> copies = copyService.findCopies();
        if (copies.isEmpty()) {
            return new ResponseEntity<>("No copies present in the db", HttpStatus.BAD_REQUEST);
        }
        List<CopyDTO> copyDTOS = copyService.listCopyToDto(copies);
        return new ResponseEntity<>(copyDTOS, HttpStatus.OK);
    }

    @GetMapping("/copies/{bookId}")
    public ResponseEntity<?> findCopiesOfBook(@PathVariable int bookId) {
        List<Copy> copies = copyService.findCopiesOfBook(bookId);
        if (copies.isEmpty()) {
            return new ResponseEntity<>("No copies of this book present in the db", HttpStatus.BAD_REQUEST);
        }
        List<CopyDTO> copyDTOS = copyService.listCopyToDto(copies);
        return new ResponseEntity<>(copyDTOS, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getCopy(@RequestParam("copyID") int id) {
        Copy copy;
        try {
            copy = copyService.findCopyById(id);
        } catch (ValidationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(copyService.copyToDto(copy), HttpStatus.OK);
    }

    @DeleteMapping("/deleteCopy")
    public ResponseEntity<?> deleteCopy(@RequestParam("id") int id) {
        try {
            copyService.deleteCopy(id);
        } catch (ValidationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Copy with id: " + id + " deleted successfully.", HttpStatus.OK);
    }

    @PatchMapping("/updateCopy/{id}")
    public ResponseEntity<?> updateEmployee(@RequestParam("isAvailable") Boolean available, @RequestParam("Status") String status,
                                            @PathVariable int id) {
        try {
            copyService.updateCopy(status, available, id);
        } catch (ValidationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Copy with id: " + id + " updated successfully.", HttpStatus.OK);
    }
}
