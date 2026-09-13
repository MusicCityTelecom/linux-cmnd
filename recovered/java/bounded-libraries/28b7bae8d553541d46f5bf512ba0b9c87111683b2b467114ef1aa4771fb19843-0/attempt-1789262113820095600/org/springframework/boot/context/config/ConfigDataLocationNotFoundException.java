/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.boot.context.config;

import org.springframework.boot.context.config.ConfigDataLocation;
import org.springframework.boot.context.config.ConfigDataNotFoundException;
import org.springframework.boot.origin.Origin;
import org.springframework.util.Assert;

public class ConfigDataLocationNotFoundException
extends ConfigDataNotFoundException {
    private final ConfigDataLocation location;

    public ConfigDataLocationNotFoundException(ConfigDataLocation location) {
        this(location, null);
    }

    public ConfigDataLocationNotFoundException(ConfigDataLocation location, Throwable cause) {
        this(location, ConfigDataLocationNotFoundException.getMessage(location), cause);
    }

    public ConfigDataLocationNotFoundException(ConfigDataLocation location, String message, Throwable cause) {
        super(message, cause);
        Assert.notNull((Object)location, (String)"Location must not be null");
        this.location = location;
    }

    public ConfigDataLocation getLocation() {
        return this.location;
    }

    @Override
    public Origin getOrigin() {
        return Origin.from(this.location);
    }

    @Override
    public String getReferenceDescription() {
        return ConfigDataLocationNotFoundException.getReferenceDescription(this.location);
    }

    private static String getMessage(ConfigDataLocation location) {
        return String.format("Config data %s cannot be found", ConfigDataLocationNotFoundException.getReferenceDescription(location));
    }

    private static String getReferenceDescription(ConfigDataLocation location) {
        return String.format("location '%s'", location);
    }
}

