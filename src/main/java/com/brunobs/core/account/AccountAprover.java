package com.brunobs.core.account;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "accounts_approvers")

public class AccountAprover {

    @Id
    private String id;

    @Column(name = "funcional")
    private String funcional;

    @Column(name = "email")
    private String email;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;


    public AccountAprover() {
        this.id = UUID.randomUUID().toString();
    }


    public AccountAprover(String funcional, String email, Account account) {
        this();
        this.id = UUID.randomUUID().toString();
        this.funcional = funcional;
        this.email = email;
        this.account = account;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public String getFuncional() {
        return funcional;
    }

    public void setFuncional(String funcional) {
        this.funcional = funcional;
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
