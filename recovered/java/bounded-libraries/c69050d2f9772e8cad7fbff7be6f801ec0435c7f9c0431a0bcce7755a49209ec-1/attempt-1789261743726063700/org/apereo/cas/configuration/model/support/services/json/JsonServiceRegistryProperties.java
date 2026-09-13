/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.core.io.ClassPathResource
 *  org.springframework.core.io.Resource
 */
package org.apereo.cas.configuration.model.support.services.json;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.SpringResourceProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@RequiresModule(name="cas-server-support-json-service-registry")
@JsonFilter(value="JsonServiceRegistryProperties")
public class JsonServiceRegistryProperties
extends SpringResourceProperties {
    private static final long serialVersionUID = -3022199446494732533L;
    private boolean watcherEnabled = true;

    public JsonServiceRegistryProperties() {
        this.setLocation((Resource)new ClassPathResource("services"));
    }

    @Generated
    public boolean isWatcherEnabled() {
        return this.watcherEnabled;
    }

    @Generated
    public JsonServiceRegistryProperties setWatcherEnabled(boolean watcherEnabled) {
        this.watcherEnabled = watcherEnabled;
        return this;
    }
}

