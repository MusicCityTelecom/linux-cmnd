/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.util.Convertible;

public enum SicpAndPlatformInfo implements Convertible
{
    SICP_VERSION(0),
    PLATFORM_LABEL(1),
    PLATFORM_VERSION(2);

    private byte data;

    private SicpAndPlatformInfo(byte data) {
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

