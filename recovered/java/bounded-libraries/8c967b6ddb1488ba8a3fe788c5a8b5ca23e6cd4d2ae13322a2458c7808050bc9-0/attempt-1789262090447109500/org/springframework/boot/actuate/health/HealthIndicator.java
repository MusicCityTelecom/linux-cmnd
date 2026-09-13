/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthContributor;

@FunctionalInterface
public interface HealthIndicator
extends HealthContributor {
    default public Health getHealth(boolean includeDetails) {
        Health health = this.health();
        return includeDetails ? health : health.withoutDetails();
    }

    public Health health();
}

