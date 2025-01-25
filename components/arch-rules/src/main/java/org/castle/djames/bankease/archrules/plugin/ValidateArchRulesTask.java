package org.castle.djames.bankease.archrules.plugin;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class ValidateArchRulesTask extends DefaultTask {

    /**
     * Validates that the project complies with architectural rules.
     * <p>
     * Ensures the presence of the "arch-rules" dependency and verifies that
     * `ArchTests` from `DefaultRules.class` is implemented. Throws a {@link RuntimeException}
     * if the required implementation is missing.
     */
    @TaskAction
    public void validate() {
        Project project = getProject();

        boolean hasArchRulesDependency = project.getConfigurations()
                .getByName("testImplementation").getDependencies()
                .stream()
                .anyMatch(dependency -> Objects.equals(dependency.getGroup(), "org.castle.djames")
                        && dependency.getName().equals("arch-rules"));

        if (hasArchRulesDependency) {
            File srcDir = new File(project.getProjectDir(), "src/test/java");

            boolean implementsArchTests = containsDefaultRules(srcDir);

            if (!implementsArchTests) {
                throw new RuntimeException("Project includes 'arch-rules' dependency but does not implement ArchTests from DefaultRules.class");
            }
        }
    }

    private boolean containsDefaultRules(File srcDir) {
        if (!srcDir.exists()) {
            return false;
        }

        try (var pathStream = Files.walk(srcDir.toPath())) {
            return pathStream
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .anyMatch(this::fileContainsDefaultRules);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean fileContainsDefaultRules(File file) {
        try {
            return new String(Files.readAllBytes(file.toPath()))
                    .contains("DefaultRules");
        } catch (Exception e) {
            return false;
        }
    }
}
