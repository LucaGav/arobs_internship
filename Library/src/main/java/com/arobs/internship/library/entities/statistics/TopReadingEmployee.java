package com.arobs.internship.library.entities.statistics;

public class TopReadingEmployee {

    private String email;

    private String firstName;

    private String lastName;

    private int count;

    public TopReadingEmployee(String email, String firstName, String lastName, int count) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.count = count;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "TopReadingEmployee{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", count=" + count +
                '}';
    }
}
