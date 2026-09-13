/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  reactor.core.publisher.Mono
 */
package org.springframework.boot.actuate.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.ReactiveHealthContributor;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface ReactiveHealthIndicator
extends ReactiveHealthContributor {
    default public Mono<Health> getHealth(boolean includeDetails) {
        Mono health = this.health();
        return includeDetails ? health : health.map(Health::withoutDetails);
    }

    public Mono<Health> health();
}

