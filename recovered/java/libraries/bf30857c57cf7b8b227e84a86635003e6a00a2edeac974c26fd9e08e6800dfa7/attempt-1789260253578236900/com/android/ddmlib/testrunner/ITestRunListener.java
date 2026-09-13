/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib.testrunner;

import com.android.ddmlib.testrunner.TestIdentifier;
import java.util.Map;

public interface ITestRunListener {
    public void testRunStarted(String var1, int var2);

    public void testStarted(TestIdentifier var1);

    default public void testStarted(TestIdentifier test, long startTime) {
        this.testStarted(test);
    }

    public void testFailed(TestIdentifier var1, String var2);

    public void testAssumptionFailure(TestIdentifier var1, String var2);

    public void testIgnored(TestIdentifier var1);

    public void testEnded(TestIdentifier var1, Map<String, String> var2);

    default public void testEnded(TestIdentifier test, long endTime, Map<String, String> testMetrics) {
        this.testEnded(test, testMetrics);
    }

    public void testRunFailed(String var1);

    public void testRunStopped(long var1);

    public void testRunEnded(long var1, Map<String, String> var3);
}

