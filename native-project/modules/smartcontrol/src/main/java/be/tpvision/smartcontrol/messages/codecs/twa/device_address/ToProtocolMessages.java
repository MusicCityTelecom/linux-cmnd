package be.tpvision.smartcontrol.messages.codecs.twa.device_address;

import be.tpvision.smartcontrol.messages.Messages;

public class ToProtocolMessages {
   public static final String DEVICE_ADDRESS_WRAPPER_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Device address wrapper");
   public static final String DEVICE_ADDRESS_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Device address");

   private ToProtocolMessages() {
   }
}
