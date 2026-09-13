/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

public final class DdmConstants {
    public static final int PLATFORM_UNKNOWN = 0;
    public static final int PLATFORM_LINUX = 1;
    public static final int PLATFORM_WINDOWS = 2;
    public static final int PLATFORM_DARWIN = 3;
    public static final int CURRENT_PLATFORM = DdmConstants.currentPlatform();
    public static final String EXTENSION = "trace";
    public static final String DOT_TRACE = ".trace";
    public static final String FN_HPROF_CONVERTER = CURRENT_PLATFORM == 2 ? "hprof-conv.exe" : "hprof-conv";
    public static final String FN_TRACEVIEW = CURRENT_PLATFORM == 2 ? "traceview.bat" : "traceview";

    public static int currentPlatform() {
        String os = System.getProperty("os.name");
        if (os.startsWith("Mac OS")) {
            return 3;
        }
        if (os.startsWith("Windows")) {
            return 2;
        }
        if (os.startsWith("Linux")) {
            return 1;
        }
        return 0;
    }
}

