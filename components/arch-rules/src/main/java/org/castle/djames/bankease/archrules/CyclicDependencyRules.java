package org.castle.djames.bankease.archrules;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.dependencies.SliceAssignment;
import com.tngtech.archunit.library.dependencies.SliceIdentifier;

import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

public class CyclicDependencyRules {

    /**
     * Verifies the absence of circular dependencies among classes within the service layer.
     */
    @ArchTest
    public ArchRule noCyclesBetweenServiceClasses = slices().assignedFrom(getClassesFromServicePackage())
            .should()
            .beFreeOfCycles()
            .allowEmptyShould(true);


    /**
     * Divides classes from the service package into separate slices.
     */
    private SliceAssignment getClassesFromServicePackage() {
        return new SliceAssignment() {
            @Override
            public SliceIdentifier getIdentifierOf(JavaClass javaClass) {
                return (javaClass.getPackageName().contains(".service"))
                        ? SliceIdentifier.of(javaClass.getPackageName())
                        : SliceIdentifier.ignore();
            }

            @Override
            public String getDescription() {
                return "classes directly from service package";
            }
        };

    }
}
