package com.arobs.internship.library.dtos.operations;

import java.util.Date;

public class BookRentDTO {

    private int copyID;
    private int bookID;


    public int getCopyID() {
        return copyID;
    }

    public void setCopyID(int copyID) {
        this.copyID = copyID;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }
}
