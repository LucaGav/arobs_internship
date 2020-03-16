package com.arobs.internship.library.dtos.operations;

import java.util.Date;

public class BookRentDTO {

    private int employeeID;
    private int bookID;

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }
}
