package com.arobs.internship.library.entities.book;

import com.arobs.internship.library.entities.operations.RentRequest;
import com.arobs.internship.library.util.status.ActiveStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @Column(name = "bookID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookID;

    @NotNull
    @Column(name = "title")
    private String title;

    @NotNull
    @Column(name = "author")
    private String author;

    @NotNull
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "addedDate")
    @Temporal(TemporalType.DATE)
    private Date addedDate;

    @NotNull
    @Column(name = "status")
    private String status;

    @ManyToMany(cascade = {
            CascadeType.ALL
    })

    @JoinTable(name = "booktag",
            joinColumns = @JoinColumn(name = "bookID"),
            inverseJoinColumns = @JoinColumn(name = "tagID")
    )
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(mappedBy = "book",
            cascade = CascadeType.ALL
    )
    private Set<Copy> copies = new HashSet<>();

    @OneToMany(
            mappedBy = "book",
            cascade = CascadeType.ALL
    )
    private Set<RentRequest> rentRequests = new HashSet<>();

    public Book() {
    }

    public Book(int bookID, @NotNull String title, @NotNull String author, @NotNull String description, @NotNull Date addedDate) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.description = description;
        this.addedDate = addedDate;
        this.status = ActiveStatus.ACTIVE.name();
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public int getBookID() {
        return bookID;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addDate) {
        this.addedDate = addDate;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Set<Copy> getCopies() {
        return copies;
    }

    public void setCopies(Set<Copy> copies) {
        this.copies = copies;
    }

    public Set<RentRequest> getRentRequests() {
        return rentRequests;
    }

    public void setRentRequests(Set<RentRequest> rentRequests) {
        this.rentRequests = rentRequests;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
