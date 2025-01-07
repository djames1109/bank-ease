package org.castle.djames.bankease.archrules;

import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;

public class LayeredArchitectureRules {

    /**
     * Enforces a strict layered architecture with three defined layers: Controller, Service, and Repository.
     * <p>
     * Rules:
     * - The Controller layer must not be accessed by any other layer.
     * - The Service layer can only be accessed by the Controller layer.
     * - The Repository layer can only be accessed by the Service layer.
     */

    @ArchTest
    public ArchRule layerDependenciesAreRespected = Architectures.layeredArchitecture()
            .consideringAllDependencies()
            .layer("Controller").definedBy("..controller..")
            .layer("Service").definedBy("..service..")
            .layer("Repository").definedBy("..repository..")
            .whereLayer("Controller").mayNotBeAccessedByAnyLayer()
            .whereLayer("Service").mayOnlyBeAccessedByLayers("Controller")
            .whereLayer("Repository").mayOnlyBeAccessedByLayers("Service");
}
