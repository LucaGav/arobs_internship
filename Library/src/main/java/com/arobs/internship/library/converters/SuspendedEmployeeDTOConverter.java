package com.arobs.internship.library.converters;


import com.arobs.internship.library.dtos.employee.SuspendedEmployeeDTO;
import com.arobs.internship.library.entities.employee.SuspendedEmployee;

import java.util.List;

public interface SuspendedEmployeeDTOConverter {

    List<SuspendedEmployeeDTO> listSuspendedEmployeeToDTO(List<SuspendedEmployee> suspendedEmployees);
}
