package com.brunobs.client.response;

import com.brunobs.core.account.AccountDTO;
import io.restassured.response.ValidatableResponse;

public class AccountResponse
        extends BaseResponse<AccountResponse> {

    public AccountResponse(ValidatableResponse response) {
        super(response);
    }

    public AccountDTO extractBody() {
        return extract(AccountDTO.class);
    }
}