/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 *  reactor.core.publisher.Mono
 *  reactor.core.scheduler.Schedulers
 */
package org.springframework.boot.actuate.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.ReactiveHealthIndicator;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

class HealthIndicatorReactiveAdapter
implements ReactiveHealthIndicator {
    private final HealthIndicator delegate;

    HealthIndicatorReactiveAdapter(HealthIndicator delegate) {
        Assert.notNull((Object)delegate, (String)"Delegate must not be null");
        this.delegate = delegate;
    }

    @Override
    public Mono<Health> health() {
        return Mono.fromCallable(this.delegate::health).subscribeOn(Schedulers.boundedElastic());
    }
}

