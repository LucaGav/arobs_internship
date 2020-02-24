package com.arobs.internship.library.entities;


import javax.persistence.*;

@Entity
@Table(name="employee")
public class Employee {

    @Id
    @Column(name="employeeID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int employeeID;

    @Column(name="firstName")
    private String firstName;

    @Column(name="lastName")
    private String lastName;

    @Column(name="role")
    private String role;

    @Column(name="password")
    private String password;

    @Column(name="email")
    private String email;

    public Employee() {
    }

    public Employee(int employeeID, String firstName, String lastName, String role, String password, String email) {
        this.employeeID = employeeID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.password = password;
        this.email = email;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
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
}
