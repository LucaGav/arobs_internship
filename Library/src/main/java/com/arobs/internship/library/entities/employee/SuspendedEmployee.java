package com.arobs.internship.library.entities.employee;

import com.arobs.internship.library.entities.book.Copy;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "suspendedemployee")
public class SuspendedEmployee {

    @Id
    @Column(name = "suspendedEmployeeID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int suspendedemployeeID;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employeeID")
    private Employee employee;

    @Column(name = "suspendedUntilDate")
    @Temporal(TemporalType.DATE)
    private Date suspendedUntilDate;

    public SuspendedEmployee() {
    }

    public SuspendedEmployee(int suspendedemployeeID, Employee employee, Date suspendedUntilDate) {
        this.suspendedemployeeID = suspendedemployeeID;
        this.employee = employee;
        this.suspendedUntilDate = suspendedUntilDate;
    }

    public int getSuspendedemployeeID() {
        return suspendedemployeeID;
    }

    public void setSuspendedemployeeID(int suspendedemployeeID) {
        this.suspendedemployeeID = suspendedemployeeID;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Date getSuspendedUntilDate() {
        return suspendedUntilDate;
    }

    public void setSuspendedUntilDate(Date suspendedUntilDate) {
        this.suspendedUntilDate = suspendedUntilDate;
    }
}
