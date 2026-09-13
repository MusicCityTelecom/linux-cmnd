/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.utils.counter;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;
import org.quartz.utils.counter.Counter;

public class CounterImpl
implements Counter,
Serializable {
    private static final long serialVersionUID = -1529134342654953984L;
    private AtomicLong value;

    public CounterImpl() {
        this(0L);
    }

    public CounterImpl(long initialValue) {
        this.value = new AtomicLong(initialValue);
    }

    @Override
    public long increment() {
        return this.value.incrementAndGet();
    }

    @Override
    public long decrement() {
        return this.value.decrementAndGet();
    }

    @Override
    public long getAndSet(long newValue) {
        return this.value.getAndSet(newValue);
    }

    @Override
    public long getValue() {
        return this.value.get();
    }

    @Override
    public long increment(long amount) {
        return this.value.addAndGet(amount);
    }

    @Override
    public long decrement(long amount) {
        return this.value.addAndGet(amount * -1L);
    }

    @Override
    public void setValue(long newValue) {
        this.value.set(newValue);
    }
}

