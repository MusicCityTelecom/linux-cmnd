package be.tpvision.smartcontrol.messages.codecs.sicp205.miscellaneous.boot_on_source;

import be.tpvision.smartcontrol.messages.Messages;

public class ToProtocolMessages {
   public static final String BOOT_ON_SOURCE_CAN_NOT_BE_NULL_MESSAGE = Messages.getCanNotBeNullMessage("Boot on source");
   public static final String VIDEO_SOURCE_TYPE_CAN_NOT_BE_NULL_MESSAGE = Messages.getCanNotBeNullMessage("Video source type");
   public static final String PROTOCOL_VIDEO_SOURCE_TYPE_CAN_NOT_BE_NULL_MESSAGE = Messages.getCanNotBeNullMessage("Protocol video source type");
   public static final String PROTOCOL_TAG_CAN_NOT_BE_NULL_MESSAGE = Messages.getCanNotBeNullMessage("Protocol tag");

   private ToProtocolMessages() {
   }
}
