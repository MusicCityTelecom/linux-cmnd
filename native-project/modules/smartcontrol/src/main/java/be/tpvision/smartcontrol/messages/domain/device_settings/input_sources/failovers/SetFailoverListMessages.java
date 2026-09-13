package be.tpvision.smartcontrol.messages.domain.device_settings.input_sources.failovers;

import be.tpvision.smartcontrol.messages.Messages;

public class SetFailoverListMessages {
   public static final String FAILOVER_LIST_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Failover list");
   public static final String FAILOVER_LIST_CAN_NOT_CONTAIN_NULL_VALUES = Messages.getCanNotContainNullValuesMessage("Failover list");

   private SetFailoverListMessages() {
   }

   public static String getMaximumNumberOfFailoversMessage(final int maximumNumberOfFailovers) {
      return String.format("The maximum number of failovers is %d.", maximumNumberOfFailovers);
   }
}
