package com.arobs.internship.library.business;

import com.arobs.internship.library.entities.operations.BookRent;
import com.arobs.internship.library.util.handler.ValidationException;

import java.util.List;

public interface BookRentService {

    BookRent insertBookRent(BookRent bookRent) throws ValidationException;

    List<BookRent> findBookRents();

    BookRent findBookRentById(int id);

    BookRent findBookRentByEmployeeAndBookID(int employeeID, int bookID);

    List<BookRent> findBookRentsByBookID(int bookID);

    List<BookRent> findBookRentsByEmployeeID(int employeeID);

    BookRent updateBookRentOnReturn(float grade, int id) throws ValidationException;

    BookRent updateBookRentOnExtension(int id) throws ValidationException;

    void checkLateBookRents();

}
