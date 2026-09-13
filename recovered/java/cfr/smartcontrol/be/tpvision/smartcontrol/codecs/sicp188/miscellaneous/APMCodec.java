/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.codecs.sicp188.miscellaneous;

import be.tpvision.smartcontrol.codecs.sicp.SingleValueCodec;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.APM;
import java.util.EnumMap;

public class APMCodec
extends SingleValueCodec<APM> {
    static final byte OFF_BYTE = 0;
    static final byte ON_BYTE = 1;
    private static APMCodec apmCodec;

    private APMCodec() {
        super(APM.class);
    }

    public static synchronized APMCodec getInstance() {
        if (apmCodec == null) {
            apmCodec = new APMCodec();
        }
        return apmCodec;
    }

    @Override
    protected void initializeDeviceSettings() {
        EnumMap<APM, Byte> domainAPM = new EnumMap<APM, Byte>(APM.class);
        domainAPM.put(APM.OFF, (byte)0);
        domainAPM.put(APM.ON, (byte)1);
        super.setDeviceSettings(domainAPM);
    }
}

