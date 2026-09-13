/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.codecs.sicp188.miscellaneous;

import be.tpvision.smartcontrol.codecs.sicp.SingleValueCodec;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.ScanConversion;
import java.util.EnumMap;

public class ScanConversionCodec
extends SingleValueCodec<ScanConversion> {
    static final byte PROGRESSIVE_BYTE = 0;
    static final byte INTERLACE_BYTE = 1;
    private static ScanConversionCodec scanConversionCodec;

    private ScanConversionCodec() {
        super(ScanConversion.class);
    }

    public static synchronized ScanConversionCodec getInstance() {
        if (scanConversionCodec == null) {
            scanConversionCodec = new ScanConversionCodec();
        }
        return scanConversionCodec;
    }

    @Override
    protected void initializeDeviceSettings() {
        EnumMap<ScanConversion, Byte> domainScanConversion = new EnumMap<ScanConversion, Byte>(ScanConversion.class);
        domainScanConversion.put(ScanConversion.PROGRESSIVE, (byte)0);
        domainScanConversion.put(ScanConversion.INTERLACE, (byte)1);
        super.setDeviceSettings(domainScanConversion);
    }
}

