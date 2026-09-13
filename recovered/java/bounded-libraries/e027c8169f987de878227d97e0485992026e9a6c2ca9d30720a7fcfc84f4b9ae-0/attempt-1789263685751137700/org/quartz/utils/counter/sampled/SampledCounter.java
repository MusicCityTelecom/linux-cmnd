/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.utils.counter.sampled;

import org.quartz.utils.counter.Counter;
import org.quartz.utils.counter.sampled.TimeStampedCounterValue;

public interface SampledCounter
extends Counter {
    public void shutdown();

    public TimeStampedCounterValue getMostRecentSample();

    public TimeStampedCounterValue[] getAllSampleValues();

    public long getAndReset();
}

