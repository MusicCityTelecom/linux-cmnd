/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.io.Resource
 *  org.springframework.util.Assert
 */
package org.springframework.boot.context.config;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import org.springframework.boot.context.config.ConfigDataLocation;
import org.springframework.boot.context.config.ConfigDataNotFoundException;
import org.springframework.boot.context.config.ConfigDataResource;
import org.springframework.boot.origin.Origin;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

public class ConfigDataResourceNotFoundException
extends ConfigDataNotFoundException {
    private final ConfigDataResource resource;
    private final ConfigDataLocation location;

    public ConfigDataResourceNotFoundException(ConfigDataResource resource) {
        this(resource, null);
    }

    public ConfigDataResourceNotFoundException(ConfigDataResource resource, Throwable cause) {
        this(resource, null, cause);
    }

    private ConfigDataResourceNotFoundException(ConfigDataResource resource, ConfigDataLocation location, Throwable cause) {
        super(ConfigDataResourceNotFoundException.getMessage(resource, location), cause);
        Assert.notNull((Object)resource, (String)"Resource must not be null");
        this.resource = resource;
        this.location = location;
    }

    public ConfigDataResource getResource() {
        return this.resource;
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
        return ConfigDataResourceNotFoundException.getReferenceDescription(this.resource, this.location);
    }

    ConfigDataResourceNotFoundException withLocation(ConfigDataLocation location) {
        return new ConfigDataResourceNotFoundException(this.resource, location, this.getCause());
    }

    private static String getMessage(ConfigDataResource resource, ConfigDataLocation location) {
        return String.format("Config data %s cannot be found", ConfigDataResourceNotFoundException.getReferenceDescription(resource, location));
    }

    private static String getReferenceDescription(ConfigDataResource resource, ConfigDataLocation location) {
        String description = String.format("resource '%s'", resource);
        if (location != null) {
            description = description + String.format(" via location '%s'", location);
        }
        return description;
    }

    public static void throwIfDoesNotExist(ConfigDataResource resource, Path pathToCheck) {
        ConfigDataResourceNotFoundException.throwIfDoesNotExist(resource, Files.exists(pathToCheck, new LinkOption[0]));
    }

    public static void throwIfDoesNotExist(ConfigDataResource resource, File fileToCheck) {
        ConfigDataResourceNotFoundException.throwIfDoesNotExist(resource, fileToCheck.exists());
    }

    public static void throwIfDoesNotExist(ConfigDataResource resource, Resource resourceToCheck) {
        ConfigDataResourceNotFoundException.throwIfDoesNotExist(resource, resourceToCheck.exists());
    }

    private static void throwIfDoesNotExist(ConfigDataResource resource, boolean exists) {
        if (!exists) {
            throw new ConfigDataResourceNotFoundException(resource);
        }
    }
}

