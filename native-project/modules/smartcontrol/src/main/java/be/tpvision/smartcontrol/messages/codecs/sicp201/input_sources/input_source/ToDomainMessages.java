package be.tpvision.smartcontrol.messages.codecs.sicp201.input_sources.input_source;

import be.tpvision.smartcontrol.messages.Messages;

public class ToDomainMessages {
   public static final String PROTOCOL_SOURCE_TYPE_CAN_NOT_BE_NULL_MESSAGE = Messages.getCanNotBeNullMessage("Protocol source type");
   public static final String DOMAIN_SOURCE_TYPE_CAN_NOT_BE_NULL_MESSAGE = Messages.getCanNotBeNullMessage("Domain source type");
   public static final String PROTOCOL_TAG_CAN_NOT_BE_NULL_MESSAGE = Messages.getCanNotBeNullMessage("Protocol tag");
   public static final String PROTOCOL_SOURCE_LABEL_CAN_NOT_BE_NULL_MESSAGE = Messages.getCanNotBeNullMessage("Protocol source label");
   public static final String DOMAIN_SOURCE_LABEL_CAN_NOT_BE_NULL_MESSAGE = Messages.getCanNotBeNullMessage("Domain source label");

   private ToDomainMessages() {
   }
}
