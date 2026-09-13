/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.util.Convertible;

public enum SwitchOnDelay implements Convertible
{
    OFF(0),
    _2_SECONDS(2),
    _4_SECONDS(4),
    _6_SECONDS(6),
    _8_SECONDS(8),
    _10_SECONDS(10),
    _20_SECONDS(20),
    _30_SECONDS(30),
    _40_SECONDS(40),
    _50_SECONDS(50),
    AUTO(60);

    private byte data;

    private SwitchOnDelay(byte data) {
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

