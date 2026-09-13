/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.diagnostics.AbstractFailureAnalyzer
 *  org.springframework.boot.diagnostics.FailureAnalysis
 */
package org.springframework.boot.autoconfigure.session;

import org.springframework.boot.autoconfigure.session.NonUniqueSessionRepositoryException;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

class NonUniqueSessionRepositoryFailureAnalyzer
extends AbstractFailureAnalyzer<NonUniqueSessionRepositoryException> {
    NonUniqueSessionRepositoryFailureAnalyzer() {
    }

    protected FailureAnalysis analyze(Throwable rootFailure, NonUniqueSessionRepositoryException cause) {
        StringBuilder message = new StringBuilder();
        message.append(String.format("Multiple Spring Session store implementations are available on the classpath:%n", new Object[0]));
        for (Class<?> candidate : cause.getAvailableCandidates()) {
            message.append(String.format("    - %s%n", candidate.getName()));
        }
        StringBuilder action = new StringBuilder();
        action.append(String.format("Consider any of the following:%n", new Object[0]));
        action.append(String.format("    - Define the 'spring.session.store-type' property to the store you want to use%n", new Object[0]));
        action.append(String.format("    - Review your classpath and remove the unwanted store implementation(s)%n", new Object[0]));
        return new FailureAnalysis(message.toString(), action.toString(), (Throwable)cause);
    }
}

