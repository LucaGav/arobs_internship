package com.arobs.internship.library.dao;

import com.arobs.internship.library.entities.operations.BookRequest;

import java.util.List;

public interface BookRequestDao {

    void save(BookRequest bookRequest);

    List<BookRequest> findBookRequests();

    BookRequest findById(int id);

    void delete(int id);

}
