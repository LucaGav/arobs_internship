package com.arobs.internship.library.controller;

import com.arobs.internship.library.business.RentRequestService;
import com.arobs.internship.library.converters.RentReqDTOConverter;
import com.arobs.internship.library.dtos.operations.RentRequestDTO;
import com.arobs.internship.library.entities.operations.RentRequest;
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
@RequestMapping(path = "/rentRequest")
public class RentRequestController {

    @Autowired
    private RentRequestService rentRequestService;

    @Autowired
    private RentReqDTOConverter rentReqDTOConverter;

    private static final Logger logger = LoggerFactory.getLogger(RentRequestController.class);


    @PostMapping("/addRentRequest")
    public ResponseEntity<?> addRentRequest(@RequestBody @Valid RentRequestDTO rentRequestDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.error("addRentRequest: RequestBody has invalid information");
            return new ResponseEntity<>("Invalid information in fields", HttpStatus.BAD_REQUEST);
        }
        try {
            RentRequest rentRequest = rentRequestService.insertRentRequest(rentReqDTOConverter.dtoToRentRequest(rentRequestDTO));
            logger.info("addRentRequest: Rent request with id " + rentRequest.getRentreqID() + " inserted successfully");
            return new ResponseEntity<>(rentReqDTOConverter.rentRequestToDTO(rentRequest), HttpStatus.OK);
        } catch (ValidationException ex) {
            logger.error("addRentRequest: " + ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/rentRequests")
    public ResponseEntity<?> findRentRequests() {
        List<RentRequest> rentRequests = rentRequestService.findRentRequests();
        if (rentRequests.isEmpty()) {
            logger.info("findRentRequests: No rent requests present in the db");
            return new ResponseEntity<>("No rent requests present in the db", HttpStatus.NO_CONTENT);
        }
        List<RentRequestDTO> rentDTOS = rentReqDTOConverter.listRentRequestToDTO(rentRequests);
        logger.info("findRentRequests: List of rent requests sent as response");
        return new ResponseEntity<>(rentDTOS, HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<?> getRentRequest(@RequestParam("rentRequestID") int id) {
        RentRequest rentRequest = rentRequestService.findRentRequestById(id);
        if (rentRequest == null) {
            logger.info("getRentRequest: No rent request with id " + id + " found");
            return new ResponseEntity<>("getRentRequest: No rent request with this id found", HttpStatus.NO_CONTENT);
        }
        logger.info("getRentRequest: Rent request with id " + id + " found and sent in response");
        return new ResponseEntity<>(rentReqDTOConverter.rentRequestToDTO(rentRequest), HttpStatus.OK);
    }

    @PatchMapping("/updateRentRequest/{id}")
    public ResponseEntity<?> updateRequestFinalStatus(@RequestParam("Confirm") boolean confirmation,
                                                      @PathVariable int id) {
        try {
            RentRequest rentRequest = rentRequestService.updateRentRequestFinalStatus(confirmation, id);
            logger.info("updateRequestFinalStatus: Rent request with id: " + rentRequest.getRentreqID() + " updated successfully");
            return new ResponseEntity<>("Rent request with id: " + rentRequest.getRentreqID() + " updated successfully", HttpStatus.OK);
        } catch (ValidationException ex) {
            logger.error("updateRequestFinalStatus: " + ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
