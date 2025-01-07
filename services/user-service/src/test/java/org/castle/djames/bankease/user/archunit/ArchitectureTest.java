package org.castle.djames.bankease.user.archunit;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchTests;
import org.castle.djames.bankease.archrules.DefaultRules;

@AnalyzeClasses(packages = "org.castle.djames.bankease.user", importOptions = ImportOption.DoNotIncludeTests.class)
public class ArchitectureTest {

    @ArchTest
    public ArchTests defaultArchRules = ArchTests.in(DefaultRules.class);

}
