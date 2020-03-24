package com.arobs.internship.library.dtos.operations;

import java.util.Date;

public class RentRequestDTO {

    private int bookID;
    private int employeeID;

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }
}
