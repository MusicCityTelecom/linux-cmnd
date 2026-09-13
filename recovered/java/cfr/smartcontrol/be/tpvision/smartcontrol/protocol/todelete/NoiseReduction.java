/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.util.Convertible;

public enum NoiseReduction implements Convertible
{
    OFF(0),
    LOW(1),
    MIDDLE(2),
    HIGH(3);

    private byte data;

    private NoiseReduction(byte data) {
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

