/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.diagnostics.AbstractFailureAnalyzer
 *  org.springframework.boot.diagnostics.FailureAnalysis
 *  org.springframework.boot.r2dbc.EmbeddedDatabaseConnection
 *  org.springframework.core.env.Environment
 *  org.springframework.util.ObjectUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.autoconfigure.r2dbc;

import org.springframework.boot.autoconfigure.r2dbc.ConnectionFactoryOptionsInitializer;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;
import org.springframework.boot.r2dbc.EmbeddedDatabaseConnection;
import org.springframework.core.env.Environment;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

class ConnectionFactoryBeanCreationFailureAnalyzer
extends AbstractFailureAnalyzer<ConnectionFactoryOptionsInitializer.ConnectionFactoryBeanCreationException> {
    private final Environment environment;

    ConnectionFactoryBeanCreationFailureAnalyzer(Environment environment) {
        this.environment = environment;
    }

    protected FailureAnalysis analyze(Throwable rootFailure, ConnectionFactoryOptionsInitializer.ConnectionFactoryBeanCreationException cause) {
        return this.getFailureAnalysis(cause);
    }

    private FailureAnalysis getFailureAnalysis(ConnectionFactoryOptionsInitializer.ConnectionFactoryBeanCreationException cause) {
        String description = this.getDescription(cause);
        String action = this.getAction(cause);
        return new FailureAnalysis(description, action, (Throwable)((Object)cause));
    }

    private String getDescription(ConnectionFactoryOptionsInitializer.ConnectionFactoryBeanCreationException cause) {
        StringBuilder description = new StringBuilder();
        description.append("Failed to configure a ConnectionFactory: ");
        if (!StringUtils.hasText((String)cause.getProperties().getUrl())) {
            description.append("'url' attribute is not specified and ");
        }
        description.append(String.format("no embedded database could be configured.%n", new Object[0]));
        description.append(String.format("%nReason: %s%n", cause.getMessage()));
        return description.toString();
    }

    private String getAction(ConnectionFactoryOptionsInitializer.ConnectionFactoryBeanCreationException cause) {
        StringBuilder action = new StringBuilder();
        action.append(String.format("Consider the following:%n", new Object[0]));
        if (EmbeddedDatabaseConnection.NONE == cause.getEmbeddedDatabaseConnection()) {
            action.append(String.format("\tIf you want an embedded database (H2), please put it on the classpath.%n", new Object[0]));
        } else {
            action.append(String.format("\tReview the configuration of %s%n.", cause.getEmbeddedDatabaseConnection()));
        }
        action.append("\tIf you have database settings to be loaded from a particular profile you may need to activate it").append(this.getActiveProfiles());
        return action.toString();
    }

    private String getActiveProfiles() {
        StringBuilder message = new StringBuilder();
        Object[] profiles = this.environment.getActiveProfiles();
        if (ObjectUtils.isEmpty((Object[])profiles)) {
            message.append(" (no profiles are currently active).");
        } else {
            message.append(" (the profiles ");
            message.append(StringUtils.arrayToCommaDelimitedString((Object[])profiles));
            message.append(" are currently active).");
        }
        return message.toString();
    }
}

