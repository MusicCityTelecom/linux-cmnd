/*
 * Decompiled with CFR 0.152.
 */
package com.sun.xml.bind;

final class StackHelper {
    private StackHelper() {
    }

    static String getCallerClassName() {
        StackTraceElement[] trace = new Exception().getStackTrace();
        if (2 < trace.length) {
            return trace[2].getClassName();
        }
        return "com.sun.xml.bind";
    }
}

