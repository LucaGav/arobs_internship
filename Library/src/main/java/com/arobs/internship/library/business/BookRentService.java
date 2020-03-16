package com.arobs.internship.library.business;

import com.arobs.internship.library.entities.operations.BookRent;

import java.util.List;

public interface BookRentService {

    BookRent insertBookRent(BookRent bookRent);

    List<BookRent> findBookRents();

    BookRent findBookRentById(int id);

    BookRent updateBookRent(float grade, String status, int id);

    int deleteBookRent(int id);

}
