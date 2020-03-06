package com.arobs.internship.library.business.impl;

import com.arobs.internship.library.business.EmployeeService;
import com.arobs.internship.library.dao.EmployeeDao;
import com.arobs.internship.library.dao.factory.DaoFactory;
import com.arobs.internship.library.entities.Employee;
import com.arobs.internship.library.util.entities.EmployeeUtil;
import com.arobs.internship.library.util.handler.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private DaoFactory daoFactory;

    private EmployeeDao employeeDao;

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @PostConstruct
    public void init() {
        DaoFactory factory = daoFactory.getInstance();
        employeeDao = factory.getEmployeeDao();
    }


    @Override
    @Transactional
    public void insertEmployee(Employee employee) throws ValidationException {
        List<Employee> employees = this.findEmployees();
        for (Employee e : employees) {
            if (e.getEmail().equals(employee.getEmail())) {
                throw new ValidationException("Account associated with this email already exists");
            }
        }
        employee.setPassword(EmployeeUtil.passwordEncryption(employee.getPassword()));
        employeeDao.save(employee);
    }

    @Override
    @Transactional
    public List<Employee> findEmployees() {
        return employeeDao.findEmployees();
    }

    @Override
    @Transactional
    public Employee findEmployeeById(int id) {
        return employeeDao.findById(id);
    }

    @Override
    @Transactional
    public void updateEmployee(String email, String firstName, String lastName, int id) throws ValidationException {
        Employee employee = this.findEmployeeById(id);
        if (employee == null) {
            throw new ValidationException("No employee with this id found");
        }
        if (!email.equals(employee.getEmail()) || !firstName.equals(employee.getFirstName()) || !lastName.equals(employee.getLastName())) {
            employee.setEmail(email);
            employee.setFirstName(firstName);
            employee.setLastName(lastName);
        } else {
            throw new ValidationException("No updated fields");
        }
    }

    @Override
    @Transactional
    public void deleteEmployee(String email) throws ValidationException {
        boolean foundEmail = false;
        List<Employee> employees = this.findEmployees();
        for (Employee employee : employees) {
            if (employee.getEmail().equals(email)) {
                foundEmail = true;
                break;
            }
        }
        if (!foundEmail) {
            throw new ValidationException("Email is invalid");
        }
        employeeDao.delete(email);
    }


    @Override
    @Transactional
    public void updateEmployeePassoword(String email, String oldPassword, String newPassword) throws ValidationException {
        Employee employee = employeeDao.findByEmail(email);
        if (employee == null) {
            throw new ValidationException("Email is invalid");
        }
        if (oldPassword.equals(newPassword)) {
            throw new ValidationException("Old password equals new password");
        }
        String oldEncrypt = EmployeeUtil.passwordEncryption(oldPassword);
        if (!oldEncrypt.equals(employee.getPassword())) {
            throw new ValidationException("Old password does not correspond");
        }
        employee.setPassword(EmployeeUtil.passwordEncryption(newPassword));
    }


}
