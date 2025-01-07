package org.castle.djames.bankease.archrules;

import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.GeneralCodingRules;

public class LoggingRules {

    /**
     * Rule to ensure that no classes access standard streams such as System.out or System.err.
     * This rule is defined to enforce proper logging practices and prevent usage of standard streams
     * for output or error logging, encouraging the use of dedicated logging frameworks instead.
     * It utilizes ArchUnit's GeneralCodingRules to enforce this restriction across the codebase.
     */
    @ArchTest
    public ArchRule noAccessToStandardStreams = GeneralCodingRules.NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS;


    /**
     * Rule to ensure that no classes in the codebase use the java.util.logging framework.
     * This rule is defined to enforce the adoption of a consistent logging framework throughout the project,
     * thereby promoting better maintainability and configurability of logging.
     * It utilizes ArchUnit's GeneralCodingRules to check for violations of this guideline.
     */
    @ArchTest
    public ArchRule noAccessToJavaUtilLogging = GeneralCodingRules.NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING;

}
