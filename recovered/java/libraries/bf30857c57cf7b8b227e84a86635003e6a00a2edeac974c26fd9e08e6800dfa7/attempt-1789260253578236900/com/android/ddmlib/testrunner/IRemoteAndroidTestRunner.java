/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib.testrunner;

import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.ShellCommandUnresponsiveException;
import com.android.ddmlib.TimeoutException;
import com.android.ddmlib.testrunner.ITestRunListener;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

public interface IRemoteAndroidTestRunner {
    public String getPackageName();

    public String getRunnerName();

    public void setClassName(String var1);

    public void setClassNames(String[] var1);

    public void setMethodName(String var1, String var2);

    public void setTestPackageName(String var1);

    public void setTestSize(TestSize var1);

    public void addInstrumentationArg(String var1, String var2);

    public void removeInstrumentationArg(String var1);

    public void addBooleanArg(String var1, boolean var2);

    public void setLogOnly(boolean var1);

    public void setDebug(boolean var1);

    public void setCoverage(boolean var1);

    public void setCoverageReportLocation(String var1);

    public CoverageOutput getCoverageOutputType();

    public void setEnforceTimeStamp(boolean var1);

    public void setTestCollection(boolean var1);

    @Deprecated
    public void setMaxtimeToOutputResponse(int var1);

    public void setMaxTimeToOutputResponse(long var1, TimeUnit var3);

    public void setMaxTimeout(long var1, TimeUnit var3);

    public void setRunName(String var1);

    public void run(ITestRunListener ... var1) throws TimeoutException, AdbCommandRejectedException, ShellCommandUnresponsiveException, IOException;

    public void run(Collection<ITestRunListener> var1) throws TimeoutException, AdbCommandRejectedException, ShellCommandUnresponsiveException, IOException;

    public void cancel();

    public static enum CoverageOutput {
        DIR,
        FILE;

    }

    public static enum TestSize {
        SMALL("small"),
        MEDIUM("medium"),
        LARGE("large");

        private String mRunnerValue;

        private TestSize(String runnerValue) {
            this.mRunnerValue = runnerValue;
        }

        String getRunnerValue() {
            return this.mRunnerValue;
        }

        public static TestSize getTestSize(String value) {
            StringBuilder msgBuilder = new StringBuilder("Unknown TestSize ");
            msgBuilder.append(value);
            msgBuilder.append(", Must be one of ");
            for (TestSize size : TestSize.values()) {
                if (size.getRunnerValue().equals(value)) {
                    return size;
                }
                msgBuilder.append(size.getRunnerValue());
                msgBuilder.append(", ");
            }
            throw new IllegalArgumentException(msgBuilder.toString());
        }
    }
}

