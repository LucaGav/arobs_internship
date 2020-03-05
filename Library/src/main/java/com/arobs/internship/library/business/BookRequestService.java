package com.arobs.internship.library.business;

import com.arobs.internship.library.entities.operations.BookRequest;
import com.arobs.internship.library.util.handler.ValidationException;

import java.util.List;

public interface BookRequestService {

    void insertBookRequest(BookRequest bookRequest);

    List<BookRequest> findBookRequests();

    BookRequest findBookRequestById(int id);

    void updateBookRequest(String status, int id) throws ValidationException;

    void deleteBookRequest(int id) throws ValidationException;

}
