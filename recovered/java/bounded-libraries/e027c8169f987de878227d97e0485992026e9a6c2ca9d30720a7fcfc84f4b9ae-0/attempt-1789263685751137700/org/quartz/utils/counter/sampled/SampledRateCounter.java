/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.utils.counter.sampled;

import org.quartz.utils.counter.sampled.SampledCounter;

public interface SampledRateCounter
extends SampledCounter {
    public void increment(long var1, long var3);

    public void decrement(long var1, long var3);

    public void setValue(long var1, long var3);

    public void setNumeratorValue(long var1);

    public void setDenominatorValue(long var1);
}

