package com.arobs.internship.library.controller;

import com.arobs.internship.library.business.EmployeeService;
import com.arobs.internship.library.dtos.EmployeeDTO;
import com.arobs.internship.library.handler.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/addEmployee")
    public ResponseEntity<?> addEmployee(@RequestBody @Valid EmployeeDTO employeeDTO) {
        try {
            employeeService.insertEmployee(employeeService.dtoToEmployee(employeeDTO));
            return new ResponseEntity<>("Employee inserted successfully", HttpStatus.OK);
        } catch (CustomException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/employees")
    public ResponseEntity<?> findEmployees() {
        List<EmployeeDTO> employees = employeeService.findEmployees();
        if (employees == null) {
            return new ResponseEntity<>("No employees present in the db", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getEmployee(@RequestParam("employeeID") int id) {
        EmployeeDTO employeeDTO;
        try {
            employeeDTO = employeeService.findEmployeeById(id);
        } catch (CustomException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
    }

    @DeleteMapping("/deleteEmployee")
    public ResponseEntity<?> deleteEmployee(@RequestParam("email") String email) {
        try {
            employeeService.deleteEmployee(email);
        } catch (CustomException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("User with email: " + email + " deleted successfully.", HttpStatus.OK);
    }

    @PatchMapping("/updateEmployee/{id}")
    public ResponseEntity<?> updateEmployee(@RequestParam("email") String email, @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
                                            @PathVariable int id) {
        try {
            employeeService.updateEmployee(email, firstName, lastName, id);
        } catch (CustomException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("User with email: " + email + " updated successfully.", HttpStatus.OK);
    }

    @PatchMapping("/updatePassword")
    public ResponseEntity<?> updateEmployeePassword(@RequestParam("email") String email, @RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword) {
        try {
            employeeService.updateEmployeePassoword(email, oldPassword, newPassword);
        } catch (CustomException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Password of user with email: " + email + " successfully updated", HttpStatus.OK);
    }
}
