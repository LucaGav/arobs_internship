package com.arobs.internship.library.dao;

import com.arobs.internship.library.entities.book.Book;

import java.util.List;

public interface BookDao {
    int save(Book book);

    List<Book> findBooks();

    Book findById(int id);

    int delete(String email);

    int update(Book book);
}


