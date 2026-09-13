/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.codecs.twa;

import be.tpvision.smartcontrol.codecs.twa.SingleValueCodec;
import be.tpvision.smartcontrol.domain.twa.CascadeMode;
import java.util.Map;

public class CascadeModeCodec
extends SingleValueCodec<CascadeMode> {
    static final byte OFF_BYTE = 0;
    static final byte ON_BYTE = 1;
    private static CascadeModeCodec cascadeModeCodec;

    private CascadeModeCodec() {
        super(CascadeMode.class);
        Map domainCascadeMode = super.getDeviceSettings();
        domainCascadeMode.put(CascadeMode.OFF, (byte)0);
        domainCascadeMode.put(CascadeMode.ON, (byte)1);
        super.setDeviceSettings(domainCascadeMode);
    }

    public static synchronized CascadeModeCodec getInstance() {
        if (cascadeModeCodec == null) {
            cascadeModeCodec = new CascadeModeCodec();
        }
        return cascadeModeCodec;
    }

    @Override
    public CascadeMode toDomain(byte[] bytes) {
        throw new UnsupportedOperationException("Cascade mode codec toDomain() is not supported.");
    }
}

