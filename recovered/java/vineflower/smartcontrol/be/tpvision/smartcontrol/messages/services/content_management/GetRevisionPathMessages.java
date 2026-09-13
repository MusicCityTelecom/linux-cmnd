package be.tpvision.smartcontrol.messages.services.content_management;

import be.tpvision.smartcontrol.messages.Messages;

public class GetRevisionPathMessages {
   public static final String HARDWARE_KEY_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Hardware key");
   public static final String REVISION_PATH_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Revision path");

   private GetRevisionPathMessages() {
   }
}
