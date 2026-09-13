/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.health;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import org.springframework.boot.actuate.health.SimpleStatusAggregator;
import org.springframework.boot.actuate.health.Status;

@FunctionalInterface
public interface StatusAggregator {
    public static StatusAggregator getDefault() {
        return SimpleStatusAggregator.INSTANCE;
    }

    default public Status getAggregateStatus(Status ... statuses) {
        return this.getAggregateStatus(new LinkedHashSet<Status>(Arrays.asList(statuses)));
    }

    public Status getAggregateStatus(Set<Status> var1);
}

