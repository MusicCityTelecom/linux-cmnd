/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.AdbHelper;
import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.InstallException;
import com.android.ddmlib.InstallReceiver;
import com.android.ddmlib.Log;
import com.android.ddmlib.MultiLineReceiver;
import com.android.ddmlib.ShellCommandUnresponsiveException;
import com.android.ddmlib.TimeoutException;
import com.android.sdklib.AndroidVersion;
import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SplitApkInstaller {
    private static final String LOG_TAG = "SplitApkInstaller";
    private final IDevice mDevice;
    private final List<File> mApks;
    private final String mOptions;
    private final String mPrefix;
    private static final CharMatcher UNSAFE_PM_INSTALL_SESSION_SPLIT_NAME_CHARS = CharMatcher.inRange('a', 'z').or(CharMatcher.inRange('A', 'Z')).or(CharMatcher.anyOf("_-")).negate();

    private SplitApkInstaller(IDevice device, List<File> apks, String options) {
        this.mDevice = device;
        this.mApks = apks;
        this.mOptions = options;
        this.mPrefix = this.mDevice.getVersion().isGreaterOrEqualThan(AndroidVersion.BINDER_CMD_AVAILABLE.getApiLevel()) ? "cmd package" : "pm";
    }

    public void install(long timeout, TimeUnit unit) throws InstallException {
        try {
            String sessionId = this.createMultiInstallSession(this.mApks, this.mOptions, timeout, unit);
            if (sessionId == null) {
                Log.d(LOG_TAG, "Failed to establish session, quit installation");
                throw new InstallException("Failed to establish session");
            }
            int index = 0;
            boolean allUploadSucceeded = true;
            while (allUploadSucceeded && index < this.mApks.size()) {
                allUploadSucceeded = this.uploadApk(sessionId, this.mApks.get(index), index++, timeout, unit);
            }
            String command = this.mPrefix + " install-" + (allUploadSucceeded ? "commit " : "abandon ") + sessionId;
            InstallReceiver receiver = new InstallReceiver();
            this.mDevice.executeShellCommand(command, receiver, timeout, unit);
            String errorMessage = receiver.getErrorMessage();
            if (errorMessage != null) {
                String message = String.format("Failed to finalize session : %1$s", errorMessage);
                Log.e(LOG_TAG, message);
                throw new InstallException(message);
            }
            if (!allUploadSucceeded) {
                throw new InstallException("Failed to install all ");
            }
        }
        catch (InstallException e2) {
            throw e2;
        }
        catch (Exception e3) {
            throw new InstallException(e3);
        }
    }

    private String createMultiInstallSession(List<File> apkFiles, String pmOptions, long timeout, TimeUnit unit) throws TimeoutException, AdbCommandRejectedException, ShellCommandUnresponsiveException, IOException {
        long totalFileSize = 0L;
        for (File apkFile : apkFiles) {
            totalFileSize += apkFile.length();
        }
        InstallCreateReceiver receiver = new InstallCreateReceiver();
        String cmd = String.format(this.mPrefix + " install-create %1$s -S %2$d", pmOptions, totalFileSize);
        this.mDevice.executeShellCommand(cmd, receiver, timeout, unit);
        return receiver.getSessionId();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean uploadApk(String sessionId, File fileToUpload, int uniqueId, long timeout, TimeUnit unit) {
        Log.d(sessionId, String.format("Uploading APK %1$s ", fileToUpload.getPath()));
        if (!fileToUpload.exists()) {
            Log.e(sessionId, String.format("File not found: %1$s", fileToUpload.getPath()));
            return false;
        }
        if (fileToUpload.isDirectory()) {
            Log.e(sessionId, String.format("Directory upload not supported: %1$s", fileToUpload.getAbsolutePath()));
            return false;
        }
        String baseName = fileToUpload.getName().lastIndexOf(46) != -1 ? fileToUpload.getName().substring(0, fileToUpload.getName().lastIndexOf(46)) : fileToUpload.getName();
        baseName = UNSAFE_PM_INSTALL_SESSION_SPLIT_NAME_CHARS.replaceFrom((CharSequence)baseName, '_');
        String command = String.format(this.mPrefix + " install-write -S %d %s %d_%s -", fileToUpload.length(), sessionId, uniqueId, baseName);
        Log.d(sessionId, String.format("Executing : %1$s", command));
        InputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(fileToUpload));
            InstallWriteReceiver receiver = new InstallWriteReceiver();
            AdbHelper.executeRemoteCommand(AndroidDebugBridge.getSocketAddress(), AdbHelper.AdbService.EXEC, command, this.mDevice, receiver, timeout, unit, inputStream);
            if (receiver.getErrorMessage() != null) {
                Log.e(sessionId, String.format("Error while uploading %1$s : %2$s", fileToUpload.getName(), receiver.getErrorMessage()));
            } else {
                Log.d(sessionId, String.format("Successfully uploaded %1$s", fileToUpload.getName()));
            }
            boolean bl = receiver.getErrorMessage() == null;
            return bl;
        }
        catch (Exception e2) {
            Log.e(sessionId, e2);
            boolean bl = false;
            return bl;
        }
        finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                }
                catch (IOException e3) {
                    Log.e(sessionId, e3);
                }
            }
        }
    }

    private static String getOptions(boolean reInstall, List<String> pmOptions) {
        return SplitApkInstaller.getOptions(reInstall, false, null, pmOptions);
    }

    private static String getOptions(boolean reInstall, boolean partialInstall, String applicationId, List<String> pmOptions) {
        StringBuilder sb = new StringBuilder();
        if (reInstall) {
            sb.append("-r ");
        }
        if (partialInstall) {
            if (applicationId == null) {
                throw new IllegalArgumentException("Cannot do a partial install without knowing the application id");
            }
            sb.append("-p ");
            sb.append(applicationId);
            sb.append(' ');
        }
        sb.append(Joiner.on(' ').join(pmOptions));
        return sb.toString();
    }

    private static void validateArguments(IDevice device, List<File> apks) {
        if (apks.isEmpty()) {
            throw new IllegalArgumentException("List of APKs is empty: the main APK must be specified.");
        }
        for (File apk : apks) {
            if (apk.isFile()) continue;
            throw new IllegalArgumentException("Invalid File: " + apk.getPath());
        }
        int apiWithSplitApk = AndroidVersion.ALLOW_SPLIT_APK_INSTALLATION.getApiLevel();
        if (!device.getVersion().isGreaterOrEqualThan(apiWithSplitApk)) {
            throw new IllegalArgumentException("Cannot install split APKs on device with API level < " + apiWithSplitApk);
        }
    }

    public static SplitApkInstaller create(IDevice device, List<File> apks, boolean reInstall, List<String> pmOptions) {
        SplitApkInstaller.validateArguments(device, apks);
        return new SplitApkInstaller(device, apks, SplitApkInstaller.getOptions(reInstall, pmOptions));
    }

    public static SplitApkInstaller create(IDevice device, String applicationId, List<File> apks, boolean reInstall, List<String> pmOptions) {
        SplitApkInstaller.validateArguments(device, apks);
        return new SplitApkInstaller(device, apks, SplitApkInstaller.getOptions(reInstall, true, applicationId, pmOptions));
    }

    static class InstallWriteReceiver
    extends MultiLineReceiver {
        private boolean processedFirstLine;
        private boolean success;
        private StringBuffer errorBuffer;

        InstallWriteReceiver() {
        }

        @Override
        public boolean isCancelled() {
            return false;
        }

        @Override
        public void processNewLines(String[] lines) {
            if (!this.processedFirstLine) {
                this.processedFirstLine = true;
                this.success = lines[0].startsWith("Success");
            }
            if (!this.success) {
                if (this.errorBuffer == null) {
                    this.errorBuffer = new StringBuffer(100);
                }
                for (String line : lines) {
                    this.errorBuffer.append(line);
                    this.errorBuffer.append('\n');
                }
            }
        }

        public boolean isSuccess() {
            return this.success;
        }

        public String getErrorMessage() {
            return this.errorBuffer == null ? null : this.errorBuffer.toString();
        }
    }

    private static class InstallCreateReceiver
    extends MultiLineReceiver {
        private static final Pattern successPattern = Pattern.compile("Success: .*\\[(\\d*)\\]");
        String sessionId = null;

        private InstallCreateReceiver() {
        }

        @Override
        public boolean isCancelled() {
            return false;
        }

        @Override
        public void processNewLines(String[] lines) {
            for (String line : lines) {
                Matcher matcher = successPattern.matcher(line);
                if (!matcher.matches()) continue;
                this.sessionId = matcher.group(1);
            }
        }

        public String getSessionId() {
            return this.sessionId;
        }
    }
}

