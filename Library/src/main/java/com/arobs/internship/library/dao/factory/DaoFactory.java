package com.arobs.internship.library.dao.factory;

import com.arobs.internship.library.dao.*;
import com.arobs.internship.library.dao.factory.hibernate.HibernateDaoFactory;
import com.arobs.internship.library.dao.factory.jdbc.JdbcDaoFactory;
import com.arobs.internship.library.entities.operations.BookRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public abstract class DaoFactory {
    @Value("${datasource.type}")
    private String type;

    @Autowired
    private HibernateDaoFactory hibernateDaoFactory;

    @Autowired
    private JdbcDaoFactory jdbcDaoFactory;


    public String getType() {
        return type;
    }


    public DaoFactory() {

    }

    public DaoFactory getInstance() {
        switch (getType()) {
            case "HIBERNATE":
                return hibernateDaoFactory;
            case "JDBCTemplate":
                return jdbcDaoFactory;
            default:
                throw new IllegalArgumentException("Invalid factory");
        }
    }


    public abstract EmployeeDao getEmployeeDao();

    public abstract BookDao getBookDao();

    public abstract TagDao getTagDao();

    public abstract CopyDao getCopyDao();

    public abstract BookRequestDao getBookRequestDao();
}
