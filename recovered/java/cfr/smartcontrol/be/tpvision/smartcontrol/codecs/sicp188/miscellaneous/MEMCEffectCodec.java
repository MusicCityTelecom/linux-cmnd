/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.codecs.sicp188.miscellaneous;

import be.tpvision.smartcontrol.codecs.sicp.SingleValueCodec;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.MEMCEffect;
import java.util.EnumMap;

public class MEMCEffectCodec
extends SingleValueCodec<MEMCEffect> {
    static final byte OFF_BYTE = 0;
    static final byte LOW_BYTE = 1;
    static final byte MEDIUM_BYTE = 2;
    static final byte HIGH_BYTE = 3;
    private static MEMCEffectCodec memcEffectCodec;

    private MEMCEffectCodec() {
        super(MEMCEffect.class);
    }

    public static synchronized MEMCEffectCodec getInstance() {
        if (memcEffectCodec == null) {
            memcEffectCodec = new MEMCEffectCodec();
        }
        return memcEffectCodec;
    }

    @Override
    protected void initializeDeviceSettings() {
        EnumMap<MEMCEffect, Byte> domainMEMCEffect = new EnumMap<MEMCEffect, Byte>(MEMCEffect.class);
        domainMEMCEffect.put(MEMCEffect.OFF, (byte)0);
        domainMEMCEffect.put(MEMCEffect.LOW, (byte)1);
        domainMEMCEffect.put(MEMCEffect.MEDIUM, (byte)2);
        domainMEMCEffect.put(MEMCEffect.HIGH, (byte)3);
        super.setDeviceSettings(domainMEMCEffect);
    }
}

