package be.tpvision.smartcontrol.messages.view_models.device.ip_destination;

import be.tpvision.smartcontrol.messages.Messages;

public class SetIpMessages {
   public static final String IP_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("IP");
   public static final String IP_CAN_NOT_BE_EMPTY = Messages.getCanNotBeEmptyMessage("IP");

   private SetIpMessages() {
   }
}
