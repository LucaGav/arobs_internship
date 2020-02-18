package com.arobs.internship.library.dao;

import com.arobs.internship.library.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class JdbcEmployeeDao implements EmployeeDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int save() {
        return jdbcTemplate.update("insert into user (username,password) values(?,?)",
                "paolo","aolo");
    }
}
