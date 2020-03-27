package com.arobs.internship.library.dao;

import com.arobs.internship.library.entities.employee.SuspendedEmployee;

import java.util.List;

public interface SuspendedEmployeeDao {

    SuspendedEmployee insert(SuspendedEmployee suspendedEmployee);

    List<SuspendedEmployee> findLateEmployees();

    List<SuspendedEmployee> findSuspendedEmployees();

    SuspendedEmployee findByEmployeeID(int employeeID);

    int delete(int id);
}
