/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.codecs.twa;

import be.tpvision.smartcontrol.domain.twa.TwaDeviceSetting;

public abstract class Codec<D extends TwaDeviceSetting> {
    private final Class<D> twaDeviceSettingClass;

    public Codec(Class<D> twaDeviceSettingClass) {
        this.twaDeviceSettingClass = twaDeviceSettingClass;
    }

    public abstract byte[] toProtocol(D var1);

    public abstract D toDomain(byte[] var1);

    public Class<D> getTwaDeviceSettingClass() {
        return this.twaDeviceSettingClass;
    }
}

