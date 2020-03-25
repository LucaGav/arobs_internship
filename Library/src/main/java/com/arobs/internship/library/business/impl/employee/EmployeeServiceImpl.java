package com.arobs.internship.library.business.impl.employee;

import com.arobs.internship.library.business.BookRentService;
import com.arobs.internship.library.business.EmployeeService;
import com.arobs.internship.library.business.RentRequestService;
import com.arobs.internship.library.dao.EmployeeDao;
import com.arobs.internship.library.dao.factory.DaoFactory;
import com.arobs.internship.library.dtos.employee.EmployeeUpdateDTO;
import com.arobs.internship.library.entities.employee.Employee;
import com.arobs.internship.library.entities.operations.BookRent;
import com.arobs.internship.library.entities.operations.RentRequest;
import com.arobs.internship.library.util.entities.EmployeeUtil;
import com.arobs.internship.library.util.handler.ValidationException;
import com.arobs.internship.library.util.status.ActiveStatus;
import com.arobs.internship.library.util.status.BookRentStatus;
import com.arobs.internship.library.util.status.RentRequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private DaoFactory daoFactory;

    @Autowired
    private RentRequestService rentRequestService;

    @Autowired
    private BookRentService bookRentService;

    private EmployeeDao employeeDao;

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
    public Employee deleteEmployee(int id) throws ValidationException {
        Employee employee = this.findEmployeeById(id);
        if (employee == null) {
            throw new ValidationException("No employee with this id found");
        }
        validateEmployeeRemoval(id);
        employee.setStatus(ActiveStatus.INACTIVE.name());
        return employee;
    }

    private void validateEmployeeRemoval(int employeeID) throws ValidationException {
        boolean brFlag = true;
        boolean rrFlag = true;
        List<BookRent> rentsOfEmployee = bookRentService.findBookRentsByEmployeeID(employeeID);
        for (BookRent bookRent : rentsOfEmployee) {
            if (!bookRent.getStatus().equals(BookRentStatus.RETURNED.name())) {
                brFlag = false;
            }
        }
        List<RentRequest> rentRequestsOfEmployee = rentRequestService.findRentRequestsByEmployeeID(employeeID);
        for (RentRequest rentRequest : rentRequestsOfEmployee) {
            if (!(rentRequest.getStatus().equals(RentRequestStatus.DECLINED.name())
                    || rentRequest.getStatus().equals(RentRequestStatus.GRANTED.name()))) {
                rrFlag = false;
            }
        }
        if (!brFlag) {
            throw new ValidationException("Cannot delete employee, as he has unfinished book rents");
        }
        if (!rrFlag) {
            throw new ValidationException("Cannot delete employee, as he has unfinished rent requests");
        }
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
