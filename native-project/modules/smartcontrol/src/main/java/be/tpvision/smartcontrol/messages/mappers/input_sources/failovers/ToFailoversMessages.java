package be.tpvision.smartcontrol.messages.mappers.input_sources.failovers;

import be.tpvision.smartcontrol.messages.Messages;

public class ToFailoversMessages {
   public static final String FAILOVER_STRING_LIST_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Failover string list");
   public static final String FAILOVER_STRING_LIST_CAN_NOT_CONTAIN_NULL_VALUES = Messages.getCanNotContainNullValuesMessage("Failover string list");
   public static final String FAILOVER_LIST_CAN_NOT_CONTAIN_NULL_VALUES = Messages.getCanNotContainNullValuesMessage("Failover list");

   private ToFailoversMessages() {
   }
}
