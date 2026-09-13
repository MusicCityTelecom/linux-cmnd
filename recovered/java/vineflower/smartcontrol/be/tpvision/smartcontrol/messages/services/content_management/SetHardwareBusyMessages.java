package be.tpvision.smartcontrol.messages.services.content_management;

import be.tpvision.smartcontrol.messages.Messages;

public class SetHardwareBusyMessages {
   public static final String HARDWARE_KEY_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Hardware key");
   public static final String HARDWARE_KEY_CAN_NOT_BE_EMPTY = Messages.getCanNotBeEmptyMessage("Hardware key");
   public static final String HARDWARE_PATH_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Hardware path");
   public static final String FLAG_PATH_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Flag path");

   private SetHardwareBusyMessages() {
   }
}
