package be.tpvision.smartcontrol.codecs.sicp202.miscellaneous;

import be.tpvision.smartcontrol.codecs.sicp.Codec;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.DisplayOrientation;
import be.tpvision.smartcontrol.messages.codecs.sicp202.miscellaneous.display_orientation.ToDomainMessages;
import be.tpvision.smartcontrol.messages.codecs.sicp202.miscellaneous.display_orientation.ToProtocolMessages;
import be.tpvision.smartcontrol.util.MapUtilities;
import java.util.EnumMap;
import java.util.Map;
import org.springframework.util.Assert;

public class DisplayOrientationCodec extends Codec<DisplayOrientation> {
   static final byte AUTO_ROTATE_OFF_BYTE = 0;
   static final byte AUTO_ROTATE_ON_BYTE = 1;
   private static final Map<DisplayOrientation.AutoRotate, Byte> domainAutoRotates = new EnumMap<>(DisplayOrientation.AutoRotate.class);
   private static final Map<Byte, DisplayOrientation.AutoRotate> protocolAutoRotates = MapUtilities.inverse(domainAutoRotates);
   static final byte OSD_ROTATION_LANDSCAPE_BYTE = 0;
   static final byte OSD_ROTATION_PORTRAIT_BYTE = 1;
   private static final Map<DisplayOrientation.OsdRotation, Byte> domainOsdRotations = new EnumMap<>(DisplayOrientation.OsdRotation.class);
   private static final Map<Byte, DisplayOrientation.OsdRotation> protocolOsdRotations = MapUtilities.inverse(domainOsdRotations);
   static final byte IMAGE_ALL_OFF_BYTE = 0;
   static final byte IMAGE_ALL_ON_BYTE = 1;
   static final byte IMAGE_ALL_ON_CLOCKWISE_BYTE = 2;
   static final byte IMAGE_ALL_ON_COUNTERCLOCKWISE_BYTE = 3;
   private static final Map<DisplayOrientation.ImageAll, Byte> domainImageAlls = new EnumMap<>(DisplayOrientation.ImageAll.class);
   private static final Map<Byte, DisplayOrientation.ImageAll> protocolImageAlls = MapUtilities.inverse(domainImageAlls);
   static final byte DISPLAY_WINDOW_1_OFF_BYTE = 0;
   static final byte DISPLAY_WINDOW_1_ON_BYTE = 1;
   private static final Map<DisplayOrientation.DisplayWindow1, Byte> domainDisplayWindow1s = new EnumMap<>(DisplayOrientation.DisplayWindow1.class);
   private static final Map<Byte, DisplayOrientation.DisplayWindow1> protocolDisplayWindow1s = MapUtilities.inverse(domainDisplayWindow1s);
   static final byte DISPLAY_WINDOW_2_OFF_BYTE = 0;
   static final byte DISPLAY_WINDOW_2_ON_BYTE = 1;
   private static final Map<DisplayOrientation.DisplayWindow2, Byte> domainDisplayWindow2s = new EnumMap<>(DisplayOrientation.DisplayWindow2.class);
   private static final Map<Byte, DisplayOrientation.DisplayWindow2> protocolDisplayWindow2s = MapUtilities.inverse(domainDisplayWindow2s);
   static final byte DISPLAY_WINDOW_3_OFF_BYTE = 0;
   static final byte DISPLAY_WINDOW_3_ON_BYTE = 1;
   private static final Map<DisplayOrientation.DisplayWindow3, Byte> domainDisplayWindow3s = new EnumMap<>(DisplayOrientation.DisplayWindow3.class);
   private static final Map<Byte, DisplayOrientation.DisplayWindow3> protocolDisplayWindow3s = MapUtilities.inverse(domainDisplayWindow3s);
   static final byte DISPLAY_WINDOW_4_OFF_BYTE = 0;
   static final byte DISPLAY_WINDOW_4_ON_BYTE = 1;
   private static final Map<DisplayOrientation.DisplayWindow4, Byte> domainDisplayWindow4s = new EnumMap<>(DisplayOrientation.DisplayWindow4.class);
   private static final Map<Byte, DisplayOrientation.DisplayWindow4> protocolDisplayWindow4s = MapUtilities.inverse(domainDisplayWindow4s);
   private static DisplayOrientationCodec displayOrientationCodec;

