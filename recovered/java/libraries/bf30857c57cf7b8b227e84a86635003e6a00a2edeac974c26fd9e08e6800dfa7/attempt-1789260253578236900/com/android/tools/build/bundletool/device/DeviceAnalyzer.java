/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.device;

import com.android.bundle.Devices;
import com.android.ddmlib.IDevice;
import com.android.tools.build.bundletool.device.AdbServer;
import com.android.tools.build.bundletool.device.Device;
import com.android.tools.build.bundletool.device.activitymanager.ActivityManagerRunner;
import com.android.tools.build.bundletool.model.exceptions.CommandExecutionException;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

public class DeviceAnalyzer {
    private final AdbServer adb;
    private static final String LOCALE_PROPERTY_SYS = "persist.sys.locale";
    private static final String LOCALE_PROPERTY_PRODUCT = "ro.product.locale";
    private static final String LEGACY_LANGUAGE_PROPERTY = "ro.product.locale.language";
    private static final String LEGACY_REGION_PROPERTY = "ro.product.locale.region";

    public DeviceAnalyzer(AdbServer adb) {
        this.adb = adb;
    }

    public Devices.DeviceSpec getDeviceSpec(Optional<String> deviceId) {
        try {
            ImmutableList<String> supportedAbis;
            Device device = this.getAndValidateDevice(deviceId);
            int deviceSdkVersion = device.getVersion().getApiLevel();
            Preconditions.checkState(deviceSdkVersion > 1, "Error retrieving device SDK version. Please try again.");
            int deviceDensity = device.getDensity();
            Preconditions.checkState(deviceDensity > 0, "Error retrieving device density. Please try again.");
            ImmutableList<String> deviceFeatures = device.getDeviceFeatures();
            ImmutableList<String> glExtensions = device.getGlExtensions();
            ActivityManagerRunner activityManagerRunner = new ActivityManagerRunner(device);
            ImmutableList<String> deviceLocales = activityManagerRunner.getDeviceLocales();
            if (deviceLocales.isEmpty()) {
                deviceLocales = ImmutableList.of(this.getMainLocaleViaProperties(device));
            }
            if ((supportedAbis = activityManagerRunner.getDeviceAbis()).isEmpty()) {
                supportedAbis = device.getAbis();
            }
            Preconditions.checkState(!supportedAbis.isEmpty(), "Error retrieving device ABIs. Please try again.");
            return Devices.DeviceSpec.newBuilder().setSdkVersion(deviceSdkVersion).addAllSupportedAbis(supportedAbis).addAllSupportedLocales(deviceLocales).setScreenDensity(deviceDensity).addAllDeviceFeatures(deviceFeatures).addAllGlExtensions(glExtensions).build();
        }
        catch (TimeoutException e2) {
            throw CommandExecutionException.builder().withCause(e2).withMessage("Timed out while waiting for ADB.").build();
        }
    }

    private String getMainLocaleViaProperties(Device device) {
        Optional<Object> locale = Optional.empty();
        int apiLevel = device.getVersion().getApiLevel();
        if (apiLevel < 23) {
            Optional<String> language = device.getProperty(LEGACY_LANGUAGE_PROPERTY);
            Optional<String> region = device.getProperty(LEGACY_REGION_PROPERTY);
            if (language.isPresent() && region.isPresent()) {
                locale = Optional.of(language.get() + "-" + region.get());
            }
        } else {
            locale = device.getProperty(LOCALE_PROPERTY_SYS);
            if (!locale.isPresent()) {
                locale = device.getProperty(LOCALE_PROPERTY_PRODUCT);
            }
        }
        return locale.orElseGet(() -> {
            System.err.println("Warning: Can't detect device locale, will use 'en-US'.");
            return "en-US";
        });
    }

    private Device getAndValidateDevice(Optional<String> deviceId) throws TimeoutException {
        Device device = this.getTargetDevice(deviceId).orElseThrow(() -> CommandExecutionException.builder().withMessage("Unable to find the requested device.").build());
        if (device.getState().equals((Object)IDevice.DeviceState.UNAUTHORIZED)) {
            throw CommandExecutionException.builder().withMessage("Device found but not authorized for connecting. Please allow USB debugging on the device.").build();
        }
        if (!device.getState().equals((Object)IDevice.DeviceState.ONLINE)) {
            throw CommandExecutionException.builder().withMessage("Unable to connect to the device (device state: '%s').", device.getState().name()).build();
        }
        return device;
    }

    private Optional<Device> getTargetDevice(Optional<String> deviceId) throws TimeoutException {
        ImmutableList<Device> devices = this.adb.getDevices();
        if (devices.isEmpty()) {
            throw new CommandExecutionException("No connected devices found.");
        }
        if (deviceId.isPresent()) {
            return devices.stream().filter(device -> device.getSerialNumber().equals(deviceId.get())).findFirst();
        }
        if (devices.size() > 1) {
            throw new CommandExecutionException("More than one device connected, please provide --device-id.");
        }
        return Optional.of(devices.get(0));
    }
}

