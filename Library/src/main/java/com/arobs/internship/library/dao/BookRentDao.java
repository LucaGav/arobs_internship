package com.arobs.internship.library.dao;

import com.arobs.internship.library.entities.operations.BookRent;

import java.util.List;

public interface BookRentDao {

    void insert(BookRent bookRent);

    List<BookRent> findBookRents();

    BookRent findById(int id);

    void delete(int id);
}
