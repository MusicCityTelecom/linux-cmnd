/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.utils.counter;

import org.quartz.utils.counter.Counter;
import org.quartz.utils.counter.CounterConfig;

public interface CounterManager {
    public Counter createCounter(CounterConfig var1);

    public void shutdown(boolean var1);

    public void shutdownCounter(Counter var1);
}

