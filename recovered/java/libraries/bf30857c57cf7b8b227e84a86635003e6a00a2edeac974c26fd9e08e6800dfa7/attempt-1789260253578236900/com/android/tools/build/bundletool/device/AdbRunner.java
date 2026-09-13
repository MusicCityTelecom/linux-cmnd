/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.device;

import com.android.tools.build.bundletool.device.AdbServer;
import com.android.tools.build.bundletool.device.Device;
import com.android.tools.build.bundletool.model.exceptions.CommandExecutionException;
import com.android.tools.build.bundletool.model.exceptions.DeviceNotFoundException;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class AdbRunner {
    private final AdbServer adbServer;

    public AdbRunner(AdbServer adbServer) {
        this.adbServer = adbServer;
    }

    public void run(Consumer<Device> deviceAction) {
        try {
            this.run(deviceAction, Predicates.alwaysTrue());
        }
        catch (DeviceNotFoundException.TooManyDevicesMatchedException e2) {
            throw CommandExecutionException.builder().withMessage("Expected to find one connected device, but found %d.", e2.getMatchedNumber()).withCause(e2).build();
        }
        catch (DeviceNotFoundException e3) {
            throw CommandExecutionException.builder().withMessage("Expected to find one connected device, but found none.").withCause(e3).build();
        }
    }

    public void run(Consumer<Device> deviceAction, String deviceId) {
        try {
            this.run(deviceAction, (Device device) -> device.getSerialNumber().equals(deviceId));
        }
        catch (DeviceNotFoundException e2) {
            throw CommandExecutionException.builder().withMessage("Expected to find one connected device with serial number '%s'.", deviceId).withCause(e2).build();
        }
    }

    private void run(Consumer<Device> deviceAction, Predicate<Device> deviceFilter) {
        try {
            ImmutableList matchedDevices = this.adbServer.getDevices().stream().filter(deviceFilter).collect(ImmutableList.toImmutableList());
            if (matchedDevices.isEmpty()) {
                throw new DeviceNotFoundException("Unable to find a device matching the criteria.", new Object[0]);
            }
            if (matchedDevices.size() > 1) {
                throw new DeviceNotFoundException.TooManyDevicesMatchedException(matchedDevices.size());
            }
            deviceAction.accept((Device)matchedDevices.get(0));
        }
        catch (TimeoutException e2) {
            throw CommandExecutionException.builder().withCause(e2).withMessage("Timed out while waiting for ADB.").build();
        }
    }
}