   public DisplayOrientationCodec() {
      super(DisplayOrientation.class);
   }

   public static Map<DisplayOrientation.AutoRotate, Byte> getDomainAutoRotates() {
      return domainAutoRotates;
   }

   public static Map<Byte, DisplayOrientation.AutoRotate> getProtocolAutoRotates() {
      return protocolAutoRotates;
   }

   public static Map<DisplayOrientation.OsdRotation, Byte> getDomainOsdRotations() {
      return domainOsdRotations;
   }

   public static Map<Byte, DisplayOrientation.OsdRotation> getProtocolOsdRotations() {
      return protocolOsdRotations;
   }

   public static Map<DisplayOrientation.ImageAll, Byte> getDomainImageAlls() {
      return domainImageAlls;
   }

   public static Map<Byte, DisplayOrientation.ImageAll> getProtocolImageAlls() {
      return protocolImageAlls;
   }

   public static Map<DisplayOrientation.DisplayWindow1, Byte> getDomainDisplayWindow1s() {
      return domainDisplayWindow1s;
   }

   public static Map<Byte, DisplayOrientation.DisplayWindow1> getProtocolDisplayWindow1s() {
      return protocolDisplayWindow1s;
   }

   public static Map<DisplayOrientation.DisplayWindow2, Byte> getDomainDisplayWindow2s() {
      return domainDisplayWindow2s;
   }

   public static Map<Byte, DisplayOrientation.DisplayWindow2> getProtocolDisplayWindow2s() {
      return protocolDisplayWindow2s;
   }

   public static Map<DisplayOrientation.DisplayWindow3, Byte> getDomainDisplayWindow3s() {
      return domainDisplayWindow3s;
   }

   public static Map<Byte, DisplayOrientation.DisplayWindow3> getProtocolDisplayWindow3s() {
      return protocolDisplayWindow3s;
   }

   public static Map<DisplayOrientation.DisplayWindow4, Byte> getDomainDisplayWindow4s() {
      return domainDisplayWindow4s;
   }

   public static Map<Byte, DisplayOrientation.DisplayWindow4> getProtocolDisplayWindow4s() {
      return protocolDisplayWindow4s;
   }

   public static synchronized DisplayOrientationCodec getInstance() {
      if (displayOrientationCodec == null) {
         displayOrientationCodec = new DisplayOrientationCodec();
      }

      return displayOrientationCodec;
   }

