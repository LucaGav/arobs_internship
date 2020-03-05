package com.arobs.internship.library.converters.impl;

import com.arobs.internship.library.converters.EmployeeDTOConverter;
import com.arobs.internship.library.dtos.EmployeeDTO;
import com.arobs.internship.library.entities.Employee;
import com.arobs.internship.library.util.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeDTOConverterImpl implements EmployeeDTOConverter {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Employee dtoToEmployee(EmployeeDTO employeeDTO) {
        ModelMapper modelMapper = objectMapper.getMapper();
        return modelMapper.map(employeeDTO, Employee.class);
    }

    @Override
    public EmployeeDTO employeeToDTO(Employee employee) {
        ModelMapper modelMapper = objectMapper.getMapper();
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    @Override
    public List<EmployeeDTO> listEmployeeToDTO(List<Employee> employees) {
        ModelMapper modelMapper = objectMapper.getMapper();
        EmployeeDTO employeeDTO;
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        for (Employee employee : employees) {
            employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
            employeeDTOS.add(employeeDTO);
        }
        return employeeDTOS;
    }
}
