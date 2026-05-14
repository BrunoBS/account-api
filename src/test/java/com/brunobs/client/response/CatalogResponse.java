package com.brunobs.client.response;

import io.restassured.response.ValidatableResponse;

public class CatalogResponse<T>
        extends BaseResponse<CatalogResponse<T>> {

    private final Class<T> responseType;

    public CatalogResponse(
            ValidatableResponse response,
            Class<T> responseType
    ) {
        super(response);
        this.responseType = responseType;
    }

    public T extractBody() {
        return extract(responseType);
    }
}