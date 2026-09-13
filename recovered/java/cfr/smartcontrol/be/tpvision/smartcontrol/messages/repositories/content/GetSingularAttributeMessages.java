/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.messages.repositories.content;

import be.tpvision.smartcontrol.domain.Content;
import be.tpvision.smartcontrol.messages.Messages;
import be.tpvision.smartcontrol.repository.order_fields.OrderField;
import org.springframework.util.Assert;

public class GetSingularAttributeMessages {
    public static final String ORDER_FIELD_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Order field");

    private GetSingularAttributeMessages() {
    }

    public static String getOrderFieldIsNotSupportedMessage(OrderField<Content> orderField) {
        Assert.notNull(orderField, ORDER_FIELD_CAN_NOT_BE_NULL);
        return String.format("Order field %s is not supported.", orderField);
    }
}

