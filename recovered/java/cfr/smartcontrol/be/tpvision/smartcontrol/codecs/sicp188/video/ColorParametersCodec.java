/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.codecs.sicp188.video;

import be.tpvision.smartcontrol.codecs.sicp.Codec;
import be.tpvision.smartcontrol.domain.device_settings.video.ColorParameters;
import be.tpvision.smartcontrol.messages.codecs.sicp188.video.color_parameters.ToProtocolMessages;
import be.tpvision.smartcontrol.util.ValueUtilities;
import org.springframework.util.Assert;

public class ColorParametersCodec
extends Codec<ColorParameters> {
    private static ColorParametersCodec colorParametersCodec;

    private ColorParametersCodec() {
        super(ColorParameters.class);
    }

    public static synchronized ColorParametersCodec getInstance() {
        if (colorParametersCodec == null) {
            colorParametersCodec = new ColorParametersCodec();
        }
        return colorParametersCodec;
    }

    @Override
    public byte[] toProtocol(ColorParameters colorParameters) {
        Assert.notNull((Object)colorParameters, ToProtocolMessages.COLOR_PARAMETERS_CAN_NOT_BE_NULL);
        ColorParameters.Color domainRed = colorParameters.getRed();
        int domainRedGain = domainRed.getGain();
        byte protocolRedGain = ValueUtilities.getByteValueFromUnsigned(domainRedGain);
        int domainRedOffset = domainRed.getOffset();
        byte protocolRedOffset = ValueUtilities.getByteValueFromUnsigned(domainRedOffset);
        ColorParameters.Color domainGreen = colorParameters.getGreen();
        int domainGreenGain = domainGreen.getGain();
        byte protocolGreenGain = ValueUtilities.getByteValueFromUnsigned(domainGreenGain);
        int domainGreenOffset = domainGreen.getOffset();
        byte protocolGreenOffset = ValueUtilities.getByteValueFromUnsigned(domainGreenOffset);
        ColorParameters.Color domainBlue = colorParameters.getBlue();
        int domainBlueGain = domainBlue.getGain();
        byte protocolBlueGain = ValueUtilities.getByteValueFromUnsigned(domainBlueGain);
        int domainBlueOffset = domainBlue.getOffset();
        byte protocolBlueOffset = ValueUtilities.getByteValueFromUnsigned(domainBlueOffset);
        return new byte[]{protocolRedGain, protocolGreenGain, protocolBlueGain, protocolRedOffset, protocolGreenOffset, protocolBlueOffset};
    }

    private ColorParameters.Color getDomainColor(byte protocolGain, byte protocolOffset) {
        int domainGain = Byte.toUnsignedInt(protocolGain);
        int domainOffset = Byte.toUnsignedInt(protocolOffset);
        return new ColorParameters.Color(domainGain, domainOffset);
    }

    @Override
    public ColorParameters toDomain(byte[] bytes) {
        if (bytes == null || bytes.length < 6) {
            return null;
        }
        byte protocolGain = bytes[0];
        byte protocolOffset = bytes[3];
        ColorParameters.Color domainRed = this.getDomainColor(protocolGain, protocolOffset);
        protocolGain = bytes[1];
        protocolOffset = bytes[4];
        ColorParameters.Color domainGreen = this.getDomainColor(protocolGain, protocolOffset);
        protocolGain = bytes[2];
        protocolOffset = bytes[5];
        ColorParameters.Color domainBlue = this.getDomainColor(protocolGain, protocolOffset);
        return new ColorParameters(domainRed, domainGreen, domainBlue);
    }
}

