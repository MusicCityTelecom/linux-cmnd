/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

import com.android.ddmlib.IDevice;

public class DebugPortManager {
    private static IDebugPortProvider sProvider = null;

    public static void setProvider(IDebugPortProvider provider) {
        sProvider = provider;
    }

    static IDebugPortProvider getProvider() {
        return sProvider;
    }

    public static interface IDebugPortProvider {
        public static final int NO_STATIC_PORT = -1;

        public int getPort(IDevice var1, String var2);
    }
}

