package com.arobs.internship.library.dao;

import com.arobs.internship.library.entities.Employee;

import java.util.List;

public interface EmployeeDao {
    int save(Employee employee);

    List<Employee> findEmployees();

    Employee findById(int id);

    Employee findByEmail(String email);

    int delete(String email);

    int update(Employee employee);
}
