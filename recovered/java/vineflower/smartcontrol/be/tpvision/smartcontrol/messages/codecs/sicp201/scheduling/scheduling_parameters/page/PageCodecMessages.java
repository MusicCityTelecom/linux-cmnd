package be.tpvision.smartcontrol.messages.codecs.sicp201.scheduling.scheduling_parameters.page;

import be.tpvision.smartcontrol.messages.Messages;

public class PageCodecMessages {
   public static final String DOMAIN_SOURCE_TYPES_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Domain source types");
   public static final String PROTOCOL_SOURCE_TYPES_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Protocol source types");
   public static final String DOMAIN_TAGS_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Domain tags");
   public static final String PROTOCOL_TAGS_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Protocol tags");

   private PageCodecMessages() {
   }
}
