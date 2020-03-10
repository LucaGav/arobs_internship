package com.arobs.internship.library.business;


import com.arobs.internship.library.dtos.employee.EmployeeDTO;
import com.arobs.internship.library.dtos.employee.EmployeeUpdateDTO;
import com.arobs.internship.library.entities.Employee;
import com.arobs.internship.library.util.handler.ValidationException;

import java.util.List;

public interface EmployeeService {

    void insertEmployee(Employee employee) throws ValidationException;

    List<Employee> findEmployees();

    Employee findEmployeeById(int id);

    void updateEmployee(EmployeeUpdateDTO employeeDTO, int id) throws ValidationException;

    void deleteEmployee(String email) throws ValidationException;

    void updateEmployeePassoword(String email, String oldPassword, String newPassword) throws ValidationException;
}
