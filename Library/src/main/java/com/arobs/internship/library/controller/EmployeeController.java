package com.arobs.internship.library.controller;

import com.arobs.internship.library.business.EmployeeService;
import com.arobs.internship.library.dtos.EmployeeDTO;
import com.arobs.internship.library.entities.Employee;
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
    public ResponseEntity<String> addEmployee(@RequestBody @Valid EmployeeDTO employeeDTO) {
        Employee employee = employeeService.dtoToEmployee(employeeDTO);
        employeeService.insertEmployee(employee);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/employees")
    public List<EmployeeDTO> findEmployees() {
        return employeeService.findEmployees();
    }

    @GetMapping
    public ResponseEntity<EmployeeDTO> getEmployee(@RequestParam("employeeID") int id) {
        Employee employee = employeeService.findEmployeeById(id);
        if (employee == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(employeeService.employeeToDto(employee), HttpStatus.OK);
    }

    @DeleteMapping("/deleteEmployee")
    public ResponseEntity<String> deleteEmployee(@RequestParam("email") String email) {
        if (employeeService.deleteEmployee(email) == 0) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/updateEmployee/{id}")
    public ResponseEntity<String> updateEmployee(@RequestParam("email") String email, @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
                                                 @PathVariable int id) {
        EmployeeDTO employeeDTO= employeeService.updateEmployee(email,firstName,lastName,id);
        if(employeeDTO == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
