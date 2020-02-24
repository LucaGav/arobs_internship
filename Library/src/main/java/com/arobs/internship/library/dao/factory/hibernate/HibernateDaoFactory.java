package com.arobs.internship.library.dao.factory.hibernate;

import com.arobs.internship.library.dao.EmployeeDao;
import com.arobs.internship.library.dao.factory.DaoFactory;
import com.arobs.internship.library.dao.impl.hibernate.HibernateEmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HibernateDaoFactory extends DaoFactory {

    @Autowired
    private HibernateEmployeeDao employeeDao;

    @Override
    public EmployeeDao getEmployeeDao() {
        return employeeDao;
    }


}
