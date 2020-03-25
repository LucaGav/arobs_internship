package com.arobs.internship.library.dao.impl.hibernate.statistics;

import com.arobs.internship.library.dao.StatisticsDao;
import com.arobs.internship.library.entities.statistics.TopReadingEmployee;
import com.arobs.internship.library.entities.statistics.TopRentedBook;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Repository
public class HibernateStatisticsDao implements StatisticsDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public List<TopRentedBook> getTopXRentedBooks(int top, Date startDate, Date endDate) {
        Session session = this.sessionFactory.getCurrentSession();
        List<TopRentedBook> topRentedBooks = new ArrayList<>();
        Query query = session.createQuery("SELECT br.book.title, br.book.author, COUNT(*) as c FROM BookRent br " +
                "JOIN Book b ON br.book.bookID = b.bookID WHERE br.rentalDate BETWEEN :startDate AND :endDate " +
                "GROUP BY b.bookID " +
                "ORDER BY c DESC").setMaxResults(top);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        List<Object[]> values = (List<Object[]>) query.getResultList();
        for (Object[] val : values) {
            TopRentedBook book = new TopRentedBook(val[0].toString(), val[1].toString(), Integer.parseInt(val[2].toString()));
            topRentedBooks.add(book);
        }
        return topRentedBooks;
    }

    @Override
    @Transactional
    public List<TopReadingEmployee> getTopXReadingEmployees(int top, Date startDate, Date endDate) {
        Session session = this.sessionFactory.getCurrentSession();
        List<TopReadingEmployee> topReadingEmployees = new ArrayList<>();
        Query query = session.createQuery("SELECT br.employee.email, br.employee.firstName, br.employee.lastName, COUNT(*) as c FROM BookRent br " +
                "JOIN Employee e ON br.employee.employeeID = e.employeeID WHERE br.returnDate BETWEEN :startDate AND :endDate " +
                "GROUP BY e.employeeID " +
                "ORDER BY c DESC").setMaxResults(top);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        List<Object[]> values = (List<Object[]>) query.getResultList();
        for (Object[] val : values) {
            TopReadingEmployee employee = new TopReadingEmployee(val[0].toString(), val[1].toString(), val[2].toString(), Integer.parseInt(val[3].toString()));
            topReadingEmployees.add(employee);
        }
        return topReadingEmployees;
    }
}