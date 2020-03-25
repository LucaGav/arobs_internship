package com.arobs.internship.library.dao.impl.hibernate.book;

import com.arobs.internship.library.dao.BookDao;
import com.arobs.internship.library.dao.impl.hibernate.util.QueryUtil;
import com.arobs.internship.library.entities.book.Book;
import com.arobs.internship.library.util.status.ActiveStatus;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Repository
public class HibernateBookDao implements BookDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Book insert(Book book) {
        Session session = this.sessionFactory.getCurrentSession();
        book.setAddedDate(new Date());
        book.setStatus(ActiveStatus.ACTIVE.name());
        session.persist(book);
        return book;
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
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Book b LEFT JOIN FETCH b.tags WHERE b.bookID =: id").setMaxResults(1);
        query.setParameter("id", id);
        return QueryUtil.safeGetUniqueResult(query.getResultList());
    }
}
