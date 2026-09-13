/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.context.config;

import org.springframework.boot.context.config.ConfigDataLocation;
import org.springframework.boot.context.config.ConfigDataResource;

class ConfigDataResolutionResult {
    private final ConfigDataLocation location;
    private final ConfigDataResource resource;
    private final boolean profileSpecific;

    ConfigDataResolutionResult(ConfigDataLocation location, ConfigDataResource resource, boolean profileSpecific) {
        this.location = location;
        this.resource = resource;
        this.profileSpecific = profileSpecific;
    }

    ConfigDataLocation getLocation() {
        return this.location;
    }

    ConfigDataResource getResource() {
        return this.resource;
    }

    boolean isProfileSpecific() {
        return this.profileSpecific;
    }
}

