package be.tpvision.smartcontrol.messages.mappers.input_sources.failovers;

import be.tpvision.smartcontrol.messages.Messages;

public class ToStringListMessages {
   public static final String FAILOVERS_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Failovers");
   public static final String FAILOVER_LIST_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Failover list");
   public static final String FAILOVER_LIST_CAN_NOT_CONTAIN_NULL_VALUES = Messages.getCanNotContainNullValuesMessage("Failover list");

   private ToStringListMessages() {
   }
}
