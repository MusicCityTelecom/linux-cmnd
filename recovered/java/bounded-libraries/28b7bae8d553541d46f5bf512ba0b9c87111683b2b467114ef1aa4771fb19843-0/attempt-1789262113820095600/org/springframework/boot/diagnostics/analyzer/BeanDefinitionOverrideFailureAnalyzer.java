/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.support.BeanDefinitionOverrideException
 */
package org.springframework.boot.diagnostics.analyzer;

import java.io.PrintWriter;
import java.io.StringWriter;
import org.springframework.beans.factory.support.BeanDefinitionOverrideException;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

class BeanDefinitionOverrideFailureAnalyzer
extends AbstractFailureAnalyzer<BeanDefinitionOverrideException> {
    private static final String ACTION = "Consider renaming one of the beans or enabling overriding by setting spring.main.allow-bean-definition-overriding=true";

    BeanDefinitionOverrideFailureAnalyzer() {
    }

    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, BeanDefinitionOverrideException cause) {
        return new FailureAnalysis(this.getDescription(cause), ACTION, (Throwable)cause);
    }

    private String getDescription(BeanDefinitionOverrideException ex) {
        StringWriter description = new StringWriter();
        PrintWriter printer = new PrintWriter(description);
        printer.printf("The bean '%s'", ex.getBeanName());
        if (ex.getBeanDefinition().getResourceDescription() != null) {
            printer.printf(", defined in %s,", ex.getBeanDefinition().getResourceDescription());
        }
        printer.printf(" could not be registered. A bean with that name has already been defined ", new Object[0]);
        if (ex.getExistingDefinition().getResourceDescription() != null) {
            printer.printf("in %s ", ex.getExistingDefinition().getResourceDescription());
        }
        printer.printf("and overriding is disabled.", new Object[0]);
        return description.toString();
    }
}

