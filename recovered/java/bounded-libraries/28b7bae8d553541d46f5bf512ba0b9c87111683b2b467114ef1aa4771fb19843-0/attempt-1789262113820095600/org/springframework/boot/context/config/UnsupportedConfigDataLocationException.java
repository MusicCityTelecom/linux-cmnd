/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.context.config;

import org.springframework.boot.context.config.ConfigDataException;
import org.springframework.boot.context.config.ConfigDataLocation;

public class UnsupportedConfigDataLocationException
extends ConfigDataException {
    private final ConfigDataLocation location;

    UnsupportedConfigDataLocationException(ConfigDataLocation location) {
        super("Unsupported config data location '" + location + "'", null);
        this.location = location;
    }

    public ConfigDataLocation getLocation() {
        return this.location;
    }
}

