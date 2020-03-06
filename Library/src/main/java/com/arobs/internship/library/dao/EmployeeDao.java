package com.arobs.internship.library.dao;

import com.arobs.internship.library.entities.Employee;

import java.util.List;

public interface EmployeeDao {

    void save(Employee employee);

    List<Employee> findEmployees();

    Employee findById(int id);

    Employee findByEmail(String email);

    void delete(String email);

    void update(Employee employee);
}
