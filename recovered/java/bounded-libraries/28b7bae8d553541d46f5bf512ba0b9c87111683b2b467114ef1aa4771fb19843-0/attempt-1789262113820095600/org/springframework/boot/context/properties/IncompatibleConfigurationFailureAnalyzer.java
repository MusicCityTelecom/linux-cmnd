/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.context.properties;

import java.util.stream.Collectors;
import org.springframework.boot.context.properties.IncompatibleConfigurationException;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

class IncompatibleConfigurationFailureAnalyzer
extends AbstractFailureAnalyzer<IncompatibleConfigurationException> {
    IncompatibleConfigurationFailureAnalyzer() {
    }

    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, IncompatibleConfigurationException cause) {
        String action = String.format("Review the docs for %s and change the configured values.", cause.getIncompatibleKeys().stream().collect(Collectors.joining(", ")));
        return new FailureAnalysis(cause.getMessage(), action, cause);
    }
}

