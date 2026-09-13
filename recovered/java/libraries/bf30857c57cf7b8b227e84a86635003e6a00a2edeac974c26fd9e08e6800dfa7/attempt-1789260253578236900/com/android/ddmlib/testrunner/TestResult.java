/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib.testrunner;

import java.util.Arrays;
import java.util.Map;

public class TestResult {
    private TestStatus mStatus = TestStatus.INCOMPLETE;
    private String mStackTrace;
    private Map<String, String> mMetrics;
    private long mStartTime = System.currentTimeMillis();
    private long mEndTime = 0L;

    public TestStatus getStatus() {
        return this.mStatus;
    }

    public String getStackTrace() {
        return this.mStackTrace;
    }

    public Map<String, String> getMetrics() {
        return this.mMetrics;
    }

    public void setMetrics(Map<String, String> metrics) {
        this.mMetrics = metrics;
    }

    public long getStartTime() {
        return this.mStartTime;
    }

    public void setStartTime(long startTime) {
        this.mStartTime = startTime;
    }

    public long getEndTime() {
        return this.mEndTime;
    }

    public TestResult setStatus(TestStatus status) {
        this.mStatus = status;
        return this;
    }

    public void setStackTrace(String trace) {
        this.mStackTrace = trace;
    }

    public void setEndTime(long currentTimeMillis) {
        this.mEndTime = currentTimeMillis;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.mMetrics, this.mStackTrace, this.mStatus});
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        TestResult other = (TestResult)obj;
        return TestResult.equal(this.mMetrics, other.mMetrics) && TestResult.equal(this.mStackTrace, other.mStackTrace) && TestResult.equal((Object)this.mStatus, (Object)other.mStatus);
    }

    private static boolean equal(Object a2, Object b2) {
        return a2 == b2 || a2 != null && a2.equals(b2);
    }

    public static enum TestStatus {
        FAILURE,
        PASSED,
        INCOMPLETE,
        ASSUMPTION_FAILURE,
        IGNORED;

    }
}

