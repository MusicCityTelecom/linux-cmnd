/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

import com.android.ddmlib.Log;
import com.android.ddmlib.MonitorThread;

public final class DdmPreferences {
    public static final boolean DEFAULT_INITIAL_THREAD_UPDATE = false;
    public static final boolean DEFAULT_INITIAL_HEAP_UPDATE = false;
    public static final int DEFAULT_SELECTED_DEBUG_PORT = 8700;
    public static final int DEFAULT_DEBUG_PORT_BASE = 8600;
    public static final Log.LogLevel DEFAULT_LOG_LEVEL = Log.LogLevel.ERROR;
    public static final int DEFAULT_TIMEOUT = 5000;
    public static final int DEFAULT_PROFILER_BUFFER_SIZE_MB = 8;
    public static final boolean DEFAULT_USE_ADBHOST = false;
    public static final String DEFAULT_ADBHOST_VALUE = "127.0.0.1";
    private static boolean sThreadUpdate = false;
    private static boolean sInitialHeapUpdate = false;
    private static int sSelectedDebugPort = 8700;
    private static int sDebugPortBase = 8600;
    private static Log.LogLevel sLogLevel = DEFAULT_LOG_LEVEL;
    private static int sTimeOut = 5000;
    private static int sProfilerBufferSizeMb = 8;
    private static boolean sUseAdbHost = false;
    private static String sAdbHostValue = "127.0.0.1";

    public static boolean getInitialThreadUpdate() {
        return sThreadUpdate;
    }

    public static void setInitialThreadUpdate(boolean state) {
        sThreadUpdate = state;
    }

    public static boolean getInitialHeapUpdate() {
        return sInitialHeapUpdate;
    }

    public static void setInitialHeapUpdate(boolean state) {
        sInitialHeapUpdate = state;
    }

    public static int getSelectedDebugPort() {
        return sSelectedDebugPort;
    }

    public static void setSelectedDebugPort(int port) {
        sSelectedDebugPort = port;
        MonitorThread monitorThread = MonitorThread.getInstance();
        if (monitorThread != null) {
            monitorThread.setDebugSelectedPort(port);
        }
    }

    public static int getDebugPortBase() {
        return sDebugPortBase;
    }

    public static void setDebugPortBase(int port) {
        sDebugPortBase = port;
    }

    public static Log.LogLevel getLogLevel() {
        return sLogLevel;
    }

    public static void setLogLevel(String value) {
        sLogLevel = Log.LogLevel.getByString(value);
        Log.setLevel(sLogLevel);
    }

    public static int getTimeOut() {
        return sTimeOut;
    }

    public static void setTimeOut(int timeOut) {
        sTimeOut = timeOut;
    }

    public static int getProfilerBufferSizeMb() {
        return sProfilerBufferSizeMb;
    }

    public static void setProfilerBufferSizeMb(int bufferSizeMb) {
        sProfilerBufferSizeMb = bufferSizeMb;
    }

    public static boolean getUseAdbHost() {
        return sUseAdbHost;
    }

    public static void setUseAdbHost(boolean useAdbHost) {
        sUseAdbHost = useAdbHost;
    }

    public static String getAdbHostValue() {
        return sAdbHostValue;
    }

    public static void setAdbHostValue(String adbHostValue) {
        sAdbHostValue = adbHostValue;
    }

    private DdmPreferences() {
    }
}

