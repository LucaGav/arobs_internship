package com.arobs.internship.library.dao.impl.hibernate;

import com.arobs.internship.library.business.impl.BookServiceImpl;
import com.arobs.internship.library.dao.BookDao;
import com.arobs.internship.library.entities.Employee;
import com.arobs.internship.library.entities.book.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class HibernateBookDao implements BookDao {

    @Autowired
    private SessionFactory sessionFactory;

    private static final Logger logger = LoggerFactory.getLogger(HibernateBookDao.class);

    public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    @Override
    public int save(Book book) {
        Transaction transaction = null;
        try {
            Session session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.merge(book);
            transaction.commit();
            return 1;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                e.printStackTrace();
                logger.warn("Rollback");
            }
        }
        return 0;
    }

    @Override
    public List<Book> findBooks() {
        Transaction transaction = null;
        List<Book> books;
        try {
            Session session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery("FROM Book");
            books = query.getResultList();
            transaction.commit();
            return books;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                logger.warn("Rollback");
            }
        }
        return null;
    }


    @Override
    public Book findById(int id) {
        Transaction transaction = null;
        Book book;
        try {
            Session session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();
            book = session.get(Book.class, id);
            transaction.commit();
            return book;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                logger.warn("Rollback");
            }
        }
        return null;
    }

    @Override
    public int delete(String title, String author) {
        Transaction transaction = null;
        try {
            Session session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery("DELETE FROM Book where title =:title AND author =:author");
            query.setParameter("title", title);
            query.setParameter("author", author);
            query.executeUpdate();
            transaction.commit();
            return 1;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                logger.warn("Rollback");
            }
        }
        return 0;
    }

    @Override
    public int update(Book book) {
        return 0;
    }
}
