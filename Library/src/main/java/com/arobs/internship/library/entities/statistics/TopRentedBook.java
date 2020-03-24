package com.arobs.internship.library.entities.statistics;

public class TopRentedBook {

    private String title;

    private String author;

    private int count;

    public TopRentedBook(String title, String author, int count) {
        this.title = title;
        this.author = author;
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
