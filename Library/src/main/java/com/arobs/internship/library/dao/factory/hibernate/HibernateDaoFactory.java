package com.arobs.internship.library.dao.factory.hibernate;

import com.arobs.internship.library.dao.EmployeeDao;
import com.arobs.internship.library.dao.factory.DaoFactory;
import org.springframework.stereotype.Component;

@Component
public class HibernateDaoFactory extends DaoFactory {

    @Override
    public EmployeeDao getEmployeeDao() {
        return null;
    }
}
