package com.arobs.internship.library.dtos;

public class BookRequestDTO {
    private String title;
    private String author;
    private String pCompany;
    private String link;
    private int noCopies;
    private int aproxCost;
    private int employeeID;

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

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }
}
