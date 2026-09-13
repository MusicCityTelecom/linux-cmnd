/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib.testrunner;

import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.IShellEnabledDevice;
import com.android.ddmlib.Log;
import com.android.ddmlib.ShellCommandUnresponsiveException;
import com.android.ddmlib.TimeoutException;
import com.android.ddmlib.testrunner.IRemoteAndroidTestRunner;
import com.android.ddmlib.testrunner.ITestRunListener;
import com.android.ddmlib.testrunner.InstrumentationResultParser;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RemoteAndroidTestRunner
implements IRemoteAndroidTestRunner {
    private final String mPackageName;
    private final String mRunnerName;
    private IShellEnabledDevice mRemoteDevice;
    private long mMaxTimeoutMs = 0L;
    private long mMaxTimeToOutputResponseMs = 0L;
    private String mRunName = null;
    private boolean mEnforceTimeStamp = false;
    private Map<String, String> mArgMap;
    private InstrumentationResultParser mParser;
    private static final String LOG_TAG = "RemoteAndroidTest";
    private static final String DEFAULT_RUNNER_NAME = "android.test.InstrumentationTestRunner";
    private static final char CLASS_SEPARATOR = ',';
    private static final char METHOD_SEPARATOR = '#';
    private static final char RUNNER_SEPARATOR = '/';
    private static final String CLASS_ARG_NAME = "class";
    private static final String LOG_ARG_NAME = "log";
    private static final String DEBUG_ARG_NAME = "debug";
    private static final String COVERAGE_ARG_NAME = "coverage";
    private static final String PACKAGE_ARG_NAME = "package";
    private static final String SIZE_ARG_NAME = "size";
    private static final String DELAY_MSEC_ARG_NAME = "delay_msec";
    private String mRunOptions = "";
    private static final int TEST_COLLECTION_TIMEOUT = 120000;

    public RemoteAndroidTestRunner(String packageName, String runnerName, IShellEnabledDevice remoteDevice) {
        this.mPackageName = packageName;
        this.mRunnerName = runnerName;
        this.mRemoteDevice = remoteDevice;
        this.mArgMap = new Hashtable<String, String>();
    }

    public RemoteAndroidTestRunner(String packageName, IShellEnabledDevice remoteDevice) {
        this(packageName, null, remoteDevice);
    }

    @Override
    public String getPackageName() {
        return this.mPackageName;
    }

    @Override
    public String getRunnerName() {
        if (this.mRunnerName == null) {
            return DEFAULT_RUNNER_NAME;
        }
        return this.mRunnerName;
    }

    protected String getRunnerPath() {
        return this.getPackageName() + '/' + this.getRunnerName();
    }

    @Override
    public void setClassName(String className) {
        this.addInstrumentationArg(CLASS_ARG_NAME, "'" + className + "'");
    }

    @Override
    public void setClassNames(String[] classNames) {
        StringBuilder classArgBuilder = new StringBuilder();
        for (int i2 = 0; i2 < classNames.length; ++i2) {
            if (i2 != 0) {
                classArgBuilder.append(',');
            }
            classArgBuilder.append(classNames[i2]);
        }
        this.setClassName(classArgBuilder.toString());
    }

    @Override
    public void setMethodName(String className, String testName) {
        this.setClassName(className + '#' + testName);
    }

    @Override
    public void setTestPackageName(String packageName) {
        this.addInstrumentationArg(PACKAGE_ARG_NAME, packageName);
    }

    @Override
    public void addInstrumentationArg(String name, String value) {
        if (name == null || value == null) {
            throw new IllegalArgumentException("name or value arguments cannot be null");
        }
        this.mArgMap.put(name, value);
    }

    @Override
    public void removeInstrumentationArg(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name argument cannot be null");
        }
        this.mArgMap.remove(name);
    }

    @Override
    public void addBooleanArg(String name, boolean value) {
        this.addInstrumentationArg(name, Boolean.toString(value));
    }

    @Override
    public void setLogOnly(boolean logOnly) {
        this.addBooleanArg(LOG_ARG_NAME, logOnly);
    }

    @Override
    public void setDebug(boolean debug) {
        this.addBooleanArg(DEBUG_ARG_NAME, debug);
    }

    @Override
    public void setCoverage(boolean coverage) {
        this.addBooleanArg(COVERAGE_ARG_NAME, coverage);
    }

    @Override
    public void setCoverageReportLocation(String reportPath) {
        this.addInstrumentationArg("coverageFile", reportPath);
    }

    @Override
    public IRemoteAndroidTestRunner.CoverageOutput getCoverageOutputType() {
        return IRemoteAndroidTestRunner.CoverageOutput.FILE;
    }

    @Override
    public void setEnforceTimeStamp(boolean timestamp) {
        this.mEnforceTimeStamp = timestamp;
    }

    @Override
    public void setTestSize(IRemoteAndroidTestRunner.TestSize size) {
        this.addInstrumentationArg(SIZE_ARG_NAME, size.getRunnerValue());
    }

    @Override
    public void setTestCollection(boolean collect) {
        if (collect) {
            this.setLogOnly(true);
            this.setMaxTimeToOutputResponse(120000L, TimeUnit.MILLISECONDS);
            if (this.getApiLevel() < 16) {
                this.addInstrumentationArg(DELAY_MSEC_ARG_NAME, "15");
            }
        } else {
            this.setLogOnly(false);
            this.setMaxTimeToOutputResponse(this.mMaxTimeToOutputResponseMs, TimeUnit.MILLISECONDS);
            if (this.getApiLevel() < 16) {
                this.removeInstrumentationArg(DELAY_MSEC_ARG_NAME);
            }
        }
    }

    private int getApiLevel() {
        try {
            return Integer.parseInt(this.mRemoteDevice.getSystemProperty("ro.build.version.sdk").get());
        }
        catch (Exception e2) {
            return -1;
        }
    }

    @Override
    @Deprecated
    public void setMaxtimeToOutputResponse(int maxTimeToOutputResponse) {
        this.setMaxTimeToOutputResponse(maxTimeToOutputResponse, TimeUnit.MILLISECONDS);
    }

    @Override
    public void setMaxTimeToOutputResponse(long maxTimeToOutputResponse, TimeUnit maxTimeUnits) {
        this.mMaxTimeToOutputResponseMs = maxTimeUnits.toMillis(maxTimeToOutputResponse);
    }

    @Override
    public void setMaxTimeout(long maxTimeout, TimeUnit maxTimeUnits) {
        this.mMaxTimeoutMs = maxTimeUnits.toMillis(maxTimeout);
    }

    @Override
    public void setRunName(String runName) {
        this.mRunName = runName;
    }

    @Override
    public void run(ITestRunListener ... listeners) throws TimeoutException, AdbCommandRejectedException, ShellCommandUnresponsiveException, IOException {
        this.run(Arrays.asList(listeners));
    }

    @Override
    public void run(Collection<ITestRunListener> listeners) throws TimeoutException, AdbCommandRejectedException, ShellCommandUnresponsiveException, IOException {
        String runCaseCommandStr = this.getAmInstrumentCommand();
        Log.i(LOG_TAG, String.format("Running %1$s on %2$s", runCaseCommandStr, this.mRemoteDevice.getName()));
        String runName = this.mRunName == null ? this.mPackageName : this.mRunName;
        this.mParser = this.createParser(runName, listeners);
        this.mParser.setEnforceTimeStamp(this.mEnforceTimeStamp);
        try {
            this.mRemoteDevice.executeShellCommand(runCaseCommandStr, this.mParser, this.mMaxTimeoutMs, this.mMaxTimeToOutputResponseMs, TimeUnit.MILLISECONDS);
        }
        catch (IOException e2) {
            Log.w(LOG_TAG, String.format("IOException %1$s when running tests %2$s on %3$s", e2.toString(), this.getPackageName(), this.mRemoteDevice.getName()));
            this.mParser.handleTestRunFailed(e2.toString());
            throw e2;
        }
        catch (ShellCommandUnresponsiveException e3) {
            Log.w(LOG_TAG, String.format("ShellCommandUnresponsiveException %1$s when running tests %2$s on %3$s", e3.toString(), this.getPackageName(), this.mRemoteDevice.getName()));
            this.mParser.handleTestRunFailed(String.format("Failed to receive adb shell test output within %1$d ms. Test may have timed out, or adb connection to device became unresponsive", this.mMaxTimeToOutputResponseMs));
            throw e3;
        }
        catch (TimeoutException e4) {
            Log.w(LOG_TAG, String.format("TimeoutException when running tests %1$s on %2$s", this.getPackageName(), this.mRemoteDevice.getName()));
            this.mParser.handleTestRunFailed(e4.toString());
            throw e4;
        }
        catch (AdbCommandRejectedException e5) {
            Log.w(LOG_TAG, String.format("AdbCommandRejectedException %1$s when running tests %2$s on %3$s", e5.toString(), this.getPackageName(), this.mRemoteDevice.getName()));
            this.mParser.handleTestRunFailed(e5.toString());
            throw e5;
        }
    }

    public InstrumentationResultParser createParser(String runName, Collection<ITestRunListener> listeners) {
        return new InstrumentationResultParser(runName, listeners);
    }

    public String getAmInstrumentCommand() {
        return String.format("am instrument -w -r %1$s %2$s %3$s", this.getRunOptions(), this.getArgsCommand(), this.getRunnerPath());
    }

    public String getRunOptions() {
        return this.mRunOptions;
    }

    public void setRunOptions(String options) {
        this.mRunOptions = options;
    }

    @Override
    public void cancel() {
        if (this.mParser != null) {
            this.mParser.cancel();
        }
    }

    protected String getArgsCommand() {
        StringBuilder commandBuilder = new StringBuilder();
        for (Map.Entry<String, String> argPair : this.mArgMap.entrySet()) {
            String argCmd = String.format(" -e %1$s %2$s", argPair.getKey(), argPair.getValue());
            commandBuilder.append(argCmd);
        }
        return commandBuilder.toString();
    }
}

