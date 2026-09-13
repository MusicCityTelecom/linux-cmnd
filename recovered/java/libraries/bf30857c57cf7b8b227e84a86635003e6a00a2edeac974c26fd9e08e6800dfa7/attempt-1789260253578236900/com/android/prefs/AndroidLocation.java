/*
 * Decompiled with CFR 0.152.
 */
package com.android.prefs;

import com.android.utils.FileUtils;
import java.io.File;

public final class AndroidLocation {
    public static final String FOLDER_DOT_ANDROID = ".android";
    public static final String FOLDER_AVD = "avd";
    private static String sPrefsLocation = null;
    private static String sAvdLocation = null;

    public static String getFolder() throws AndroidLocationException {
        File f2;
        if (sPrefsLocation == null) {
            sPrefsLocation = AndroidLocation.findHomeFolder();
        }
        if (!(f2 = new File(sPrefsLocation)).exists()) {
            try {
                FileUtils.mkdirs(f2);
            }
            catch (SecurityException e2) {
                AndroidLocationException e22 = new AndroidLocationException(String.format("Unable to create folder '%1$s'. This is the path of preference folder expected by the Android tools.", sPrefsLocation));
                e22.initCause(e2);
                throw e22;
            }
        } else if (f2.isFile()) {
            throw new AndroidLocationException(String.format("%1$s is not a directory!\nThis is the path of preference folder expected by the Android tools.", sPrefsLocation));
        }
        return sPrefsLocation;
    }

    public static String getFolderWithoutWrites() {
        if (sPrefsLocation == null) {
            try {
                sPrefsLocation = AndroidLocation.findHomeFolder();
            }
            catch (AndroidLocationException e2) {
                return null;
            }
        }
        return sPrefsLocation;
    }

    public static void checkAndroidSdkHome() throws AndroidLocationException {
        Global.ANDROID_SDK_HOME.validatePath(false);
    }

    public static String getAvdFolder() throws AndroidLocationException {
        if (sAvdLocation == null) {
            String home = AndroidLocation.findValidPath(Global.ANDROID_AVD_HOME);
            if (home == null) {
                home = AndroidLocation.getFolder() + FOLDER_AVD;
            }
            if (!(sAvdLocation = home).endsWith(File.separator)) {
                sAvdLocation = sAvdLocation + File.separator;
            }
        }
        return sAvdLocation;
    }

    public static String getUserHomeFolder() throws AndroidLocationException {
        return AndroidLocation.findValidPath(Global.TEST_TMPDIR, Global.USER_HOME, Global.HOME);
    }

    private static String findHomeFolder() throws AndroidLocationException {
        String home = AndroidLocation.findValidPath(Global.ANDROID_SDK_HOME, Global.TEST_TMPDIR, Global.USER_HOME, Global.HOME);
        if (home == null) {
            throw new AndroidLocationException("prop: " + System.getProperty("ANDROID_SDK_HOME"));
        }
        if (!home.endsWith(File.separator)) {
            home = home + File.separator;
        }
        return home + FOLDER_DOT_ANDROID + File.separator;
    }

    public static void resetFolder() {
        sPrefsLocation = null;
        sAvdLocation = null;
    }

    private static String findValidPath(Global ... vars) throws AndroidLocationException {
        for (Global var : vars) {
            String path = var.validatePath(true);
            if (path == null) continue;
            return path;
        }
        return null;
    }

    private static enum Global {
        ANDROID_AVD_HOME("ANDROID_AVD_HOME", true, true),
        ANDROID_SDK_HOME("ANDROID_SDK_HOME", true, true),
        TEST_TMPDIR("TEST_TMPDIR", false, true),
        USER_HOME("user.home", true, false),
        HOME("HOME", false, true);

        final String mName;
        final boolean mIsSysProp;
        final boolean mIsEnvVar;

        private Global(String name, boolean isSysProp, boolean isEnvVar) {
            this.mName = name;
            this.mIsSysProp = isSysProp;
            this.mIsEnvVar = isEnvVar;
        }

        public String validatePath(boolean silent) throws AndroidLocationException {
            String path;
            if (this.mIsSysProp && (path = this.checkPath(System.getProperty(this.mName), silent)) != null) {
                return path;
            }
            if (this.mIsEnvVar && (path = this.checkPath(System.getenv(this.mName), silent)) != null) {
                return path;
            }
            return null;
        }

        private String checkPath(String path, boolean silent) throws AndroidLocationException {
            if (path == null) {
                return null;
            }
            File file = new File(path);
            if (!file.isDirectory()) {
                return null;
            }
            if (this != ANDROID_SDK_HOME || !Global.isSdkRootWithoutDotAndroid(file)) {
                return path;
            }
            if (!silent) {
                throw new AndroidLocationException(String.format("ANDROID_SDK_HOME is set to the root of your SDK: %1$s\nThis is the path of the preference folder expected by the Android tools.\nIt should NOT be set to the same as the root of your SDK.\nPlease set it to a different folder or do not set it at all.\nIf this is not set we default to: %2$s", path, AndroidLocation.findValidPath(new Global[]{Global.TEST_TMPDIR, Global.USER_HOME, Global.HOME})));
            }
            return null;
        }

        private static boolean isSdkRootWithoutDotAndroid(File folder) {
            return Global.subFolderExist(folder, "platforms") && Global.subFolderExist(folder, "platform-tools") && !Global.subFolderExist(folder, AndroidLocation.FOLDER_DOT_ANDROID);
        }

        private static boolean subFolderExist(File folder, String subFolder) {
            return new File(folder, subFolder).isDirectory();
        }
    }

    public static final class AndroidLocationException
    extends Exception {
        private static final long serialVersionUID = 1L;

        public AndroidLocationException(String string) {
            super(string);
        }
    }
}

