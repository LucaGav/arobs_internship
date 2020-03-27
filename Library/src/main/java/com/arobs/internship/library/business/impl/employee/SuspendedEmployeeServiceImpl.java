package com.arobs.internship.library.business.impl.employee;

import com.arobs.internship.library.business.SuspendedEmployeeService;
import com.arobs.internship.library.dao.SuspendedEmployeeDao;
import com.arobs.internship.library.dao.factory.DaoFactory;
import com.arobs.internship.library.entities.employee.Employee;
import com.arobs.internship.library.entities.employee.SuspendedEmployee;
import com.arobs.internship.library.util.date.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class SuspendedEmployeeServiceImpl implements SuspendedEmployeeService {

    @Autowired
    private DaoFactory daoFactory;

    private SuspendedEmployeeDao suspendedEmployeeDao;

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @PostConstruct
    public void init() {
        DaoFactory factory = daoFactory.getInstance();
        suspendedEmployeeDao = factory.getSuspendedEmployeeDao();
    }

    @Override
    @Transactional
    public SuspendedEmployee insertSuspendedEmployee(SuspendedEmployee suspendedEmployee) {
        return suspendedEmployeeDao.insert(suspendedEmployee);
    }

    @Override
    @Transactional
    public List<SuspendedEmployee> findLateEmployees() {
        return suspendedEmployeeDao.findLateEmployees();
    }

    @Override
    @Transactional
    public List<SuspendedEmployee> findSuspendedEmployees() {
        return suspendedEmployeeDao.findSuspendedEmployees();
    }

    @Override
    @Transactional
    public SuspendedEmployee findSuspendedEmployeeByEmployeeID(int employeeID) {
        return suspendedEmployeeDao.findByEmployeeID(employeeID);
    }

    @Override
    @Transactional
    public void updateOnBookReturn(Employee employee, Date initialReturnDate) {
        SuspendedEmployee suspendedEmployee = suspendedEmployeeDao.findByEmployeeID(employee.getEmployeeID());
        if (suspendedEmployee != null) {
            Date oldSuspensionDate = suspendedEmployee.getSuspendedUntilDate();
            if (oldSuspensionDate != null) {
                suspendedEmployee.setSuspendedUntilDate(DateUtil.addDays(oldSuspensionDate, this.suspensionDateUpdate(initialReturnDate)));
            } else {
                suspendedEmployee.setSuspendedUntilDate(DateUtil.addDays(new Date(), this.suspensionDateUpdate(initialReturnDate)));
            }
        } else {
            logger.info("Employee was not late returning the book");
        }
    }

    @Override
    public int deleteSuspendedEmployee(int id) {
        return suspendedEmployeeDao.delete(id);
    }

    private int suspensionDateUpdate(Date initialReturnDate) {
        int daysLateReturn = DateUtil.getDaysBetween(initialReturnDate, new Date());
        if ((daysLateReturn * 2) < 10) {
            return 10;
        } else {
            return daysLateReturn * 2;
        }
    }

    @Override
    @Transactional
    public void checkSuspensionDates() {
        List<SuspendedEmployee> suspendedEmployees = this.findSuspendedEmployees();
        for (SuspendedEmployee se : suspendedEmployees) {
            if (null != se.getSuspendedUntilDate()) {
                if (DateUtil.compareDates(se.getSuspendedUntilDate(), new Date())) {
                    this.deleteSuspendedEmployee(se.getSuspendedemployeeID());
                }
            }
        }
    }
}
