/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib.testrunner;

import com.android.ddmlib.IShellEnabledDevice;
import com.android.ddmlib.testrunner.IRemoteAndroidTestRunner;
import com.android.ddmlib.testrunner.RemoteAndroidTestRunner;
import com.android.support.AndroidxName;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import java.util.ArrayList;

public class AndroidTestOrchestratorRemoteAndroidTestRunner
extends RemoteAndroidTestRunner {
    private static final AndroidxName SERVICES_APK_PACKAGE = AndroidxName.of("android.support.test.services.");
    private static final AndroidxName SHELL_MAIN_CLASS = AndroidxName.of("android.support.test.services.shellexecutor.", "ShellMain");
    private static final AndroidxName ORCHESTRATOR_PACKAGE = AndroidxName.of("android.support.test.orchestrator.");
    private static final AndroidxName ORCHESTRATOR_CLASS = AndroidxName.of("android.support.test.orchestrator.", "AndroidTestOrchestrator");
    private final boolean useAndroidx;

    public AndroidTestOrchestratorRemoteAndroidTestRunner(String applicationId, String instrumentationRunner, IShellEnabledDevice device, boolean useAndroidx) {
        super(applicationId, instrumentationRunner, device);
        this.useAndroidx = useAndroidx;
    }

    @Override
    public String getAmInstrumentCommand() {
        ArrayList<String> adbArgs = Lists.newArrayList();
        adbArgs.add("CLASSPATH=$(pm path " + this.getPackageName(SERVICES_APK_PACKAGE) + ")");
        adbArgs.add("app_process / " + this.getClassName(SHELL_MAIN_CLASS));
        adbArgs.add("am");
        adbArgs.add("instrument");
        adbArgs.add("-r");
        adbArgs.add("-w");
        adbArgs.add("-e");
        adbArgs.add("targetInstrumentation");
        adbArgs.add(this.getRunnerPath());
        adbArgs.add(this.getRunOptions());
        adbArgs.add(this.getArgsCommand());
        adbArgs.add(this.getPackageName(ORCHESTRATOR_PACKAGE) + "/" + this.getClassName(ORCHESTRATOR_CLASS));
        return Joiner.on(' ').join(adbArgs);
    }

    private String getPackageName(AndroidxName aPackage) {
        String result = this.useAndroidx ? aPackage.newName() : aPackage.oldName();
        result = result.substring(0, result.length() - 1);
        return result;
    }

    private String getClassName(AndroidxName name) {
        return this.useAndroidx ? name.newName() : name.oldName();
    }

    @Override
    public void setCoverageReportLocation(String reportPath) {
        this.addInstrumentationArg("coverageFilePath", reportPath);
    }

    @Override
    public IRemoteAndroidTestRunner.CoverageOutput getCoverageOutputType() {
        return IRemoteAndroidTestRunner.CoverageOutput.DIR;
    }
}

