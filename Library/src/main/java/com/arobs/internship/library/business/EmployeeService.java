package com.arobs.internship.library.business;


import com.arobs.internship.library.dtos.EmployeeDTO;
import com.arobs.internship.library.entities.Employee;
import com.arobs.internship.library.handler.ValidationException;

import java.util.List;

public interface EmployeeService {
    void insertEmployee(Employee employee) throws ValidationException;

    List<Employee> findEmployees();

    Employee findEmployeeById(int id) throws ValidationException;

    void updateEmployee(String email, String firstName, String lastName, int id) throws ValidationException;

    void deleteEmployee(String email) throws ValidationException;

    Employee dtoToEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO employeeToDto(Employee employee);

    List<EmployeeDTO> listEmployeeToDto(List<Employee> employees);

    void updateEmployeePassoword(String email, String oldPassword, String newPassword) throws ValidationException;
}
