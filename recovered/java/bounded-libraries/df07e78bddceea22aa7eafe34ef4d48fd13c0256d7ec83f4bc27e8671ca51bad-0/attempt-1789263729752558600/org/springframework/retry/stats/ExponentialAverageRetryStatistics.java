/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.retry.stats;

import org.springframework.retry.stats.DefaultRetryStatistics;

public class ExponentialAverageRetryStatistics
extends DefaultRetryStatistics {
    private long window = 15000L;
    private ExponentialAverage started;
    private ExponentialAverage error;
    private ExponentialAverage complete;
    private ExponentialAverage recovery;
    private ExponentialAverage abort;

    public ExponentialAverageRetryStatistics(String name) {
        super(name);
        this.init();
    }

    private void init() {
        this.started = new ExponentialAverage(this.window);
        this.error = new ExponentialAverage(this.window);
        this.complete = new ExponentialAverage(this.window);
        this.abort = new ExponentialAverage(this.window);
        this.recovery = new ExponentialAverage(this.window);
    }

    public void setWindow(long window) {
        this.window = window;
        this.init();
    }

    public int getRollingStartedCount() {
        return (int)Math.round(this.started.getValue());
    }

    public int getRollingErrorCount() {
        return (int)Math.round(this.error.getValue());
    }

    public int getRollingAbortCount() {
        return (int)Math.round(this.abort.getValue());
    }

    public int getRollingRecoveryCount() {
        return (int)Math.round(this.recovery.getValue());
    }

    public int getRollingCompleteCount() {
        return (int)Math.round(this.complete.getValue());
    }

    public double getRollingErrorRate() {
        if (Math.round(this.started.getValue()) == 0L) {
            return 0.0;
        }
        return (this.abort.getValue() + this.recovery.getValue()) / this.started.getValue();
    }

    @Override
    public void incrementStartedCount() {
        super.incrementStartedCount();
        this.started.increment();
    }

    @Override
    public void incrementCompleteCount() {
        super.incrementCompleteCount();
        this.complete.increment();
    }

    @Override
    public void incrementRecoveryCount() {
        super.incrementRecoveryCount();
        this.recovery.increment();
    }

    @Override
    public void incrementErrorCount() {
        super.incrementErrorCount();
        this.error.increment();
    }

    @Override
    public void incrementAbortCount() {
        super.incrementAbortCount();
        this.abort.increment();
    }

    private class ExponentialAverage {
        private final double alpha;
        private volatile long lastTime = System.currentTimeMillis();
        private volatile double value = 0.0;

        public ExponentialAverage(long window) {
            this.alpha = 1.0 / (double)window;
        }

        public synchronized void increment() {
            long time = System.currentTimeMillis();
            this.value = this.value * Math.exp(-this.alpha * (double)(time - this.lastTime)) + 1.0;
            this.lastTime = time;
        }

        public double getValue() {
            long time = System.currentTimeMillis();
            return this.value * Math.exp(-this.alpha * (double)(time - this.lastTime));
        }
    }
}

