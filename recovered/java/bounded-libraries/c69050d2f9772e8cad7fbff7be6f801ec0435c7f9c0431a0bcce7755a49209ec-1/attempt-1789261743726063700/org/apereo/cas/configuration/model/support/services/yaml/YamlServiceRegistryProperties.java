/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.core.io.ClassPathResource
 *  org.springframework.core.io.Resource
 */
package org.apereo.cas.configuration.model.support.services.yaml;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.SpringResourceProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@RequiresModule(name="cas-server-support-yaml-service-registry")
@JsonFilter(value="YamlServiceRegistryProperties")
public class YamlServiceRegistryProperties
extends SpringResourceProperties {
    private static final long serialVersionUID = 4863603996990314548L;
    private boolean watcherEnabled = true;

    public YamlServiceRegistryProperties() {
        this.setLocation((Resource)new ClassPathResource("services"));
    }

    @Generated
    public boolean isWatcherEnabled() {
        return this.watcherEnabled;
    }

    @Generated
    public YamlServiceRegistryProperties setWatcherEnabled(boolean watcherEnabled) {
        this.watcherEnabled = watcherEnabled;
        return this;
    }
}