   public byte[] toProtocol(final DisplayOrientation displayOrientation) {
      Assert.notNull(displayOrientation, ToProtocolMessages.DISPLAY_ORIENTATION_CAN_NOT_BE_NULL);
      DisplayOrientation.AutoRotate domainAutoRotate = displayOrientation.getAutoRotate();
      Assert.state(domainAutoRotate != null, ToProtocolMessages.DOMAIN_AUTO_ROTATE_CAN_NOT_BE_NULL);
      Byte protocolAutoRotate = domainAutoRotates.get(domainAutoRotate);
      Assert.state(protocolAutoRotate != null, ToProtocolMessages.PROTOCOL_AUTO_ROTATE_CAN_NOT_BE_NULL);
      DisplayOrientation.OsdRotation domainOsdRotation = displayOrientation.getOsdRotation();
      Assert.state(domainOsdRotation != null, ToProtocolMessages.DOMAIN_OSD_ROTATION_CAN_NOT_BE_NULL);
      Byte protocolOsdRotation = domainOsdRotations.get(domainOsdRotation);
      Assert.state(protocolOsdRotation != null, ToProtocolMessages.PROTOCOL_OSD_ROTATION_CAN_NOT_BE_NULL);
      DisplayOrientation.ImageAll domainImageAll = displayOrientation.getImageAll();
      Assert.state(domainImageAll != null, ToProtocolMessages.DOMAIN_IMAGE_ALL_CAN_NOT_BE_NULL);
      Byte protocolImageAll = domainImageAlls.get(domainImageAll);
      Assert.state(protocolImageAll != null, ToProtocolMessages.PROTOCOL_IMAGE_ALL_CAN_NOT_BE_NULL);
      DisplayOrientation.DisplayWindow1 domainDisplayWindow1 = displayOrientation.getDisplayWindow1();
      Assert.state(domainDisplayWindow1 != null, ToProtocolMessages.DOMAIN_DISPLAY_WINDOW_1_CAN_NOT_BE_NULL);
      Byte protocolDisplayWindow1 = domainDisplayWindow1s.get(domainDisplayWindow1);
      Assert.state(protocolDisplayWindow1 != null, ToProtocolMessages.PROTOCOL_DISPLAY_WINDOW_1_CAN_NOT_BE_NULL);
      DisplayOrientation.DisplayWindow2 domainDisplayWindow2 = displayOrientation.getDisplayWindow2();
      Assert.state(domainDisplayWindow2 != null, ToProtocolMessages.DOMAIN_DISPLAY_WINDOW_2_CAN_NOT_BE_NULL);
      Byte protocolDisplayWindow2 = domainDisplayWindow2s.get(domainDisplayWindow2);
      Assert.state(protocolDisplayWindow2 != null, ToProtocolMessages.PROTOCOL_DISPLAY_WINDOW_2_CAN_NOT_BE_NULL);
      DisplayOrientation.DisplayWindow3 domainDisplayWindow3 = displayOrientation.getDisplayWindow3();
      Assert.state(domainDisplayWindow3 != null, ToProtocolMessages.DOMAIN_DISPLAY_WINDOW_3_CAN_NOT_BE_NULL);
      Byte protocolDisplayWindow3 = domainDisplayWindow3s.get(domainDisplayWindow3);
      Assert.state(protocolDisplayWindow3 != null, ToProtocolMessages.PROTOCOL_DISPLAY_WINDOW_3_CAN_NOT_BE_NULL);
      DisplayOrientation.DisplayWindow4 domainDisplayWindow4 = displayOrientation.getDisplayWindow4();
      Assert.state(domainDisplayWindow4 != null, ToProtocolMessages.DOMAIN_DISPLAY_WINDOW_4_CAN_NOT_BE_NULL);
      Byte protocolDisplayWindow4 = domainDisplayWindow4s.get(domainDisplayWindow4);
      Assert.state(protocolDisplayWindow4 != null, ToProtocolMessages.PROTOCOL_DISPLAY_WINDOW_4_CAN_NOT_BE_NULL);
      return new byte[]{
         protocolAutoRotate,
         protocolOsdRotation,
         protocolImageAll,
         protocolDisplayWindow1,
         protocolDisplayWindow2,
         protocolDisplayWindow3,
         protocolDisplayWindow4
      };
   }

