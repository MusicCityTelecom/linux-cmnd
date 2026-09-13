/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.device;

import com.android.bundle.Devices;
import com.android.tools.build.bundletool.model.exceptions.ValidationException;
import com.android.tools.build.bundletool.model.utils.files.BufferedIo;
import com.google.common.io.MoreFiles;
import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Path;

public class DeviceSpecParser {
    private static final String JSON_EXTENSION = "json";

    public static Devices.DeviceSpec parseDeviceSpec(Path deviceSpecFile) {
        return DeviceSpecParser.parseDeviceSpecInternal(deviceSpecFile, false);
    }

    public static Devices.DeviceSpec parseDeviceSpec(Reader deviceSpecReader) throws IOException {
        return DeviceSpecParser.parseDeviceSpecInternal(deviceSpecReader, false);
    }

    public static Devices.DeviceSpec parsePartialDeviceSpec(Path deviceSpecFile) {
        return DeviceSpecParser.parseDeviceSpecInternal(deviceSpecFile, true);
    }

    public static Devices.DeviceSpec parsePartialDeviceSpec(Reader deviceSpecReader) throws IOException {
        return DeviceSpecParser.parseDeviceSpecInternal(deviceSpecReader, true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static Devices.DeviceSpec parseDeviceSpecInternal(Path deviceSpecFile, boolean canSkipFields) {
        if (!JSON_EXTENSION.equals(MoreFiles.getFileExtension(deviceSpecFile))) {
            throw ValidationException.builder().withMessage("Expected .json extension for the device spec file but found '%s'.", deviceSpecFile.getFileName()).build();
        }
        try (BufferedReader deviceSpecReader = BufferedIo.reader(deviceSpecFile);){
            Devices.DeviceSpec deviceSpec = DeviceSpecParser.parseDeviceSpecInternal(deviceSpecReader, canSkipFields);
            return deviceSpec;
        }
        catch (IOException e2) {
            throw new UncheckedIOException(String.format("Error while reading the device spec file '%s'.", deviceSpecFile), e2);
        }
    }

    private static Devices.DeviceSpec parseDeviceSpecInternal(Reader deviceSpecReader, boolean canSkipFields) throws IOException {
        Devices.DeviceSpec.Builder builder = Devices.DeviceSpec.newBuilder();
        JsonFormat.parser().merge(deviceSpecReader, (Message.Builder)builder);
        Devices.DeviceSpec deviceSpec = builder.build();
        DeviceSpecParser.validateDeviceSpec(deviceSpec, canSkipFields);
        return deviceSpec;
    }

    public static void validateDeviceSpec(Devices.DeviceSpec deviceSpec, boolean canSkipFields) {
        if (deviceSpec.getSdkVersion() < 0 || !canSkipFields && deviceSpec.getSdkVersion() == 0) {
            throw ValidationException.builder().withMessage("Device spec SDK version (%d) should be set to a strictly positive number.", deviceSpec.getSdkVersion()).build();
        }
        if (deviceSpec.getScreenDensity() < 0 || !canSkipFields && deviceSpec.getScreenDensity() == 0) {
            throw ValidationException.builder().withMessage("Device spec screen density (%d) should be set to a strictly positive number.", deviceSpec.getScreenDensity()).build();
        }
        if (!canSkipFields) {
            if (deviceSpec.getSupportedAbisList().isEmpty()) {
                throw new ValidationException("Device spec supported ABI list is empty.");
            }
            if (deviceSpec.getSupportedLocalesList().isEmpty()) {
                throw new ValidationException("Device spec supported locales list is empty.");
            }
        }
    }

    private DeviceSpecParser() {
    }
}

