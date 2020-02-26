package com.arobs.internship.library.dao.factory.hibernate;

import com.arobs.internship.library.dao.BookDao;
import com.arobs.internship.library.dao.EmployeeDao;
import com.arobs.internship.library.dao.TagDao;
import com.arobs.internship.library.dao.factory.DaoFactory;
import com.arobs.internship.library.dao.impl.hibernate.HibernateBookDao;
import com.arobs.internship.library.dao.impl.hibernate.HibernateEmployeeDao;
import com.arobs.internship.library.dao.impl.hibernate.HibernateTagDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HibernateDaoFactory extends DaoFactory {

    @Autowired
    private HibernateEmployeeDao employeeDao;

    @Autowired
    private HibernateBookDao bookDao;

    @Autowired
    private HibernateTagDao tagDao;

    @Override
    public EmployeeDao getEmployeeDao() {
        return employeeDao;
    }

    @Override
    public BookDao getBookDao() {
        return bookDao;
    }

    @Override
    public TagDao getTagDao() {
        return tagDao;
    }

}
