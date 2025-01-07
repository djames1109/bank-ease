package org.castle.djames.bankease.archrules;

import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.transaction.annotation.Transactional;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;

public class TransactionalRules {

    /**
     * Ensures methods annotated with @Transactional are public to prevent access and transaction issues.
     */
    @ArchTest
    public ArchRule transactionalMethodsShouldBePublic = methods().that()
            .areAnnotatedWith(Transactional.class)
            .should().bePublic();
}
