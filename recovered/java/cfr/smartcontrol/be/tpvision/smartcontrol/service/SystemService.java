/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.device_settings.StringWrapper;

public interface SystemService {
    public StringWrapper getSICPVersion(Device var1);

    public StringWrapper getPlatformLabel(Device var1);

    public StringWrapper getPlatformVersion(Device var1);

    public StringWrapper getModelNumber(Device var1);

    public StringWrapper getFirmwareVersion(Device var1);

    public StringWrapper getBuildDate(Device var1);

    public StringWrapper getFirmwareVersionAndroid(Device var1);
}

