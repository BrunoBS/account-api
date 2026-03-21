package com.brunobs.core.account;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "APPROVER")

public class Approver {

    @Id
    private String id;

    @Column(name = "EMPLOYEE_ID")
    private String employeeId;

    @Column(name = "EMAIL")
    private String email;

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;


    public Approver() {
        this.id = UUID.randomUUID().toString();
    }


    public Approver(String employeeId, String email, Account account) {
        this();
        this.id = UUID.randomUUID().toString();
        this.employeeId = employeeId;
        this.email = email;
        this.account = account;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }





}
