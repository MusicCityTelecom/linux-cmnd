/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.codecs.sicp207.general;

import be.tpvision.smartcontrol.codecs.sicp.Codec;
import be.tpvision.smartcontrol.domain.device_settings.general.AutoRestartParameter;

public class AutoRestartParameterCodec
extends Codec<AutoRestartParameter> {
    private static AutoRestartParameterCodec autoRestartParameterCodec;

    private AutoRestartParameterCodec() {
        super(AutoRestartParameter.class);
    }

    public static synchronized AutoRestartParameterCodec getInstance() {
        if (autoRestartParameterCodec == null) {
            autoRestartParameterCodec = new AutoRestartParameterCodec();
        }
        return autoRestartParameterCodec;
    }

    @Override
    public byte[] toProtocol(AutoRestartParameter autoRestartParameter) {
        byte status = (byte)autoRestartParameter.getStatus().ordinal();
        byte hour = (byte)autoRestartParameter.getHour();
        byte minute = (byte)autoRestartParameter.getMinute();
        return new byte[]{status, hour, minute};
    }

    @Override
    public AutoRestartParameter toDomain(byte[] bytes) {
        if (bytes == null || bytes.length < 3) {
            return null;
        }
        AutoRestartParameter.Status status = AutoRestartParameter.Status.values()[bytes[0]];
        byte hour = bytes[1];
        byte minute = bytes[2];
        return new AutoRestartParameter(status, hour, minute);
    }
}

