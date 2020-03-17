package com.arobs.internship.library.dao;
import com.arobs.internship.library.entities.operations.BookRent;

import java.util.List;

public interface BookRentDao {

    BookRent insert(BookRent bookRent);

    List<BookRent> findBookRents();

    BookRent findById(int id);

    BookRent findByEmployeeAndBookId(int employeeID, int bookID);

    int delete(int id);
}
