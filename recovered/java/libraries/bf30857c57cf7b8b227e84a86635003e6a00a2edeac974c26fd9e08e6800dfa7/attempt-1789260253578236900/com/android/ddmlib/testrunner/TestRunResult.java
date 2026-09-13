/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib.testrunner;

import com.android.ddmlib.Log;
import com.android.ddmlib.testrunner.ITestRunListener;
import com.android.ddmlib.testrunner.TestIdentifier;
import com.android.ddmlib.testrunner.TestResult;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class TestRunResult
implements ITestRunListener {
    private static final String LOG_TAG = TestRunResult.class.getSimpleName();
    private String mTestRunName = "not started";
    private Map<TestIdentifier, TestResult> mTestResults = new LinkedHashMap<TestIdentifier, TestResult>();
    private Map<String, String> mRunMetrics = new HashMap<String, String>();
    private boolean mIsRunComplete = false;
    private long mElapsedTime = 0L;
    private int[] mStatusCounts = new int[TestResult.TestStatus.values().length];
    private boolean mIsCountDirty = true;
    private String mRunFailureError = null;
    private boolean mAggregateMetrics = false;

    public void setAggregateMetrics(boolean metricAggregation) {
        this.mAggregateMetrics = metricAggregation;
    }

    public String getName() {
        return this.mTestRunName;
    }

    public Map<TestIdentifier, TestResult> getTestResults() {
        return this.mTestResults;
    }

    public Map<String, String> getRunMetrics() {
        return this.mRunMetrics;
    }

    public Set<TestIdentifier> getCompletedTests() {
        LinkedHashSet<TestIdentifier> completedTests = new LinkedHashSet<TestIdentifier>();
        for (Map.Entry<TestIdentifier, TestResult> testEntry : this.getTestResults().entrySet()) {
            if (testEntry.getValue().getStatus().equals((Object)TestResult.TestStatus.INCOMPLETE)) continue;
            completedTests.add(testEntry.getKey());
        }
        return completedTests;
    }

    public boolean isRunFailure() {
        return this.mRunFailureError != null;
    }

    public boolean isRunComplete() {
        return this.mIsRunComplete;
    }

    public void setRunComplete(boolean runComplete) {
        this.mIsRunComplete = runComplete;
    }

    public int getNumTestsInState(TestResult.TestStatus status) {
        if (this.mIsCountDirty) {
            for (int i2 = 0; i2 < this.mStatusCounts.length; ++i2) {
                this.mStatusCounts[i2] = 0;
            }
            for (TestResult r3 : this.mTestResults.values()) {
                int n2 = r3.getStatus().ordinal();
                this.mStatusCounts[n2] = this.mStatusCounts[n2] + 1;
            }
            this.mIsCountDirty = false;
        }
        return this.mStatusCounts[status.ordinal()];
    }

    public int getNumTests() {
        return this.mTestResults.size();
    }

    public int getNumCompleteTests() {
        return this.getNumTests() - this.getNumTestsInState(TestResult.TestStatus.INCOMPLETE);
    }

    public boolean hasFailedTests() {
        return this.getNumAllFailedTests() > 0;
    }

    public int getNumAllFailedTests() {
        return this.getNumTestsInState(TestResult.TestStatus.FAILURE);
    }

    public long getElapsedTime() {
        return this.mElapsedTime;
    }

    public String getRunFailureMessage() {
        return this.mRunFailureError;
    }

    @Override
    public void testRunStarted(String runName, int testCount) {
        this.mTestRunName = runName;
        this.mIsRunComplete = false;
        this.mRunFailureError = null;
    }

    @Override
    public void testStarted(TestIdentifier test) {
        this.testStarted(test, System.currentTimeMillis());
    }

    @Override
    public void testStarted(TestIdentifier test, long startTime) {
        TestResult res = new TestResult();
        res.setStartTime(startTime);
        this.addTestResult(test, res);
    }

    private void addTestResult(TestIdentifier test, TestResult testResult) {
        this.mIsCountDirty = true;
        this.mTestResults.put(test, testResult);
    }

    private void updateTestResult(TestIdentifier test, TestResult.TestStatus status, String trace) {
        TestResult r3 = this.mTestResults.get(test);
        if (r3 == null) {
            Log.d(LOG_TAG, String.format("received test event without test start for %s", test));
            r3 = new TestResult();
        }
        r3.setStatus(status);
        r3.setStackTrace(trace);
        this.addTestResult(test, r3);
    }

    @Override
    public void testFailed(TestIdentifier test, String trace) {
        this.updateTestResult(test, TestResult.TestStatus.FAILURE, trace);
    }

    @Override
    public void testAssumptionFailure(TestIdentifier test, String trace) {
        this.updateTestResult(test, TestResult.TestStatus.ASSUMPTION_FAILURE, trace);
    }

    @Override
    public void testIgnored(TestIdentifier test) {
        this.updateTestResult(test, TestResult.TestStatus.IGNORED, null);
    }

    @Override
    public void testEnded(TestIdentifier test, Map<String, String> testMetrics) {
        this.testEnded(test, System.currentTimeMillis(), testMetrics);
    }

    @Override
    public void testEnded(TestIdentifier test, long endTime, Map<String, String> testMetrics) {
        TestResult result = this.mTestResults.get(test);
        if (result == null) {
            result = new TestResult();
        }
        if (result.getStatus().equals((Object)TestResult.TestStatus.INCOMPLETE)) {
            result.setStatus(TestResult.TestStatus.PASSED);
        }
        result.setEndTime(endTime);
        result.setMetrics(testMetrics);
        this.addTestResult(test, result);
    }

    @Override
    public void testRunFailed(String errorMessage) {
        this.mRunFailureError = errorMessage;
    }

    @Override
    public void testRunStopped(long elapsedTime) {
        this.mElapsedTime += elapsedTime;
        this.mIsRunComplete = true;
    }

    @Override
    public void testRunEnded(long elapsedTime, Map<String, String> runMetrics) {
        if (this.mAggregateMetrics) {
            for (Map.Entry<String, String> entry : runMetrics.entrySet()) {
                String existingValue = this.mRunMetrics.get(entry.getKey());
                String combinedValue = this.combineValues(existingValue, entry.getValue());
                this.mRunMetrics.put(entry.getKey(), combinedValue);
            }
        } else {
            this.mRunMetrics.putAll(runMetrics);
        }
        this.mElapsedTime += elapsedTime;
        this.mIsRunComplete = true;
    }

    private String combineValues(String existingValue, String newValue) {
        if (existingValue != null) {
            try {
                Long existingLong = Long.parseLong(existingValue);
                Long newLong = Long.parseLong(newValue);
                return Long.toString(existingLong + newLong);
            }
            catch (NumberFormatException existingLong) {
                try {
                    Double existingDouble = Double.parseDouble(existingValue);
                    Double newDouble = Double.parseDouble(newValue);
                    return Double.toString(existingDouble + newDouble);
                }
                catch (NumberFormatException numberFormatException) {
                    // empty catch block
                }
            }
        }
        return newValue;
    }

    public String getTextSummary() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Total tests %d, ", this.getNumTests()));
        for (TestResult.TestStatus status : TestResult.TestStatus.values()) {
            int count = this.getNumTestsInState(status);
            if (count <= 0) continue;
            builder.append(String.format("%s %d, ", status.toString().toLowerCase(), count));
        }
        return builder.toString();
    }
}

