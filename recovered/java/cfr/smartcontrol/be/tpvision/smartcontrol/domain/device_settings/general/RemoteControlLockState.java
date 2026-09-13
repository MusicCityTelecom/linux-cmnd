/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.domain.device_settings.general;

import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;

public enum RemoteControlLockState implements DeviceSetting
{
    UNLOCK_ALL,
    LOCK_ALL,
    LOCK_ALL_BUT_POWER,
    LOCK_ALL_BUT_VOLUME,
    PRIMARY,
    SECONDARY,
    LOCK_ALL_EXCEPT_POWER_AND_VOLUME;

}

