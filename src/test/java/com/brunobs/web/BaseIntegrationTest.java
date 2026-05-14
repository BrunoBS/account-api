package com.brunobs.web;

import com.brunobs.common.AuthorizationMockHelper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseIntegrationTest {

    @LocalServerPort
    protected int port;

    @Autowired
    protected JdbcTemplate jdbcTemplate;
    private static String queryLimpezaEmLote = null;
    protected static AuthorizationMockHelper authMock;
    protected static final WireMockServer wireMockServer = new WireMockServer(wireMockConfig().dynamicPort());

    protected static final MySQLContainer<?> MYSQL = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("account_db")
            .withUsername("test")
            .withPassword("test");

    protected static final KafkaContainer KAFKA = new KafkaContainer(
            DockerImageName.parse("confluentinc/cp-kafka:7.4.0")
                    .asCompatibleSubstituteFor("apache/kafka")
    );

    @BeforeAll
    static void startAll() {
        authMock = new AuthorizationMockHelper(wireMockServer);
        Startables.deepStart(MYSQL, KAFKA).join();

        if (!wireMockServer.isRunning()) {
            wireMockServer.start();
            WireMock.configureFor("localhost", wireMockServer.port());
        }
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> MYSQL.getJdbcUrl() + "?allowMultiQueries=true");
        registry.add("spring.datasource.username", MYSQL::getUsername);
        registry.add("spring.datasource.password", MYSQL::getPassword);
        registry.add("spring.kafka.bootstrap-servers", KAFKA::getBootstrapServers);
        registry.add("auth.service.url", () -> "http://localhost:" + wireMockServer.port());
    }

    @BeforeEach
    void setupBase() {
        limparTodaABase();
        wireMockServer.resetAll();
        RestAssured.port = port;
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .addHeader("correlationId", UUID.randomUUID().toString())
                .addHeader("Authorization", "Bearer token-teste")
                .setContentType(ContentType.JSON)
                .build();

        authMock.mockSucesso();
    }

    protected RequestSpecification requestSpec() {
        return RestAssured.given()
                .header("X-Correlation-Id", UUID.randomUUID().toString())
                .header("Authorization", "Bearer token-teste")
                .contentType(io.restassured.http.ContentType.JSON);
    }

    private void limparTodaABase() {
        if (queryLimpezaEmLote == null) {
            List<String> tabelas = jdbcTemplate.queryForList(
                    "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES " +
                            "WHERE TABLE_SCHEMA = DATABASE() AND TABLE_TYPE = 'BASE TABLE' AND TABLE_NAME != 'flyway_schema_history'",
                    String.class
            );

            if (tabelas.isEmpty()) {
                queryLimpezaEmLote = "";
                return;
            }

            StringBuilder sb = new StringBuilder();
            sb.append("SET FOREIGN_KEY_CHECKS = 0; ");
            for (String tabela : tabelas) {
                sb.append("TRUNCATE TABLE ").append(tabela).append("; ");
            }
            sb.append("SET FOREIGN_KEY_CHECKS = 1;");
            queryLimpezaEmLote = sb.toString();

        }

        if (!queryLimpezaEmLote.isEmpty()) {
            jdbcTemplate.execute(queryLimpezaEmLote);
        }
    }


}
