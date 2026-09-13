/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.codecs.sicp188.audio;

import be.tpvision.smartcontrol.codecs.sicp.Codec;
import be.tpvision.smartcontrol.domain.device_settings.audio.Volume;
import be.tpvision.smartcontrol.messages.codecs.sicp188.audio.volume.ToProtocolMessages;
import be.tpvision.smartcontrol.util.ValueUtilities;
import org.springframework.util.Assert;

public class VolumeCodec
extends Codec<Volume> {
    private static VolumeCodec volumeCodec;

    private VolumeCodec() {
        super(Volume.class);
    }

    public static synchronized VolumeCodec getInstance() {
        if (volumeCodec == null) {
            volumeCodec = new VolumeCodec();
        }
        return volumeCodec;
    }

    @Override
    public byte[] toProtocol(Volume volume) {
        Assert.notNull((Object)volume, ToProtocolMessages.VOLUME_CAN_NOT_BE_NULL);
        int domainSpeakerOut = volume.getSpeakerOut();
        byte protocolSpeakerOut = ValueUtilities.getByteValueFromUnsigned(domainSpeakerOut);
        int domainAudioOut = volume.getAudioOut();
        byte protocolAudioOut = ValueUtilities.getByteValueFromUnsigned(domainAudioOut);
        return new byte[]{protocolSpeakerOut, protocolAudioOut};
    }

    @Override
    public Volume toDomain(byte[] bytes) {
        if (bytes == null || bytes.length < 2) {
            return null;
        }
        byte protocolSpeakerOut = bytes[0];
        int domainSpeakerOut = Byte.toUnsignedInt(protocolSpeakerOut);
        byte protocolAudioOut = bytes[1];
        int domainAudioOut = Byte.toUnsignedInt(protocolAudioOut);
        return new Volume(domainSpeakerOut, domainAudioOut);
    }
}

