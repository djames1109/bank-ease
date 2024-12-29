package org.castle.djames.bankease.user.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class WebConfiguration {

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(16);
    }

    @Bean(name = "transactionManager")
    public JpaTransactionManager transactionManager() {
        return new JpaTransactionManager();
    }
}
