package com.arobs.internship.library.dao.impl.hibernate.operations;

import com.arobs.internship.library.dao.BookRentDao;
import com.arobs.internship.library.dao.impl.hibernate.util.QueryUtil;
import com.arobs.internship.library.entities.operations.BookRent;
import com.arobs.internship.library.util.date.DateUtil;
import com.arobs.internship.library.util.status.BookRentStatus;
import com.arobs.internship.library.util.status.CopyStatus;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Repository
public class HibernateBookRentDao implements BookRentDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public BookRent insert(BookRent bookRent) {
        Session session = this.sessionFactory.getCurrentSession();
        bookRent.setStatus(BookRentStatus.ON_GOING.name());
        bookRent.setRentalDate(new Date());
        bookRent.setReturnDate(DateUtil.addDays(new Date(),30));
        session.save(bookRent);
        return bookRent;
    }

    @Override
    public List<BookRent> findBookRents() {
        List<BookRent> bookRents;
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM BookRent");
        bookRents = query.getResultList();
        return bookRents;

    }

    @Override
    public BookRent findById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.get(BookRent.class,id);
    }

    @Override
    public BookRent findByEmployeeAndBookId(int employeeID, int bookID) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("from BookRent WHERE employeeID =: id AND bookID =: id2");
        query.setParameter("id", employeeID);
        query.setParameter("id2", bookID);
        return QueryUtil.safeGetUniqueResult(query.getResultList());
    }

    @Override
    public int delete(int id) {
       Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("DELETE FROM BookRent where rentID =:id");
        query.setParameter("id", id);
        return query.executeUpdate();
    }
}
