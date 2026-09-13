/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.util.Convertible;

public enum PictureFormat implements Convertible
{
    NORMAL(0),
    CUSTOM(1),
    REAL(2),
    FULL(3),
    _21_9(4),
    DYNAMIC(5);

    private byte data;

    private PictureFormat(byte data) {
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

