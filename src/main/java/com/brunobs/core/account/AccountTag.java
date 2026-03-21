package com.brunobs.core.account;

import jakarta.persistence.*;

import java.util.UUID;


@Entity
@Table(name = "ACCOUNT_TAG")
public class AccountTag {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_ID", nullable = false)
    private Account account;

    public AccountTag() {
        this.id = UUID.randomUUID().toString();
    }


    public AccountTag(String name, Account account) {
        this();
        this.name = name;
        this.account = account;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
