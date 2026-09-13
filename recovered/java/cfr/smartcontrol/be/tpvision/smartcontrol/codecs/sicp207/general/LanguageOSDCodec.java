/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.codecs.sicp207.general;

import be.tpvision.smartcontrol.codecs.sicp.SingleValueCodec;
import be.tpvision.smartcontrol.domain.device_settings.general.LanguageOSD;
import java.util.EnumMap;

public class LanguageOSDCodec
extends SingleValueCodec<LanguageOSD> {
    private static LanguageOSDCodec languageOSDCodec;

    private LanguageOSDCodec() {
        super(LanguageOSD.class);
    }

    public static synchronized LanguageOSDCodec getInstance() {
        if (languageOSDCodec == null) {
            languageOSDCodec = new LanguageOSDCodec();
        }
        return languageOSDCodec;
    }

    @Override
    protected void initializeDeviceSettings() {
        EnumMap<LanguageOSD, Byte> domainTouch = new EnumMap<LanguageOSD, Byte>(LanguageOSD.class);
        for (LanguageOSD languageOSD : LanguageOSD.values()) {
            domainTouch.put(languageOSD, (byte)languageOSD.getIndex());
        }
        super.setDeviceSettings(domainTouch);
    }
}

