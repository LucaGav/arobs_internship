package com.arobs.internship.library.entities.operations;

import com.arobs.internship.library.entities.Employee;
import com.arobs.internship.library.entities.book.Book;
import com.arobs.internship.library.entities.book.Copy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "rentrequest")
public class RentRequest {
    @Id
    @Column(name = "rentreqID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rentreqID;

    @NotNull
    @Column(name = "requestDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date requestDate;

    @NotNull
    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookID")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employeeID")
    private Employee employee;

    public RentRequest() {
    }

    public RentRequest(Book book, Employee employee) {
        this.book = book;
        this.employee = employee;
    }

    public RentRequest(int rentreqID, @NotNull Date requestDate, @NotNull String status, Book book, Employee employee) {
        this.rentreqID = rentreqID;
        this.requestDate = requestDate;
        this.status = status;
        this.book = book;
        this.employee = employee;
    }

    public int getRentreqID() {
        return rentreqID;
    }

    public void setRentreqID(int rentreqID) {
        this.rentreqID = rentreqID;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
