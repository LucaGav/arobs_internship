package com.arobs.internship.library.dao.impl.hibernate;

import com.arobs.internship.library.dao.BookDao;
import com.arobs.internship.library.dao.impl.hibernate.util.QueryUtil;
import com.arobs.internship.library.entities.book.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
    public void save(Book book) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(book);
    }

    @Override
    public List<Book> findBooks() {
        List<Book> books;
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT b from Book b LEFT JOIN FETCH b.tags");
        books = query.getResultList();
        return books;
    }


    @Override
    public Book findById(int id) {
        Book book = null;
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT b from Book b LEFT JOIN FETCH b.tags WHERE b.bookID =: id");
        query.setParameter("id", id);
        return QueryUtil.safeGetUniqueResult(query.getResultList());
    }

    @Override
    public void delete(String title, String author) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("DELETE FROM Book where title =:title AND author =:author");
        query.setParameter("title", title);
        query.setParameter("author", author);
        query.executeUpdate();
    }
}
