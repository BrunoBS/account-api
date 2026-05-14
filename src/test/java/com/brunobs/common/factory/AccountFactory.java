package com.brunobs.common.factory;

import com.brunobs.common.builder.AccountBuilder;
import com.brunobs.core.account.AccountDTO;

public class AccountFactory {

    public static AccountDTO valid() {

        return AccountBuilder.builder()
                .build();
    }

    public static AccountDTO withoutName() {

        return AccountBuilder.builder()
                .withName(null)
                .build();
    }

    public static AccountDTO withoutDescription() {

        return AccountBuilder.builder()
                .withDescription(null)
                .build();
    }

    public static AccountDTO withoutRequester() {

        return AccountBuilder.builder()
                .withRequester(null)
                .build();
    }

    public static AccountDTO withoutAcronym() {

        return AccountBuilder.builder()
                .withAcronym(null)
                .build();
    }

    public static AccountDTO withoutAuthorizerGroup() {

        return AccountBuilder.builder()
                .withAuthorizerGroup(null)
                .build();
    }

    public static AccountDTO withoutEmailGroup() {

        return AccountBuilder.builder()
                .withEmailGroup(null)
                .build();
    }

    public static AccountDTO withoutAccountType() {

        return AccountBuilder.builder()
                .withAccountType(null)
                .build();
    }

    public static AccountDTO duplicatedName(
            String existingName
    ) {

        return AccountBuilder.builder()
                .withName(existingName)
                .build();
    }

    public static AccountDTO custom(
            String name,
            String type
    ) {

        return AccountBuilder.builder()
                .withName(name)
                .withAccountType(type)
                .build();
    }
}