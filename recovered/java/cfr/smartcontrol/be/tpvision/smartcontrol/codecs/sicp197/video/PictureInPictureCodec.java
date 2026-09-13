/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.codecs.sicp197.video;

import be.tpvision.smartcontrol.codecs.sicp.Codec;
import be.tpvision.smartcontrol.domain.device_settings.video.PictureInPicture;
import be.tpvision.smartcontrol.messages.codecs.sicp197.video.picture_in_picture.ToDomainMessages;
import be.tpvision.smartcontrol.messages.codecs.sicp197.video.picture_in_picture.ToProtocolMessages;
import be.tpvision.smartcontrol.util.MapUtilities;
import java.util.EnumMap;
import java.util.Map;
import org.springframework.util.Assert;

public class PictureInPictureCodec
extends Codec<PictureInPicture> {
    static final byte STATUS_OFF_BYTE = 0;
    static final byte STATUS_ON_BYTE = 1;
    static final byte STATUS_POP_BYTE = 2;
    static final byte STATUS_QUICK_SWAP_BYTE = 3;
    static final byte STATUS_PBP_2WIN_BYTE = 4;
    static final byte STATUS_PBP_3WIN_BYTE = 5;
    static final byte STATUS_PBP_4WIN_BYTE = 6;
    static final byte STATUS_PBP_3WIN_1_BYTE = 7;
    static final byte STATUS_PBP_3WIN_2_BYTE = 8;
    static final byte STATUS_PBP_4WIN_1_BYTE = 9;
    static final byte STATUS_SICP_BYTE = 10;
    private static final Map<PictureInPicture.Status, Byte> domainStatuses = new EnumMap<PictureInPicture.Status, Byte>(PictureInPicture.Status.class);
    private static final Map<Byte, PictureInPicture.Status> protocolStatuses;
    static final byte WINDOW_POSITION_BOTTOM_LEFT_BYTE = 0;
    static final byte WINDOW_POSITION_TOP_LEFT_BYTE = 1;
    static final byte WINDOW_POSITION_TOP_RIGHT_BYTE = 2;
    static final byte WINDOW_POSITION_BOTTOM_RIGHT_BYTE = 3;
    static final byte WINDOW_POSITION_CENTER_BYTE = 4;
    private static final Map<PictureInPicture.WindowPosition, Byte> domainWindowPositions;
    private static final Map<Byte, PictureInPicture.WindowPosition> protocolWindowPositions;
    static final byte RESERVED_BYTE = 0;
    private static PictureInPictureCodec pictureInPictureCodec;

    private PictureInPictureCodec() {
        super(PictureInPicture.class);
    }

    public static Map<PictureInPicture.Status, Byte> getDomainStatuses() {
        return domainStatuses;
    }

    public static Map<Byte, PictureInPicture.Status> getProtocolStatuses() {
        return protocolStatuses;
    }

    public static Map<PictureInPicture.WindowPosition, Byte> getDomainWindowPositions() {
        return domainWindowPositions;
    }

    public static Map<Byte, PictureInPicture.WindowPosition> getProtocolWindowPositions() {
        return protocolWindowPositions;
    }

    public static synchronized PictureInPictureCodec getInstance() {
        if (pictureInPictureCodec == null) {
            pictureInPictureCodec = new PictureInPictureCodec();
        }
        return pictureInPictureCodec;
    }

    @Override
    public byte[] toProtocol(PictureInPicture pictureInPicture) {
        Assert.notNull((Object)pictureInPicture, ToProtocolMessages.PICTURE_IN_PICTURE_CAN_NOT_BE_NULL);
        PictureInPicture.Status domainStatus = pictureInPicture.getStatus();
        Assert.state(domainStatus != null, ToProtocolMessages.DOMAIN_STATUS_CAN_NOT_BE_NULL);
        Byte protocolStatus = domainStatuses.get((Object)domainStatus);
        Assert.state(protocolStatus != null, ToProtocolMessages.PROTOCOL_STATUS_CAN_NOT_BE_NULL);
        PictureInPicture.WindowPosition domainWindowPosition = pictureInPicture.getWindowPosition();
        Assert.state(domainWindowPosition != null, ToProtocolMessages.DOMAIN_WINDOW_POSITION_CAN_NOT_BE_NULL);
        Byte protocolWindowPosition = domainWindowPositions.get((Object)domainWindowPosition);
        Assert.state(protocolWindowPosition != null, ToProtocolMessages.PROTOCOL_WINDOW_POSITION_CAN_NOT_BE_NULL);
        return new byte[]{protocolStatus, protocolWindowPosition, 0, 0};
    }

    @Override
    public PictureInPicture toDomain(byte[] bytes) {
        if (bytes == null || bytes.length < 4) {
            return null;
        }
        byte protocolStatus = bytes[0];
        PictureInPicture.Status domainStatus = protocolStatuses.get(protocolStatus);
        Assert.state(domainStatus != null, ToDomainMessages.DOMAIN_STATUS_CAN_NOT_BE_NULL);
        byte protocolWindowPosition = bytes[1];
        PictureInPicture.WindowPosition domainWindowPosition = protocolWindowPositions.get(protocolWindowPosition);
        Assert.state(domainWindowPosition != null, ToDomainMessages.DOMAIN_WINDOW_POSITION_CAN_NOT_BE_NULL);
        return new PictureInPicture(domainStatus, domainWindowPosition);
    }

    static {
        domainStatuses.put(PictureInPicture.Status.OFF, (byte)0);
        domainStatuses.put(PictureInPicture.Status.ON, (byte)1);
        domainStatuses.put(PictureInPicture.Status.POP, (byte)2);
        domainStatuses.put(PictureInPicture.Status.QUICK_SWAP, (byte)3);
        domainStatuses.put(PictureInPicture.Status.PBP_2WIN, (byte)4);
        domainStatuses.put(PictureInPicture.Status.PBP_3WIN, (byte)5);
        domainStatuses.put(PictureInPicture.Status.PBP_4WIN, (byte)6);
        domainStatuses.put(PictureInPicture.Status.PBP_3WIN_1, (byte)7);
        domainStatuses.put(PictureInPicture.Status.PBP_3WIN_2, (byte)8);
        domainStatuses.put(PictureInPicture.Status.PBP_4WIN_1, (byte)9);
        domainStatuses.put(PictureInPicture.Status.SICP, (byte)10);
        protocolStatuses = MapUtilities.inverse(domainStatuses);
        domainWindowPositions = new EnumMap<PictureInPicture.WindowPosition, Byte>(PictureInPicture.WindowPosition.class);
        domainWindowPositions.put(PictureInPicture.WindowPosition.BOTTOM_LEFT, (byte)0);
        domainWindowPositions.put(PictureInPicture.WindowPosition.TOP_LEFT, (byte)1);
        domainWindowPositions.put(PictureInPicture.WindowPosition.TOP_RIGHT, (byte)2);
        domainWindowPositions.put(PictureInPicture.WindowPosition.BOTTOM_RIGHT, (byte)3);
        domainWindowPositions.put(PictureInPicture.WindowPosition.CENTER, (byte)4);
        protocolWindowPositions = MapUtilities.inverse(domainWindowPositions);
    }
}

