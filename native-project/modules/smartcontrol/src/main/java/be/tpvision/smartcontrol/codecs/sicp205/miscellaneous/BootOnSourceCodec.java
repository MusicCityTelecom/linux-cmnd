package be.tpvision.smartcontrol.codecs.sicp205.miscellaneous;

import be.tpvision.smartcontrol.codecs.sicp.Codec;
import be.tpvision.smartcontrol.domain.device_settings.general.BootOnSource;
import be.tpvision.smartcontrol.messages.codecs.sicp205.miscellaneous.boot_on_source.ToDomainMessages;
import be.tpvision.smartcontrol.messages.codecs.sicp205.miscellaneous.boot_on_source.ToProtocolMessages;
import be.tpvision.smartcontrol.util.MapUtilities;
import java.util.EnumMap;
import java.util.Map;
import org.springframework.util.Assert;

public class BootOnSourceCodec extends Codec<BootOnSource> {
   static final byte SOURCE_TYPE_LAST_INPUT = 0;
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
   static final byte SOURCE_TYPE_HOME_LAUNCHER_BYTE = 30;
   static final byte SOURCE_TYPE_USB_TYPEC_BYTE = 31;
   static final byte SOURCE_TYPE_KIOSK_BYTE = 32;
   static final byte SOURCE_TYPE_SMART_INFO_BYTE = 33;
   static final byte SOURCE_TYPE_TUNER_BYTE = 34;
   static final byte SOURCE_TYPE_GOOGLE_CAST_BYTE = 35;
   private static final Map<BootOnSource.VideoSourceType, Byte> domainVideoSourceTypes = new EnumMap<>(BootOnSource.VideoSourceType.class);
   private static final Map<Byte, BootOnSource.VideoSourceType> protocolVideoSourceTypes = MapUtilities.inverse(domainVideoSourceTypes);
   static final byte TAG_TAG_ZERO_BYTE = 0;
   static final byte TAG_TAG_ONE_BYTE = 1;
   static final byte TAG_TAG_TWO_BYTE = 2;
   static final byte TAG_TAG_THREE_BYTE = 3;
   static final byte TAG_TAG_FOUR_BYTE = 4;
   static final byte TAG_TAG_FIVE_BYTE = 5;
   static final byte TAG_TAG_SIX_BYTE = 6;
   static final byte TAG_TAG_SEVEN_BYTE = 7;
   static final byte TAG_USB_AUTOPLAY_BYTE = 8;
   private static final Map<BootOnSource.Tag, Byte> domainTags = new EnumMap<>(BootOnSource.Tag.class);
   private static final Map<Byte, BootOnSource.Tag> protocolTags = MapUtilities.inverse(domainTags);
   private static BootOnSourceCodec bootOnSourceCodec;

   private BootOnSourceCodec() {
      super(BootOnSource.class);
   }

   public static Map<BootOnSource.VideoSourceType, Byte> getDomainvideosourcetypes() {
      return domainVideoSourceTypes;
   }

   public static Map<Byte, BootOnSource.VideoSourceType> getProtocolvideosourcetypes() {
      return protocolVideoSourceTypes;
   }

   public static Map<BootOnSource.Tag, Byte> getDomaintags() {
      return domainTags;
   }

   public static Map<Byte, BootOnSource.Tag> getProtocoltags() {
      return protocolTags;
   }

   public static synchronized BootOnSourceCodec getInstance() {
      if (bootOnSourceCodec == null) {
         bootOnSourceCodec = new BootOnSourceCodec();
      }

      return bootOnSourceCodec;
   }

   public byte[] toProtocol(final BootOnSource bootOnSource) {
      Assert.notNull(bootOnSource, ToProtocolMessages.BOOT_ON_SOURCE_CAN_NOT_BE_NULL_MESSAGE);
      BootOnSource.VideoSourceType videoSourceType = bootOnSource.getVideoSourceType();
      Assert.state(videoSourceType != null, ToProtocolMessages.VIDEO_SOURCE_TYPE_CAN_NOT_BE_NULL_MESSAGE);
      Byte protocolVideoSourceType = domainVideoSourceTypes.get(videoSourceType);
      Assert.state(protocolVideoSourceType != null, ToProtocolMessages.PROTOCOL_VIDEO_SOURCE_TYPE_CAN_NOT_BE_NULL_MESSAGE);
      BootOnSource.Tag domainTag = bootOnSource.getTag();
      Byte protocolTag;
      if (domainTag != null) {
         protocolTag = domainTags.get(domainTag);
      } else {
         protocolTag = domainTags.get(BootOnSource.Tag.TAG_ZERO);
      }

      Assert.state(protocolTag != null, ToProtocolMessages.PROTOCOL_TAG_CAN_NOT_BE_NULL_MESSAGE);
      return new byte[]{protocolVideoSourceType, protocolTag};
   }

   public BootOnSource toDomain(final byte[] bytes) {
      if (bytes != null && bytes.length >= 2) {
         Byte protocolVideoSourceType = bytes[0];
         Assert.state(protocolVideoSourceType != null, ToDomainMessages.PROTOCOL_VIDEO_SOURCE_TYPE_CAN_NOT_BE_NULL_MESSAGE);
         BootOnSource.VideoSourceType domainVideoSourceType = protocolVideoSourceTypes.get(protocolVideoSourceType);
         Assert.state(domainVideoSourceType != null, ToDomainMessages.DOMAIN_VIDEO_SOURCE_TYPE_CAN_NOT_BE_NULL_MESSAGE);
         Byte protocolTag = bytes[1];
         Assert.state(protocolTag != null, ToDomainMessages.PROTOCOL_TAG_CAN_NOT_BE_NULL_MESSAGE);
         BootOnSource.Tag domainTag = protocolTags.get(protocolTag);
         return new BootOnSource(domainVideoSourceType, domainTag);
      } else {
         return null;
      }
   }

