package com.arobs.internship.library.dao.impl.hibernate;

import com.arobs.internship.library.dao.BookRentDao;
import com.arobs.internship.library.entities.operations.BookRent;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HibernateBookRentDao implements BookRentDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public BookRent insert(BookRent bookRent) {
        return null;
    }

    @Override
    public List<BookRent> findBookRents() {
        return null;
    }

    @Override
    public BookRent findById(int id) {
        return null;
    }

    @Override
    public int delete(int id) {
        return 0;
    }
}
