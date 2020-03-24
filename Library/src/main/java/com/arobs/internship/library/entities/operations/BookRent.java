package com.arobs.internship.library.entities.operations;

import com.arobs.internship.library.entities.employee.Employee;
import com.arobs.internship.library.entities.book.Book;
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

    @Column(name = "returnDate")
    @Temporal(TemporalType.DATE)
    private Date returnDate;

    @NotNull
    @Column(name = "status")
    private String status;

    @Column(name = "grade")
    private float grade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "copyID")
    private Copy copy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookID")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employeeID")
    private Employee employee;

    public BookRent() {
    }

    public BookRent(Book book, Employee employee) {
        this.book = book;
        this.employee = employee;
    }

    public BookRent(int rentID, @NotNull Date rentalDate, Date returnDate, @NotNull String status, float grade, Copy copy, Book book, Employee employee) {
        this.rentID = rentID;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.status = status;
        this.grade = grade;
        this.copy = copy;
        this.book = book;
        this.employee = employee;
    }

    public int getRentID() {
        return rentID;
    }

    public void setRentID(int rentID) {
        this.rentID = rentID;
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

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public Copy getCopy() {
        return copy;
    }

    public void setCopy(Copy copy) {
        this.copy = copy;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }

    @Override
    public String toString() {
        return "BookRent{" +
                "rentID=" + rentID +
                ", rentalDate=" + rentalDate +
                ", returnDate=" + returnDate +
                ", status='" + status + '\'' +
                ", grade=" + grade +
                ", copy=" + copy +
                ", book=" + book +
                ", employee=" + employee +
                '}';
    }
}
