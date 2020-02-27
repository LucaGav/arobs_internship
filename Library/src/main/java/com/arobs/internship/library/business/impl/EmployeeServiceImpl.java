package com.arobs.internship.library.business.impl;

import com.arobs.internship.library.business.EmployeeService;
import com.arobs.internship.library.dao.EmployeeDao;
import com.arobs.internship.library.dao.factory.DaoFactory;
import com.arobs.internship.library.dtos.EmployeeDTO;
import com.arobs.internship.library.entities.Employee;
import com.arobs.internship.library.handler.MyCustomException;
import com.arobs.internship.library.util.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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
    public void insertEmployee(Employee employee) {
        List<EmployeeDTO> employees = this.findEmployees();
        for(EmployeeDTO e : employees){
            if(e.getEmail().equals(employee.getEmail())){
                throw new MyCustomException("Account associated with this email already exists");
            }
        }
        employee.setPassword(this.passwordEncryption(employee.getPassword()));
        employeeDao.save(employee);
    }

    @Override
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
    public EmployeeDTO findEmployeeById(int id) {
        Employee employee = employeeDao.findById(id);
        if(employee == null){
            throw new MyCustomException("No employee with id: " + id + " found in the database");
        }
        return this.employeeToDto(employee);
    }

    @Override
    public void updateEmployee(String email, String firstName, String lastName, int id) {
        EmployeeDTO employeeDTO = this.findEmployeeById(id);
        if (!email.equals(employeeDTO.getEmail()) || !firstName.equals(employeeDTO.getFirstName()) || !lastName.equals(employeeDTO.getLastName())) {
            employeeDTO.setEmail(email);
            employeeDTO.setFirstName(firstName);
            employeeDTO.setLastName(lastName);
            Employee employee = this.dtoToEmployee(employeeDTO);
            employeeDao.update(employee);
        }
    }

    @Override
    public void deleteEmployee(String email) {
        boolean foundEmail = false;
        List<EmployeeDTO> employees = this.findEmployees();
        for(EmployeeDTO employeeDTO: employees){
            if(employeeDTO.getEmail().equals(email)){
                foundEmail = true;
                break;
            }
        }
        if(!foundEmail){
            throw new MyCustomException("Email is invalid");
        }
        employeeDao.delete(email);
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
    public void updateEmployeePassoword(String email, String oldPassword, String newPassword) {
        Employee employee = employeeDao.findByEmail(email);
        if(employee == null){
            throw new MyCustomException("Email is invalid");
        }
        if(oldPassword.equals(newPassword)){
            throw new MyCustomException("Old password equals new password");
        }
        String oldEncrypt = this.passwordEncryption(oldPassword);
        if(!oldEncrypt.equals(employee.getPassword())){
            throw new MyCustomException("Old password does not correspond");
        }
        employee.setPassword(this.passwordEncryption(newPassword));
        employeeDao.update(employee);
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
