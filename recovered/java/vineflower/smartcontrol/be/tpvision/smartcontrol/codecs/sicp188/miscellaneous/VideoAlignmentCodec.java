package be.tpvision.smartcontrol.codecs.sicp188.miscellaneous;

import be.tpvision.smartcontrol.codecs.sicp.Codec;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.VideoAlignment;
import be.tpvision.smartcontrol.messages.codecs.sicp188.miscellaneous.video_alignment.ToDomainMessages;
import be.tpvision.smartcontrol.messages.codecs.sicp188.miscellaneous.video_alignment.ToProtocolMessages;
import be.tpvision.smartcontrol.util.MapUtilities;
import java.util.EnumMap;
import java.util.Map;
import org.springframework.util.Assert;

public class VideoAlignmentCodec extends Codec<VideoAlignment> {
   static final byte AUTO_ADJUST_BYTE = 64;
   static final byte RESERVED_BYTE = 0;
   private static final Map<VideoAlignment.Item, Byte> domainItems = new EnumMap<>(VideoAlignment.Item.class);
   private static final Map<Byte, VideoAlignment.Item> protocolItems = MapUtilities.inverse(domainItems);
   private static VideoAlignmentCodec videoAlignmentCodec;

   private VideoAlignmentCodec() {
      super(VideoAlignment.class);
   }

   public static Map<VideoAlignment.Item, Byte> getDomainItems() {
      return domainItems;
   }

   public static Map<Byte, VideoAlignment.Item> getProtocolItems() {
      return protocolItems;
   }

   public static synchronized VideoAlignmentCodec getInstance() {
      if (videoAlignmentCodec == null) {
         videoAlignmentCodec = new VideoAlignmentCodec();
      }

      return videoAlignmentCodec;
   }

   public byte[] toProtocol(VideoAlignment videoAlignment) {
      VideoAlignment.Item domainItem = videoAlignment.getItem();
      Assert.state(domainItem != null, ToProtocolMessages.DOMAIN_ITEM_CAN_NOT_BE_NULL);
      Byte protocolItem = domainItems.get(domainItem);
      Assert.state(protocolItem != null, ToProtocolMessages.PROTOCOL_ITEM_CAN_NOT_BE_NULL);
      return new byte[]{protocolItem, 0};
   }

   public VideoAlignment toDomain(byte[] bytes) {
      throw new UnsupportedOperationException(ToDomainMessages.TO_DOMAIN_IS_NOT_SUPPORTED_FOR_THIS_SICP_VERSION);
   }

   static {
      domainItems.put(VideoAlignment.Item.AUTO_ADJUST, (byte)64);
   }
}
