/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.zip;

import com.google.common.collect.ImmutableList;

public interface VerifyLog {
    public void log(String var1);

    public ImmutableList<String> getLogs();

    default public void verify(boolean condition, String message, Object ... args) {
        if (!condition) {
            this.log(String.format(message, args));
        }
    }
}

