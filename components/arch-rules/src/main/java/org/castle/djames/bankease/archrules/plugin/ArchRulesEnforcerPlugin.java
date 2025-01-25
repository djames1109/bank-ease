package org.castle.djames.bankease.archrules.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * A Gradle plugin that registers the "validateArchRules" task in the project.
 * <p>
 * The "validateArchRules" task is used to validate the implementation of architectural tests
 * defined in the DefaultRules class. This task is automatically added to the "verification"
 * task group and provides a description of its purpose.
 */
public class ArchRulesEnforcerPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.getTasks().register("validateArchRules",
                ValidateArchRulesTask.class, task -> {
                    task.setGroup("verification");
                    task.setDescription("Validates that ArchTests defined in DefaultRules.class are implemented");
                });
    }
}
