/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.util.Convertible;

public enum FanSpeed implements Convertible
{
    OFF(0),
    AUTO(1),
    LOW(2),
    MIDDLE(3),
    HIGH(4);

    private byte data;

    private FanSpeed(byte data) {
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

