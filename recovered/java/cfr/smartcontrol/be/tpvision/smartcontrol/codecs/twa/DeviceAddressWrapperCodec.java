/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.codecs.twa;

import be.tpvision.smartcontrol.codecs.twa.Codec;
import be.tpvision.smartcontrol.domain.twa.DeviceAddress;
import be.tpvision.smartcontrol.domain.twa.DeviceAddressWrapper;
import be.tpvision.smartcontrol.messages.codecs.twa.device_address.ToProtocolMessages;
import be.tpvision.smartcontrol.util.ValueUtilities;
import java.nio.ByteBuffer;
import org.springframework.util.Assert;

public class DeviceAddressWrapperCodec
extends Codec<DeviceAddressWrapper> {
    private static DeviceAddressWrapperCodec deviceAddressWrapperCodec;

    private DeviceAddressWrapperCodec() {
        super(DeviceAddressWrapper.class);
    }

    public static synchronized DeviceAddressWrapperCodec getInstance() {
        if (deviceAddressWrapperCodec == null) {
            deviceAddressWrapperCodec = new DeviceAddressWrapperCodec();
        }
        return deviceAddressWrapperCodec;
    }

    @Override
    public byte[] toProtocol(DeviceAddressWrapper deviceAddressWrapper) {
        Assert.notNull((Object)deviceAddressWrapper, ToProtocolMessages.DEVICE_ADDRESS_WRAPPER_CAN_NOT_BE_NULL);
        ByteBuffer byteBuffer = ByteBuffer.allocate(20);
        for (int i = 0; i < 4; ++i) {
            byteBuffer.put((byte)0);
        }
        DeviceAddress deviceAddress = deviceAddressWrapper.getDeviceAddress();
        Assert.state(deviceAddress != null, ToProtocolMessages.DEVICE_ADDRESS_CAN_NOT_BE_NULL);
        int domainX = deviceAddress.getX();
        byte protocolX = ValueUtilities.getByteValueFromUnsigned(domainX);
        byteBuffer.put(protocolX);
        int domainY = deviceAddress.getY();
        byte protocolY = ValueUtilities.getByteValueFromUnsigned(domainY);
        byteBuffer.put(protocolY);
        for (int i = 0; i < 14; ++i) {
            byteBuffer.put((byte)0);
        }
        return byteBuffer.array();
    }

    @Override
    public DeviceAddressWrapper toDomain(byte[] bytes) {
        throw new UnsupportedOperationException("Device address codec toDomain() is not supported.");
    }
}

