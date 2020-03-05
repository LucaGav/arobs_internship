package com.arobs.internship.library.dtos;

public class CopyDTO {

    private boolean isRentable;
    private int bookID;

    public boolean isRentable() {
        return isRentable;
    }

    public void setRentable(boolean rentable) {
        isRentable = rentable;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }
}