   public DisplayOrientation toDomain(final byte[] bytes) {
      if (bytes != null && bytes.length >= 7) {
         byte protocolAutoRotate = bytes[0];
         DisplayOrientation.AutoRotate domainAutoRotate = protocolAutoRotates.get(protocolAutoRotate);
         Assert.state(domainAutoRotate != null, ToDomainMessages.DOMAIN_AUTO_ROTATE_CAN_NOT_BE_NULL);
         byte protocolOsdRotation = bytes[1];
         DisplayOrientation.OsdRotation domainOsdRotation = protocolOsdRotations.get(protocolOsdRotation);
         Assert.state(domainOsdRotation != null, ToDomainMessages.DOMAIN_OSD_ROTATION_CAN_NOT_BE_NULL);
         byte protocolImageAll = bytes[2];
         DisplayOrientation.ImageAll domainImageAll = protocolImageAlls.get(protocolImageAll);
         Assert.state(domainImageAll != null, ToDomainMessages.DOMAIN_IMAGE_ALL_CAN_NOT_BE_NULL);
         byte protocolDisplayWindow1 = bytes[3];
         DisplayOrientation.DisplayWindow1 domainDisplayWindow1 = protocolDisplayWindow1s.get(protocolDisplayWindow1);
         Assert.state(domainDisplayWindow1 != null, ToDomainMessages.DOMAIN_DISPLAY_WINDOW_1_CAN_NOT_BE_NULL);
         byte protocolDisplayWindow2 = bytes[4];
         DisplayOrientation.DisplayWindow2 domainDisplayWindow2 = protocolDisplayWindow2s.get(protocolDisplayWindow2);
         Assert.state(domainDisplayWindow2 != null, ToDomainMessages.DOMAIN_DISPLAY_WINDOW_2_CAN_NOT_BE_NULL);
         byte protocolDisplayWindow3 = bytes[5];
         DisplayOrientation.DisplayWindow3 domainDisplayWindow3 = protocolDisplayWindow3s.get(protocolDisplayWindow3);
         Assert.state(domainDisplayWindow3 != null, ToDomainMessages.DOMAIN_DISPLAY_WINDOW_3_CAN_NOT_BE_NULL);
         byte protocolDisplayWindow4 = bytes[6];
         DisplayOrientation.DisplayWindow4 domainDisplayWindow4 = protocolDisplayWindow4s.get(protocolDisplayWindow4);
         Assert.state(domainDisplayWindow4 != null, ToDomainMessages.DOMAIN_DISPLAY_WINDOW_4_CAN_NOT_BE_NULL);
         return new DisplayOrientation(
            domainAutoRotate, domainOsdRotation, domainImageAll, domainDisplayWindow1, domainDisplayWindow2, domainDisplayWindow3, domainDisplayWindow4
         );
      } else {
         return null;
      }
   }

   static {
      domainAutoRotates.put(DisplayOrientation.AutoRotate.OFF, (byte)0);
      domainAutoRotates.put(DisplayOrientation.AutoRotate.ON, (byte)1);
      domainOsdRotations.put(DisplayOrientation.OsdRotation.LANDSCAPE, (byte)0);
      domainOsdRotations.put(DisplayOrientation.OsdRotation.PORTRAIT, (byte)1);
      domainImageAlls.put(DisplayOrientation.ImageAll.OFF, (byte)0);
      domainImageAlls.put(DisplayOrientation.ImageAll.ON, (byte)1);
      domainImageAlls.put(DisplayOrientation.ImageAll.ON_CLOCKWISE, (byte)2);
      domainImageAlls.put(DisplayOrientation.ImageAll.ON_COUNTERCLOCKWISE, (byte)3);
      domainDisplayWindow1s.put(DisplayOrientation.DisplayWindow1.OFF, (byte)0);
      domainDisplayWindow1s.put(DisplayOrientation.DisplayWindow1.ON, (byte)1);
      domainDisplayWindow2s.put(DisplayOrientation.DisplayWindow2.OFF, (byte)0);
      domainDisplayWindow2s.put(DisplayOrientation.DisplayWindow2.ON, (byte)1);
      domainDisplayWindow3s.put(DisplayOrientation.DisplayWindow3.OFF, (byte)0);
      domainDisplayWindow3s.put(DisplayOrientation.DisplayWindow3.ON, (byte)1);
      domainDisplayWindow4s.put(DisplayOrientation.DisplayWindow4.OFF, (byte)0);
      domainDisplayWindow4s.put(DisplayOrientation.DisplayWindow4.ON, (byte)1);
   }
}
