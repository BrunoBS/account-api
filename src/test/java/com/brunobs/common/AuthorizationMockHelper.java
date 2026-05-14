package com.brunobs.common;

import com.github.javafaker.Faker;
import com.github.tomakehurst.wiremock.WireMockServer;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class AuthorizationMockHelper {
    private static final Faker faker = new Faker();
    private final WireMockServer wireMockServer;

    public AuthorizationMockHelper(WireMockServer wireMockServer) {
        this.wireMockServer = wireMockServer;
    }

    /**
     * Resposta padrão de Sucesso (200 OK)
     */
    public void mockSucesso() {
        wireMockServer.resetAll();
        wireMockServer.stubFor(post(urlEqualTo("/authorize"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(jsonUserSession())));
    }

    /**
     * Resposta de Acesso Negado (401 Unauthorized)
     */
    public void mockNegado() {
        wireMockServer.stubFor(post(urlEqualTo("/authorize"))
                .willReturn(aResponse()
                        .withStatus(401)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"message\": \"Acesso negado pelo autorizador\"}")));
    }

    /**
     * Simula Erro no Servidor (500) para testar os Retries do Spring
     */
    public void mockErroInterno() {
        wireMockServer.stubFor(post(urlEqualTo("/authorize"))
                .willReturn(aResponse()
                        .withStatus(500)));
    }

    /**
     * Mock customizado para cenários específicos
     */
    public void mockCustomizado(int status, String body) {
        wireMockServer.stubFor(post(urlEqualTo("/authorize"))
                .willReturn(aResponse()
                        .withStatus(status)
                        .withHeader("Content-Type", "application/json")
                        .withBody(body)));
    }

    public static String jsonUserSession() {

        String userName = faker.regexify("[A-Z0-9]{3}");
        Long accountId = 0l;
        return """
                {
                  "expirationTime": %d,
                  "userName": "%s",
                  "email": "%s@teste.com",
                  "accountId": %d,
                  "applicationId": 100,
                  "environmentId": 1,
                  "traceId": "trace-123",
                  "groups": ["USER", "ADMIN", "PM5_ORWER"],
                  "authorizerGroups": [
                    {
                      "fullGroup": "GRP_APP_PROD_ADMIN",
                      "profile": "ADMIN",
                      "environment": "PROD",
                      "authorizer": "APP"
                    }
                  ]
                }
                """.formatted(
                System.currentTimeMillis() + 3600000, // 1 hora de expiração
                userName,
                userName.toLowerCase(),
                accountId
        );
    }

}
