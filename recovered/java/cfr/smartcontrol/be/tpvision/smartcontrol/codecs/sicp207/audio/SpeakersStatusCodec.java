/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.codecs.sicp207.audio;

import be.tpvision.smartcontrol.codecs.sicp.SingleValueCodec;
import be.tpvision.smartcontrol.domain.device_settings.audio.SpeakersStatus;
import java.util.EnumMap;

public class SpeakersStatusCodec
extends SingleValueCodec<SpeakersStatus> {
    static final byte OFF_BYTE = 0;
    static final byte ON_BYTE = 1;
    private static SpeakersStatusCodec speakersStatusCodec;

    private SpeakersStatusCodec() {
        super(SpeakersStatus.class);
    }

    public static synchronized SpeakersStatusCodec getInstance() {
        if (speakersStatusCodec == null) {
            speakersStatusCodec = new SpeakersStatusCodec();
        }
        return speakersStatusCodec;
    }

    @Override
    protected void initializeDeviceSettings() {
        EnumMap<SpeakersStatus, Byte> domainTouch = new EnumMap<SpeakersStatus, Byte>(SpeakersStatus.class);
        domainTouch.put(SpeakersStatus.OFF, (byte)0);
        domainTouch.put(SpeakersStatus.ON, (byte)1);
        super.setDeviceSettings(domainTouch);
    }
}

