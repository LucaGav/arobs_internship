package com.arobs.internship.library.dao.impl.hibernate;

import com.arobs.internship.library.dao.CopyDao;
import com.arobs.internship.library.entities.book.Copy;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class HibernateCopyDao implements CopyDao {

    @Autowired
    private SessionFactory sessionFactory;

    private static final Logger logger = LoggerFactory.getLogger(HibernateBookDao.class);

    public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }


    @Override
    public void save(Copy copy) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(copy);
    }

    @Override
    public List<Copy> findCopies() {
        List<Copy> copies;
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Copy");
        copies = query.getResultList();
        return copies;
    }

    @Override
    public List<Copy> findByBookID(int bookId) {
        List<Copy> copies;
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Copy WHERE bookID =: bookID");
        query.setParameter("bookID", bookId);
        copies = query.getResultList();
        return copies;
    }

    @Override
    public Copy findById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.get(Copy.class, id);
    }

    @Override
    public void delete(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("DELETE FROM Copy where copyID =:id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

}
