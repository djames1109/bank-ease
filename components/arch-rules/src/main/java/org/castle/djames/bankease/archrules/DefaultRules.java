package org.castle.djames.bankease.archrules;

import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchTests;

public class DefaultRules {

    @ArchTest
    public ArchTests allLoggingRules = ArchTests.in(LoggingRules.class);

    @ArchTest
    public ArchTests allLayeredArchitectureRules = ArchTests.in(LayeredArchitectureRules.class);

    @ArchTest
    public ArchTests allCyclicDependencyRules = ArchTests.in(CyclicDependencyRules.class);

    @ArchTest
    public ArchTests allTransactionalRules = ArchTests.in(TransactionalRules.class);
    
}
