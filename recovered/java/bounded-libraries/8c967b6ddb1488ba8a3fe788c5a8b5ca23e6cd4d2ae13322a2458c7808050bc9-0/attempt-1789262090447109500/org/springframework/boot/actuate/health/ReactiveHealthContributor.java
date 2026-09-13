/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.boot.actuate.health;

import org.springframework.boot.actuate.health.CompositeHealthContributor;
import org.springframework.boot.actuate.health.CompositeHealthContributorReactiveAdapter;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.HealthIndicatorReactiveAdapter;
import org.springframework.util.Assert;

public interface ReactiveHealthContributor {
    public static ReactiveHealthContributor adapt(HealthContributor healthContributor) {
        Assert.notNull((Object)healthContributor, (String)"HealthContributor must not be null");
        if (healthContributor instanceof HealthIndicator) {
            return new HealthIndicatorReactiveAdapter((HealthIndicator)healthContributor);
        }
        if (healthContributor instanceof CompositeHealthContributor) {
            return new CompositeHealthContributorReactiveAdapter((CompositeHealthContributor)healthContributor);
        }
        throw new IllegalStateException("Unknown HealthContributor type");
    }
}

