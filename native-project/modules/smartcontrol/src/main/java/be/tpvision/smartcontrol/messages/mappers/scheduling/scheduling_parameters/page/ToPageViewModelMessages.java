package be.tpvision.smartcontrol.messages.mappers.scheduling.scheduling_parameters.page;

import be.tpvision.smartcontrol.messages.Messages;

public class ToPageViewModelMessages {
   public static final String PAGE_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Page");
   public static final String DOMAIN_STATUS_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Domain status");
   public static final String DOMAIN_WORKING_DAYS_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Domain working days");
   public static final String VIEW_MODEL_WORKING_DAYS_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("View model working days");
   public static final String VIEW_MODEL_WORKING_DAYS_CAN_NOT_BE_EMPTY = Messages.getCanNotBeEmptyMessage("View model working days");
   public static final String VIEW_MODEL_WORKING_DAYS_CAN_NOT_CONTAIN_NULL_VALUES = Messages.getCanNotContainNullValuesMessage("View model working days");

   private ToPageViewModelMessages() {
   }
}
