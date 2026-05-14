package com.brunobs.client;

import com.brunobs.client.response.CatalogResponse;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.util.List;

import static io.restassured.RestAssured.given;

public abstract class BaseCatalogClient<D>
        extends BaseClient {

    protected final String basePath;
    protected final Class<D> responseType;

    protected BaseCatalogClient(
            String basePath,
            Class<D> responseType
    ) {
        super();
        this.basePath = basePath;
        this.responseType = responseType;
    }

    protected BaseCatalogClient(
            String basePath,
            Class<D> responseType,
            RequestSpecification spec
    ) {
        super(spec);
        this.basePath = basePath;
        this.responseType = responseType;
    }

    // =========================================================
    // FIND ALL
    // =========================================================

    public FindAllAction findAll() {
        return new FindAllAction(spec);
    }

    public class FindAllAction {

        private final RequestSpecification spec;
        private Boolean active;
        private String name;
        private String scope;

        public FindAllAction(RequestSpecification spec) {
            this.spec = spec;
        }

        public FindAllAction active(Boolean active) {
            this.active = active;
            return this;
        }

        public FindAllAction name(String name) {
            this.name = name;
            return this;
        }

        public FindAllAction scope(String scope) {
            this.scope = scope;
            return this;
        }

        public CatalogResponse<List<D>> execute() {

            var request = given()
                    .spec(spec);

            if (active != null) {
                request.queryParam("active", active);
            }

            if (name != null) {
                request.queryParam("name", name);
            }

            if (scope != null) {
                request.queryParam("scope", scope);
            }

            return new CatalogResponse<>(
                    request
                            .when()
                            .get(basePath)
                            .then(),
                    null
            );
        }
    }

    // =========================================================
    // FIND BY ID
    // =========================================================

    public CatalogResponse<D> findById(Long id) {

        return response(
                given()
                        .spec(spec)
                        .when()
                        .get(basePath + "/{id}", id)
                        .then()
        );
    }

    // =========================================================
    // CREATE
    // =========================================================

    public CatalogResponse<List<D>> create(D request) {
        return create(List.of(request));
    }

    public CatalogResponse<List<D>> create(List<D> requests) {

        return new CatalogResponse<>(
                given()
                        .spec(spec)
                        .body(requests)
                        .when()
                        .post(basePath)
                        .then(),
                null
        );
    }

    // =========================================================
    // UPDATE
    // =========================================================

    public CatalogResponse<D> update(Long id, D request) {

        return response(
                given()
                        .spec(spec)
                        .body(request)
                        .when()
                        .put(basePath + "/{id}", id)
                        .then()
        );
    }

    // =========================================================
    // DELETE
    // =========================================================

    public CatalogResponse<D> delete(Long id) {

        return response(
                given()
                        .spec(spec)
                        .when()
                        .delete(basePath + "/{id}", id)
                        .then()
        );
    }

    // =========================================================
    // RESTORE
    // =========================================================

    public CatalogResponse<D> restore(Long id) {

        return response(
                given()
                        .spec(spec)
                        .when()
                        .post(basePath + "/{id}/restore", id)
                        .then()
        );
    }

    // =========================================================
    // INTERNAL
    // =========================================================

    protected CatalogResponse<D> response(
            ValidatableResponse response
    ) {
        return new CatalogResponse<>(response, responseType);
    }
}