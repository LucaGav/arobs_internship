package com.arobs.internship.library.business.impl;

import com.arobs.internship.library.business.BookRentService;
import com.arobs.internship.library.dao.BookRentDao;
import com.arobs.internship.library.dao.factory.DaoFactory;
import com.arobs.internship.library.entities.operations.BookRent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class BookRentServiceImpl implements BookRentService {

    @Autowired
    private DaoFactory daoFactory;

    private BookRentDao bookRentDao;

    @PostConstruct
    public void init(){
        DaoFactory factory = daoFactory.getInstance();
        bookRentDao = factory.getBookRentDao();
    }

    @Override
    public void insertBookRent(BookRent bookRent) {

    }

    @Override
    public List<BookRent> findBookRents() {
        return null;
    }

    @Override
    public BookRent findBookRentById(int id) {
        return null;
    }

    @Override
    public void updateBookRent() {

    }

    @Override
    public void deleteBookRent(int id) {

    }
}
