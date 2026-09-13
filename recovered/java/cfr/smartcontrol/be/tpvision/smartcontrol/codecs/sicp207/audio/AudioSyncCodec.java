/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.codecs.sicp207.audio;

import be.tpvision.smartcontrol.codecs.sicp.SingleValueCodec;
import be.tpvision.smartcontrol.domain.device_settings.audio.AudioSync;
import java.util.EnumMap;

public class AudioSyncCodec
extends SingleValueCodec<AudioSync> {
    static final byte OFF_BYTE = 0;
    static final byte ON_BYTE = 1;
    private static AudioSyncCodec audioSyncCodec;

    private AudioSyncCodec() {
        super(AudioSync.class);
    }

    public static synchronized AudioSyncCodec getInstance() {
        if (audioSyncCodec == null) {
            audioSyncCodec = new AudioSyncCodec();
        }
        return audioSyncCodec;
    }

    @Override
    protected void initializeDeviceSettings() {
        EnumMap<AudioSync, Byte> domainTouch = new EnumMap<AudioSync, Byte>(AudioSync.class);
        domainTouch.put(AudioSync.OFF, (byte)0);
        domainTouch.put(AudioSync.ON, (byte)1);
        super.setDeviceSettings(domainTouch);
    }
}

