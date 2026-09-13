package be.tpvision.smartcontrol.messages.mappers.group.group;

import be.tpvision.smartcontrol.messages.Messages;

public class ToGroupMessages {
   public static final String GROUP_VIEW_MODEL_CAN_NOT_BE_NULL = Messages.getViewModelCanNotBeNullMessage("Group");
   public static final String DETECT_GROUPS_VIEW_MODEL_CAN_NOT_BE_NULL = Messages.getViewModelCanNotBeNullMessage("Detect groups");
   public static final String IMPORT_EXPORT_GROUP_VIEW_MODEL_CAN_NOT_BE_NULL = Messages.getViewModelCanNotBeNullMessage("Import / export group");

   private ToGroupMessages() {
   }
}
