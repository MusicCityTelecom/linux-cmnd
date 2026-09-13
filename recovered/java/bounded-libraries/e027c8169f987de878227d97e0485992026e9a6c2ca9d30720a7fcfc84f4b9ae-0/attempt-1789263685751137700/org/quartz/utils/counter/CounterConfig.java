/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.utils.counter;

import org.quartz.utils.counter.Counter;
import org.quartz.utils.counter.CounterImpl;

public class CounterConfig {
    private final long initialValue;

    public CounterConfig(long initialValue) {
        this.initialValue = initialValue;
    }

    public final long getInitialValue() {
        return this.initialValue;
    }

    public Counter createCounter() {
        return new CounterImpl(this.initialValue);
    }
}

