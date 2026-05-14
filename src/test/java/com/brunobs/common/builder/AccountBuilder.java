package com.brunobs.common.builder;

import com.brunobs.core.account.AccountDTO;
import com.brunobs.core.account.ApproverDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

public class AccountBuilder {

    private static final Faker faker = new Faker();
    private static final ObjectMapper mapper = new ObjectMapper();

    private Long id;

    private String identifier =
            UUID.randomUUID().toString();

    /**
     * No DTO o accountType é String
     */
    private String accountType = "ADMIN";

    private String name = faker.company().name();

    private String description = faker.lorem().sentence();

    private String requester = faker.name().fullName();

    private String acronym = faker.regexify("[A-Z]{3}");

    private String authorizerGroup = "DEFAULT_GROUP";

    private JsonNode settings = mapper.createObjectNode();

    private String emailGroup = faker.internet().emailAddress();

    private Set<ApproverDTO> approvers = Set.of(
            new ApproverDTO("F" + faker.number().digits(4),
                    faker.internet().emailAddress()));

    private Set<String> tags = new LinkedHashSet<>();

    public static AccountBuilder builder() {
        return new AccountBuilder();
    }


    public AccountBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public AccountBuilder withIdentifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public AccountBuilder withAccountType(String accountType) {
        this.accountType = accountType;
        return this;
    }

    public AccountBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public AccountBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public AccountBuilder withRequester(String requester) {
        this.requester = requester;
        return this;
    }

    public AccountBuilder withAcronym(String acronym) {
        this.acronym = acronym;
        return this;
    }

    public AccountBuilder withAuthorizerGroup(
            String authorizerGroup
    ) {
        this.authorizerGroup = authorizerGroup;
        return this;
    }

    public AccountBuilder withEmailGroup(
            String emailGroup
    ) {
        this.emailGroup = emailGroup;
        return this;
    }

    public AccountBuilder withSettings(String json) {
        try {
            this.settings = (json == null ? mapper.createObjectNode() : mapper.readTree(json));
        } catch (Exception e) {
            this.settings = mapper.createObjectNode();
        }

        return this;
    }

    public AccountBuilder addApprover(
            ApproverDTO approver
    ) {

        this.approvers.add(approver);
        return this;
    }

    public AccountBuilder addTag(String tag) {

        this.tags.add(tag);
        return this;
    }

    public AccountBuilder withApprovers(Set<ApproverDTO> approvers
    ) {
        this.approvers = approvers;
        return this;
    }

    public AccountBuilder withTags(
            Set<String> tags
    ) {

        this.tags = tags;
        return this;
    }

    // =========================================================
    // BUILD
    // =========================================================

    public AccountDTO build() {
        return new AccountDTO(
                id,
                identifier,
                accountType,
                name,
                description,
                requester,
                acronym,
                authorizerGroup,
                settings,
                emailGroup,
                approvers,
                tags
        );
    }
}
