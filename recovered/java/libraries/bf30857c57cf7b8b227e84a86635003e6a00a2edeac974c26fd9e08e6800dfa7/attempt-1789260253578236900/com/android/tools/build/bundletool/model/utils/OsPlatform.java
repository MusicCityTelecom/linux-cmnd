/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.utils;

public enum OsPlatform {
    WINDOWS,
    MACOS,
    LINUX,
    OTHER;


    public static OsPlatform getCurrentPlatform() {
        String os = System.getProperty("os.name");
        if (os.startsWith("Mac OS")) {
            return MACOS;
        }
        if (os.startsWith("Windows")) {
            return WINDOWS;
        }
        if (os.startsWith("Linux")) {
            return LINUX;
        }
        return OTHER;
    }
}

