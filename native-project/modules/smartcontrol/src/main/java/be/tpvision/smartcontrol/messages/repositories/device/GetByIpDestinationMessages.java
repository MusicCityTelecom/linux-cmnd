package be.tpvision.smartcontrol.messages.repositories.device;

import be.tpvision.smartcontrol.messages.Messages;

public class GetByIpDestinationMessages {
   public static final String IP_DESTINATION_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("IP destination");
   public static final String ENTITY_MANAGER_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Entity manager");
   public static final String CRITERIA_BUILDER_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Criteria builder");
   public static final String CRITERIA_QUERY_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Criteria query");
   public static final String QUERY_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Query");

   private GetByIpDestinationMessages() {
   }
}
