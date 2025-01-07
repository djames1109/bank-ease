package org.castle.djames.bankease.user.archunit;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchTests;
import org.castle.djames.bankease.archrules.logging.LoggingRulesTest;

@AnalyzeClasses(packages = "org.castle.djames.bankease.user", importOptions = ImportOption.DoNotIncludeTests.class)
public class ArchitectureTest {

    @ArchTest
    public static final ArchTests allLoggingRules = ArchTests.in(LoggingRulesTest.class);
}
