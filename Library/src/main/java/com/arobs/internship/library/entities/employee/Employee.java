package com.arobs.internship.library.entities.employee;

import com.arobs.internship.library.entities.operations.BookRequest;
import com.arobs.internship.library.entities.operations.RentRequest;
import com.arobs.internship.library.util.status.ActiveStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "employee",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})}
)
public class Employee {

    @Id
    @Column(name = "employeeID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int employeeID;

    @NotNull
    @Column(name = "firstName")
    private String firstName;

    @NotNull
    @Column(name = "lastName")
    private String lastName;

    @NotNull
    @Column(name = "role")
    private String role;

    @NotNull
    @Column(name = "password")
    private String password;

    @NotNull
    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "status")
    private String status;

    @OneToMany(
            mappedBy = "employee",
            cascade = CascadeType.ALL
    )
    private Set<BookRequest> bookRequests = new HashSet<>();

    @OneToMany(
            mappedBy = "employee",
            cascade = CascadeType.ALL
    )
    private Set<RentRequest> rentRequests = new HashSet<>();

    public Employee() {
    }

    public Employee(int employeeID, @NotNull String firstName, @NotNull String lastName, @NotNull String role, @NotNull String password,
                    @NotNull String email) {
        this.employeeID = employeeID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.password = password;
        this.email = email;
        this.status = ActiveStatus.ACTIVE.name();
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<BookRequest> getBookRequests() {
        return bookRequests;
    }

    public void setBookRequests(Set<BookRequest> bookRequests) {
        this.bookRequests = bookRequests;
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
