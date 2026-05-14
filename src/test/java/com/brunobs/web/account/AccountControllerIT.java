package com.brunobs.web.account;


import com.brunobs.client.AccountClient;
import com.brunobs.common.factory.AccountFactory;
import com.brunobs.common.scenario.CatalogScenario;
import com.brunobs.web.BaseIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@DisplayName("Integração - Gerenciamento de Contas")
public class AccountControllerIT extends BaseIntegrationTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setup() {
        authMock.mockSucesso();
    }


    @Test
    @DisplayName("C - Deve criar uma nova conta Valida")
    void deveCriarContaValida() {
        CatalogScenario.builder()
                .withAccountTypes()
                .withOnboardingPhases()
                .setup();

        AccountClient.client()
                .create(AccountFactory.valid())
                .expect2xx()
                .expectNotNull("id");
    }


    @Test
    @DisplayName("C - Nao deve criar uma nova conta Sem Nome")
    void deveCriarContaSemNome() {
        CatalogScenario.builder()
                .withAccountTypes()
                .withOnboardingPhases()
                .setup();

        AccountClient.client()
                .create(AccountFactory.withoutName())
                .expect4xx();
    }

    @Test
    @DisplayName("C - Tenta criar conta sem Autorização")
    void tentaCriarContaSemAutoizacao() {
        authMock.mockSucesso();
        CatalogScenario.builder()
                .withAccountTypes()
                .withOnboardingPhases()
                .setup();

        authMock.mockNegado();

        AccountClient.client()
                .create(AccountFactory.valid())
                .expectUnauthorized()
                .expect("message", "Acesso não permitido");
    }
}
