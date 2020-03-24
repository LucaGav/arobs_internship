package com.arobs.internship.library.dao;

import com.arobs.internship.library.entities.operations.BookRequest;

import java.util.List;

public interface BookRequestDao {

    BookRequest insert(BookRequest bookRequest);

    List<BookRequest> findBookRequests();

    BookRequest findById(int id);

    int delete(int id);

}
