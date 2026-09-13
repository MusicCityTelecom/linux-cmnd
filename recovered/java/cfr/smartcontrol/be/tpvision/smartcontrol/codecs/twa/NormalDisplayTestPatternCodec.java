/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.codecs.twa;

import be.tpvision.smartcontrol.codecs.twa.SingleValueCodec;
import be.tpvision.smartcontrol.domain.twa.NormalDisplayTestPattern;
import java.util.Map;

public class NormalDisplayTestPatternCodec
extends SingleValueCodec<NormalDisplayTestPattern> {
    static final byte NORMAL_DISPLAY_BYTE = 0;
    static final byte TEST_PATTERN_BYTE = 1;
    private static NormalDisplayTestPatternCodec normalDisplayTestPatternCodec;

    private NormalDisplayTestPatternCodec() {
        super(NormalDisplayTestPattern.class);
        Map domainNormalDisplayTestPattern = super.getDeviceSettings();
        domainNormalDisplayTestPattern.put(NormalDisplayTestPattern.NORMAL_DISPLAY, (byte)0);
        domainNormalDisplayTestPattern.put(NormalDisplayTestPattern.TEST_PATTERN, (byte)1);
        super.setDeviceSettings(domainNormalDisplayTestPattern);
    }

    public static synchronized NormalDisplayTestPatternCodec getInstance() {
        if (normalDisplayTestPatternCodec == null) {
            normalDisplayTestPatternCodec = new NormalDisplayTestPatternCodec();
        }
        return normalDisplayTestPatternCodec;
    }

    @Override
    public NormalDisplayTestPattern toDomain(byte[] bytes) {
        throw new UnsupportedOperationException("Normal display / test pattern codec toDomain() is not supported.");
    }
}

