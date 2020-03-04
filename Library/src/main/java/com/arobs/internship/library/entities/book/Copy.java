package com.arobs.internship.library.entities.book;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "copy")
public class Copy {
    @Id
    @Column(name = "copyID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int copyID;

    @NotNull
    @Column(name = "isAvailable")
    private boolean isAvailable;

    @NotNull
    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "bookID")
    private Book book;

    public Copy() {
    }

    public int getCopyID() {
        return copyID;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public void setCopyID(int copyID) {
        this.copyID = copyID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
