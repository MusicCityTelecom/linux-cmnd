/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package org.springframework.boot.actuate.health;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.springframework.boot.actuate.endpoint.ApiVersion;
import org.springframework.boot.actuate.health.CompositeHealth;
import org.springframework.boot.actuate.health.HealthComponent;
import org.springframework.boot.actuate.health.Status;

public final class SystemHealth
extends CompositeHealth {
    private final Set<String> groups;

    SystemHealth(ApiVersion apiVersion, Status status, Map<String, HealthComponent> instances, Set<String> groups) {
        super(apiVersion, status, instances);
        this.groups = groups != null ? new TreeSet<String>(groups) : null;
    }

    @JsonInclude(value=JsonInclude.Include.NON_EMPTY)
    public Set<String> getGroups() {
        return this.groups;
    }
}

