package com.arobs.internship.library.business;

import com.arobs.internship.library.dtos.employee.EmployeeUpdateDTO;
import com.arobs.internship.library.entities.employee.Employee;
import com.arobs.internship.library.util.handler.ValidationException;

import java.util.List;

public interface EmployeeService {

    Employee insertEmployee(Employee employee) throws ValidationException;

    List<Employee> findEmployees();

    Employee findEmployeeById(int id);

    Employee updateEmployee(EmployeeUpdateDTO employeeDTO, int id) throws ValidationException;

    Employee deleteEmployee(int id) throws ValidationException;

    Employee updateEmployeePassoword(String email, String oldPassword, String newPassword) throws ValidationException;

}
