/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.utils;

import com.android.tools.build.apkzlib.utils.IOExceptionWrapper;
import com.google.common.base.Function;
import java.io.IOException;
import javax.annotation.Nullable;

public interface IOExceptionFunction<F, T> {
    @Nullable
    public T apply(@Nullable F var1) throws IOException;

    public static <F, T> Function<F, T> asFunction(IOExceptionFunction<F, T> f2) {
        return i2 -> {
            try {
                return f2.apply(i2);
            }
            catch (IOException e2) {
                throw new IOExceptionWrapper(e2);
            }
        };
    }
}

