/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.bundle.Devices;
import com.google.common.collect.ImmutableSet;
import java.util.Optional;

public interface GetSizeRequest {
    public Devices.DeviceSpec getDeviceSpec();

    public Optional<ImmutableSet<String>> getModules();

    public ImmutableSet<Dimension> getDimensions();

    public boolean getInstant();

    public static enum Dimension {
        SDK,
        ABI,
        SCREEN_DENSITY,
        LANGUAGE,
        ALL;

    }
}

