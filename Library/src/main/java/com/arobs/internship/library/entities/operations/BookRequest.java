package com.arobs.internship.library.entities.operations;

import com.arobs.internship.library.entities.Employee;

import javax.persistence.*;
import javax.persistence.criteria.Join;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "bookrequest")
public class BookRequest {
    @Id
    @Column(name = "bookreqID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookreqID;

    @Column(name = "title")
    private String title;

    @NotNull
    @Column(name = "author")
    private String author;

    @NotNull
    @Column(name = "pCompany")
    private String pCompany;

    @Column(name = "link")
    private String link;

    @Column(name = "noCopies")
    private int noCopies;

    @Column(name = "aproxCost")
    private int aproxCost;

    @NotNull
    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employeeID")
    private Employee employee;

    public BookRequest() {
    }

    public BookRequest(int bookreqID, String title, String author, String pCompany, String link, int noCopies, int aproxCost, String status, Employee employee) {
        this.bookreqID = bookreqID;
        this.title = title;
        this.author = author;
        this.pCompany = pCompany;
        this.link = link;
        this.noCopies = noCopies;
        this.aproxCost = aproxCost;
        this.status = status;
        this.employee = employee;
    }

    public int getBookreqID() {
        return bookreqID;
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

    public String getpCompany() {
        return pCompany;
    }

    public void setpCompany(String pCompany) {
        this.pCompany = pCompany;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getNoCopies() {
        return noCopies;
    }

    public void setNoCopies(int noCopies) {
        this.noCopies = noCopies;
    }

    public int getAproxCost() {
        return aproxCost;
    }

    public void setAproxCost(int aproxCost) {
        this.aproxCost = aproxCost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
