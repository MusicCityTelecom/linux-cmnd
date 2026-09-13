/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.codecs.sicp200.audio;

import be.tpvision.smartcontrol.codecs.sicp.SingleValueCodec;
import be.tpvision.smartcontrol.domain.device_settings.audio.Mute;
import java.util.EnumMap;

public class MuteCodec
extends SingleValueCodec<Mute> {
    static final byte OFF_BYTE = 0;
    static final byte ON_BYTE = 1;
    private static MuteCodec muteCodec;

    private MuteCodec() {
        super(Mute.class);
    }

    public static synchronized MuteCodec getInstance() {
        if (muteCodec == null) {
            muteCodec = new MuteCodec();
        }
        return muteCodec;
    }

    @Override
    protected void initializeDeviceSettings() {
        EnumMap<Mute, Byte> domainTouch = new EnumMap<Mute, Byte>(Mute.class);
        domainTouch.put(Mute.OFF, (byte)0);
        domainTouch.put(Mute.ON, (byte)1);
        super.setDeviceSettings(domainTouch);
    }
}

