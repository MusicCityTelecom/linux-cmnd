package be.tpvision.smartcontrol.messages.codecs.sicp199.miscellaneous.pixel_shift;

import be.tpvision.smartcontrol.messages.Messages;

public class ToProtocolMessages {
   public static final String PIXEL_SHIFT_CAN_NOT_BE_NULL_MESSAGE = Messages.getCanNotBeNullMessage("Pixel shift");
   public static final String DOMAIN_STATE_CAN_NOT_BE_NULL_MESSAGE = Messages.getCanNotBeNullMessage("Domain state");
   public static final String PROTOCOL_STATE_CAN_NOT_BE_NULL_MESSAGE = Messages.getCanNotBeNullMessage("Protocol state");

   private ToProtocolMessages() {
   }
}
