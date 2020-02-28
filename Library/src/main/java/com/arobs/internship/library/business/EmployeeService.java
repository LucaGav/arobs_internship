package com.arobs.internship.library.business;


import com.arobs.internship.library.dtos.EmployeeDTO;
import com.arobs.internship.library.entities.Employee;
import com.arobs.internship.library.handler.CustomException;

import java.util.List;

public interface EmployeeService {
    void insertEmployee(Employee employee) throws CustomException;

    List<EmployeeDTO> findEmployees();

    EmployeeDTO findEmployeeById(int id) throws CustomException;

    void updateEmployee(String email, String firstName, String lastName, int id) throws CustomException;

    void deleteEmployee(String email) throws CustomException;

    Employee dtoToEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO employeeToDto(Employee employee);

    void updateEmployeePassoword(String email, String oldPassword, String newPassword) throws CustomException;
}
