package com.arobs.internship.library.dao.impl.hibernate;

import com.arobs.internship.library.dao.BookDao;
import com.arobs.internship.library.entities.book.Book;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HibernateBookDao implements BookDao {
    @Override
    public int save(Book book) {
        return 0;
    }

    @Override
    public List<Book> findEmployees() {
        return null;
    }

    @Override
    public Book findById(int id) {
        return null;
    }

    @Override
    public int delete(String email) {
        return 0;
    }

    @Override
    public int update(Book book) {
        return 0;
    }
}
