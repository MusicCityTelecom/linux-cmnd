/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.utils;

import java.io.IOException;
import javax.annotation.Nullable;

public interface IOExceptionConsumer<T> {
    public void accept(@Nullable T var1) throws IOException;
}

