/*
 * Decompiled with CFR 0.152.
 */
package io.micrometer.core.instrument.step;

import io.micrometer.core.instrument.Clock;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

public abstract class StepValue<V> {
    private final Clock clock;
    private final long stepMillis;
    private AtomicLong lastInitPos;
    private volatile V previous = this.noValue();

    public StepValue(Clock clock, long stepMillis) {
        this.clock = clock;
        this.stepMillis = stepMillis;
        this.lastInitPos = new AtomicLong(clock.wallTime() / stepMillis);
    }

    protected abstract Supplier<V> valueSupplier();

    protected abstract V noValue();

    private void rollCount(long now) {
        long stepTime = now / this.stepMillis;
        long lastInit = this.lastInitPos.get();
        if (lastInit < stepTime && this.lastInitPos.compareAndSet(lastInit, stepTime)) {
            V v = this.valueSupplier().get();
            this.previous = lastInit == stepTime - 1L ? v : this.noValue();
        }
    }

    public V poll() {
        this.rollCount(this.clock.wallTime());
        return this.previous;
    }
}

