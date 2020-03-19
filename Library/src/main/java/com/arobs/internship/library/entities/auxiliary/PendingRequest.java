package com.arobs.internship.library.entities.auxiliary;
import com.arobs.internship.library.entities.book.Copy;
import com.arobs.internship.library.entities.operations.RentRequest;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "pendingrequest")
public class PendingRequest {

    @Id
    @Column(name = "pendingreqID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pendingreqID;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "copyID")
    private Copy copy;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rentreqID")
    private RentRequest rentRequest;


    @Column(name = "pendingDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pendingDate;

    public PendingRequest() {
    }

    public PendingRequest(Copy copy, RentRequest rentRequest) {
        this.copy = copy;
        this.rentRequest = rentRequest;
    }

    public PendingRequest(int pendingreqID, Copy copy, RentRequest rentRequest, Date pendingDate) {
        this.pendingreqID = pendingreqID;
        this.copy = copy;
        this.rentRequest = rentRequest;
        this.pendingDate = pendingDate;
    }

    public int getPendingreqID() {
        return pendingreqID;
    }

    public void setPendingreqID(int pendingreqID) {
        this.pendingreqID = pendingreqID;
    }

    public Copy getCopy() {
        return copy;
    }

    public void setCopy(Copy copy) {
        this.copy = copy;
    }

    public RentRequest getRentRequest() {
        return rentRequest;
    }

    public void setRentRequest(RentRequest rentRequest) {
        this.rentRequest = rentRequest;
    }

    public Date getPendingDate() {
        return pendingDate;
    }

    public void setPendingDate(Date pendingDate) {
        this.pendingDate = pendingDate;
    }
}
