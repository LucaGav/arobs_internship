package com.arobs.internship.library.business.impl;

import com.arobs.internship.library.business.EmployeeService;
import com.arobs.internship.library.dao.EmployeeDao;
import com.arobs.internship.library.dao.factory.DaoFactory;
import com.arobs.internship.library.dtos.EmployeeDTO;
import com.arobs.internship.library.entities.Employee;
import com.arobs.internship.library.util.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DaoFactory daoFactory;

    private EmployeeDao employeeDao;

    @PostConstruct
    public void init(){
        DaoFactory factory = daoFactory.getInstance();
        employeeDao = factory.getEmployeeDao();
    }


    @Override
    public void insertEmployee(Employee employee) {
        employeeDao.save(employee);
    }

    @Override
    public List<EmployeeDTO> findEmployees() {
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        List<Employee> employees = employeeDao.findEmployees();
        for(Employee e: employees){
            EmployeeDTO dto = employeeToDto(e);
            employeeDTOS.add(dto);
        }
        return employeeDTOS;
    }

    @Override
    public Employee findEmployeeById(int id) {
        Employee employee = employeeDao.findById(id);
        return employee;
    }

    @Override
    public void updateEmployee(Employee employee) {
        employeeDao.update(employee);
    }

    @Override
    public int deleteEmployee(String email) {
        return employeeDao.delete(email);
    }

    @Override
    public Employee dtoToEmployee(EmployeeDTO employeeDTO) {
        ModelMapper modelMapper = objectMapper.getMapper();
        Employee employee = modelMapper.map(employeeDTO, Employee.class);
        return employee;
    }

    @Override
    public EmployeeDTO employeeToDto(Employee employee) {
        ModelMapper modelMapper = objectMapper.getMapper();
        EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
        return employeeDTO;
    }
}
