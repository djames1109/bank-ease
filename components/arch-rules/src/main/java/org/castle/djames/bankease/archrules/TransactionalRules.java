package org.castle.djames.bankease.archrules;

import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.ProxyRules;
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

    /**
     * Enforces that methods annotated with @Transactional should not be directly
     * called from other methods within the same class.
     * <p>
     * Direct calls within the same class bypass the proxy created by Spring,
     * which is responsible for starting and managing transactions.
     */
    @ArchTest
    public ArchRule transactionalMethodsShouldNotBeCalledFromTheSameClass =
            ProxyRules.no_classes_should_directly_call_other_methods_declared_in_the_same_class_that_are_annotated_with(Transactional.class);
}
