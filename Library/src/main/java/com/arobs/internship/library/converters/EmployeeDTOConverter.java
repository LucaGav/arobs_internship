package com.arobs.internship.library.converters;

import com.arobs.internship.library.dtos.EmployeeDTO;
import com.arobs.internship.library.entities.Employee;

import java.util.List;

public interface EmployeeDTOConverter {

    Employee dtoToEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO employeeToDTO(Employee employee);

    List<EmployeeDTO> listEmployeeToDTO(List<Employee> employees);
}
