/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.retry.support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RetrySimulation {
    private final List<SleepSequence> sleepSequences = new ArrayList<SleepSequence>();
    private final List<Long> sleepHistogram = new ArrayList<Long>();

    public void addSequence(List<Long> sleeps) {
        this.sleepHistogram.addAll(sleeps);
        this.sleepSequences.add(new SleepSequence(sleeps));
    }

    public List<Double> getPercentiles() {
        ArrayList<Double> res = new ArrayList<Double>();
        for (double percentile : new double[]{10.0, 20.0, 30.0, 40.0, 50.0, 60.0, 70.0, 80.0, 90.0}) {
            res.add(this.getPercentile(percentile / 100.0));
        }
        return res;
    }

    public double getPercentile(double p) {
        Collections.sort(this.sleepHistogram);
        int size = this.sleepHistogram.size();
        double pos = p * (double)(size - 1);
        int i0 = (int)pos;
        int i1 = i0 + 1;
        double weight = pos - (double)i0;
        return (double)this.sleepHistogram.get(i0).longValue() * (1.0 - weight) + (double)this.sleepHistogram.get(i1).longValue() * weight;
    }

    public SleepSequence getLongestTotalSleepSequence() {
        SleepSequence longest = null;
        for (SleepSequence sequence : this.sleepSequences) {
            if (longest != null && sequence.getTotalSleep() <= longest.getTotalSleep()) continue;
            longest = sequence;
        }
        return longest;
    }

    public static class SleepSequence {
        private final List<Long> sleeps;
        private final long longestSleep;
        private final long totalSleep;

        public SleepSequence(List<Long> sleeps) {
            this.sleeps = sleeps;
            this.longestSleep = Collections.max(sleeps);
            long totalSleep = 0L;
            for (Long sleep : sleeps) {
                totalSleep += sleep.longValue();
            }
            this.totalSleep = totalSleep;
        }

        public List<Long> getSleeps() {
            return this.sleeps;
        }

        public long getLongestSleep() {
            return this.longestSleep;
        }

        public long getTotalSleep() {
            return this.totalSleep;
        }

        public String toString() {
            return "totalSleep=" + this.totalSleep + ": " + this.sleeps.toString();
        }
    }
}

