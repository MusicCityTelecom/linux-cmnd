/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.util.Convertible;

public enum CommunicationControl implements Convertible
{
    ACKNOWLEDGED(6),
    NOT_ACKNOWLEDGED(21),
    NOT_AVAILABLE(24);

    private byte data;

    private CommunicationControl(byte data) {
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

