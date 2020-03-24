package com.arobs.internship.library.business;

import com.arobs.internship.library.entities.operations.BookRequest;
import com.arobs.internship.library.util.handler.ValidationException;

import java.util.List;

public interface BookRequestService {

    BookRequest insertBookRequest(BookRequest bookRequest);

    List<BookRequest> findBookRequests();

    BookRequest findBookRequestById(int id);

    BookRequest updateBookRequest(String status, int id) throws ValidationException;

    int deleteBookRequest(int id);

}
