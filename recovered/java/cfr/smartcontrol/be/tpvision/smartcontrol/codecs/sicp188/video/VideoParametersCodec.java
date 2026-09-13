/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.codecs.sicp188.video;

import be.tpvision.smartcontrol.codecs.sicp.Codec;
import be.tpvision.smartcontrol.domain.device_settings.video.VideoParameters;
import be.tpvision.smartcontrol.messages.codecs.sicp188.video.video_parameters.ToDomainMessages;
import be.tpvision.smartcontrol.messages.codecs.sicp188.video.video_parameters.ToProtocolMessages;
import be.tpvision.smartcontrol.util.MapUtilities;
import be.tpvision.smartcontrol.util.ValueUtilities;
import java.util.EnumMap;
import java.util.Map;
import org.springframework.util.Assert;

public class VideoParametersCodec
extends Codec<VideoParameters> {
    static final byte GAMMA_SELECTION_NATIVE_BYTE = 1;
    static final byte GAMMA_SELECTION_S_BYTE = 2;
    static final byte GAMMA_SELECTION_TWO_DOT_TWO_BYTE = 3;
    static final byte GAMMA_SELECTION_TWO_DOT_FOUR_BYTE = 4;
    static final byte GAMMA_SELECTION_D_IMAGE_BYTE = 5;
    private static final Map<VideoParameters.GammaSelection, Byte> domainGammaSelections = new EnumMap<VideoParameters.GammaSelection, Byte>(VideoParameters.GammaSelection.class);
    private static final Map<Byte, VideoParameters.GammaSelection> protocolGammaSelections;
    private static VideoParametersCodec vgaVideoParametersCodec;

    private VideoParametersCodec() {
        super(VideoParameters.class);
    }

    public static synchronized VideoParametersCodec getInstance() {
        if (vgaVideoParametersCodec == null) {
            vgaVideoParametersCodec = new VideoParametersCodec();
        }
        return vgaVideoParametersCodec;
    }

    @Override
    public byte[] toProtocol(VideoParameters videoParameters) {
        Assert.notNull((Object)videoParameters, ToProtocolMessages.VIDEO_PARAMETERS_CAN_NOT_BE_NULL);
        int domainBrightness = videoParameters.getBrightness();
        byte protocolBrightness = ValueUtilities.getByteValueFromUnsigned(domainBrightness);
        int domainColor = videoParameters.getColor();
        byte protocolColor = ValueUtilities.getByteValueFromUnsigned(domainColor);
        int domainContrast = videoParameters.getContrast();
        byte protocolContrast = ValueUtilities.getByteValueFromUnsigned(domainContrast);
        int domainSharpness = videoParameters.getSharpness();
        byte protocolSharpness = ValueUtilities.getByteValueFromUnsigned(domainSharpness);
        int domainHue = videoParameters.getHue();
        byte protocolHue = ValueUtilities.getByteValue(domainHue);
        int domainBacklight = videoParameters.getBacklight();
        byte protocolBacklight = ValueUtilities.getByteValueFromUnsigned(domainBacklight);
        VideoParameters.GammaSelection domainGammaSelection = videoParameters.getGammaSelection();
        Assert.state(domainGammaSelection != null, ToProtocolMessages.DOMAIN_GAMMA_SELECTION_CAN_NOT_BE_NULL);
        Byte protocolGammaSelection = domainGammaSelections.get((Object)domainGammaSelection);
        Assert.state(protocolGammaSelection != null, ToProtocolMessages.PROTOCOL_GAMMA_SELECTION_CAN_NOT_BE_NULL);
        return new byte[]{protocolBrightness, protocolColor, protocolContrast, protocolSharpness, protocolHue, protocolBacklight, protocolGammaSelection};
    }

    @Override
    public VideoParameters toDomain(byte[] bytes) {
        if (bytes == null || bytes.length < 7) {
            return null;
        }
        byte protocolBrightness = bytes[0];
        int domainBrightness = Byte.toUnsignedInt(protocolBrightness);
        byte protocolColor = bytes[1];
        int domainColor = Byte.toUnsignedInt(protocolColor);
        byte protocolContrast = bytes[2];
        int domainContrast = Byte.toUnsignedInt(protocolContrast);
        byte protocolSharpness = bytes[3];
        int domainSharpness = Byte.toUnsignedInt(protocolSharpness);
        byte hue = bytes[4];
        byte protocolBacklight = bytes[5];
        int domainBacklight = Byte.toUnsignedInt(protocolBacklight);
        byte protocolGammaSelection = bytes[6];
        VideoParameters.GammaSelection domainGammaSelection = protocolGammaSelections.get(protocolGammaSelection);
        Assert.state(domainGammaSelection != null, ToDomainMessages.DOMAIN_GAMMA_SELECTION_CAN_NOT_BE_NULL);
        return new VideoParameters(domainBrightness, domainColor, domainContrast, domainSharpness, hue, domainBacklight, domainGammaSelection);
    }

    static {
        domainGammaSelections.put(VideoParameters.GammaSelection.NATIVE, (byte)1);
        domainGammaSelections.put(VideoParameters.GammaSelection.S, (byte)2);
        domainGammaSelections.put(VideoParameters.GammaSelection.TWO_DOT_TWO, (byte)3);
        domainGammaSelections.put(VideoParameters.GammaSelection.TWO_DOT_FOUR, (byte)4);
        domainGammaSelections.put(VideoParameters.GammaSelection.D_IMAGE, (byte)5);
        protocolGammaSelections = MapUtilities.inverse(domainGammaSelections);
    }
}

