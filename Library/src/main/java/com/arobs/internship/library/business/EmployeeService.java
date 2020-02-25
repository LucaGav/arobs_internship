package com.arobs.internship.library.business;


import com.arobs.internship.library.dtos.EmployeeDTO;
import com.arobs.internship.library.entities.Employee;

import java.util.List;

public interface EmployeeService {
    void insertEmployee(Employee employee);

    List<EmployeeDTO> findEmployees();

    Employee findEmployeeById(int id);

    EmployeeDTO updateEmployee(String email,String firstName, String lastName, int id);

    int deleteEmployee(String email);

    Employee dtoToEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO employeeToDto(Employee employee);


}
