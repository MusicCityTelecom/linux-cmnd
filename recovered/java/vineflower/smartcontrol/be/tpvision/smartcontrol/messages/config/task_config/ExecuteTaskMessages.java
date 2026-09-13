package be.tpvision.smartcontrol.messages.config.task_config;

import be.tpvision.smartcontrol.messages.Messages;

public class ExecuteTaskMessages {
   public static final String FUTURE_TASK_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Future task");
   public static final String TIMEOUT_HAS_TO_BE_A_POSITIVE_NUMBER = Messages.getHasToBePositiveNumberMessage("Timeout");
   public static final String TIME_UNIT_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Time unit");

   private ExecuteTaskMessages() {
   }
}
