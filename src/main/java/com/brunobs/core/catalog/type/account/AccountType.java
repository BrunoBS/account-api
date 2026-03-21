package com.brunobs.core.catalog.type.account;

import com.brunobs.core.catalog.common.BaseType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "account_types") // Plural e snake_case
public class AccountType extends BaseType {


}
