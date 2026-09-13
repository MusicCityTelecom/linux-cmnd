/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.util.Convertible;

public enum AutoSignalDetecting implements Convertible
{
    OFF(0),
    ALL(1),
    RESERVED(2),
    PC_ONLY(3),
    VIDEO_ONLY(4),
    FAILOVER(5);

    private byte data;

    private AutoSignalDetecting(byte data) {
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

