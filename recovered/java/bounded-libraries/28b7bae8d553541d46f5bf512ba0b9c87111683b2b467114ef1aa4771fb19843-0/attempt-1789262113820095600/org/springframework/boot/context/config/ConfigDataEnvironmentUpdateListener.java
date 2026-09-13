/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.env.PropertySource
 */
package org.springframework.boot.context.config;

import java.util.EventListener;
import org.springframework.boot.context.config.ConfigDataLocation;
import org.springframework.boot.context.config.ConfigDataResource;
import org.springframework.boot.context.config.Profiles;
import org.springframework.core.env.PropertySource;

public interface ConfigDataEnvironmentUpdateListener
extends EventListener {
    public static final ConfigDataEnvironmentUpdateListener NONE = new ConfigDataEnvironmentUpdateListener(){};

    default public void onPropertySourceAdded(PropertySource<?> propertySource, ConfigDataLocation location, ConfigDataResource resource) {
    }

    default public void onSetProfiles(Profiles profiles) {
    }
}

