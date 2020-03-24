package com.arobs.internship.library.controller;

import com.arobs.internship.library.business.StatisticsService;
import com.arobs.internship.library.business.SuspendedEmployeeService;
import com.arobs.internship.library.converters.SuspendedEmployeeDTOConverter;
import com.arobs.internship.library.entities.employee.SuspendedEmployee;
import com.arobs.internship.library.entities.statistics.TopReadingEmployee;
import com.arobs.internship.library.entities.statistics.TopRentedBook;
import com.arobs.internship.library.util.handler.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private SuspendedEmployeeService suspendedEmployeeService;

    @Autowired
    private SuspendedEmployeeDTOConverter suspendedEmployeeDTOConverter;

    @GetMapping(path = "/getRentedBooks")
    public ResponseEntity<?> getTopRentedBooks(@RequestParam("NumberOfResults") int top, @RequestParam("startDate") Date startDate,
                                               @RequestParam("endDate") Date endDate) {
        List<TopRentedBook> rentedBooks;
        try {
            rentedBooks = statisticsService.topXRentedBooks(top, startDate, endDate);
        } catch (ValidationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        if (rentedBooks == null) {
            return new ResponseEntity<>("No books rented in this period of time", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(rentedBooks, HttpStatus.OK);
    }

    @GetMapping(path = "/getReadingEmployees")
    public ResponseEntity<?> getTopReadingEmployees(@RequestParam("NumberOfResults") int top, @RequestParam("startDate") Date startDate,
                                                    @RequestParam("endDate") Date endDate) {
        List<TopReadingEmployee> readingEmployees;
        try {
            readingEmployees = statisticsService.topXReadingEmployees(top, startDate, endDate);
        } catch (ValidationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        if (readingEmployees == null) {
            return new ResponseEntity<>("No books read and returned in this period of time", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(readingEmployees, HttpStatus.OK);
    }

    @GetMapping(path = "/getSuspendedEmployees")
    public ResponseEntity<?> getSuspendedEmployees() {
        List<SuspendedEmployee> suspendedEmployees = suspendedEmployeeService.findSuspendedEmployees();
        if (suspendedEmployees.isEmpty()) {
            return new ResponseEntity<>("No suspended employees in the database", HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(suspendedEmployeeDTOConverter.listSuspendedEmployeeToDTO(suspendedEmployees), HttpStatus.OK);
    }
}
