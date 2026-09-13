/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib.testrunner;

import com.android.ddmlib.Log;
import com.android.ddmlib.MultiLineReceiver;
import com.android.ddmlib.testrunner.ITestRunListener;
import com.android.ddmlib.testrunner.TestIdentifier;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InstrumentationResultParser
extends MultiLineReceiver {
    private static final String STREAM = "stream";
    private static final Set<String> KNOWN_KEYS = new HashSet<String>();
    private final Collection<ITestRunListener> mTestListeners;
    private final String mTestRunName;
    private TestResult mCurrentTestResult = null;
    private TestResult mLastTestResult = null;
    private String mCurrentKey = null;
    private StringBuilder mCurrentValue = null;
    private boolean mTestStartReported = false;
    private boolean mTestRunFinished = false;
    private boolean mTestRunFailReported = false;
    private Long mTestTime = null;
    private boolean mIsCancelled = false;
    private int mNumTestsRun = 0;
    private int mNumTestsExpected = 0;
    private boolean mInInstrumentationResultKey = false;
    private boolean mEnforceTimeStamp = false;
    private String mStreamError = null;
    private String mOnError = null;
    private Map<String, String> mInstrumentationResultBundle = new HashMap<String, String>();
    private Map<String, String> mTestMetrics = new HashMap<String, String>();
    private static final String LOG_TAG = "InstrumentationResultParser";
    static final String NO_TEST_RESULTS_MSG = "No test results";
    static final String INCOMPLETE_TEST_ERR_MSG_PREFIX = "Test failed to run to completion";
    static final String INCOMPLETE_TEST_ERR_MSG_POSTFIX = "Check device logcat for details";
    static final String INCOMPLETE_RUN_ERR_MSG_PREFIX = "Test run failed to complete";
    static final String FATAL_EXCEPTION_MSG = "Fatal exception when running tests";
    public static final String INVALID_OUTPUT_ERR_MSG = "Output from instrumentation is missing its time stamp";

    public InstrumentationResultParser(String runName, Collection<ITestRunListener> listeners) {
        this.mTestRunName = runName;
        this.mTestListeners = new ArrayList<ITestRunListener>(listeners);
    }

    public InstrumentationResultParser(String runName, ITestRunListener listener) {
        this(runName, Collections.singletonList(listener));
    }

    @Override
    public void processNewLines(String[] lines) {
        for (String line : lines) {
            this.parse(line);
            Log.v(LOG_TAG, line);
        }
    }

    private void parse(String line) {
        if (line.startsWith("INSTRUMENTATION_STATUS_CODE: ")) {
            this.submitCurrentKeyValue();
            this.mInInstrumentationResultKey = false;
            this.parseStatusCode(line);
        } else if (line.startsWith("INSTRUMENTATION_STATUS: ")) {
            this.submitCurrentKeyValue();
            this.mInInstrumentationResultKey = false;
            this.parseKey(line, "INSTRUMENTATION_STATUS: ".length());
        } else if (line.startsWith("INSTRUMENTATION_RESULT: ")) {
            this.submitCurrentKeyValue();
            this.mInInstrumentationResultKey = true;
            this.parseKey(line, "INSTRUMENTATION_RESULT: ".length());
        } else if (line.startsWith("INSTRUMENTATION_FAILED: ") || line.startsWith("INSTRUMENTATION_CODE: ")) {
            this.submitCurrentKeyValue();
            this.mInInstrumentationResultKey = false;
            this.mTestRunFinished = true;
        } else if (line.startsWith("Time: ")) {
            this.parseTime(line);
        } else if (line.startsWith("onError:")) {
            this.mOnError = line;
        } else if (this.mCurrentValue != null) {
            this.mCurrentValue.append("\r\n");
            this.mCurrentValue.append(line);
        } else if (!line.trim().isEmpty()) {
            Log.d(LOG_TAG, "unrecognized line " + line);
        }
    }

    private void submitCurrentKeyValue() {
        if (this.mCurrentKey != null && this.mCurrentValue != null) {
            String statusValue = this.mCurrentValue.toString();
            if (this.mInInstrumentationResultKey) {
                if (!KNOWN_KEYS.contains(this.mCurrentKey)) {
                    this.mInstrumentationResultBundle.put(this.mCurrentKey, statusValue);
                } else if (this.mCurrentKey.equals("shortMsg")) {
                    this.handleTestRunFailed(String.format("Instrumentation run failed due to '%1$s'", statusValue));
                } else if (STREAM.equals(this.mCurrentKey) && statusValue != null && statusValue.contains(FATAL_EXCEPTION_MSG)) {
                    this.mStreamError = statusValue;
                }
            } else {
                TestResult testInfo = this.getCurrentTestInfo();
                if (this.mCurrentKey.equals("class")) {
                    testInfo.mTestClass = statusValue.trim();
                } else if (this.mCurrentKey.equals("test")) {
                    testInfo.mTestName = statusValue.trim();
                } else if (this.mCurrentKey.equals("numtests")) {
                    try {
                        testInfo.mNumTests = Integer.parseInt(statusValue);
                    }
                    catch (NumberFormatException e2) {
                        Log.w(LOG_TAG, "Unexpected integer number of tests, received " + statusValue);
                    }
                } else if (this.mCurrentKey.equals("Error")) {
                    this.handleTestRunFailed(statusValue);
                } else if (this.mCurrentKey.equals("stack")) {
                    testInfo.mStackTrace = statusValue;
                } else if (!KNOWN_KEYS.contains(this.mCurrentKey)) {
                    this.mTestMetrics.put(this.mCurrentKey, statusValue);
                }
            }
            this.mCurrentKey = null;
            this.mCurrentValue = null;
        }
    }

    private Map<String, String> getAndResetTestMetrics() {
        Map<String, String> retVal = this.mTestMetrics;
        this.mTestMetrics = new HashMap<String, String>();
        return retVal;
    }

    private TestResult getCurrentTestInfo() {
        if (this.mCurrentTestResult == null) {
            this.mCurrentTestResult = new TestResult();
        }
        return this.mCurrentTestResult;
    }

    private void clearCurrentTestInfo() {
        this.mLastTestResult = this.mCurrentTestResult;
        this.mCurrentTestResult = null;
    }

    private void parseKey(String line, int keyStartPos) {
        int endKeyPos = line.indexOf(61, keyStartPos);
        if (endKeyPos != -1) {
            this.mCurrentKey = line.substring(keyStartPos, endKeyPos).trim();
            this.parseValue(line, endKeyPos + 1);
        }
    }

    private void parseValue(String line, int valueStartPos) {
        this.mCurrentValue = new StringBuilder();
        this.mCurrentValue.append(line.substring(valueStartPos));
    }

    private void parseStatusCode(String line) {
        String value = line.substring("INSTRUMENTATION_STATUS_CODE: ".length()).trim();
        TestResult testInfo = this.getCurrentTestInfo();
        testInfo.mCode = -1;
        try {
            testInfo.mCode = Integer.parseInt(value);
        }
        catch (NumberFormatException e2) {
            Log.w(LOG_TAG, "Expected integer status code, received: " + value);
            testInfo.mCode = -1;
        }
        if (testInfo.mCode != 2) {
            this.reportResult(testInfo);
            this.clearCurrentTestInfo();
        }
    }

    @Override
    public boolean isCancelled() {
        return this.mIsCancelled;
    }

    public void cancel() {
        this.mIsCancelled = true;
    }

    private void reportResult(TestResult testInfo) {
        if (!testInfo.isComplete()) {
            Log.w(LOG_TAG, "invalid instrumentation status bundle " + testInfo.toString());
            return;
        }
        this.reportTestRunStarted(testInfo);
        TestIdentifier testId = new TestIdentifier(testInfo.mTestClass, testInfo.mTestName);
        switch (testInfo.mCode) {
            case 1: {
                for (ITestRunListener listener : this.mTestListeners) {
                    listener.testStarted(testId);
                }
                break;
            }
            case -2: {
                Map<String, String> metrics = this.getAndResetTestMetrics();
                for (ITestRunListener listener : this.mTestListeners) {
                    listener.testFailed(testId, this.getTrace(testInfo));
                    listener.testEnded(testId, metrics);
                }
                ++this.mNumTestsRun;
                break;
            }
            case -1: {
                Map<String, String> metrics = this.getAndResetTestMetrics();
                for (ITestRunListener listener : this.mTestListeners) {
                    listener.testFailed(testId, this.getTrace(testInfo));
                    listener.testEnded(testId, metrics);
                }
                ++this.mNumTestsRun;
                break;
            }
            case -3: {
                Map<String, String> metrics = this.getAndResetTestMetrics();
                for (ITestRunListener listener : this.mTestListeners) {
                    listener.testIgnored(testId);
                    listener.testEnded(testId, metrics);
                }
                ++this.mNumTestsRun;
                break;
            }
            case -4: {
                Map<String, String> metrics = this.getAndResetTestMetrics();
                for (ITestRunListener listener : this.mTestListeners) {
                    listener.testAssumptionFailure(testId, this.getTrace(testInfo));
                    listener.testEnded(testId, metrics);
                }
                ++this.mNumTestsRun;
                break;
            }
            case 0: {
                Map<String, String> metrics = this.getAndResetTestMetrics();
                for (ITestRunListener listener : this.mTestListeners) {
                    listener.testEnded(testId, metrics);
                }
                ++this.mNumTestsRun;
                break;
            }
            default: {
                Map<String, String> metrics = this.getAndResetTestMetrics();
                Log.e(LOG_TAG, "Unknown status code received: " + testInfo.mCode);
                for (ITestRunListener listener : this.mTestListeners) {
                    listener.testEnded(testId, metrics);
                }
                ++this.mNumTestsRun;
            }
        }
    }

    private void reportTestRunStarted(TestResult testInfo) {
        if (!this.mTestStartReported && testInfo.mNumTests != null) {
            for (ITestRunListener listener : this.mTestListeners) {
                listener.testRunStarted(this.mTestRunName, testInfo.mNumTests);
            }
            this.mNumTestsExpected = testInfo.mNumTests;
            this.mTestStartReported = true;
        }
    }

    private String getTrace(TestResult testInfo) {
        if (testInfo.mStackTrace != null) {
            return testInfo.mStackTrace;
        }
        Log.e(LOG_TAG, "Could not find stack trace for failed test ");
        return new Throwable("Unknown failure").toString();
    }

    private void parseTime(String line) {
        Pattern timePattern = Pattern.compile(String.format("%s\\s*([\\d\\,]*[\\d\\.]+)", "Time: "));
        Matcher timeMatcher = timePattern.matcher(line);
        if (timeMatcher.find()) {
            String timeString = timeMatcher.group(1);
            try {
                Number n2 = NumberFormat.getInstance().parse(timeString);
                float timeSeconds = n2.floatValue();
                this.mTestTime = (long)(timeSeconds * 1000.0f);
            }
            catch (ParseException e2) {
                Log.w(LOG_TAG, String.format("Unexpected time format %1$s", line));
            }
        } else {
            Log.w(LOG_TAG, String.format("Unexpected time format %1$s", line));
        }
    }

    public void handleTestRunFailed(String errorMsg) {
        errorMsg = errorMsg == null ? "Unknown error" : errorMsg;
        Log.i(LOG_TAG, String.format("test run failed: '%1$s'", errorMsg));
        if (this.mLastTestResult != null && this.mLastTestResult.isComplete() && 1 == this.mLastTestResult.mCode) {
            TestIdentifier testId = new TestIdentifier(this.mLastTestResult.mTestClass, this.mLastTestResult.mTestName);
            for (ITestRunListener listener : this.mTestListeners) {
                listener.testFailed(testId, String.format("%1$s. Reason: '%2$s'. %3$s", INCOMPLETE_TEST_ERR_MSG_PREFIX, errorMsg, INCOMPLETE_TEST_ERR_MSG_POSTFIX));
                listener.testEnded(testId, this.getAndResetTestMetrics());
            }
        }
        for (ITestRunListener listener : this.mTestListeners) {
            if (!this.mTestStartReported) {
                listener.testRunStarted(this.mTestRunName, 0);
            }
            String runErrorMsg = errorMsg;
            if (this.mOnError != null) {
                runErrorMsg = String.format("%s. %s", errorMsg, this.mOnError);
            }
            listener.testRunFailed(runErrorMsg);
            if (this.mTestTime == null) {
                this.mTestTime = 0L;
            }
            listener.testRunEnded(this.mTestTime, this.mInstrumentationResultBundle);
        }
        this.mOnError = null;
        this.mTestStartReported = true;
        this.mTestRunFailReported = true;
    }

    @Override
    public void done() {
        super.done();
        if (!this.mTestRunFailReported) {
            this.handleOutputDone();
        }
    }

    private void handleOutputDone() {
        if (!this.mTestStartReported && !this.mTestRunFinished) {
            this.handleTestRunFailed(NO_TEST_RESULTS_MSG);
        } else if (this.mNumTestsExpected > this.mNumTestsRun) {
            String message = String.format("%1$s. Expected %2$d tests, received %3$d", INCOMPLETE_RUN_ERR_MSG_PREFIX, this.mNumTestsExpected, this.mNumTestsRun);
            this.handleTestRunFailed(message);
        } else {
            for (ITestRunListener listener : this.mTestListeners) {
                if (!this.mTestStartReported) {
                    listener.testRunStarted(this.mTestRunName, 0);
                }
                if (this.mTestTime == null) {
                    if (this.mEnforceTimeStamp) {
                        if (this.mStreamError == null) {
                            listener.testRunFailed(INVALID_OUTPUT_ERR_MSG);
                        } else {
                            listener.testRunFailed(String.format("%s: %s", INVALID_OUTPUT_ERR_MSG, this.mStreamError));
                        }
                        this.mTestRunFailReported = true;
                    }
                    this.mTestTime = 0L;
                }
                if (!this.mTestRunFailReported && this.mStreamError != null && this.mStreamError.contains(FATAL_EXCEPTION_MSG)) {
                    listener.testRunFailed(this.mStreamError.trim());
                }
                listener.testRunEnded(this.mTestTime, this.mInstrumentationResultBundle);
            }
        }
    }

    public void setEnforceTimeStamp(boolean isEnforced) {
        this.mEnforceTimeStamp = isEnforced;
    }

    static {
        KNOWN_KEYS.add("test");
        KNOWN_KEYS.add("class");
        KNOWN_KEYS.add("stack");
        KNOWN_KEYS.add("numtests");
        KNOWN_KEYS.add("Error");
        KNOWN_KEYS.add("shortMsg");
        KNOWN_KEYS.add(STREAM);
        KNOWN_KEYS.add("id");
        KNOWN_KEYS.add("current");
    }

    private static class TestResult {
        private Integer mCode = null;
        private String mTestName = null;
        private String mTestClass = null;
        private String mStackTrace = null;
        private Integer mNumTests = null;

        private TestResult() {
        }

        boolean isComplete() {
            return this.mCode != null && this.mTestName != null && this.mTestClass != null;
        }

        public String toString() {
            StringBuilder output = new StringBuilder();
            if (this.mTestClass != null) {
                output.append(this.mTestClass);
                output.append('#');
            }
            if (this.mTestName != null) {
                output.append(this.mTestName);
            }
            if (output.length() > 0) {
                return output.toString();
            }
            return "unknown result";
        }
    }

    private static class Prefixes {
        private static final String STATUS = "INSTRUMENTATION_STATUS: ";
        private static final String STATUS_CODE = "INSTRUMENTATION_STATUS_CODE: ";
        private static final String STATUS_FAILED = "INSTRUMENTATION_FAILED: ";
        private static final String ON_ERROR = "onError:";
        private static final String CODE = "INSTRUMENTATION_CODE: ";
        private static final String RESULT = "INSTRUMENTATION_RESULT: ";
        private static final String TIME_REPORT = "Time: ";

        private Prefixes() {
        }
    }

    private static class StatusCodes {
        private static final int START = 1;
        private static final int IN_PROGRESS = 2;
        private static final int ASSUMPTION_FAILURE = -4;
        private static final int IGNORED = -3;
        private static final int FAILURE = -2;
        private static final int ERROR = -1;
        private static final int OK = 0;

        private StatusCodes() {
        }
    }

    private static class StatusKeys {
        private static final String TEST = "test";
        private static final String CLASS = "class";
        private static final String STACK = "stack";
        private static final String NUMTESTS = "numtests";
        private static final String ERROR = "Error";
        private static final String SHORTMSG = "shortMsg";

        private StatusKeys() {
        }
    }
}

