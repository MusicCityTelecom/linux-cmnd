/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.codecs.sicp188.miscellaneous;

import be.tpvision.smartcontrol.codecs.sicp.Codec;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.TemperatureSensor;
import be.tpvision.smartcontrol.messages.codecs.sicp188.miscellaneous.temperature_sensor.ToProtocolMessages;

public class TemperatureSensorCodec
extends Codec<TemperatureSensor> {
    private static TemperatureSensorCodec temperatureSensorCodec;

    public TemperatureSensorCodec() {
        super(TemperatureSensor.class);
    }

    public static synchronized TemperatureSensorCodec getInstance() {
        if (temperatureSensorCodec == null) {
            temperatureSensorCodec = new TemperatureSensorCodec();
        }
        return temperatureSensorCodec;
    }

    @Override
    public byte[] toProtocol(TemperatureSensor temperatureSensor) {
        throw new UnsupportedOperationException(ToProtocolMessages.TO_PROTOCOL_IS_NOT_SUPPORTED_FOR_THIS_SICP_VERSION);
    }

    @Override
    public TemperatureSensor toDomain(byte[] bytes) {
        if (bytes == null || bytes.length < 1) {
            return null;
        }
        byte temperatureOne = bytes[0];
        Integer domainTemperatureTwo = bytes.length >= 2 ? Integer.valueOf(bytes[1]) : null;
        return new TemperatureSensor(temperatureOne, domainTemperatureTwo);
    }
}

