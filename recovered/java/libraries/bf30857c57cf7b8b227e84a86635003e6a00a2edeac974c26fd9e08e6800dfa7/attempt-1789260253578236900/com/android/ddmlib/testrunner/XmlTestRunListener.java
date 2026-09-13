/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib.testrunner;

import com.android.ddmlib.Log;
import com.android.ddmlib.testrunner.ITestRunListener;
import com.android.ddmlib.testrunner.TestIdentifier;
import com.android.ddmlib.testrunner.TestResult;
import com.android.ddmlib.testrunner.TestRunResult;
import com.google.common.collect.ImmutableMap;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import org.kxml2.io.KXmlSerializer;

public class XmlTestRunListener
implements ITestRunListener {
    private static final String LOG_TAG = "XmlResultReporter";
    private static final String TEST_RESULT_FILE_SUFFIX = ".xml";
    private static final String TEST_RESULT_FILE_PREFIX = "test_result_";
    private static final String TESTSUITE = "testsuite";
    private static final String TESTCASE = "testcase";
    private static final String ERROR = "error";
    private static final String FAILURE = "failure";
    private static final String SKIPPED_TAG = "skipped";
    private static final String ATTR_NAME = "name";
    private static final String ATTR_TIME = "time";
    private static final String ATTR_ERRORS = "errors";
    private static final String ATTR_FAILURES = "failures";
    private static final String ATTR_SKIPPED = "skipped";
    private static final String ATTR_ASSERTIOMS = "assertions";
    private static final String ATTR_TESTS = "tests";
    private static final String PROPERTIES = "properties";
    private static final String PROPERTY = "property";
    private static final String ATTR_CLASSNAME = "classname";
    private static final String TIMESTAMP = "timestamp";
    private static final String HOSTNAME = "hostname";
    private static final String ns = null;
    private String mHostName = "localhost";
    private File mReportDir = new File(System.getProperty("java.io.tmpdir"));
    private String mReportPath = "";
    private TestRunResult mRunResult = new TestRunResult();

    public void setReportDir(File file) {
        this.mReportDir = file;
    }

    public void setHostName(String hostName) {
        this.mHostName = hostName;
    }

    public TestRunResult getRunResult() {
        return this.mRunResult;
    }

    @Override
    public void testRunStarted(String runName, int numTests) {
        this.mRunResult = new TestRunResult();
        this.mRunResult.testRunStarted(runName, numTests);
    }

    @Override
    public void testStarted(TestIdentifier test) {
        this.mRunResult.testStarted(test);
    }

    @Override
    public void testFailed(TestIdentifier test, String trace) {
        this.mRunResult.testFailed(test, trace);
    }

    @Override
    public void testAssumptionFailure(TestIdentifier test, String trace) {
        this.mRunResult.testAssumptionFailure(test, trace);
    }

    @Override
    public void testIgnored(TestIdentifier test) {
        this.mRunResult.testIgnored(test);
    }

    @Override
    public void testEnded(TestIdentifier test, Map<String, String> testMetrics) {
        this.mRunResult.testEnded(test, testMetrics);
    }

    @Override
    public void testRunFailed(String errorMessage) {
        this.mRunResult.testRunFailed(errorMessage);
    }

    @Override
    public void testRunStopped(long elapsedTime) {
        this.mRunResult.testRunStopped(elapsedTime);
    }

    @Override
    public void testRunEnded(long elapsedTime, Map<String, String> runMetrics) {
        this.mRunResult.testRunEnded(elapsedTime, runMetrics);
        this.generateDocument(this.mReportDir, elapsedTime);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void generateDocument(File reportDir, long elapsedTime) {
        String timestamp = this.getTimestamp();
        OutputStream stream = null;
        try {
            stream = this.createOutputResultStream(reportDir);
            KXmlSerializer serializer = new KXmlSerializer();
            serializer.setOutput(stream, "UTF-8");
            serializer.startDocument("UTF-8", null);
            serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            this.printTestResults(serializer, timestamp, elapsedTime);
            serializer.endDocument();
            String msg = String.format("XML test result file generated at %s. %s", this.getAbsoluteReportPath(), this.mRunResult.getTextSummary());
            Log.logAndDisplay(Log.LogLevel.INFO, LOG_TAG, msg);
        }
        catch (IOException e2) {
            Log.e(LOG_TAG, "Failed to generate report data");
        }
        finally {
            if (stream != null) {
                try {
                    stream.close();
                }
                catch (IOException iOException) {}
            }
        }
    }

    private String getAbsoluteReportPath() {
        return this.mReportPath;
    }

    String getTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        TimeZone gmt = TimeZone.getTimeZone("UTC");
        dateFormat.setTimeZone(gmt);
        dateFormat.setLenient(true);
        String timestamp = dateFormat.format(new Date());
        return timestamp;
    }

    protected File getResultFile(File reportDir) throws IOException {
        File reportFile = File.createTempFile(TEST_RESULT_FILE_PREFIX, TEST_RESULT_FILE_SUFFIX, reportDir);
        Log.i(LOG_TAG, String.format("Created xml report file at %s", reportFile.getAbsolutePath()));
        return reportFile;
    }

    OutputStream createOutputResultStream(File reportDir) throws IOException {
        File reportFile = this.getResultFile(reportDir);
        this.mReportPath = reportFile.getAbsolutePath();
        return new BufferedOutputStream(new FileOutputStream(reportFile));
    }

    protected String getTestSuiteName() {
        return this.mRunResult.getName();
    }

    void printTestResults(KXmlSerializer serializer, String timestamp, long elapsedTime) throws IOException {
        serializer.startTag(ns, TESTSUITE);
        String name = this.getTestSuiteName();
        if (name != null) {
            serializer.attribute(ns, ATTR_NAME, name);
        }
        serializer.attribute(ns, ATTR_TESTS, Integer.toString(this.mRunResult.getNumTests()));
        serializer.attribute(ns, ATTR_FAILURES, Integer.toString(this.mRunResult.getNumAllFailedTests()));
        serializer.attribute(ns, ATTR_ERRORS, "0");
        serializer.attribute(ns, "skipped", Integer.toString(this.mRunResult.getNumTestsInState(TestResult.TestStatus.IGNORED)));
        serializer.attribute(ns, ATTR_TIME, Double.toString((double)elapsedTime / 1000.0));
        serializer.attribute(ns, TIMESTAMP, timestamp);
        serializer.attribute(ns, HOSTNAME, this.mHostName);
        serializer.startTag(ns, PROPERTIES);
        for (Map.Entry<String, String> entry : this.getPropertiesAttributes().entrySet()) {
            serializer.startTag(ns, PROPERTY);
            serializer.attribute(ns, ATTR_NAME, entry.getKey());
            serializer.attribute(ns, "value", entry.getValue());
            serializer.endTag(ns, PROPERTY);
        }
        serializer.endTag(ns, PROPERTIES);
        Map<TestIdentifier, TestResult> testResults = this.mRunResult.getTestResults();
        for (Map.Entry<TestIdentifier, TestResult> testEntry : testResults.entrySet()) {
            this.print(serializer, testEntry.getKey(), testEntry.getValue());
        }
        serializer.endTag(ns, TESTSUITE);
    }

    protected Map<String, String> getPropertiesAttributes() {
        return ImmutableMap.of();
    }

    protected String getTestName(TestIdentifier testId) {
        return testId.getTestName();
    }

    void print(KXmlSerializer serializer, TestIdentifier testId, TestResult testResult) throws IOException {
        serializer.startTag(ns, TESTCASE);
        serializer.attribute(ns, ATTR_NAME, this.getTestName(testId));
        serializer.attribute(ns, ATTR_CLASSNAME, testId.getClassName());
        long elapsedTimeMs = testResult.getEndTime() - testResult.getStartTime();
        serializer.attribute(ns, ATTR_TIME, Double.toString((double)elapsedTimeMs / 1000.0));
        switch (testResult.getStatus()) {
            case FAILURE: {
                this.printFailedTest(serializer, FAILURE, testResult.getStackTrace());
                break;
            }
            case ASSUMPTION_FAILURE: {
                this.printFailedTest(serializer, "skipped", testResult.getStackTrace());
                break;
            }
            case IGNORED: {
                serializer.startTag(ns, "skipped");
                serializer.endTag(ns, "skipped");
            }
        }
        serializer.endTag(ns, TESTCASE);
    }

    private void printFailedTest(KXmlSerializer serializer, String tag, String stack) throws IOException {
        serializer.startTag(ns, tag);
        serializer.text(this.sanitize(stack));
        serializer.endTag(ns, tag);
    }

    private String sanitize(String text) {
        return text.replace("\u0000", "<\\0>");
    }
}

