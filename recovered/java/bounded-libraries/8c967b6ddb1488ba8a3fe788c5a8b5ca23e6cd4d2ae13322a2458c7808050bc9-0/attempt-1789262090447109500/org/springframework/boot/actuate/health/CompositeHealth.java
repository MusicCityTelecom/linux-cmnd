/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  org.springframework.util.Assert
 */
package org.springframework.boot.actuate.health;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.boot.actuate.endpoint.ApiVersion;
import org.springframework.boot.actuate.health.HealthComponent;
import org.springframework.boot.actuate.health.Status;
import org.springframework.util.Assert;

public class CompositeHealth
extends HealthComponent {
    private final Status status;
    private final Map<String, HealthComponent> components;
    private final Map<String, HealthComponent> details;

    CompositeHealth(ApiVersion apiVersion, Status status, Map<String, HealthComponent> components) {
        Assert.notNull((Object)status, (String)"Status must not be null");
        this.status = status;
        this.components = apiVersion != ApiVersion.V3 ? null : this.sort(components);
        this.details = apiVersion != ApiVersion.V2 ? null : this.sort(components);
    }

    private Map<String, HealthComponent> sort(Map<String, HealthComponent> components) {
        return components != null ? new TreeMap(components) : components;
    }

    @Override
    public Status getStatus() {
        return this.status;
    }

    @JsonInclude(value=JsonInclude.Include.NON_EMPTY)
    public Map<String, HealthComponent> getComponents() {
        return this.components;
    }

    @JsonInclude(value=JsonInclude.Include.NON_EMPTY)
    @JsonProperty
    public Map<String, HealthComponent> getDetails() {
        return this.details;
    }
}

