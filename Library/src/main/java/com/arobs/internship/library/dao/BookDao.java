package com.arobs.internship.library.dao;

import com.arobs.internship.library.entities.book.Book;

import java.util.List;

public interface BookDao {

    void save(Book book);

    List<Book> findBooks();

    Book findById(int id);

    void delete(String title, String author);

    void update(Book book);
}


