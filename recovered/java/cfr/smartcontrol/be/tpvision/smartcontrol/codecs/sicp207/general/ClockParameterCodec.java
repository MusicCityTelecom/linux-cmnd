/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.codecs.sicp207.general;

import be.tpvision.smartcontrol.codecs.sicp.Codec;
import be.tpvision.smartcontrol.domain.device_settings.general.ClockParameter;

public class ClockParameterCodec
extends Codec<ClockParameter> {
    private static ClockParameterCodec clockParameterCodec;

    private ClockParameterCodec() {
        super(ClockParameter.class);
    }

    public static synchronized ClockParameterCodec getInstance() {
        if (clockParameterCodec == null) {
            clockParameterCodec = new ClockParameterCodec();
        }
        return clockParameterCodec;
    }

    @Override
    public byte[] toProtocol(ClockParameter clockParameter) {
        byte hour = (byte)clockParameter.getHour();
        byte minute = (byte)clockParameter.getMinute();
        return new byte[]{hour, minute};
    }

    @Override
    public ClockParameter toDomain(byte[] bytes) {
        if (bytes == null || bytes.length < 2) {
            return null;
        }
        return new ClockParameter(bytes[0], bytes[1]);
    }
}

