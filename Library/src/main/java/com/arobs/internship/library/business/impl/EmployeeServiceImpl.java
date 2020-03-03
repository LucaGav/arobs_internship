package com.arobs.internship.library.business.impl;

import com.arobs.internship.library.business.EmployeeService;
import com.arobs.internship.library.dao.EmployeeDao;
import com.arobs.internship.library.dao.factory.DaoFactory;
import com.arobs.internship.library.dtos.EmployeeDTO;
import com.arobs.internship.library.entities.Employee;
import com.arobs.internship.library.handler.ValidationException;
import com.arobs.internship.library.util.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private ObjectMapper objectMapper;

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
        for(Employee e : employees){
            if(e.getEmail().equals(employee.getEmail())){
                throw new ValidationException("Account associated with this email already exists");
            }
        }
        employee.setPassword(this.passwordEncryption(employee.getPassword()));
        employeeDao.save(employee);
    }

    @Override
    @Transactional
    public List<Employee> findEmployees() {
        List<Employee> employees = employeeDao.findEmployees();
        return employees;
    }

    @Override
    @Transactional
    public Employee findEmployeeById(int id) throws ValidationException {
        Employee employee = employeeDao.findById(id);
        if(employee == null){
            throw new ValidationException("No employee with id: " + id + " found in the database");
        }
        return employee;
    }

    @Override
    @Transactional
    public void updateEmployee(String email, String firstName, String lastName, int id) throws ValidationException {
        Employee employee = this.findEmployeeById(id);
        if(employee == null){
            throw new ValidationException("No employee with this id found");
        }
        if (!email.equals(employee.getEmail()) || !firstName.equals(employee.getFirstName()) || !lastName.equals(employee.getLastName())) {
            employee.setEmail(email);
            employee.setFirstName(firstName);
            employee.setLastName(lastName);
        }
        else {
            throw new ValidationException("No updated fields");
        }
    }

    @Override
    @Transactional
    public void deleteEmployee(String email) throws ValidationException {
        boolean foundEmail = false;
        List<Employee> employees = this.findEmployees();
        for(Employee employee: employees){
            if(employee.getEmail().equals(email)){
                foundEmail = true;
                break;
            }
        }
        if(!foundEmail){
            throw new ValidationException("Email is invalid");
        }
        employeeDao.delete(email);
    }


    @Override
    @Transactional
    public void updateEmployeePassoword(String email, String oldPassword, String newPassword) throws ValidationException {
        Employee employee = employeeDao.findByEmail(email);
        if(employee == null){
            throw new ValidationException("Email is invalid");
        }
        if(oldPassword.equals(newPassword)){
            throw new ValidationException("Old password equals new password");
        }
        String oldEncrypt = this.passwordEncryption(oldPassword);
        if(!oldEncrypt.equals(employee.getPassword())){
            throw new ValidationException("Old password does not correspond");
        }
        employee.setPassword(this.passwordEncryption(newPassword));
    }

    @Override
    public Employee dtoToEmployee(EmployeeDTO employeeDTO) {
        ModelMapper modelMapper = objectMapper.getMapper();
        return modelMapper.map(employeeDTO, Employee.class);
    }

    @Override
    public EmployeeDTO employeeToDto(Employee employee) {
        ModelMapper modelMapper = objectMapper.getMapper();
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    @Override
    public List<EmployeeDTO> listEmployeeToDto(List<Employee> employees){
        ModelMapper modelMapper = objectMapper.getMapper();
        EmployeeDTO employeeDTO;
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        for(Employee employee: employees){
            employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
            employeeDTOS.add(employeeDTO);
        }
        return employeeDTOS;
    }

    private String passwordEncryption(String password) {
        MessageDigest messageDigest = null;
        try{
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        assert messageDigest != null;
        byte[] hashInBytes = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
