/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.utils.counter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import org.quartz.utils.counter.Counter;
import org.quartz.utils.counter.CounterConfig;
import org.quartz.utils.counter.CounterManager;
import org.quartz.utils.counter.sampled.SampledCounter;
import org.quartz.utils.counter.sampled.SampledCounterImpl;

public class CounterManagerImpl
implements CounterManager {
    private Timer timer;
    private boolean shutdown;
    private List<Counter> counters = new ArrayList<Counter>();

    public CounterManagerImpl(Timer timer) {
        if (timer == null) {
            throw new IllegalArgumentException("Timer cannot be null");
        }
        this.timer = timer;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public synchronized void shutdown(boolean killTimer) {
        if (this.shutdown) {
            return;
        }
        try {
            for (Counter counter : this.counters) {
                if (!(counter instanceof SampledCounter)) continue;
                ((SampledCounter)counter).shutdown();
            }
            if (killTimer) {
                this.timer.cancel();
            }
        }
        finally {
            this.shutdown = true;
        }
    }

    @Override
    public synchronized Counter createCounter(CounterConfig config) {
        if (this.shutdown) {
            throw new IllegalStateException("counter manager is shutdown");
        }
        if (config == null) {
            throw new NullPointerException("config cannot be null");
        }
        Counter counter = config.createCounter();
        if (counter instanceof SampledCounterImpl) {
            SampledCounterImpl sampledCounter = (SampledCounterImpl)counter;
            this.timer.schedule(sampledCounter.getTimerTask(), sampledCounter.getIntervalMillis(), sampledCounter.getIntervalMillis());
        }
        this.counters.add(counter);
        return counter;
    }

    @Override
    public void shutdownCounter(Counter counter) {
        if (counter instanceof SampledCounter) {
            SampledCounter sc = (SampledCounter)counter;
            sc.shutdown();
        }
    }
}

