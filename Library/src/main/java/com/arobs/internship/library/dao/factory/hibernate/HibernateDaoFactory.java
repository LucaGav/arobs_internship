package com.arobs.internship.library.dao.factory.hibernate;

import com.arobs.internship.library.dao.*;
import com.arobs.internship.library.dao.factory.DaoFactory;
import com.arobs.internship.library.dao.impl.hibernate.auxiliary.HibernatePendingRequestDao;
import com.arobs.internship.library.dao.impl.hibernate.book.HibernateBookDao;
import com.arobs.internship.library.dao.impl.hibernate.book.HibernateCopyDao;
import com.arobs.internship.library.dao.impl.hibernate.book.HibernateTagDao;
import com.arobs.internship.library.dao.impl.hibernate.employee.HibernateEmployeeDao;
import com.arobs.internship.library.dao.impl.hibernate.employee.HibernateSuspendedEmployeeDao;
import com.arobs.internship.library.dao.impl.hibernate.operations.HibernateBookRentDao;
import com.arobs.internship.library.dao.impl.hibernate.operations.HibernateBookRequestDao;
import com.arobs.internship.library.dao.impl.hibernate.operations.HibernateRentRequestDao;
import com.arobs.internship.library.dao.impl.hibernate.statistics.HibernateStatisticsDao;
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

    @Autowired
    private HibernateRentRequestDao rentRequestDao;

    @Autowired
    private HibernatePendingRequestDao pendingRequestDao;

    @Autowired
    private HibernateSuspendedEmployeeDao suspendedEmployeeDao;

    @Autowired
    private HibernateStatisticsDao statisticsDao;

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

    @Override
    public RentRequestDao getRentRequestDao() {
        return rentRequestDao;
    }

    @Override
    public PendingRequestDao getPendingRequestDao() {
        return pendingRequestDao;
    }

    @Override
    public SuspendedEmployeeDao getSuspendedEmployeeDao() {
        return suspendedEmployeeDao;
    }

    @Override
    public StatisticsDao getStatisticsDao() {
        return statisticsDao;
    }
}
