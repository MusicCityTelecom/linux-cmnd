package be.tpvision.smartcontrol.messages.codecs.sicp197.miscellaneous.led_strips;

import be.tpvision.smartcontrol.messages.Messages;

public class ToProtocolMessages {
   public static final String LED_STRIPS_CAN_NOT_BE_NULL_MESSAGE = Messages.getCanNotBeNullMessage("Led strips");
   public static final String DOMAIN_STATUS_CAN_NOT_BE_NULL_MESSAGE = Messages.getCanNotBeNullMessage("Domain status");
   public static final String PROTOCOL_STATUS_CAN_NOT_BE_NULL_MESSAGE = Messages.getCanNotBeNullMessage("Protocol status");

   private ToProtocolMessages() {
   }
}
