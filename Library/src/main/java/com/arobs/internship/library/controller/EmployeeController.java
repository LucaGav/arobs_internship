package com.arobs.internship.library.controller;

import com.arobs.internship.library.business.EmployeeService;
import com.arobs.internship.library.converters.EmployeeDTOConverter;
import com.arobs.internship.library.dtos.employee.EmployeeDTO;
import com.arobs.internship.library.dtos.employee.EmployeeUpdateDTO;
import com.arobs.internship.library.entities.employee.Employee;
import com.arobs.internship.library.util.entities.EmployeeUtil;
import com.arobs.internship.library.util.handler.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeDTOConverter employeeDTOConverter;

    @PostMapping("/addEmployee")
    public ResponseEntity<?> addEmployee(@RequestBody @Valid EmployeeDTO employeeDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>("Invalid information in fields", HttpStatus.BAD_REQUEST);
        }
        if (!EmployeeUtil.validateEmail(employeeDTO.getEmail())) {
            return new ResponseEntity<>("Bad e-mail format", HttpStatus.BAD_REQUEST);
        }
        if (!EmployeeUtil.validateRole(employeeDTO.getRole())) {
            return new ResponseEntity<>("No such role exists", HttpStatus.BAD_REQUEST);
        }
        try {
            Employee employee = employeeService.insertEmployee(employeeDTOConverter.dtoToEmployee(employeeDTO));
            return new ResponseEntity<>(employeeDTOConverter.employeeToDTO(employee), HttpStatus.OK);
        } catch (ValidationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/employees")
    public ResponseEntity<?> findEmployees() {
        List<Employee> employees = employeeService.findEmployees();
        if (employees.isEmpty()) {
            return new ResponseEntity<>("No employees present in the db", HttpStatus.BAD_REQUEST);
        }
        List<EmployeeDTO> employeeDTOS = employeeDTOConverter.listEmployeeToDTO(employees);
        return new ResponseEntity<>(employeeDTOS, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getEmployee(@RequestParam("employeeID") int id) {
        Employee employee = employeeService.findEmployeeById(id);
        if (employee == null) {
            return new ResponseEntity<>("No employee with this id found", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(employeeDTOConverter.employeeToDTO(employee), HttpStatus.OK);
    }

    @DeleteMapping("/deleteEmployee")
    public ResponseEntity<?> deleteEmployee(@RequestParam("email") String email) {
        if(employeeService.deleteEmployee(email)==1){
            return new ResponseEntity<>("User with email: " + email + " deleted successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No employee with this email found", HttpStatus.BAD_REQUEST);
        }

    }

    @PatchMapping("/updateEmployee/{id}")
    public ResponseEntity<?> updateEmployee(@RequestBody @Valid EmployeeUpdateDTO employeeDTO, BindingResult bindingResult,
                                            @PathVariable int id) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>("Invalid information in fields", HttpStatus.BAD_REQUEST);
        }
        if (!EmployeeUtil.validateEmail(employeeDTO.getEmail())) {
            return new ResponseEntity<>("Bad e-mail format", HttpStatus.BAD_REQUEST);
        }
        try {
            employeeService.updateEmployee(employeeDTO, id);
        } catch (ValidationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("User with email: " + employeeDTO.getEmail() + " updated successfully.", HttpStatus.OK);
    }

    @PatchMapping("/updatePassword")
    public ResponseEntity<?> updateEmployeePassword(@RequestParam("email") String email, @RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword) {
        try {
            employeeService.updateEmployeePassoword(email, oldPassword, newPassword);
        } catch (ValidationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Password of user with email: " + email + " successfully updated", HttpStatus.OK);
    }
}
