package be.tpvision.smartcontrol.messages.codecs.sicp197.input_sources.failovers;

import be.tpvision.smartcontrol.messages.Messages;

public class ToDomainMessages {
   public static final String PROTOCOL_FAILOVER_ARRAY_CAN_NOT_CONTAIN_NULL_VALUES = Messages.getCanNotContainNullValuesMessage("Protocol failover array");
   public static final String DOMAIN_FAILOVER_LIST_CAN_NOT_CONTAIN_NULL_VALUES = Messages.getCanNotContainNullValuesMessage("Domain failover list");

   private ToDomainMessages() {
   }
}
