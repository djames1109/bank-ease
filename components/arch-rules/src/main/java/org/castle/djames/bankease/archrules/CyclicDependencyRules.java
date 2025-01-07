package org.castle.djames.bankease.archrules;

import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

public class CyclicDependencyRules {

    /**
     * Ensures no cyclic dependencies exist between classes in the service layer.
     * <p>
     * This rule enforces a modular architecture by detecting circular relationships among classes matching
     * the pattern "..service.(*)..".
     * <p>
     * Note: This only works if the classes are in different packages: `..service.order.Order` and `..service.payment.Payment`
     * todo: check how to prevent cyclic dependency violation between classes on the same package
     */
    @ArchTest
    public ArchRule noCyclesBetweenServiceClasses = slices().matching("..(service).(*)..")
            .namingSlices("$2 of $1")
            .should()
            .beFreeOfCycles()
            .allowEmptyShould(true);

}
