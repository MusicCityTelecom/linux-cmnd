/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.utils;

import java.io.IOException;

public class IOExceptionWrapper
extends RuntimeException {
    public IOExceptionWrapper(IOException e2) {
        super(e2);
    }

    @Override
    public IOException getCause() {
        return (IOException)super.getCause();
    }
}

