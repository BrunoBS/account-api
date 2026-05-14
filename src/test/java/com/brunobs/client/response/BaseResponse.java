package com.brunobs.client.response;

import io.restassured.response.ValidatableResponse;

public abstract class BaseResponse<T extends BaseResponse<T>> {

    protected final ValidatableResponse response;

    protected BaseResponse(ValidatableResponse response) {
        this.response = response;
    }

    @SuppressWarnings("unchecked")
    protected T self() {
        return (T) this;
    }

    // =========================================================
    // EXACT STATUS
    // =========================================================

    public T expectOk() {
        response.statusCode(200);
        return self();
    }

    public T expectCreated() {
        response.statusCode(201);
        return self();
    }

    public T expectNoContent() {
        response.statusCode(204);
        return self();
    }

    public T expectBadRequest() {
        response.statusCode(400);
        return self();
    }

    public T expectUnauthorized() {
        response.statusCode(401);
        return self();
    }

    public T expectForbidden() {
        response.statusCode(403);
        return self();
    }

    public T expectNotFound() {
        response.statusCode(404);
        return self();
    }

    public T expectConflict() {
        response.statusCode(409);
        return self();
    }

    // =========================================================
    // STATUS FAMILY
    // =========================================================

    public T expect2xx() {

        response.statusCode(
                org.hamcrest.Matchers.allOf(
                        org.hamcrest.Matchers.greaterThanOrEqualTo(200),
                        org.hamcrest.Matchers.lessThan(300)
                )
        );

        return self();
    }

    public T expect4xx() {

        response.statusCode(
                org.hamcrest.Matchers.allOf(
                        org.hamcrest.Matchers.greaterThanOrEqualTo(400),
                        org.hamcrest.Matchers.lessThan(500)
                )
        );

        return self();
    }

    public T expect5xx() {

        response.statusCode(
                org.hamcrest.Matchers.allOf(
                        org.hamcrest.Matchers.greaterThanOrEqualTo(500),
                        org.hamcrest.Matchers.lessThan(600)
                )
        );

        return self();
    }

    // =========================================================
    // BODY ASSERTIONS
    // =========================================================

    public T expect(String path, Object value) {

        response.body(
                path,
                org.hamcrest.Matchers.is(value)
        );

        return self();
    }

    public T expectNotNull(String path) {

        response.body(
                path,
                org.hamcrest.Matchers.notNullValue()
        );

        return self();
    }

    public T expectContains(String path, String value) {

        response.body(
                path,
                org.hamcrest.Matchers.containsString(value)
        );

        return self();
    }

    public T expectSize(String path, int size) {

        response.body(
                path,
                org.hamcrest.Matchers.hasSize(size)
        );

        return self();
    }

    // =========================================================
    // EXTRACT
    // =========================================================

    public <R> R extract(Class<R> clazz) {
        return response.extract().as(clazz);
    }

    public ValidatableResponse response() {
        return response;
    }
}