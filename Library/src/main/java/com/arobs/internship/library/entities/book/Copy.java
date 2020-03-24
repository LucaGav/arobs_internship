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
    @Column(name = "isRentable")
    private boolean isRentable;

    @NotNull
    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookID")
    private Book book;

    public Copy() {
    }

    public Copy(@NotNull boolean isRentable, @NotNull String status, Book book) {
        this.isRentable = isRentable;
        this.status = status;
        this.book = book;
    }

    public int getCopyID() {
        return copyID;
    }

    public boolean isRentable() {
        return isRentable;
    }

    public void setRentable(boolean rentable) {
        isRentable = rentable;
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
