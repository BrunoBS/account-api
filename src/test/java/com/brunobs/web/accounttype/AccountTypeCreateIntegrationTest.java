package com.brunobs.web.accounttype;


import com.brunobs.client.AccountTypeClient;
import com.brunobs.common.factory.AccountTypeFactory;
import com.brunobs.web.BaseIntegrationTest;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

@DisplayName("Integração - Gerenciamento de Criacao de Tipos de Contas")
public class AccountTypeCreateIntegrationTest extends BaseIntegrationTest {

    AccountTypeFactory factory = new AccountTypeFactory();

    @BeforeEach
    public void setup(){
        authMock.mockSucesso();
    }

    @Test
    @DisplayName("C - Deve criar uma tipo valido")
    void deveCriarTipoContaValido() {
        AccountTypeClient.client()
                .create(List.of(factory.manager()))
                .expect2xx()
                .expectNotNull("[0].id");
    }

    @Test
    @DisplayName("C - Deve gerar erro ao tentar salvar tipo duplicado")
    void deveCriarTipoContaDuplicado() {

        AccountTypeClient.client()
                .create(List.of(factory.manager()))
                .expect2xx()
                .expectNotNull("[0].id");

        AccountTypeClient.client()
                .create(List.of(factory.manager()))
                .expect4xx()
                .expect("code", "BAD_REQUEST")
                .expect("details[0].field", "name");
    }

    @Test
    @DisplayName("C - Deve gerar erro ao tentar salvar  tipo  Sem Nome")
    void deveGerarErroAoCriarTipoContaSemNome() {
        AccountTypeClient.client()
                .create(factory.withoutName())
                .expect4xx()
                .expect("code", "BAD_REQUEST")
                .expect("details[0].field", "name");
    }

    @Test
    @DisplayName("C - Deve gerar erro ao tentar salvar  tipo com Nome Vazio")
    void deveGerarErroAoCriarTipoContaComNomeVazio() {
        AccountTypeClient.client()
                .create(factory.withEmptyName())
                .expect4xx()
                .expect("code", "BAD_REQUEST")
                .expect("details[0].field", "name");
    }

    @Test
    @DisplayName("C - Deve gerar erro ao tentar salvar  tipo  Com Label Vazio")
    void deveGerarErroAoCriarTipoContaComLabelVazio() {
        AccountTypeClient.client()
                .create(factory.withEmptyLabel())
                .expect4xx()
                .expect("code", "BAD_REQUEST")
                .expect("details[0].field", "label");
    }

    @Test
    @DisplayName("C - Deve gerar erro ao tentar salvar  tipo  Sem Label")
    void deveGerarErroAoCriarTipoContaSemLabel() {
        AccountTypeClient.client()
                .create(factory.withoutLabel())
                .expect4xx()
                .expect("code", "BAD_REQUEST")
                .expect("details[0].field", "label");
    }

    @Test
    @DisplayName("C - Deve gerar erro ao tentar salvar  tipo com Descricao vazia ")
    void deveGerarErroAoCriarTipoContaComDescricaoVazia() {
        AccountTypeClient.client()
                .create(factory.withEmptyDescription())
                .expect4xx()
                .expect("code", "BAD_REQUEST")
                .expect("details[0].field", "description");
    }

    @Test
    @DisplayName("C - Deve gerar erro ao tentar salvar  tipo  Sem Descricao")
    void deveGerarErroAoCriarTipoContaSemDescricao() {
        AccountTypeClient.client()
                .create(factory.withoutDescription())
                .expect4xx()
                .expect("code", "BAD_REQUEST")
                .expect("details[0].field", "description");
    }

    @Test
    @DisplayName("C - Deve gerar erro ao tentar salvar  tipo com Descricao Curta")
    void deveGerarErroAoCriarTipoContaComDescricaoCurta() {
        AccountTypeClient.client()
                .create(factory.withShortDescription())
                .expect4xx()
                .expect("code", "BAD_REQUEST")
                .expect("details[0].field", "description");
    }

    @Test
    @DisplayName("C - Deve gerar erro ao tentar salvar  tipo com Descricao Longa")
    void deveGerarErroAoCriarTipoContaComDescricaoLonga() {
        AccountTypeClient.client()
                .create(factory.withLongDescription())
                .expect4xx()
                .expect("code", "BAD_REQUEST")
                .expect("details[0].field", "description");
    }

    @Test
    @DisplayName("C - Deve gerar erro ao tentar salvar  tipo com ordem nula")
    void deveGerarErroAoCriarTipoContaComOrdemNula() {
        AccountTypeClient.client()
                .create(List.of(factory.withoutSortOrder()))
                .expect2xx()
                .expectNotNull("[0].id");
    }

    @Test
    @DisplayName("C - Deve gerar erro ao tentar salvar  tipo com ordem Negativa")
    void deveGerarErroAoCriarTipoContaComOrdemNegativa() {
        AccountTypeClient.client()
                .create(List.of(factory.withSortOrderNegative()))
                .expect2xx()
                .expectNotNull("[0].id");
    }


}
