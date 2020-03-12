package com.arobs.internship.library.dao.factory.hibernate;

import com.arobs.internship.library.dao.*;
import com.arobs.internship.library.dao.factory.DaoFactory;
import com.arobs.internship.library.dao.impl.hibernate.*;
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

    @Autowired
    private HibernateCopyDao copyDao;

    @Autowired
    private HibernateBookRequestDao bookRequestDao;

    @Autowired
    private HibernateBookRentDao bookRentDao;

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

    @Override
    public CopyDao getCopyDao() {
        return copyDao;
    }

    @Override
    public BookRequestDao getBookRequestDao() {
        return bookRequestDao;
    }

    @Override
    public BookRentDao getBookRentDao() {
        return bookRentDao;
    }

}
