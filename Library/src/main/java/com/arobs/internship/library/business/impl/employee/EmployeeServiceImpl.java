package com.arobs.internship.library.business.impl.employee;

import com.arobs.internship.library.business.EmployeeService;
import com.arobs.internship.library.dao.EmployeeDao;
import com.arobs.internship.library.dao.factory.DaoFactory;
import com.arobs.internship.library.dtos.employee.EmployeeUpdateDTO;
import com.arobs.internship.library.entities.employee.Employee;
import com.arobs.internship.library.util.entities.EmployeeUtil;
import com.arobs.internship.library.util.handler.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
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
    public Employee insertEmployee(Employee employee) throws ValidationException {
        List<Employee> employees = this.findEmployees();
        for (Employee e : employees) {
            if (e.getEmail().equals(employee.getEmail())) {
                throw new ValidationException("Account associated with this email already exists");
            }
        }
        return employeeDao.insert(employee);
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
    public Employee updateEmployee(EmployeeUpdateDTO employeeDTO, int id) throws ValidationException {
        Employee employee = this.findEmployeeById(id);
        if (employee == null) {
            throw new ValidationException("No employee with this id found");
        }
        if (!employeeDTO.getEmail().equals(employee.getEmail()) || !employeeDTO.getFirstName().equals(employee.getFirstName())
                || !employeeDTO.getLastName().equals(employee.getLastName())) {
            employee.setEmail(employeeDTO.getEmail());
            employee.setFirstName(employeeDTO.getFirstName());
            employee.setLastName(employeeDTO.getLastName());
        } else {
            throw new ValidationException("No updated fields");
        }
        return employee;
    }

    @Override
    @Transactional
    public int deleteEmployee(String email){
        return employeeDao.delete(email);
    }


    @Override
    @Transactional
    public Employee updateEmployeePassoword(String email, String oldPassword, String newPassword) throws ValidationException {
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
        return employee;
    }
}
