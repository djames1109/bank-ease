package org.castle.djames.bankease.archrules;

import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

public class CyclicDependencyRules {

    /**
     * Ensures that there are no cyclic dependencies between classes within the service layer of the application.
     * <p>
     * This rule checks for any circular relationships that might exist among the classes matching the pattern
     * "..service.(*)..". It is designed to enforce a clean and manageable architecture by preventing tightly
     * coupled code that can lead to maintenance issues and hinder extensibility within the service layer.
     * <p>
     * By enforcing this rule, the service layer maintains a modular design, making it easier to understand,
     * test, and evolve over time.
     * <p>
     * Note: This only works if the classes are on different packages: ..service.order.Order and ..service.payment.Payment
     * todo: check how to prevent cyclic dependency violation between classes on the same package
     */
    @ArchTest
    public ArchRule noCyclesBetweenServiceClasses = slices().matching("..(service).(*)..")
            .namingSlices("$2 of $1")
            .should()
            .beFreeOfCycles()
            .allowEmptyShould(true);

}
