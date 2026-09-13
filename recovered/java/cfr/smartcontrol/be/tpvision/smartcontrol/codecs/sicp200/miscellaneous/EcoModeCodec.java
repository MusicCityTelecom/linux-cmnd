/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.codecs.sicp200.miscellaneous;

import be.tpvision.smartcontrol.codecs.sicp.SingleValueCodec;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.EcoMode;
import java.util.EnumMap;

public class EcoModeCodec
extends SingleValueCodec<EcoMode> {
    static final byte LOW_POWER_STANDBY_BYTE = 0;
    static final byte NORMAL_BYTE = 1;
    private static EcoModeCodec ecoModeCodec;

    private EcoModeCodec() {
        super(EcoMode.class);
    }

    public static synchronized EcoModeCodec getInstance() {
        if (ecoModeCodec == null) {
            ecoModeCodec = new EcoModeCodec();
        }
        return ecoModeCodec;
    }

    @Override
    protected void initializeDeviceSettings() {
        EnumMap<EcoMode, Byte> domainEcoMode = new EnumMap<EcoMode, Byte>(EcoMode.class);
        domainEcoMode.put(EcoMode.LOW_POWER_STANDBY, (byte)0);
        domainEcoMode.put(EcoMode.NORMAL, (byte)1);
        super.setDeviceSettings(domainEcoMode);
    }
}

