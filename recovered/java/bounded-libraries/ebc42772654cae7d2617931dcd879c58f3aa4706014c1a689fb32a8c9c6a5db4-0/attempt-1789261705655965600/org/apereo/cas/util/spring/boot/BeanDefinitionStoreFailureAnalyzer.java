/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.exception.ExceptionUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.BeanDefinitionStoreException
 *  org.springframework.boot.diagnostics.AbstractFailureAnalyzer
 *  org.springframework.boot.diagnostics.FailureAnalysis
 */
package org.apereo.cas.util.spring.boot;

import java.io.PrintWriter;
import java.io.StringWriter;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apereo.cas.util.LoggingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

public class BeanDefinitionStoreFailureAnalyzer
extends AbstractFailureAnalyzer<BeanDefinitionStoreException> {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(BeanDefinitionStoreFailureAnalyzer.class);
    private static final String ANALYSIS = "Review the properties available for the configuration. Enable debug logging on " + BeanDefinitionStoreFailureAnalyzer.class.getName() + " to see exception stack trace";

    protected FailureAnalysis analyze(Throwable rootFailure, BeanDefinitionStoreException cause) {
        if (LOGGER.isDebugEnabled()) {
            LoggingUtils.error(LOGGER, BeanDefinitionStoreFailureAnalyzer.getDescription(cause), (Throwable)cause);
        }
        return new FailureAnalysis(BeanDefinitionStoreFailureAnalyzer.getDescription(cause), ANALYSIS, (Throwable)cause);
    }

    private static String getDescription(BeanDefinitionStoreException ex) {
        String causedMsg = ExceptionUtils.getRootCauseMessage((Throwable)ex);
        StringWriter description = new StringWriter();
        PrintWriter printer = new PrintWriter(description);
        printer.printf("Error creating bean", new Object[0]);
        if (ex.getBeanName() != null) {
            printer.printf(" named %s", ex.getBeanName());
        }
        if (ex.getResourceDescription() != null) {
            printer.printf(", with resource description %s,", ex.getResourceDescription());
        }
        printer.printf(" due to: %s ", ex.getMessage());
        if (StringUtils.isNotBlank((CharSequence)causedMsg)) {
            printer.printf(" caused by %s ", causedMsg);
        }
        return description.toString();
    }
}

