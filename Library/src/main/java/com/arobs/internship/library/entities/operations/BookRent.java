package com.arobs.internship.library.entities.operations;

import com.arobs.internship.library.entities.book.Copy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "bookrent")
public class BookRent {
    @Id
    @Column(name = "rentID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rentID;

    @NotNull
    @Column(name = "rentalDate")
    @Temporal(TemporalType.DATE)
    private Date rentalDate;

    @NotNull
    @Column(name = "returnDate")
    @Temporal(TemporalType.DATE)
    private Date returnDate;

    @NotNull
    @Column(name = "status")
    private String status;

    @Column(name = "note")
    private String note;

    @OneToOne
    @JoinColumn(name = "copyID")
    private Copy copy;

    public BookRent() {
    }

    public BookRent(int rentID, Date rentalDate, Date returnDate, String status, String note) {
        this.rentID = rentID;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.status = status;
        this.note = note;
    }

    public int getRentID() {
        return rentID;
    }

    public Date getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(Date rentalDate) {
        this.rentalDate = rentalDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Copy getCopy() {
        return copy;
    }

    public void setCopy(Copy copy) {
        this.copy = copy;
    }
}
