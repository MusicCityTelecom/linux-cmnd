/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.codecs.sicp188.miscellaneous;

import be.tpvision.smartcontrol.codecs.sicp.Codec;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.Tiling;
import be.tpvision.smartcontrol.messages.codecs.sicp188.miscellaneous.tiling.ToDomainMessages;
import be.tpvision.smartcontrol.messages.codecs.sicp188.miscellaneous.tiling.ToProtocolMessages;
import be.tpvision.smartcontrol.util.MapUtilities;
import be.tpvision.smartcontrol.util.ValueUtilities;
import java.util.EnumMap;
import java.util.Map;
import org.springframework.util.Assert;

public class TilingCodec
extends Codec<Tiling> {
    static final int MAXIMUM_NUMBER_OF_H_MONITORS = 15;
    static final byte ENABLE_NO_BYTE = 0;
    static final byte ENABLE_YES_BYTE = 1;
    private static final Map<Tiling.Enable, Byte> domainEnables = new EnumMap<Tiling.Enable, Byte>(Tiling.Enable.class);
    private static final Map<Byte, Tiling.Enable> protocolEnables;
    static final byte FRAME_COMP_NO_BYTE = 0;
    static final byte FRAME_COMP_YES_BYTE = 1;
    static final byte FRAME_COMP_DO_NOT_OVERWRITE_BYTE = 2;
    private static final Map<Tiling.FrameComp, Byte> domainFrameComps;
    private static final Map<Byte, Tiling.FrameComp> protocolFrameComps;
    private static TilingCodec tilingCodec;

    private TilingCodec() {
        super(Tiling.class);
    }

    public static synchronized TilingCodec getInstance() {
        if (tilingCodec == null) {
            tilingCodec = new TilingCodec();
        }
        return tilingCodec;
    }

    @Override
    public byte[] toProtocol(Tiling tiling) {
        Assert.notNull((Object)tiling, ToProtocolMessages.TILING_CAN_NOT_BE_NULL);
        Tiling.Enable domainEnable = tiling.getEnable();
        Assert.state(domainEnable != null, ToProtocolMessages.DOMAIN_ENABLE_CAN_NOT_BE_NULL);
        Byte protocolEnable = domainEnables.get((Object)domainEnable);
        Assert.state(protocolEnable != null, ToProtocolMessages.PROTOCOL_ENABLE_CAN_NOT_BE_NULL);
        Tiling.FrameComp domainFrameComp = tiling.getFrameComp();
        Assert.state(domainFrameComp != null, ToProtocolMessages.DOMAIN_FRAME_COMP_CAN_NOT_BE_NULL);
        Byte protocolFrameComp = domainFrameComps.get((Object)domainFrameComp);
        Assert.state(protocolFrameComp != null, ToProtocolMessages.PROTOCOL_FRAME_COMP_CAN_NOT_BE_NULL);
        int domainPosition = tiling.getPosition();
        byte protocolPosition = ValueUtilities.getByteValueFromUnsigned(domainPosition);
        int domainNumberOfHorizontalMonitors = tiling.getNumberOfHorizontalMonitors();
        int domainNumberOfVerticalMonitors = tiling.getNumberOfVerticalMonitors();
        int domainMonitors = (domainNumberOfVerticalMonitors - 1) * 15 + domainNumberOfHorizontalMonitors;
        byte protocolMonitors = ValueUtilities.getByteValueFromUnsigned(domainMonitors);
        return new byte[]{protocolEnable, protocolFrameComp, protocolPosition, protocolMonitors};
    }

    @Override
    public Tiling toDomain(byte[] bytes) {
        if (bytes == null || bytes.length < 4) {
            return null;
        }
        byte protocolEnable = bytes[0];
        Tiling.Enable domainEnable = protocolEnables.get(protocolEnable);
        Assert.state(domainEnable != null, ToDomainMessages.DOMAIN_ENABLE_CAN_NOT_BE_NULL);
        byte protocolFrameComp = bytes[1];
        Tiling.FrameComp domainFrameComp = protocolFrameComps.get(protocolFrameComp);
        Assert.state(domainFrameComp != null, ToDomainMessages.DOMAIN_FRAME_COMP_CAN_NOT_BE_NULL);
        byte protocolPosition = bytes[2];
        int domainPosition = Byte.toUnsignedInt(protocolPosition);
        byte protocolMonitors = bytes[3];
        int domainMonitors = Byte.toUnsignedInt(protocolMonitors);
        int numberOfHMonitors = domainMonitors % 15;
        int numberOfVMonitors = domainMonitors / 15 + 1;
        return new Tiling(domainEnable, domainFrameComp, domainPosition, numberOfHMonitors, numberOfVMonitors);
    }

    static {
        domainEnables.put(Tiling.Enable.NO, (byte)0);
        domainEnables.put(Tiling.Enable.YES, (byte)1);
        protocolEnables = MapUtilities.inverse(domainEnables);
        domainFrameComps = new EnumMap<Tiling.FrameComp, Byte>(Tiling.FrameComp.class);
        domainFrameComps.put(Tiling.FrameComp.NO, (byte)0);
        domainFrameComps.put(Tiling.FrameComp.YES, (byte)1);
        domainFrameComps.put(Tiling.FrameComp.DO_NOT_OVERWRITE, (byte)2);
        protocolFrameComps = MapUtilities.inverse(domainFrameComps);
    }
}

