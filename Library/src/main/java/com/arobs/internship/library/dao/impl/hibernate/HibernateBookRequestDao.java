package com.arobs.internship.library.dao.impl.hibernate;

import com.arobs.internship.library.dao.BookRequestDao;
import com.arobs.internship.library.entities.operations.BookRequest;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class HibernateBookRequestDao implements BookRequestDao {

    @Autowired
    private SessionFactory sessionFactory;

    private static final Logger logger = LoggerFactory.getLogger(HibernateBookDao.class);

    @Override
    public BookRequest insert(BookRequest bookRequest) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(bookRequest);
        return bookRequest;
    }

    @Override
    public List<BookRequest> findBookRequests() {
        List<BookRequest> bookRequests;
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM BookRequest");
        bookRequests = query.getResultList();
        return bookRequests;
    }

    @Override
    public BookRequest findById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.get(BookRequest.class, id);
    }

    @Override
    public int delete(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("DELETE FROM BookRequest where bookreqID =:id");
        query.setParameter("id", id);
        return query.executeUpdate();
    }

}
