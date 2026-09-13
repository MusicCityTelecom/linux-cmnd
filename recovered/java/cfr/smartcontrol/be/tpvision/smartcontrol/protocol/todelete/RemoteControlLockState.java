/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.util.Convertible;

public enum RemoteControlLockState implements Convertible
{
    UNLOCK_ALL(1),
    LOCK_ALL(2),
    LOCK_ALL_BUT_POWER(3),
    LOCK_ALL_BUT_VOLUME(4),
    PRIMARY(5),
    SECONDARY(6);

    private byte data;

    private RemoteControlLockState(byte data) {
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

