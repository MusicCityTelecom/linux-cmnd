/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.context.properties.source;

import java.lang.ref.SoftReference;
import java.time.Duration;
import java.time.Instant;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import org.springframework.boot.context.properties.source.ConfigurationPropertyCaching;

class SoftReferenceConfigurationPropertyCache<T>
implements ConfigurationPropertyCaching {
    private static final Duration UNLIMITED = Duration.ZERO;
    private final boolean neverExpire;
    private volatile Duration timeToLive;
    private volatile SoftReference<T> value = new SoftReference<Object>(null);
    private volatile Instant lastAccessed = this.now();

    SoftReferenceConfigurationPropertyCache(boolean neverExpire) {
        this.neverExpire = neverExpire;
    }

    @Override
    public void enable() {
        this.timeToLive = UNLIMITED;
    }

    @Override
    public void disable() {
        this.timeToLive = null;
    }

    @Override
    public void setTimeToLive(Duration timeToLive) {
        this.timeToLive = timeToLive == null || timeToLive.isZero() ? null : timeToLive;
    }

    @Override
    public void clear() {
        this.lastAccessed = null;
    }

    T get(Supplier<T> factory, UnaryOperator<T> refreshAction) {
        Object value = this.getValue();
        if (value == null) {
            value = refreshAction.apply(factory.get());
            this.setValue(value);
        } else if (this.hasExpired()) {
            value = refreshAction.apply(value);
            this.setValue(value);
        }
        if (!this.neverExpire) {
            this.lastAccessed = this.now();
        }
        return value;
    }

    private boolean hasExpired() {
        if (this.neverExpire) {
            return false;
        }
        Duration timeToLive = this.timeToLive;
        Instant lastAccessed = this.lastAccessed;
        if (timeToLive == null || lastAccessed == null) {
            return true;
        }
        return !UNLIMITED.equals(timeToLive) && this.now().isAfter(lastAccessed.plus(timeToLive));
    }

    protected Instant now() {
        return Instant.now();
    }

    protected T getValue() {
        return this.value.get();
    }

    protected void setValue(T value) {
        this.value = new SoftReference<T>(value);
    }
}

