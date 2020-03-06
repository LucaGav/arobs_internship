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

    @ManyToOne
    @JoinColumn(name = "bookID")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "employeeID")
    private Employee employee;
}
