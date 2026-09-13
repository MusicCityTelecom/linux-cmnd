/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.device;

import com.android.ddmlib.AndroidDebugBridge;
import com.android.tools.build.bundletool.device.AdbServer;
import com.android.tools.build.bundletool.device.DdmlibDevice;
import com.android.tools.build.bundletool.device.Device;
import com.android.tools.build.bundletool.model.exceptions.CommandExecutionException;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.concurrent.GuardedBy;
import java.nio.file.Path;
import java.util.Arrays;
import javax.annotation.Nullable;

public class DdmlibAdbServer
extends AdbServer {
    private static final DdmlibAdbServer instance = new DdmlibAdbServer();
    @Nullable
    private AndroidDebugBridge adb;
    @GuardedBy(value="this")
    private State state = State.UNINITIALIZED;
    private Path pathToAdb;

    private DdmlibAdbServer() {
    }

    public static DdmlibAdbServer getInstance() {
        return instance;
    }

    @Override
    public synchronized void init(Path pathToAdb) {
        Preconditions.checkState(this.state != State.CLOSED, "Android Debug Bridge has been closed.");
        if (this.state.equals((Object)State.INITIALIZED)) {
            Preconditions.checkState(pathToAdb.equals(this.pathToAdb), "Re-initializing DdmlibAdbServer with a different ADB path. Expected: '%s', got '%s'.", (Object)this.pathToAdb, (Object)pathToAdb);
            return;
        }
        AndroidDebugBridge.initIfNeeded(false);
        this.adb = AndroidDebugBridge.createBridge(pathToAdb.toString(), false);
        if (this.adb == null) {
            throw new CommandExecutionException("Failed to start ADB server.");
        }
        this.pathToAdb = pathToAdb;
        this.state = State.INITIALIZED;
    }

    @Override
    public synchronized ImmutableList<Device> getDevicesInternal() {
        Preconditions.checkState(this.state.equals((Object)State.INITIALIZED), "Android Debug Bridge is not initialized.");
        return Arrays.stream(this.adb.getDevices()).map(DdmlibDevice::new).collect(ImmutableList.toImmutableList());
    }

    @Override
    public synchronized boolean hasInitialDeviceList() {
        Preconditions.checkState(this.state.equals((Object)State.INITIALIZED), "Android Debug Bridge is not initialized.");
        return this.adb.hasInitialDeviceList();
    }

    @Override
    public synchronized void close() {
        if (this.state.equals((Object)State.INITIALIZED)) {
            AndroidDebugBridge.terminate();
        }
        this.state = State.CLOSED;
    }

    private static enum State {
        UNINITIALIZED,
        INITIALIZED,
        CLOSED;

    }
}

