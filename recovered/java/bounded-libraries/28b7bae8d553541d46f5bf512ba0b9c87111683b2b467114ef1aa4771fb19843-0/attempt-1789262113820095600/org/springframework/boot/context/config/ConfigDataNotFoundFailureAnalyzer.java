/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.context.config;

import org.springframework.boot.context.config.ConfigDataLocation;
import org.springframework.boot.context.config.ConfigDataLocationNotFoundException;
import org.springframework.boot.context.config.ConfigDataNotFoundException;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;
import org.springframework.boot.origin.Origin;

class ConfigDataNotFoundFailureAnalyzer
extends AbstractFailureAnalyzer<ConfigDataNotFoundException> {
    ConfigDataNotFoundFailureAnalyzer() {
    }

    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, ConfigDataNotFoundException cause) {
        ConfigDataLocation location = this.getLocation(cause);
        Origin origin = Origin.from(location);
        String message = String.format("Config data %s does not exist", cause.getReferenceDescription());
        StringBuilder action = new StringBuilder("Check that the value ");
        if (location != null) {
            action.append(String.format("'%s' ", location));
        }
        if (origin != null) {
            action.append(String.format("at %s ", origin));
        }
        action.append("is correct");
        if (location != null && !location.isOptional()) {
            action.append(String.format(", or prefix it with '%s'", "optional:"));
        }
        return new FailureAnalysis(message, action.toString(), cause);
    }

    private ConfigDataLocation getLocation(ConfigDataNotFoundException cause) {
        if (cause instanceof ConfigDataLocationNotFoundException) {
            return ((ConfigDataLocationNotFoundException)cause).getLocation();
        }
        if (cause instanceof ConfigDataResourceNotFoundException) {
            return ((ConfigDataResourceNotFoundException)cause).getLocation();
        }
        return null;
    }
}

