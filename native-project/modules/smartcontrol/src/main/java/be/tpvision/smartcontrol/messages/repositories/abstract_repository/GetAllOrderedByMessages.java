package be.tpvision.smartcontrol.messages.repositories.abstract_repository;

import be.tpvision.smartcontrol.messages.Messages;

public class GetAllOrderedByMessages {
   public static final String ORDER_FIELD_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Order field");
   public static final String ORDER_DIRECTION_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Order direction");
   public static final String SINGULAR_ATTRIBUTE_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Singular attribute");
   public static final String CRITERIA_BUILDER_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Criteria builder");
   public static final String CRITERIA_QUERY_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Criteria query");
   public static final String ROOT_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Root");
   public static final String EXPRESSION_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Expression");
   public static final String ORDER_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Order");
   public static final String QUERY_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Query");

   private GetAllOrderedByMessages() {
   }
}
