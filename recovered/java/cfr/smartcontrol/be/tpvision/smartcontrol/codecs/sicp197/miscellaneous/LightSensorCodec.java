/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.codecs.sicp197.miscellaneous;

import be.tpvision.smartcontrol.codecs.sicp.SingleValueCodec;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.LightSensor;
import java.util.EnumMap;

public class LightSensorCodec
extends SingleValueCodec<LightSensor> {
    static final byte OFF_BYTE = 0;
    static final byte ON_BYTE = 1;
    static final byte HW_UNAVAILABLE_BYTE = -1;
    private static LightSensorCodec lightSensorCodec;

    public LightSensorCodec() {
        super(LightSensor.class);
    }

    public static synchronized LightSensorCodec getInstance() {
        if (lightSensorCodec == null) {
            lightSensorCodec = new LightSensorCodec();
        }
        return lightSensorCodec;
    }

    @Override
    protected void initializeDeviceSettings() {
        EnumMap<LightSensor, Byte> domainLightSensors = new EnumMap<LightSensor, Byte>(LightSensor.class);
        domainLightSensors.put(LightSensor.OFF, (byte)0);
        domainLightSensors.put(LightSensor.ON, (byte)1);
        domainLightSensors.put(LightSensor.HW_UNAVAILABLE, (byte)-1);
        super.setDeviceSettings(domainLightSensors);
    }
}

