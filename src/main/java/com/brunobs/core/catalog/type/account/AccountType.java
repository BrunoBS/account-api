package com.brunobs.core.catalog.type.account;

import com.brunobs.core.catalog.common.BaseType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "account_types") // Plural e snake_case
public class AccountType extends BaseType {

    @Column(nullable = false, columnDefinition = "TEXT")
    private String settings;

    public String getSettings() {
        return settings;
    }

    public void setSettings(String settings) {
        this.settings = settings;
    }
}
