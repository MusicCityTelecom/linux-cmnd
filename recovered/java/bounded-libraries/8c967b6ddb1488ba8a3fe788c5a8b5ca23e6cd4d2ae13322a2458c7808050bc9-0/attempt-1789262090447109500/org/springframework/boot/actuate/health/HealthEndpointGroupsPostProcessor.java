/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.health;

import org.springframework.boot.actuate.health.HealthEndpointGroups;

@FunctionalInterface
public interface HealthEndpointGroupsPostProcessor {
    public HealthEndpointGroups postProcessHealthEndpointGroups(HealthEndpointGroups var1);
}

