/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.device;

import com.android.tools.build.bundletool.device.Device;
import com.android.tools.build.bundletool.model.exceptions.CommandExecutionException;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import java.io.Closeable;
import java.nio.file.Path;
import java.util.concurrent.TimeoutException;

public abstract class AdbServer
implements Closeable {
    public static final int ADB_TIMEOUT_MS = 60000;

    public abstract void init(Path var1);

    public ImmutableList<Device> getDevices() throws TimeoutException {
        this.waitTillInitialDeviceListPopulated(60000L);
        return this.getDevicesInternal();
    }

    protected abstract ImmutableList<Device> getDevicesInternal();

    public abstract boolean hasInitialDeviceList();

    private final void waitTillInitialDeviceListPopulated(long timeoutMs) throws TimeoutException {
        if (this.hasInitialDeviceList()) {
            return;
        }
        Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            Thread.sleep(50L);
            while (!this.hasInitialDeviceList()) {
                if (stopwatch.elapsed().toMillis() > timeoutMs) {
                    throw new TimeoutException(String.format("Timed out (%d ms) while waiting for ADB.", timeoutMs));
                }
                Thread.sleep(1000L);
            }
        }
        catch (InterruptedException e2) {
            throw CommandExecutionException.builder().withCause(e2).withMessage("Interrupted while waiting for ADB.").build();
        }
    }

    @Override
    public abstract void close();
}

