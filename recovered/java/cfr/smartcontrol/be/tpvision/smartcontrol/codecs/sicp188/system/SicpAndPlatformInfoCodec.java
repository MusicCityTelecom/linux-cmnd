/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.codecs.sicp188.system;

import be.tpvision.smartcontrol.codecs.sicp.SingleValueCodec;
import be.tpvision.smartcontrol.domain.device_settings.system.SicpAndPlatformInfo;
import java.util.EnumMap;

public class SicpAndPlatformInfoCodec
extends SingleValueCodec<SicpAndPlatformInfo> {
    static final byte SICP_VERSION_BYTE = 0;
    static final byte PLATFORM_LABEL_BYTE = 1;
    static final byte PLATFORM_VERSION_BYTE = 2;
    private static SicpAndPlatformInfoCodec sicpAndPlatformInfoCodec;

    private SicpAndPlatformInfoCodec() {
        super(SicpAndPlatformInfo.class);
    }

    public static synchronized SicpAndPlatformInfoCodec getInstance() {
        if (sicpAndPlatformInfoCodec == null) {
            sicpAndPlatformInfoCodec = new SicpAndPlatformInfoCodec();
        }
        return sicpAndPlatformInfoCodec;
    }

    @Override
    protected void initializeDeviceSettings() {
        EnumMap<SicpAndPlatformInfo, Byte> domainSicpAndPlatformInfo = new EnumMap<SicpAndPlatformInfo, Byte>(SicpAndPlatformInfo.class);
        domainSicpAndPlatformInfo.put(SicpAndPlatformInfo.SICP_VERSION, (byte)0);
        domainSicpAndPlatformInfo.put(SicpAndPlatformInfo.PLATFORM_LABEL, (byte)1);
        domainSicpAndPlatformInfo.put(SicpAndPlatformInfo.PLATFORM_VERSION, (byte)2);
        super.setDeviceSettings(domainSicpAndPlatformInfo);
    }
}

