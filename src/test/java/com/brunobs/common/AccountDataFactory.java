package com.brunobs.common;

import com.brunobs.core.account.AccountDTO;
import com.brunobs.core.account.ApproverDTO;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.github.javafaker.Faker;
import java.util.Set;

public class AccountDataFactory {
    private static final Faker faker = new Faker();

    public static AccountDTO createValidDTO() {
        return new AccountDTO(
            null, null, "ADMIN", 
            faker.company().name(), 
            "Descrição detalhada com mais de dez caracteres",
                faker.name().fullName(),
                faker.regexify("[A-Z0-9]{3}"),
                "GRP_ADM",
            JsonNodeFactory.instance.objectNode(), 
            faker.internet().emailAddress(), 
            Set.of(new ApproverDTO("F" + faker.number().digits(4), faker.internet().emailAddress())), 
            Set.of("tag-teste")
        );
    }
}
