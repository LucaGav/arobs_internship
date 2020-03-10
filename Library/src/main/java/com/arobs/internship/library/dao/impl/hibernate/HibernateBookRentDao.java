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
    public void insert(BookRent bookRent) {

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
    public void delete(int id) {

    }
}
