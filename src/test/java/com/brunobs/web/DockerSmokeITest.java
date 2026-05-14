package com.brunobs.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DockerSmokeITest extends BaseIntegrationTest {

    @Test
    @DisplayName("Validar se os containers de MySQL e Kafka subiram corretamente")
    void deveSubirContainers() {
        // Se o código chegar aqui, significa que o @BeforeAll da BaseIntegrationTest funcionou
        
        System.out.println("--- 🔎 Verificando status dos containers ---");
        
        System.out.println("MySQL está rodando: " + MYSQL.isRunning());
        System.out.println("MySQL URL: " + MYSQL.getJdbcUrl());
        
        System.out.println("Kafka está rodando: " + KAFKA.isRunning());
        System.out.println("Kafka Bootstrap: " + KAFKA.getBootstrapServers());

        assertTrue(MYSQL.isRunning(), "O container do MySQL deveria estar rodando");
        assertTrue(KAFKA.isRunning(), "O container do Kafka deveria estar rodando");
        
        System.out.println("--- ✅ Teste de infraestrutura passou! ---");
    }
}
