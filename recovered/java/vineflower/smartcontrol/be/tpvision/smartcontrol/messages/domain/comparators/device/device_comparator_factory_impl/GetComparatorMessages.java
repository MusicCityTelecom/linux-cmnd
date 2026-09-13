package be.tpvision.smartcontrol.messages.domain.comparators.device.device_comparator_factory_impl;

import be.tpvision.smartcontrol.messages.Messages;
import be.tpvision.smartcontrol.repository.OrderDirection;
import be.tpvision.smartcontrol.repository.order_fields.OrderField;
import org.springframework.util.Assert;

public class GetComparatorMessages {
   public static final String ORDER_FIELD_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Order field");
   public static final String ORDER_DIRECTION_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Order direction");

   private GetComparatorMessages() {
   }

   public static String getNoComparatorsFoundForOrderFieldMessage(final OrderField orderField) {
      String orderFieldCanNotBeNullMessage = Messages.getCanNotBeNullMessage("Order field");
      Assert.notNull(orderField, orderFieldCanNotBeNullMessage);
      return String.format("No comparators found for order field %s.", orderField);
   }

   public static String getOrderDirectionIsNotSupportedMessage(final OrderDirection orderDirection) {
      String orderDirectionCanNotBeNullMessage = Messages.getCanNotBeNullMessage("Order direction");
      Assert.notNull(orderDirection, orderDirectionCanNotBeNullMessage);
      return String.format("Order direction %s is not supported.", orderDirection);
   }
}
