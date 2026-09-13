package be.tpvision.smartcontrol.codecs.sicp201.input_sources;

import be.tpvision.smartcontrol.codecs.sicp.Codec;
import be.tpvision.smartcontrol.domain.device_settings.input_sources.InputSource;
import be.tpvision.smartcontrol.messages.codecs.sicp201.input_sources.input_source.ToDomainMessages;
import be.tpvision.smartcontrol.messages.codecs.sicp201.input_sources.input_source.ToProtocolMessages;
import be.tpvision.smartcontrol.util.MapUtilities;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.Assert;

public class InputSourceCodec extends Codec<InputSource> {
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
   private static final Map<InputSource.SourceType, Byte> domainSourceTypes = new EnumMap<>(InputSource.SourceType.class);
   private static final Map<Byte, InputSource.SourceType> protocolSourceTypes = MapUtilities.inverse(domainSourceTypes);
   static final byte TAG_TAG_ONE_BYTE = 1;
   static final byte TAG_TAG_TWO_BYTE = 2;
   static final byte TAG_TAG_THREE_BYTE = 3;
   static final byte TAG_TAG_FOUR_BYTE = 4;
   static final byte TAG_TAG_FIVE_BYTE = 5;
   static final byte TAG_TAG_SIX_BYTE = 6;
   static final byte TAG_TAG_SEVEN_BYTE = 7;
   static final byte TAG_USB_AUTOPLAY_BYTE = 8;
   private static final Map<InputSource.Tag, Byte> domainTags = new EnumMap<>(InputSource.Tag.class);
   private static final Map<Byte, InputSource.Tag> protocolTags = MapUtilities.inverse(domainTags);
   static final byte SOURCE_LABEL_OFF_BYTE = 0;
   static final byte SOURCE_LABEL_ON_BYTE = 1;
   private static final Map<InputSource.SourceLabel, Byte> domainSourceLabels = new EnumMap<>(InputSource.SourceLabel.class);
   private static final Map<Byte, InputSource.SourceLabel> protocolSourceLabels = MapUtilities.inverse(domainSourceLabels);
   static final byte MUTE_STYLE_RESERVED_BYTE = 0;
   private static InputSourceCodec inputSourceCodec;

   private InputSourceCodec() {
      super(InputSource.class);
   }

   public static Map<InputSource.SourceType, Byte> getDomainSourceTypes() {
      return domainSourceTypes;
   }

   public static Map<Byte, InputSource.SourceType> getProtocolSourceTypes() {
      return protocolSourceTypes;
   }

   public static Map<InputSource.Tag, Byte> getDomainTags() {
      return domainTags;
   }

   public static Map<Byte, InputSource.Tag> getProtocolTags() {
      return protocolTags;
   }

   public static Map<InputSource.SourceLabel, Byte> getDomainSourceLabels() {
      return domainSourceLabels;
   }

   public static Map<Byte, InputSource.SourceLabel> getProtocolSourceLabels() {
      return protocolSourceLabels;
   }

   public static synchronized InputSourceCodec getInstance() {
      if (inputSourceCodec == null) {
         inputSourceCodec = new InputSourceCodec();
      }

      return inputSourceCodec;
   }

   public byte[] toProtocol(final InputSource inputSource) {
      Assert.notNull(inputSource, ToProtocolMessages.INPUT_SOURCE_CAN_NOT_BE_NULL_MESSAGE);
      InputSource.SourceType domainSourceType = inputSource.getSourceType();
      Assert.state(domainSourceType != null, ToProtocolMessages.DOMAIN_SOURCE_TYPE_CAN_NOT_BE_NULL_MESSAGE);
      Byte protocolSourceType = domainSourceTypes.get(domainSourceType);
      Assert.state(protocolSourceType != null, ToProtocolMessages.PROTOCOL_SOURCE_TYPE_CAN_NOT_BE_NULL_MESSAGE);
      Byte protocolTag;
      if (this.isTagSourceType(domainSourceType)) {
         InputSource.Tag domainTag = inputSource.getTag();
         if (domainTag != null) {
            protocolTag = domainTags.get(domainTag);
         } else {
            protocolTag = domainTags.get(InputSource.Tag.TAG_ONE);
         }

         Assert.state(protocolTag != null, ToProtocolMessages.PROTOCOL_TAG_CAN_NOT_BE_NULL_MESSAGE);
      } else {
         protocolTag = (byte)0;
      }

      InputSource.SourceLabel domainSourceLabel = inputSource.getSourceLabel();
      Assert.state(domainSourceLabel != null, ToProtocolMessages.DOMAIN_SOURCE_LABEL_CAN_NOT_BE_NULL_MESSAGE);
      Byte protocolSourceLabel = domainSourceLabels.get(domainSourceLabel);
      Assert.state(protocolSourceLabel != null, ToProtocolMessages.PROTOCOL_SOURCE_LABEL_CAN_NOT_BE_NULL_MESSAGE);
      return new byte[]{protocolSourceType, protocolTag, protocolSourceLabel, 0};
   }

   private boolean isTagSourceType(InputSource.SourceType sourceType) {
      List<InputSource.SourceType> tagSourceTypesList = Arrays.asList(
         InputSource.SourceType.BROWSER, InputSource.SourceType.MEDIA_PLAYER, InputSource.SourceType.PDF_PLAYER
      );
      return tagSourceTypesList.contains(sourceType);
   }

