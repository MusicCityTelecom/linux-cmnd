/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.IShellOutputReceiver;
import com.android.ddmlib.ShellCommandUnresponsiveException;
import com.android.ddmlib.TimeoutException;
import java.io.IOException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public interface IShellEnabledDevice {
    public String getName();

    public void executeShellCommand(String var1, IShellOutputReceiver var2, long var3, TimeUnit var5) throws TimeoutException, AdbCommandRejectedException, ShellCommandUnresponsiveException, IOException;

    public void executeShellCommand(String var1, IShellOutputReceiver var2, long var3, long var5, TimeUnit var7) throws TimeoutException, AdbCommandRejectedException, ShellCommandUnresponsiveException, IOException;

    public Future<String> getSystemProperty(String var1);
}

