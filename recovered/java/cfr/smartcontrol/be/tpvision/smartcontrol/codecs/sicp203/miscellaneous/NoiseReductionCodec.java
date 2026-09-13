/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.codecs.sicp203.miscellaneous;

import be.tpvision.smartcontrol.codecs.sicp.SingleValueCodec;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.NoiseReduction;
import java.util.EnumMap;

public class NoiseReductionCodec
extends SingleValueCodec<NoiseReduction> {
    static final byte OFF_BYTE = 0;
    static final byte LOW_BYTE = 1;
    static final byte MIDDLE_BYTE = 2;
    static final byte HIGH_BYTE = 3;
    static final byte DEFAULT_BYTE = 4;
    private static NoiseReductionCodec noiseReductionCodec;

    private NoiseReductionCodec() {
        super(NoiseReduction.class);
    }

    public static synchronized NoiseReductionCodec getInstance() {
        if (noiseReductionCodec == null) {
            noiseReductionCodec = new NoiseReductionCodec();
        }
        return noiseReductionCodec;
    }

    @Override
    protected void initializeDeviceSettings() {
        EnumMap<NoiseReduction, Byte> domainNoiseReduction = new EnumMap<NoiseReduction, Byte>(NoiseReduction.class);
        domainNoiseReduction.put(NoiseReduction.OFF, (byte)0);
        domainNoiseReduction.put(NoiseReduction.LOW, (byte)1);
        domainNoiseReduction.put(NoiseReduction.MIDDLE, (byte)2);
        domainNoiseReduction.put(NoiseReduction.HIGH, (byte)3);
        domainNoiseReduction.put(NoiseReduction.DEFAULT, (byte)4);
        super.setDeviceSettings(domainNoiseReduction);
    }
}

