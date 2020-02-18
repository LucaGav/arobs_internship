package com.arobs.internship.library.dao.factory;

import com.arobs.internship.library.dao.EmployeeDao;
import com.arobs.internship.library.dao.JdbcEmployeeDao;

public class JdbcDaoFactory extends DaoFactory {

    @Override
    public EmployeeDao getEmployeeDao() {
        return new JdbcEmployeeDao();
    }
}
