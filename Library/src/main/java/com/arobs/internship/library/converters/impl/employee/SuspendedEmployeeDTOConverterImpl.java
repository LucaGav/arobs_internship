package com.arobs.internship.library.converters.impl.employee;

import com.arobs.internship.library.converters.SuspendedEmployeeDTOConverter;
import com.arobs.internship.library.dtos.employee.SuspendedEmployeeDTO;
import com.arobs.internship.library.entities.employee.SuspendedEmployee;
import com.arobs.internship.library.util.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SuspendedEmployeeDTOConverterImpl implements SuspendedEmployeeDTOConverter {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<SuspendedEmployeeDTO> listSuspendedEmployeeToDTO(List<SuspendedEmployee> suspendedEmployees) {
        ModelMapper modelMapper = objectMapper.getMapper();
        SuspendedEmployeeDTO suspendedEmployeeDTO = new SuspendedEmployeeDTO();
        List<SuspendedEmployeeDTO> suspendedEmployeeDTOS = new ArrayList<>();
        for (SuspendedEmployee suspendedEmployee : suspendedEmployees) {
            suspendedEmployeeDTO.setEmail(suspendedEmployee.getEmployee().getEmail());
            suspendedEmployeeDTOS.add(suspendedEmployeeDTO);
        }
        return suspendedEmployeeDTOS;
    }
}
