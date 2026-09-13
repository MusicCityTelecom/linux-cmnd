/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.util.Convertible;

public enum ModelNumberFirmwareVersionBuildDateInfo implements Convertible
{
    MODEL_NUMBER(0),
    FIRMWARE_VERSION(1),
    BUILD_DATE(2);

    private byte data;

    private ModelNumberFirmwareVersionBuildDateInfo(byte data) {
        this.data = data;
    }

    public byte getData() {
        return this.data;
    }

    @Override
    public byte convert() {
        return this.data;
    }
}

