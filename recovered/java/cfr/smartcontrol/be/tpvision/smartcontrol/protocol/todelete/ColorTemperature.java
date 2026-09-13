/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.util.Convertible;

public enum ColorTemperature implements Convertible
{
    USER_1(0),
    NATURE(1),
    _11000K(2),
    _10000K(3),
    _9300K(4),
    _7500K(5),
    _6500K(6),
    _5770K(7),
    _5500K(8),
    _5000K(9),
    _4000K(10),
    _3400K(11),
    _3350K(12),
    _3000K(13),
    _2800K(14),
    _2600K(15),
    _1850K(16),
    USER_2(18);

    private byte data;

    private ColorTemperature(byte data) {
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

