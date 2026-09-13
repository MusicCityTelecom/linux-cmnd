/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.util.Convertible;

public enum ScanConversion implements Convertible
{
    PROGRESSIVE(0),
    INTERLACE(1);

    private byte data;

    private ScanConversion(byte data) {
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

