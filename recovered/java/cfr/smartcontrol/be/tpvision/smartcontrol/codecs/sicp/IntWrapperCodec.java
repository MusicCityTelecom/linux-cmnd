/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.codecs.sicp;

import be.tpvision.smartcontrol.codecs.sicp.Codec;
import be.tpvision.smartcontrol.domain.device_settings.IntWrapper;
import be.tpvision.smartcontrol.messages.codecs.sicp.int_wrapper.ToProtocolMessages;
import be.tpvision.smartcontrol.util.ValueUtilities;
import org.springframework.util.Assert;

public class IntWrapperCodec
extends Codec<IntWrapper> {
    private static IntWrapperCodec intWrapperCodec;

    private IntWrapperCodec() {
        super(IntWrapper.class);
    }

    public static synchronized IntWrapperCodec getInstance() {
        if (intWrapperCodec == null) {
            intWrapperCodec = new IntWrapperCodec();
        }
        return intWrapperCodec;
    }

    @Override
    public byte[] toProtocol(IntWrapper intWrapper) {
        Assert.notNull((Object)intWrapper, ToProtocolMessages.INT_WRAPPER_CAN_NOT_BE_NULL);
        int domainValue = intWrapper.getValue();
        byte protocolValue = ValueUtilities.getByteValue(domainValue);
        return new byte[]{protocolValue};
    }

    @Override
    public IntWrapper toDomain(byte[] bytes) {
        if (bytes == null || bytes.length < 1) {
            return null;
        }
        byte value = bytes[0];
        return new IntWrapper(value);
    }
}

