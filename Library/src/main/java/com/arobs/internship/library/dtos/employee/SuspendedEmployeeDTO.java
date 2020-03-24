package com.arobs.internship.library.dtos.employee;

import java.util.Date;

public class SuspendedEmployeeDTO {

    private String email;

    private Date suspendedUntilDate;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getSuspendedUntilDate() {
        return suspendedUntilDate;
    }

    public void setSuspendedUntilDate(Date suspendedUntilDate) {
        this.suspendedUntilDate = suspendedUntilDate;
    }
}