   static {
      domainVideoSourceTypes.put(BootOnSource.VideoSourceType.LAST_INPUT, (byte)0);
      domainVideoSourceTypes.put(BootOnSource.VideoSourceType.VIDEO, (byte)1);
      domainVideoSourceTypes.put(BootOnSource.VideoSourceType.S_VIDEO, (byte)2);
      domainVideoSourceTypes.put(BootOnSource.VideoSourceType.COMPONENT, (byte)3);
      domainVideoSourceTypes.put(BootOnSource.VideoSourceType.CVI_2, (byte)4);
      domainVideoSourceTypes.put(BootOnSource.VideoSourceType.VGA, (byte)5);
      domainVideoSourceTypes.put(BootOnSource.VideoSourceType.HDMI_2, (byte)6);
      domainVideoSourceTypes.put(BootOnSource.VideoSourceType.DISPLAY_PORT_2, (byte)7);
      domainVideoSourceTypes.put(BootOnSource.VideoSourceType.USB_2, (byte)8);
      domainVideoSourceTypes.put(BootOnSource.VideoSourceType.CARD_DVI_D, (byte)9);
      domainVideoSourceTypes.put(BootOnSource.VideoSourceType.DISPLAY_PORT_1, (byte)10);
      domainVideoSourceTypes.put(BootOnSource.VideoSourceType.CARD_OPS, (byte)11);
      domainVideoSourceTypes.put(BootOnSource.VideoSourceType.USB_1, (byte)12);
      domainVideoSourceTypes.put(BootOnSource.VideoSourceType.HDMI_1, (byte)13);
      domainVideoSourceTypes.put(BootOnSource.VideoSourceType.DVI_D, (byte)14);
      domainVideoSourceTypes.put(BootOnSource.VideoSourceType.HDMI_3, (byte)15);
      domainVideoSourceTypes.put(BootOnSource.VideoSourceType.BROWSER, (byte)16);
      domainVideoSourceTypes.put(BootOnSource.VideoSourceType.SMART_CMS, (byte)17);
      domainVideoSourceTypes.put(BootOnSource.VideoSourceType.DIGITAL_MEDIA_SERVER, (byte)18);
      domainVideoSourceTypes.put(BootOnSource.VideoSourceType.INTERNAL_STORAGE, (byte)19);
      domainVideoSourceTypes.put(BootOnSource.VideoSourceType.RESERVED_1, (byte)20);
      domainVideoSourceTypes.put(BootOnSource.VideoSourceType.RESERVED_2, (byte)21);
      domainVideoSourceTypes.put(BootOnSource.VideoSourceType.MEDIA_PLAYER, (byte)22);
      domainVideoSourceTypes.put(BootOnSource.VideoSourceType.PDF_PLAYER, (byte)23);
      domainVideoSourceTypes.put(BootOnSource.VideoSourceType.CUSTOM, (byte)24);
      domainVideoSourceTypes.put(BootOnSource.VideoSourceType.HDMI_4, (byte)25);
      domainVideoSourceTypes.put(BootOnSource.VideoSourceType.VGA_2, (byte)26);
      domainVideoSourceTypes.put(BootOnSource.VideoSourceType.VGA_3, (byte)27);
      domainVideoSourceTypes.put(BootOnSource.VideoSourceType.IWB, (byte)28);
      domainVideoSourceTypes.put(BootOnSource.VideoSourceType.CMND_PLAY_WEB, (byte)29);
      domainVideoSourceTypes.put(BootOnSource.VideoSourceType.HOME_LAUNCHER, (byte)30);
      domainVideoSourceTypes.put(BootOnSource.VideoSourceType.USB_TYPEC, (byte)31);
      domainVideoSourceTypes.put(BootOnSource.VideoSourceType.KIOSK, (byte)32);
      domainVideoSourceTypes.put(BootOnSource.VideoSourceType.SMART_INFO, (byte)33);
      domainVideoSourceTypes.put(BootOnSource.VideoSourceType.TUNER, (byte)34);
      domainVideoSourceTypes.put(BootOnSource.VideoSourceType.GOOGLE_CAST, (byte)35);
      domainTags.put(BootOnSource.Tag.TAG_ZERO, (byte)0);
      domainTags.put(BootOnSource.Tag.TAG_ONE, (byte)1);
      domainTags.put(BootOnSource.Tag.TAG_TWO, (byte)2);
      domainTags.put(BootOnSource.Tag.TAG_THREE, (byte)3);
      domainTags.put(BootOnSource.Tag.TAG_FOUR, (byte)4);
      domainTags.put(BootOnSource.Tag.TAG_FIVE, (byte)5);
      domainTags.put(BootOnSource.Tag.TAG_SIX, (byte)6);
      domainTags.put(BootOnSource.Tag.TAG_SEVEN, (byte)7);
      domainTags.put(BootOnSource.Tag.USB_AUTOPLAY, (byte)8);
   }
}
