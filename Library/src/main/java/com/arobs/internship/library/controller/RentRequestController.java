package com.arobs.internship.library.controller;

import com.arobs.internship.library.business.RentRequestService;
import com.arobs.internship.library.converters.RentReqDTOConverter;
import com.arobs.internship.library.dtos.book.CopyDTO;
import com.arobs.internship.library.dtos.operations.BookRequestDTO;
import com.arobs.internship.library.dtos.operations.RentRequestDTO;
import com.arobs.internship.library.entities.book.Copy;
import com.arobs.internship.library.entities.operations.BookRequest;
import com.arobs.internship.library.entities.operations.RentRequest;
import com.arobs.internship.library.util.handler.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/rentRequest")
public class RentRequestController {

    @Autowired
    private RentRequestService rentRequestService;

    @Autowired
    private RentReqDTOConverter rentReqDTOConverter;

    @PostMapping("/addRentRequest")
    public ResponseEntity<?> addRentRequest(@RequestBody @Valid RentRequestDTO rentRequestDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>("Invalid information in fields", HttpStatus.BAD_REQUEST);
        }
        try {
            RentRequest rentRequest = rentRequestService.insertRentRequest(rentReqDTOConverter.dtoToRentRequest(rentRequestDTO));
            return new ResponseEntity<>(rentReqDTOConverter.rentRequestToDTO(rentRequest), HttpStatus.OK);
        } catch (ValidationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/rentRequests")
    public ResponseEntity<?> findRentRequests() {
        List<RentRequest> rentRequests = rentRequestService.findRentRequests();
        if (rentRequests.isEmpty()) {
            return new ResponseEntity<>("No rent request requests present in the db", HttpStatus.BAD_REQUEST);
        }
        List<RentRequestDTO> rentDTOS = rentReqDTOConverter.listRentRequestToDTO(rentRequests);
        return new ResponseEntity<>(rentDTOS, HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<?> getRentRequest(@RequestParam("rentRequestID") int id) {
        RentRequest rentRequest = rentRequestService.findRentRequestById(id);
        if (rentRequest == null) {
            return new ResponseEntity<>("No rent request with this id found", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(rentReqDTOConverter.rentRequestToDTO(rentRequest), HttpStatus.OK);
    }

    @DeleteMapping("/deleteRentRequest")
    public ResponseEntity<?> deleteRentRequest(@RequestParam("id") int id) {
        if(rentRequestService.deleteRentRequest(id)==1){
            return new ResponseEntity<>("Rent request with id: " + id + " deleted successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No rent request with this id found", HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/updateRentRequest/{id}")
    public ResponseEntity<?> updateRequestFinalStatus(@RequestParam("Confirm") boolean confirmation,
                                            @PathVariable int id) {
        try {
            rentRequestService.updateRentRequestFinalStatus(confirmation, id);
        } catch (ValidationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Rent request with id: " + id + " updated successfully.", HttpStatus.OK);
    }
}
