package org.castle.djames.bankease.user.localdev;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration(proxyBeanMethods = false)
public class LocalDevTestContainersConfiguration {

    @Bean
    @ServiceConnection
    public PostgreSQLContainer<?> posgresqlContainer() {
        return new PostgreSQLContainer<>("postgres:alpine3.19")
                .withInitScript("init-local-dev.sql");
    }

}
