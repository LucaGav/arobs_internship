package com.arobs.internship.library.controller;

import com.arobs.internship.library.business.CopyService;
import com.arobs.internship.library.converters.CopyDTOConverter;
import com.arobs.internship.library.dtos.book.CopyDTO;
import com.arobs.internship.library.entities.book.Copy;
import com.arobs.internship.library.util.handler.ValidationException;
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

    @PostMapping("/addCopy")
    public ResponseEntity<?> addCopy(@RequestBody @Valid CopyDTO copyDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>("Invalid information in fields", HttpStatus.BAD_REQUEST);
        }
        try {
            copyService.insertCopy(copyDTOConverter.dtoToCopy(copyDTO));
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
        List<CopyDTO> copyDTOS = copyDTOConverter.listCopyToDTO(copies);
        return new ResponseEntity<>(copyDTOS, HttpStatus.OK);
    }

    @GetMapping("/copies/{bookId}")
    public ResponseEntity<?> findCopiesOfBook(@PathVariable int bookId) {
        List<Copy> copies = copyService.findCopiesOfBook(bookId);
        if (copies.isEmpty()) {
            return new ResponseEntity<>("No copies of this book present in the db", HttpStatus.BAD_REQUEST);
        }
        List<CopyDTO> copyDTOS = copyDTOConverter.listCopyToDTO(copies);
        return new ResponseEntity<>(copyDTOS, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getCopy(@RequestParam("copyID") int id) {
        Copy copy = copyService.findCopyById(id);
        if (copy == null) {
            return new ResponseEntity<>("No copy with this id found", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(copyDTOConverter.copyToDTO(copy), HttpStatus.OK);
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
    public ResponseEntity<?> updateEmployee(@RequestParam("isRentable") Boolean rentable, @RequestParam("Status") String status,
                                            @PathVariable int id) {
        try {
            copyService.updateCopy(status, rentable, id);
        } catch (ValidationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Copy with id: " + id + " updated successfully.", HttpStatus.OK);
    }
}
