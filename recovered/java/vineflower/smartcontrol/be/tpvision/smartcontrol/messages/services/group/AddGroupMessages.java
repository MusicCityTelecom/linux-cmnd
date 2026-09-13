package be.tpvision.smartcontrol.messages.services.group;

import be.tpvision.smartcontrol.messages.Messages;

public class AddGroupMessages {
   public static final String DEVICE_REPOSITORY_CAN_NOT_BE_NULL = Messages.getRepositoryCanNotBeNullMessage("Device");
   public static final String GROUP_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Group");

   private AddGroupMessages() {
   }
}
