package com.arobs.internship.library.dao.factory;

import com.arobs.internship.library.dao.EmployeeDao;
import com.arobs.internship.library.dao.factory.hibernate.HibernateDaoFactory;
import com.arobs.internship.library.dao.factory.jdbc.JdbcDaoFactory;
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
                System.out.println("Ajuns la hibernate");
                return hibernateDaoFactory;
            case "JDBCTemplate":
                return jdbcDaoFactory;
            default:
                throw new IllegalArgumentException("Invalid factory");
        }
    }


    public abstract EmployeeDao getEmployeeDao();
}
