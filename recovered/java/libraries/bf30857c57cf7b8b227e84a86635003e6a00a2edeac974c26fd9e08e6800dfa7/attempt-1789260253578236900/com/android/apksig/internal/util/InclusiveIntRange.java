/*
 * Decompiled with CFR 0.152.
 */
package com.android.apksig.internal.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InclusiveIntRange {
    private final int min;
    private final int max;

    private InclusiveIntRange(int min2, int max) {
        this.min = min2;
        this.max = max;
    }

    public int getMin() {
        return this.min;
    }

    public int getMax() {
        return this.max;
    }

    public static InclusiveIntRange fromTo(int min2, int max) {
        return new InclusiveIntRange(min2, max);
    }

    public static InclusiveIntRange from(int min2) {
        return new InclusiveIntRange(min2, Integer.MAX_VALUE);
    }

    public List<InclusiveIntRange> getValuesNotIn(List<InclusiveIntRange> sortedNonOverlappingRanges) {
        if (sortedNonOverlappingRanges.isEmpty()) {
            return Collections.singletonList(this);
        }
        int testValue = this.min;
        List<InclusiveIntRange> result = null;
        for (InclusiveIntRange range : sortedNonOverlappingRanges) {
            int rangeMax = range.max;
            if (testValue > rangeMax) continue;
            int rangeMin = range.min;
            if (testValue < range.min) {
                if (result == null) {
                    result = new ArrayList<InclusiveIntRange>();
                }
                result.add(InclusiveIntRange.fromTo(testValue, rangeMin - 1));
            }
            if (rangeMax >= this.max) {
                return result != null ? result : Collections.emptyList();
            }
            testValue = rangeMax + 1;
        }
        if (testValue <= this.max) {
            if (result == null) {
                result = new ArrayList<InclusiveIntRange>(1);
            }
            result.add(InclusiveIntRange.fromTo(testValue, this.max));
        }
        return result != null ? result : Collections.emptyList();
    }

    public String toString() {
        return "[" + this.min + ", " + (this.max < Integer.MAX_VALUE ? this.max + "]" : "\u221e)");
    }
}

