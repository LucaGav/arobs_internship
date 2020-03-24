package com.arobs.internship.library.business;

import com.arobs.internship.library.entities.employee.Employee;
import com.arobs.internship.library.entities.employee.SuspendedEmployee;

import java.util.Date;
import java.util.List;

public interface SuspendedEmployeeService {

    SuspendedEmployee insertSuspendedEmployee(SuspendedEmployee suspendedEmployee);

    List<SuspendedEmployee> findSuspendedEmployees();

    SuspendedEmployee findSuspendedEmployeeByID(int id);

    SuspendedEmployee findSuspendedEmployeeByEmployeeID(int employeeID);

    void updateOnBookReturn(Employee employee, Date initialReturnDate);

    int deleteSuspendedEmployee(int id);

    void checkSuspensionDates();
}
