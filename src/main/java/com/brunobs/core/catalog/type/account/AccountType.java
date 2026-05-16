package com.brunobs.core.catalog.type.account;

import com.brunobs.core.catalog.common.BaseType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "type_accounts") // Plural e snake_case
public class AccountType extends BaseType {
}
