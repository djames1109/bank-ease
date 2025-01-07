package org.castle.djames.bankease.archrules;

import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.GeneralCodingRules;

public class LoggingRules {

    /**
     * Ensures no classes use System.out or System.err to promote proper logging practices.
     */
    @ArchTest
    public ArchRule noAccessToStandardStreams = GeneralCodingRules.NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS;


    /**
     * Ensures no classes use java.util.logging to enforce consistent logging.
     */
    @ArchTest
    public ArchRule noAccessToJavaUtilLogging = GeneralCodingRules.NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING;

}
