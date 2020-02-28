package com.arobs.internship.library.business.impl;

import com.arobs.internship.library.business.EmployeeService;
import com.arobs.internship.library.dao.EmployeeDao;
import com.arobs.internship.library.dao.factory.DaoFactory;
import com.arobs.internship.library.dtos.EmployeeDTO;
import com.arobs.internship.library.entities.Employee;
import com.arobs.internship.library.handler.CustomException;
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
    public void insertEmployee(Employee employee) throws CustomException {
        List<EmployeeDTO> employees = this.findEmployees();
        for(EmployeeDTO e : employees){
            if(e.getEmail().equals(employee.getEmail())){
                throw new CustomException("Account associated with this email already exists");
            }
        }
        employee.setPassword(this.passwordEncryption(employee.getPassword()));
        employeeDao.save(employee);
    }

    @Override
    @Transactional
    public List<EmployeeDTO> findEmployees() {
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        List<Employee> employees = employeeDao.findEmployees();
        for (Employee e : employees) {
            EmployeeDTO dto = employeeToDto(e);
            employeeDTOS.add(dto);
        }
        return employeeDTOS;
    }

    @Override
    @Transactional
    public EmployeeDTO findEmployeeById(int id) throws CustomException {
        Employee employee = employeeDao.findById(id);
        if(employee == null){
            throw new CustomException("No employee with id: " + id + " found in the database");
        }
        return this.employeeToDto(employee);
    }

    @Override
    @Transactional
    public void updateEmployee(String email, String firstName, String lastName, int id) throws CustomException {
        EmployeeDTO employeeDTO = this.findEmployeeById(id);
        if(employeeDTO == null){
            throw new CustomException("No employee with this id found");
        }
        if (!email.equals(employeeDTO.getEmail()) || !firstName.equals(employeeDTO.getFirstName()) || !lastName.equals(employeeDTO.getLastName())) {
            employeeDTO.setEmail(email);
            employeeDTO.setFirstName(firstName);
            employeeDTO.setLastName(lastName);
            Employee employee = this.dtoToEmployee(employeeDTO);
            employee.setEmployeeID(id);
            employeeDao.update(employee);
        }
        else {
            throw new CustomException("No updated fields");
        }
    }

    @Override
    @Transactional
    public void deleteEmployee(String email) throws CustomException {
        boolean foundEmail = false;
        List<EmployeeDTO> employees = this.findEmployees();
        for(EmployeeDTO employeeDTO: employees){
            if(employeeDTO.getEmail().equals(email)){
                foundEmail = true;
                break;
            }
        }
        if(!foundEmail){
            throw new CustomException("Email is invalid");
        }
        employeeDao.delete(email);
    }


    @Override
    @Transactional
    public void updateEmployeePassoword(String email, String oldPassword, String newPassword) throws CustomException {
        Employee employee = employeeDao.findByEmail(email);
        if(employee == null){
            throw new CustomException("Email is invalid");
        }
        if(oldPassword.equals(newPassword)){
            throw new CustomException("Old password equals new password");
        }
        String oldEncrypt = this.passwordEncryption(oldPassword);
        if(!oldEncrypt.equals(employee.getPassword())){
            throw new CustomException("Old password does not correspond");
        }
        employee.setPassword(this.passwordEncryption(newPassword));
        employeeDao.update(employee);
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
