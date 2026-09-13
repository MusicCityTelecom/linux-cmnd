/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.util.Convertible;

public enum PowerSavingMode implements Convertible
{
    RGB_OFF_VIDEO_OFF(0),
    RGB_OFF_VIDEO_ON(1),
    RGB_ON_VIDEO_OFF(2),
    RGB_ON_VIDEO_ON(3);

    private byte data;

    private PowerSavingMode(byte data) {
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

