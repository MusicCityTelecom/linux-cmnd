/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.zip;

import javax.annotation.Nullable;

public enum CompressionMethod {
    STORE(0),
    DEFLATE(8);

    int methodCode;

    private CompressionMethod(int methodCode) {
        this.methodCode = methodCode;
    }

    @Nullable
    static CompressionMethod fromCode(long code) {
        for (CompressionMethod method : CompressionMethod.values()) {
            if ((long)method.methodCode != code) continue;
            return method;
        }
        return null;
    }
}

