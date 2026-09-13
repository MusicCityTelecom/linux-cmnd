package be.tpvision.smartcontrol.messages.services.miscellaneous;

import be.tpvision.smartcontrol.messages.Messages;

public class SetPortStatusMessages {
   public static final String DEVICE_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Device");
   public static final String PORT_STATUS_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Port status");

   private SetPortStatusMessages() {
   }
}
