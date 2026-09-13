package be.tpvision.smartcontrol.messages.mappers.miscellaneous.tiling;

import be.tpvision.smartcontrol.messages.Messages;

public class ToTilingMessages {
   public static final String TILING_VIEW_MODEL_CAN_NOT_BE_NULL = Messages.getViewModelCanNotBeNullMessage("Tiling");
   public static final String VIEW_MODEL_ENABLE_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("View model enable");
   public static final String DOMAIN_ENABLE_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Domain enable");
   public static final String VIEW_MODEL_FRAME_COMP_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("View model frame comp");
   public static final String DOMAIN_FRAME_COMP_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Domain frame comp");

   private ToTilingMessages() {
   }
}
