package be.tpvision.smartcontrol.messages.mappers.scheduling.scheduling_parameters.page;

import be.tpvision.smartcontrol.messages.Messages;

public class ToPageMessages {
   public static final String PAGE_VIEW_MODEL_CAN_NOT_BE_NULL = Messages.getViewModelCanNotBeNullMessage("Page");
   public static final String VIEW_MODEL_STATUS_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("View model status");
   public static final String VIEW_MODEL_SOURCE_TYPE_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("View model source type");
   public static final String VIEW_MODEL_WORKING_DAYS_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("View model working days");
   public static final String VIEW_MODEL_WORKING_DAYS_CAN_NOT_BE_EMPTY = Messages.getCanNotBeEmptyMessage("View model working days");
   public static final String VIEW_MODEL_WORKING_DAYS_CAN_NOT_CONTAIN_NULL_VALUES = Messages.getCanNotContainNullValuesMessage("View model working days");
   public static final String DOMAIN_WORKING_DAYS_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Domain working days");

   private ToPageMessages() {
   }
}
