/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.codecs.twa;

import be.tpvision.smartcontrol.codecs.twa.Codec;
import be.tpvision.smartcontrol.domain.twa.MainboardOriginPosition;
import be.tpvision.smartcontrol.messages.codecs.twa.mainboard_origin_position.ToProtocolMessages;
import java.nio.ByteBuffer;
import org.springframework.util.Assert;

public class MainboardOriginPositionCodec
extends Codec<MainboardOriginPosition> {
    private static MainboardOriginPositionCodec mainboardOriginPositionCodec;

    private MainboardOriginPositionCodec() {
        super(MainboardOriginPosition.class);
    }

    public static synchronized MainboardOriginPositionCodec getInstance() {
        if (mainboardOriginPositionCodec == null) {
            mainboardOriginPositionCodec = new MainboardOriginPositionCodec();
        }
        return mainboardOriginPositionCodec;
    }

    @Override
    public byte[] toProtocol(MainboardOriginPosition mainboardOriginPosition) {
        Assert.notNull((Object)mainboardOriginPosition, ToProtocolMessages.MAINBOARD_ORIGIN_POSITION_CAN_NOT_BE_NULL);
        ByteBuffer byteBuffer = ByteBuffer.allocate(20);
        for (int i = 0; i < 4; ++i) {
            byteBuffer.put((byte)0);
        }
        int domainX = mainboardOriginPosition.getX();
        byte protocolHighX = (byte)(domainX >> 8 & 0xFF);
        byte protocolLowX = (byte)(domainX & 0xFF);
        byte[] protocolX = new byte[]{protocolHighX, protocolLowX};
        byteBuffer.put(protocolX);
        int domainY = mainboardOriginPosition.getY();
        byte protocolHighY = (byte)(domainY >> 8 & 0xFF);
        byte protocolLowY = (byte)(domainY & 0xFF);
        byte[] protocolY = new byte[]{protocolHighY, protocolLowY};
        byteBuffer.put(protocolY);
        for (int i = 0; i < 12; ++i) {
            byteBuffer.put((byte)0);
        }
        return byteBuffer.array();
    }

    @Override
    public MainboardOriginPosition toDomain(byte[] bytes) {
        throw new UnsupportedOperationException("Mainboard origin position codec toDomain() is not supported.");
    }
}

