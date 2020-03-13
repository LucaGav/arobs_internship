package com.arobs.internship.library.controller;

import com.arobs.internship.library.business.EmployeeService;
import com.arobs.internship.library.converters.EmployeeDTOConverter;
import com.arobs.internship.library.entities.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

    @InjectMocks
    private EmployeeController employeeController;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private EmployeeDTOConverter employeeDTOConverter;

    private List<Employee> employeeList;

    @BeforeEach
    public void setUp() {
        employeeList = new ArrayList<>();
        Employee employee = new Employee(1,"Luca","Gavril","ADMIN","1234","pritata@gmail.com");
        Employee employee1 = new Employee(2,"Luca","Gfavril","ADMIN","1234","postup@gmail.com");
        employeeList.add(employee);
        employeeList.add(employee1);
    }

    @Test
    public void whenFindEmployees_given_returnResponseEntity(){

        when(employeeService.findEmployees()).thenReturn(employeeList);
        ResponseEntity responseEntity = employeeController.findEmployees();
        System.out.println(responseEntity.getStatusCodeValue());
        assertEquals(responseEntity.getStatusCodeValue(),200);

    }
}
