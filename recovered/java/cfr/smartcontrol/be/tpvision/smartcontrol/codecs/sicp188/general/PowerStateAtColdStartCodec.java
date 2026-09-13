/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.codecs.sicp188.general;

import be.tpvision.smartcontrol.codecs.sicp.SingleValueCodec;
import be.tpvision.smartcontrol.domain.device_settings.general.PowerStateAtColdStart;
import java.util.EnumMap;

public class PowerStateAtColdStartCodec
extends SingleValueCodec<PowerStateAtColdStart> {
    static final byte OFF_BYTE = 0;
    static final byte ON_BYTE = 1;
    static final byte LAST_STATUS_BYTE = 2;
    private static PowerStateAtColdStartCodec powerStateAtColdStartCodec;

    private PowerStateAtColdStartCodec() {
        super(PowerStateAtColdStart.class);
    }

    public static synchronized PowerStateAtColdStartCodec getInstance() {
        if (powerStateAtColdStartCodec == null) {
            powerStateAtColdStartCodec = new PowerStateAtColdStartCodec();
        }
        return powerStateAtColdStartCodec;
    }

    @Override
    protected void initializeDeviceSettings() {
        EnumMap<PowerStateAtColdStart, Byte> domainPowerStateAtColdStart = new EnumMap<PowerStateAtColdStart, Byte>(PowerStateAtColdStart.class);
        domainPowerStateAtColdStart.put(PowerStateAtColdStart.OFF, (byte)0);
        domainPowerStateAtColdStart.put(PowerStateAtColdStart.ON, (byte)1);
        domainPowerStateAtColdStart.put(PowerStateAtColdStart.LAST_STATUS, (byte)2);
        super.setDeviceSettings(domainPowerStateAtColdStart);
    }
}

