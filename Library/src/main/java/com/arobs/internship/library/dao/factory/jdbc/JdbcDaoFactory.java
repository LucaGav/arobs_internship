package com.arobs.internship.library.dao.factory.jdbc;

import com.arobs.internship.library.dao.*;
import com.arobs.internship.library.dao.impl.jdbc.JdbcEmployeeDao;
import com.arobs.internship.library.dao.factory.DaoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class JdbcDaoFactory extends DaoFactory {

    @Autowired
    private JdbcEmployeeDao employeeDao;

    @Override
    public EmployeeDao getEmployeeDao() {
        return employeeDao;
    }

    @Override
    public BookDao getBookDao() {
        return null;
    }

    @Override
    public TagDao getTagDao() {
        return null;
    }

    @Override
    public CopyDao getCopyDao() {
        return null;
    }

    @Override
    public BookRequestDao getBookRequestDao() {
        return null;
    }

    @Override
    public BookRentDao getBookRentDao() {
        return null;
    }

    @Override
    public RentRequestDao getRentRequestDao() {
        return null;
    }


}
