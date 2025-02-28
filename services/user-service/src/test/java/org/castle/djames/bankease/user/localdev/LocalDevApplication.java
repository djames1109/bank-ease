package org.castle.djames.bankease.user.localdev;

import org.castle.djames.bankease.user.BankEaseApplication;
import org.springframework.boot.SpringApplication;

public class LocalDevApplication {

    public static void main(String[] args) {
        SpringApplication.from(BankEaseApplication::main)
                .with(LocalDevTestContainersConfiguration.class)
                .run(args);
    }
}
