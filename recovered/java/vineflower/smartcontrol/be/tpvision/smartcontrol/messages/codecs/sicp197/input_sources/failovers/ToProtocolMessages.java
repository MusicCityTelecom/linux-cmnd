package be.tpvision.smartcontrol.messages.codecs.sicp197.input_sources.failovers;

import be.tpvision.smartcontrol.messages.Messages;

public class ToProtocolMessages {
   public static final String FAILOVERS_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Failovers");
   public static final String DOMAIN_FAILOVER_LIST_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Domain failover list");
   public static final String DOMAIN_FAILOVER_LIST_CAN_NOT_CONTAIN_NULL_VALUES = Messages.getCanNotContainNullValuesMessage("Domain failover list");
   public static final String PROTOCOL_FAILOVER_ARRAY_CAN_NOT_CONTAIN_NULL_VALUES = Messages.getCanNotContainNullValuesMessage("Protocol failover array");

   private ToProtocolMessages() {
   }
}