   public InputSource toDomain(final byte[] bytes) {
      if (bytes != null && bytes.length >= 4) {
         Byte protocolSourceType = bytes[0];
         Assert.state(protocolSourceType != null, ToDomainMessages.PROTOCOL_SOURCE_TYPE_CAN_NOT_BE_NULL_MESSAGE);
         InputSource.SourceType domainSourceType = protocolSourceTypes.get(protocolSourceType);
         Assert.state(protocolSourceType != null, ToDomainMessages.DOMAIN_SOURCE_TYPE_CAN_NOT_BE_NULL_MESSAGE);
         Byte protocolTag = bytes[1];
         Assert.state(protocolTag != null, ToDomainMessages.PROTOCOL_TAG_CAN_NOT_BE_NULL_MESSAGE);
         InputSource.Tag domainTag = protocolTags.get(protocolTag);
         Byte protocolSourceLabel = bytes[2];
         Assert.state(protocolSourceType != null, ToDomainMessages.PROTOCOL_SOURCE_LABEL_CAN_NOT_BE_NULL_MESSAGE);
         InputSource.SourceLabel domainSourceLabel = protocolSourceLabels.get(protocolSourceLabel);
         Assert.state(protocolSourceType != null, ToDomainMessages.DOMAIN_SOURCE_LABEL_CAN_NOT_BE_NULL_MESSAGE);
         return new InputSource(domainSourceType, domainTag, domainSourceLabel);
      } else {
         return null;
      }
   }

   static {
      domainSourceTypes.put(InputSource.SourceType.VIDEO, (byte)1);
      domainSourceTypes.put(InputSource.SourceType.S_VIDEO, (byte)2);
      domainSourceTypes.put(InputSource.SourceType.COMPONENT, (byte)3);
      domainSourceTypes.put(InputSource.SourceType.CVI_2, (byte)4);
      domainSourceTypes.put(InputSource.SourceType.VGA, (byte)5);
      domainSourceTypes.put(InputSource.SourceType.HDMI_2, (byte)6);
      domainSourceTypes.put(InputSource.SourceType.DISPLAY_PORT_2, (byte)7);
      domainSourceTypes.put(InputSource.SourceType.USB_2, (byte)8);
      domainSourceTypes.put(InputSource.SourceType.CARD_DVI_D, (byte)9);
      domainSourceTypes.put(InputSource.SourceType.DISPLAY_PORT_1, (byte)10);
      domainSourceTypes.put(InputSource.SourceType.CARD_OPS, (byte)11);
      domainSourceTypes.put(InputSource.SourceType.USB_1, (byte)12);
      domainSourceTypes.put(InputSource.SourceType.HDMI_1, (byte)13);
      domainSourceTypes.put(InputSource.SourceType.DVI_D, (byte)14);
      domainSourceTypes.put(InputSource.SourceType.HDMI_3, (byte)15);
      domainSourceTypes.put(InputSource.SourceType.BROWSER, (byte)16);
      domainSourceTypes.put(InputSource.SourceType.SMART_CMS, (byte)17);
      domainSourceTypes.put(InputSource.SourceType.DIGITAL_MEDIA_SERVER, (byte)18);
      domainSourceTypes.put(InputSource.SourceType.INTERNAL_STORAGE, (byte)19);
      domainSourceTypes.put(InputSource.SourceType.RESERVED_1, (byte)20);
      domainSourceTypes.put(InputSource.SourceType.RESERVED_2, (byte)21);
      domainSourceTypes.put(InputSource.SourceType.MEDIA_PLAYER, (byte)22);
      domainSourceTypes.put(InputSource.SourceType.PDF_PLAYER, (byte)23);
      domainSourceTypes.put(InputSource.SourceType.CUSTOM, (byte)24);
      domainSourceTypes.put(InputSource.SourceType.HDMI_4, (byte)25);
      domainSourceTypes.put(InputSource.SourceType.VGA_2, (byte)26);
      domainSourceTypes.put(InputSource.SourceType.VGA_3, (byte)27);
      domainSourceTypes.put(InputSource.SourceType.IWB, (byte)28);
      domainSourceTypes.put(InputSource.SourceType.CMND_PLAY_WEB, (byte)29);
      domainSourceTypes.put(InputSource.SourceType.HOME_LAUNCHER, (byte)30);
      domainSourceTypes.put(InputSource.SourceType.USB_TYPEC, (byte)31);
      domainSourceTypes.put(InputSource.SourceType.KIOSK, (byte)32);
      domainSourceTypes.put(InputSource.SourceType.SMART_INFO, (byte)33);
      domainSourceTypes.put(InputSource.SourceType.TUNER, (byte)34);
      domainSourceTypes.put(InputSource.SourceType.GOOGLE_CAST, (byte)35);
      domainTags.put(InputSource.Tag.TAG_ONE, (byte)1);
      domainTags.put(InputSource.Tag.TAG_TWO, (byte)2);
      domainTags.put(InputSource.Tag.TAG_THREE, (byte)3);
      domainTags.put(InputSource.Tag.TAG_FOUR, (byte)4);
      domainTags.put(InputSource.Tag.TAG_FIVE, (byte)5);
      domainTags.put(InputSource.Tag.TAG_SIX, (byte)6);
      domainTags.put(InputSource.Tag.TAG_SEVEN, (byte)7);
      domainTags.put(InputSource.Tag.USB_AUTOPLAY, (byte)8);
      domainSourceLabels.put(InputSource.SourceLabel.OFF, (byte)0);
      domainSourceLabels.put(InputSource.SourceLabel.ON, (byte)1);
   }
}
