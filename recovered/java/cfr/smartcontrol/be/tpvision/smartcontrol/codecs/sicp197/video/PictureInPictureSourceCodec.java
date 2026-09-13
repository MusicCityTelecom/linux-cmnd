/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.codecs.sicp197.video;

import be.tpvision.smartcontrol.codecs.sicp.Codec;
import be.tpvision.smartcontrol.domain.device_settings.video.PictureInPictureSource;
import be.tpvision.smartcontrol.messages.codecs.sicp197.video.picture_in_picture_source.PictureInPictureSourceCodecMessages;
import be.tpvision.smartcontrol.messages.codecs.sicp197.video.picture_in_picture_source.ToDomainMessages;
import be.tpvision.smartcontrol.messages.codecs.sicp197.video.picture_in_picture_source.ToProtocolMessages;
import be.tpvision.smartcontrol.util.MapUtilities;
import java.util.EnumMap;
import java.util.Map;
import org.springframework.util.Assert;

public class PictureInPictureSourceCodec
extends Codec<PictureInPictureSource> {
    private static final byte SOURCE_TYPE_INPUT_SOURCE_BYTE = -3;
    private static final byte SOURCE_TYPE_SMART_CARD_BYTE = -2;
    private static final Map<PictureInPictureSource.SourceType, Byte> domainPictureInPictureSourceSourceTypes = new EnumMap<PictureInPictureSource.SourceType, Byte>(PictureInPictureSource.SourceType.class);
    private static final Map<Byte, PictureInPictureSource.SourceType> protocolPictureInPictureSourceSourceTypes;
    static final byte SOURCE_TYPE_VIDEO_BYTE = 1;
    static final byte SOURCE_TYPE_S_VIDEO_BYTE = 2;
    static final byte SOURCE_TYPE_COMPONENT_BYTE = 3;
    static final byte SOURCE_TYPE_CVI_2_BYTE = 4;
    static final byte SOURCE_TYPE_VGA_BYTE = 5;
    static final byte SOURCE_TYPE_HDMI_2_BYTE = 6;
    static final byte SOURCE_TYPE_DISPLAY_PORT_2_BYTE = 7;
    static final byte SOURCE_TYPE_USB_2_BYTE = 8;
    static final byte SOURCE_TYPE_CARD_DVI_D_BYTE = 9;
    static final byte SOURCE_TYPE_DISPLAY_PORT_1_BYTE = 10;
    static final byte SOURCE_TYPE_CARD_OPS_BYTE = 11;
    static final byte SOURCE_TYPE_USB_1_BYTE = 12;
    static final byte SOURCE_TYPE_HDMI_1_BYTE = 13;
    static final byte SOURCE_TYPE_DVI_D_BYTE = 14;
    static final byte SOURCE_TYPE_HDMI_3_BYTE = 15;
    static final byte SOURCE_TYPE_BROWSER_BYTE = 16;
    static final byte SOURCE_TYPE_SMART_CMS_BYTE = 17;
    static final byte SOURCE_TYPE_DIGITAL_MEDIA_SERVER_BYTE = 18;
    static final byte SOURCE_TYPE_INTERNAL_STORAGE_BYTE = 19;
    static final byte SOURCE_TYPE_RESERVED_1_BYTE = 20;
    static final byte SOURCE_TYPE_RESERVED_2_BYTE = 21;
    static final byte SOURCE_TYPE_MEDIA_PLAYER_BYTE = 22;
    static final byte SOURCE_TYPE_PDF_PLAYER_BYTE = 23;
    static final byte SOURCE_TYPE_CUSTOM_BYTE = 24;
    static final byte SOURCE_TYPE_HDMI_4_BYTE = 25;
    static final byte SOURCE_TYPE_VGA_2_BYTE = 26;
    static final byte SOURCE_TYPE_VGA_3_BYTE = 27;
    static final byte SOURCE_TYPE_IWB_BYTE = 28;
    static final byte SOURCE_TYPE_CMND_PLAY_WEB_BYTE = 29;
    static final byte SOURCE_TYPE_USB_TYPEC_BYTE = 30;
    static final byte SOURCE_TYPE_KIOSK_BYTE = 31;
    static final byte SOURCE_TYPE_SMART_INFO_BYTE = 32;
    static final byte SOURCE_TYPE_TUNER_BYTE = 33;
    static final byte SOURCE_TYPE_GOOGLE_CAST_BYTE = 34;
    private static final Map<PictureInPictureSource.InputSourceSourceType, Byte> domainInputSourceSourceTypes;
    private static final Map<Byte, PictureInPictureSource.InputSourceSourceType> protocolInputSourceSourceTypes;
    private static final byte pictureInPictureSourceSourceTypeInputSource;
    private static PictureInPictureSourceCodec pictureInPictureSourceCodec;

    private PictureInPictureSourceCodec() {
        super(PictureInPictureSource.class);
    }

    public static Map<PictureInPictureSource.SourceType, Byte> getDomainPictureInPictureSourceSourceTypes() {
        return domainPictureInPictureSourceSourceTypes;
    }

    public static Map<Byte, PictureInPictureSource.SourceType> getProtocolPictureInPictureSourceSourceTypes() {
        return protocolPictureInPictureSourceSourceTypes;
    }

    public static Map<PictureInPictureSource.InputSourceSourceType, Byte> getDomainInputSourceSourceTypes() {
        return domainInputSourceSourceTypes;
    }

    public static Map<Byte, PictureInPictureSource.InputSourceSourceType> getProtocolInputSourceSourceTypes() {
        return protocolInputSourceSourceTypes;
    }

    public static synchronized PictureInPictureSourceCodec getInstance() {
        if (pictureInPictureSourceCodec == null) {
            pictureInPictureSourceCodec = new PictureInPictureSourceCodec();
        }
        return pictureInPictureSourceCodec;
    }

    @Override
    public byte[] toProtocol(PictureInPictureSource pictureInPictureSource) {
        Assert.notNull((Object)pictureInPictureSource, ToProtocolMessages.PICTURE_IN_PICTURE_SOURCE_CAN_NOT_BE_NULL);
        PictureInPictureSource.SourceType domainPictureInPictureSourceSourceType = pictureInPictureSource.getPictureInPictureSourceSourceType();
        Assert.state(domainPictureInPictureSourceSourceType != null, ToProtocolMessages.DOMAIN_PICTURE_IN_PICTURE_SOURCE_SOURCE_TYPE_CAN_NOT_BE_NULL);
        Byte protocolPictureInPictureSourceSourceType = domainPictureInPictureSourceSourceTypes.get((Object)domainPictureInPictureSourceSourceType);
        Assert.state(protocolPictureInPictureSourceSourceType != null, ToProtocolMessages.PROTOCOL_PICTURE_IN_PICTURE_SOURCE_SOURCE_TYPE_CAN_NOT_BE_NULL);
        PictureInPictureSource.InputSourceSourceType domainInputSourceSourceTypeQ2 = pictureInPictureSource.getInputSourceSourceTypeQ2();
        Byte protocolInputSourceQ2Byte = domainInputSourceSourceTypeQ2 != null ? domainInputSourceSourceTypes.get((Object)domainInputSourceSourceTypeQ2) : Byte.valueOf((byte)0);
        PictureInPictureSource.InputSourceSourceType domainInputSourceSourceTypeQ3 = pictureInPictureSource.getInputSourceSourceTypeQ3();
        Byte protocolInputSourceQ3Byte = domainInputSourceSourceTypeQ3 != null ? domainInputSourceSourceTypes.get((Object)domainInputSourceSourceTypeQ3) : Byte.valueOf((byte)0);
        PictureInPictureSource.InputSourceSourceType domainInputSourceSourceTypeQ4 = pictureInPictureSource.getInputSourceSourceTypeQ4();
        Byte protocolInputSourceQ4Byte = domainInputSourceSourceTypeQ4 != null ? domainInputSourceSourceTypes.get((Object)domainInputSourceSourceTypeQ4) : Byte.valueOf((byte)0);
        return new byte[]{protocolPictureInPictureSourceSourceType, protocolInputSourceQ2Byte, protocolInputSourceQ3Byte, protocolInputSourceQ4Byte};
    }

    @Override
    public PictureInPictureSource toDomain(byte[] bytes) {
        if (bytes == null || bytes.length < 2) {
            return null;
        }
        byte protocolPictureInPictureSourceSourceType = bytes[0];
        PictureInPictureSource.SourceType domainPictureInPictureSourceSourceType = protocolPictureInPictureSourceSourceTypes.get(protocolPictureInPictureSourceSourceType);
        Assert.state(domainPictureInPictureSourceSourceType != null, ToDomainMessages.DOMAIN_PICTURE_IN_PICTURE_SOURCE_SOURCE_TYPE_CAN_NOT_BE_NULL);
        Byte protocolInputSourceSourceTypeQ2 = protocolPictureInPictureSourceSourceType == pictureInPictureSourceSourceTypeInputSource ? Byte.valueOf(bytes[1]) : null;
        PictureInPictureSource.InputSourceSourceType domainInputSourceSourceQ2Type = protocolInputSourceSourceTypeQ2 != null ? protocolInputSourceSourceTypes.get(protocolInputSourceSourceTypeQ2) : null;
        Byte protocolInputSourceSourceTypeQ3 = bytes.length >= 3 && protocolPictureInPictureSourceSourceType == pictureInPictureSourceSourceTypeInputSource ? Byte.valueOf(bytes[2]) : null;
        PictureInPictureSource.InputSourceSourceType domainInputSourceSourcQ3Type = protocolInputSourceSourceTypeQ3 != null ? protocolInputSourceSourceTypes.get(protocolInputSourceSourceTypeQ3) : null;
        Byte protocolInputSourceSourceTypeQ4 = bytes.length >= 4 && protocolPictureInPictureSourceSourceType == pictureInPictureSourceSourceTypeInputSource ? Byte.valueOf(bytes[3]) : null;
        PictureInPictureSource.InputSourceSourceType domainInputSourceSourceQ4Type = protocolInputSourceSourceTypeQ4 != null ? protocolInputSourceSourceTypes.get(protocolInputSourceSourceTypeQ4) : null;
        return new PictureInPictureSource(domainPictureInPictureSourceSourceType, domainInputSourceSourceQ2Type, domainInputSourceSourcQ3Type, domainInputSourceSourceQ4Type);
    }

    static {
        domainPictureInPictureSourceSourceTypes.put(PictureInPictureSource.SourceType.INPUT_SOURCE, (byte)-3);
        domainPictureInPictureSourceSourceTypes.put(PictureInPictureSource.SourceType.SMART_CARD, (byte)-2);
        protocolPictureInPictureSourceSourceTypes = MapUtilities.inverse(domainPictureInPictureSourceSourceTypes);
        domainInputSourceSourceTypes = new EnumMap<PictureInPictureSource.InputSourceSourceType, Byte>(PictureInPictureSource.InputSourceSourceType.class);
        domainInputSourceSourceTypes.put(PictureInPictureSource.InputSourceSourceType.VIDEO, (byte)1);
        domainInputSourceSourceTypes.put(PictureInPictureSource.InputSourceSourceType.S_VIDEO, (byte)2);
        domainInputSourceSourceTypes.put(PictureInPictureSource.InputSourceSourceType.COMPONENT, (byte)3);
        domainInputSourceSourceTypes.put(PictureInPictureSource.InputSourceSourceType.CVI_2, (byte)4);
        domainInputSourceSourceTypes.put(PictureInPictureSource.InputSourceSourceType.VGA, (byte)5);
        domainInputSourceSourceTypes.put(PictureInPictureSource.InputSourceSourceType.HDMI_2, (byte)6);
        domainInputSourceSourceTypes.put(PictureInPictureSource.InputSourceSourceType.DISPLAY_PORT_2, (byte)7);
        domainInputSourceSourceTypes.put(PictureInPictureSource.InputSourceSourceType.USB_2, (byte)8);
        domainInputSourceSourceTypes.put(PictureInPictureSource.InputSourceSourceType.CARD_DVI_D, (byte)9);
        domainInputSourceSourceTypes.put(PictureInPictureSource.InputSourceSourceType.DISPLAY_PORT_1, (byte)10);
        domainInputSourceSourceTypes.put(PictureInPictureSource.InputSourceSourceType.CARD_OPS, (byte)11);
        domainInputSourceSourceTypes.put(PictureInPictureSource.InputSourceSourceType.USB_1, (byte)12);
        domainInputSourceSourceTypes.put(PictureInPictureSource.InputSourceSourceType.HDMI_1, (byte)13);
        domainInputSourceSourceTypes.put(PictureInPictureSource.InputSourceSourceType.DVI_D, (byte)14);
        domainInputSourceSourceTypes.put(PictureInPictureSource.InputSourceSourceType.HDMI_3, (byte)15);
        domainInputSourceSourceTypes.put(PictureInPictureSource.InputSourceSourceType.BROWSER, (byte)16);
        domainInputSourceSourceTypes.put(PictureInPictureSource.InputSourceSourceType.SMART_CMS, (byte)17);
        domainInputSourceSourceTypes.put(PictureInPictureSource.InputSourceSourceType.DIGITAL_MEDIA_SERVER, (byte)18);
        domainInputSourceSourceTypes.put(PictureInPictureSource.InputSourceSourceType.INTERNAL_STORAGE, (byte)19);
        domainInputSourceSourceTypes.put(PictureInPictureSource.InputSourceSourceType.RESERVED_1, (byte)20);
        domainInputSourceSourceTypes.put(PictureInPictureSource.InputSourceSourceType.RESERVED_2, (byte)21);
        domainInputSourceSourceTypes.put(PictureInPictureSource.InputSourceSourceType.MEDIA_PLAYER, (byte)22);
        domainInputSourceSourceTypes.put(PictureInPictureSource.InputSourceSourceType.PDF_PLAYER, (byte)23);
        domainInputSourceSourceTypes.put(PictureInPictureSource.InputSourceSourceType.CUSTOM, (byte)24);
        domainInputSourceSourceTypes.put(PictureInPictureSource.InputSourceSourceType.HDMI_4, (byte)25);
        domainInputSourceSourceTypes.put(PictureInPictureSource.InputSourceSourceType.VGA_2, (byte)26);
        domainInputSourceSourceTypes.put(PictureInPictureSource.InputSourceSourceType.VGA_3, (byte)27);
        domainInputSourceSourceTypes.put(PictureInPictureSource.InputSourceSourceType.IWB, (byte)28);
        domainInputSourceSourceTypes.put(PictureInPictureSource.InputSourceSourceType.CMND_PLAY_WEB, (byte)29);
        domainInputSourceSourceTypes.put(PictureInPictureSource.InputSourceSourceType.USB_TYPEC, (byte)30);
        domainInputSourceSourceTypes.put(PictureInPictureSource.InputSourceSourceType.KIOSK, (byte)31);
        domainInputSourceSourceTypes.put(PictureInPictureSource.InputSourceSourceType.SMART_INFO, (byte)32);
        domainInputSourceSourceTypes.put(PictureInPictureSource.InputSourceSourceType.TUNER, (byte)33);
        domainInputSourceSourceTypes.put(PictureInPictureSource.InputSourceSourceType.GOOGLE_CAST, (byte)34);
        protocolInputSourceSourceTypes = MapUtilities.inverse(domainInputSourceSourceTypes);
        Map<PictureInPictureSource.SourceType, Byte> sourceTypes = PictureInPictureSourceCodec.getDomainPictureInPictureSourceSourceTypes();
        Assert.state(sourceTypes != null, PictureInPictureSourceCodecMessages.SOURCE_TYPES_CAN_NOT_BE_NULL);
        pictureInPictureSourceSourceTypeInputSource = sourceTypes.get((Object)PictureInPictureSource.SourceType.INPUT_SOURCE);
    }
}

