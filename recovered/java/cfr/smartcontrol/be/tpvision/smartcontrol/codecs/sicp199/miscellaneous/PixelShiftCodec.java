/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.codecs.sicp199.miscellaneous;

import be.tpvision.smartcontrol.codecs.sicp.Codec;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.PixelShift;
import be.tpvision.smartcontrol.messages.codecs.sicp199.miscellaneous.pixel_shift.ToDomainMessages;
import be.tpvision.smartcontrol.messages.codecs.sicp199.miscellaneous.pixel_shift.ToProtocolMessages;
import be.tpvision.smartcontrol.util.MapUtilities;
import be.tpvision.smartcontrol.util.ValueUtilities;
import java.util.EnumMap;
import java.util.Map;
import org.springframework.util.Assert;

public class PixelShiftCodec
extends Codec<PixelShift> {
    static final byte PIXEL_SHIFT_AUTO_BYTE = 91;
    private static final Map<PixelShift.State, Byte> domainStates = new EnumMap<PixelShift.State, Byte>(PixelShift.State.class);
    private static final Map<Byte, PixelShift.State> protocolStates;
    private static PixelShiftCodec pixelShiftCodec;

    private PixelShiftCodec() {
        super(PixelShift.class);
    }

    @Override
    public byte[] toProtocol(PixelShift pixelShift) {
        Assert.notNull((Object)pixelShift, ToProtocolMessages.PIXEL_SHIFT_CAN_NOT_BE_NULL_MESSAGE);
        PixelShift.State domainState = pixelShift.getState();
        Assert.state(domainState != null, ToProtocolMessages.DOMAIN_STATE_CAN_NOT_BE_NULL_MESSAGE);
        Byte protocolState = domainStates.get((Object)domainState);
        if (protocolState == null && domainState.equals((Object)PixelShift.State.CUSTOM)) {
            protocolState = ValueUtilities.getByteValueFromUnsigned(pixelShift.getValue());
        }
        Assert.state(protocolState != null, ToProtocolMessages.PROTOCOL_STATE_CAN_NOT_BE_NULL_MESSAGE);
        return new byte[]{protocolState};
    }

    @Override
    public PixelShift toDomain(byte[] bytes) {
        if (bytes == null || bytes.length < 1) {
            return null;
        }
        byte protocolResponse = bytes[0];
        PixelShift.State domainState = protocolStates.get(protocolResponse);
        if (domainState == null && protocolResponse < 91) {
            domainState = PixelShift.State.CUSTOM;
        }
        Assert.notNull((Object)domainState, ToDomainMessages.DOMAIN_STATE_CAN_NOT_BE_NULL_MESSAGE);
        byte protocolValue = bytes[0];
        int domainValue = Byte.toUnsignedInt(protocolValue);
        return new PixelShift(domainState, domainValue);
    }

    public static synchronized PixelShiftCodec getInstance() {
        if (pixelShiftCodec == null) {
            pixelShiftCodec = new PixelShiftCodec();
        }
        return pixelShiftCodec;
    }

    static {
        domainStates.put(PixelShift.State.AUTO, (byte)91);
        protocolStates = MapUtilities.inverse(domainStates);
    }
}

