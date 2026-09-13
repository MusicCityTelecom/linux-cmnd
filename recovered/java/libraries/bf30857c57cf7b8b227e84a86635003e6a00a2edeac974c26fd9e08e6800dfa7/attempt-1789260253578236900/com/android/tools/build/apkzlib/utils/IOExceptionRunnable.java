/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.utils;

import com.android.tools.build.apkzlib.utils.IOExceptionWrapper;
import java.io.IOException;

public interface IOExceptionRunnable {
    public void run() throws IOException;

    public static Runnable asRunnable(IOExceptionRunnable r3) {
        return () -> {
            try {
                r3.run();
            }
            catch (IOException e2) {
                throw new IOExceptionWrapper(e2);
            }
        };
    }
}

