package com.brunobs.client;

import com.brunobs.client.response.AccountResponse;
import com.brunobs.core.account.AccountDTO;
import com.brunobs.shared.RestoreDTO;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class AccountClient extends BaseClient {

    private static final String BASE_PATH =
            "/api/v1/accounts";

    private AccountClient() {
        super();
    }

    public static AccountClient client() {
        return new AccountClient();
    }

    // =========================================================
    // CREATE
    // =========================================================

    public AccountResponse create(AccountDTO request) {

        return response(
                given()
                        .spec(spec)
                        .body(request)
                        .when()
                        .post(BASE_PATH)
                        .then()
        );
    }

    // =========================================================
    // FIND ALL
    // =========================================================

    public FindAllAction findAll() {
        return new FindAllAction();
    }

    public class FindAllAction {

        private Boolean active = true;
        private Boolean simplify = false;
        private String typeName;
        private String tagName;

        public FindAllAction active(Boolean active) {
            this.active = active;
            return this;
        }

        public FindAllAction simplify(Boolean simplify) {
            this.simplify = simplify;
            return this;
        }

        public FindAllAction typeName(String typeName) {
            this.typeName = typeName;
            return this;
        }

        public FindAllAction tagName(String tagName) {
            this.tagName = tagName;
            return this;
        }

        public AccountResponse execute() {

            return response(
                    given()
                            .spec(spec)
                            .queryParam("active", active)
                            .queryParam("simplify", simplify)
                            .queryParam("typeName", typeName)
                            .queryParam("tagName", tagName)
                            .when()
                            .get(BASE_PATH)
                            .then()
            );
        }
    }

    // =========================================================
    // FIND BY ID
    // =========================================================

    public AccountResponse findById(Long accountId) {

        return response(
                given()
                        .spec(spec)
                        .when()
                        .get(BASE_PATH + "/{id}", accountId)
                        .then()
        );
    }

    // =========================================================
    // UPDATE
    // =========================================================

    public AccountResponse update(
            Long accountId,
            AccountDTO request
    ) {

        return response(
                given()
                        .spec(spec)
                        .body(request)
                        .when()
                        .put(BASE_PATH + "/{id}", accountId)
                        .then()
        );
    }

    // =========================================================
    // DELETE
    // =========================================================

    public AccountResponse delete(Long accountId) {

        return response(
                given()
                        .spec(spec)
                        .when()
                        .delete(BASE_PATH + "/{id}", accountId)
                        .then()
        );
    }

    // =========================================================
    // RESTORE
    // =========================================================

    public AccountResponse restore(
            Long accountId,
            RestoreDTO request
    ) {

        return response(
                given()
                        .spec(spec)
                        .body(request)
                        .when()
                        .post(BASE_PATH + "/{id}/restore", accountId)
                        .then()
        );
    }

    public AccountResponse restore(Long accountId) {

        return response(
                given()
                        .spec(spec)
                        .when()
                        .post(BASE_PATH + "/{id}/restore", accountId)
                        .then()
        );
    }

    // =========================================================
    // ONBOARDING
    // =========================================================

    public AccountResponse onboardingProgress(Long accountId) {

        return response(
                given()
                        .spec(spec)
                        .when()
                        .get(BASE_PATH + "/{id}/onboarding", accountId)
                        .then()
        );
    }

    public AccountResponse onboardingUpdate(Long accountId) {

        return response(
                given()
                        .spec(spec)
                        .when()
                        .patch(BASE_PATH + "/{id}/onboarding", accountId)
                        .then()
        );
    }

    // =========================================================
    // INTERNAL
    // =========================================================

    private AccountResponse response(
            ValidatableResponse response
    ) {
        return new AccountResponse(response);
    }
}