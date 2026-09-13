/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib.logcat;

import com.android.ddmlib.logcat.LogCatMessage;
import java.util.List;

public interface LogCatListener {
    public void log(List<LogCatMessage> var1);
}

