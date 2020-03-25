package com.arobs.internship.library.controller;

import com.arobs.internship.library.business.EmployeeService;
import com.arobs.internship.library.converters.EmployeeDTOConverter;
import com.arobs.internship.library.dtos.employee.EmployeeDTO;
import com.arobs.internship.library.dtos.employee.EmployeeUpdateDTO;
import com.arobs.internship.library.entities.employee.Employee;
import com.arobs.internship.library.util.entities.EmployeeUtil;
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
@RequestMapping(path = "/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeDTOConverter employeeDTOConverter;

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);


    @PostMapping("/addEmployee")
    public ResponseEntity<?> addEmployee(@RequestBody @Valid EmployeeDTO employeeDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.error("addEmployee: RequestBody has invalid information");
            return new ResponseEntity<>("Invalid information in fields", HttpStatus.BAD_REQUEST);
        }
        if (!EmployeeUtil.validateEmail(employeeDTO.getEmail())) {
            logger.error("addEmployee: Bad email format");
            return new ResponseEntity<>("Bad email format", HttpStatus.BAD_REQUEST);
        }
        if (!EmployeeUtil.validateRole(employeeDTO.getRole())) {
            logger.error("addEmployee: No such role exists");
            return new ResponseEntity<>("No such role exists", HttpStatus.BAD_REQUEST);
        }
        try {
            Employee employee = employeeService.insertEmployee(employeeDTOConverter.dtoToEmployee(employeeDTO));
            logger.info("addEmployee: Employee with email " + employee.getEmail() + " inserted successfully");
            return new ResponseEntity<>(employeeDTOConverter.employeeToDTO(employee), HttpStatus.OK);
        } catch (ValidationException ex) {
            logger.error("addEmployee" + ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/employees")
    public ResponseEntity<?> findEmployees() {
        List<Employee> employees = employeeService.findEmployees();
        if (employees.isEmpty()) {
            logger.info("findEmployees: No employees in the database");
            return new ResponseEntity<>("No employees present in the db", HttpStatus.NO_CONTENT);
        }
        List<EmployeeDTO> employeeDTOS = employeeDTOConverter.listEmployeeToDTO(employees);
        logger.info("findEmployees: List of employees sent as response");
        return new ResponseEntity<>(employeeDTOS, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getEmployee(@RequestParam("employeeID") int id) {
        Employee employee = employeeService.findEmployeeById(id);
        if (employee == null) {
            logger.info("getEmployee: No employee with id " + id + " in the database");
            return new ResponseEntity<>("No employee with this id found", HttpStatus.NO_CONTENT);
        }
        logger.info("getEmployee: Employee with id " + id + " found and sent in response");
        return new ResponseEntity<>(employeeDTOConverter.employeeToDTO(employee), HttpStatus.OK);
    }

    @DeleteMapping("/deleteEmployee")
    public ResponseEntity<?> deleteEmployee(@RequestParam("id") int id) {
        try {
            Employee employee = employeeService.deleteEmployee(id);
            logger.info("deleteEmployee: Employee with email " + employee.getEmail() + " deleted");
            return new ResponseEntity<>("Employee " + employee.getEmail() + " deleted successfully.", HttpStatus.OK);
        } catch (ValidationException e) {
            logger.error("deleteEmployee: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/updateEmployee/{id}")
    public ResponseEntity<?> updateEmployee(@RequestBody @Valid EmployeeUpdateDTO employeeDTO, BindingResult bindingResult,
                                            @PathVariable int id) {
        if (bindingResult.hasErrors()) {
            logger.error("updateEmployee: RequestBody has invalid information");
            return new ResponseEntity<>("Invalid information in fields", HttpStatus.BAD_REQUEST);
        }
        if (!EmployeeUtil.validateEmail(employeeDTO.getEmail())) {
            logger.error("updateEmployee: Bad email format");
            return new ResponseEntity<>("Bad email format", HttpStatus.BAD_REQUEST);
        }
        try {
            employeeService.updateEmployee(employeeDTO, id);
            logger.info("updateEmployee: Employee with email: " + employeeDTO.getEmail() + " updated successfully");
            return new ResponseEntity<>("Employee with email: " + employeeDTO.getEmail() + " updated successfully", HttpStatus.OK);
        } catch (ValidationException ex) {
            logger.error("updateEmployee: " + ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/updatePassword")
    public ResponseEntity<?> updateEmployeePassword(@RequestParam("email") String email, @RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword) {
        try {
            Employee employee = employeeService.updateEmployeePassoword(email, oldPassword, newPassword);
            logger.info("updateEmployeePassword: Password of user with email: " + employee.getEmail() + " successfully updated");
            return new ResponseEntity<>("updateEmployeePassword: Password of user with email: " + employee.getEmail() + " successfully updated", HttpStatus.OK);

        } catch (ValidationException ex) {
            logger.error("updateEmployeePassword: " + ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
