/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.codecs.sicp197.miscellaneous;

import be.tpvision.smartcontrol.codecs.sicp.SingleValueCodec;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.PowerSavingMode;
import java.util.EnumMap;

public class PowerSavingModeCodec
extends SingleValueCodec<PowerSavingMode> {
    static final byte RGB_OFF_VIDEO_OFF_BYTE = 0;
    static final byte RGB_OFF_VIDEO_ON_BYTE = 1;
    static final byte RGB_ON_VIDEO_OFF_BYTE = 2;
    static final byte RGB_ON_VIDEO_ON_BYTE = 3;
    static final byte MODE_ONE_BYTE = 4;
    static final byte MODE_TWO_BYTE = 5;
    static final byte MODE_THREE_BYTE = 6;
    static final byte MODE_FOUR_BYTE = 7;
    private static PowerSavingModeCodec powerSavingModeCodec;

    private PowerSavingModeCodec() {
        super(PowerSavingMode.class);
    }

    public static synchronized PowerSavingModeCodec getInstance() {
        if (powerSavingModeCodec == null) {
            powerSavingModeCodec = new PowerSavingModeCodec();
        }
        return powerSavingModeCodec;
    }

    @Override
    protected void initializeDeviceSettings() {
        EnumMap<PowerSavingMode, Byte> domainPowerSavingMode = new EnumMap<PowerSavingMode, Byte>(PowerSavingMode.class);
        domainPowerSavingMode.put(PowerSavingMode.RGB_OFF_VIDEO_OFF, (byte)0);
        domainPowerSavingMode.put(PowerSavingMode.RGB_OFF_VIDEO_ON, (byte)1);
        domainPowerSavingMode.put(PowerSavingMode.RGB_ON_VIDEO_OFF, (byte)2);
        domainPowerSavingMode.put(PowerSavingMode.RGB_ON_VIDEO_ON, (byte)3);
        domainPowerSavingMode.put(PowerSavingMode.MODE_ONE, (byte)4);
        domainPowerSavingMode.put(PowerSavingMode.MODE_TWO, (byte)5);
        domainPowerSavingMode.put(PowerSavingMode.MODE_THREE, (byte)6);
        domainPowerSavingMode.put(PowerSavingMode.MODE_FOUR, (byte)7);
        super.setDeviceSettings(domainPowerSavingMode);
    }
}

