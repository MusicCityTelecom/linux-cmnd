/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageDeliveryException
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.dispatcher;

import java.util.ArrayList;
import java.util.List;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.util.StringUtils;

public class AggregateMessageDeliveryException
extends MessageDeliveryException {
    private final List<? extends Exception> aggregatedExceptions;

    public AggregateMessageDeliveryException(Message<?> undeliveredMessage, String description, List<? extends Exception> aggregatedExceptions) {
        super(undeliveredMessage, description);
        this.initCause(aggregatedExceptions.get(0));
        this.aggregatedExceptions = new ArrayList<Exception>(aggregatedExceptions);
    }

    public List<? extends Exception> getAggregatedExceptions() {
        return new ArrayList<Exception>(this.aggregatedExceptions);
    }

    public String getMessage() {
        String baseMessage = super.getMessage();
        StringBuilder message = new StringBuilder(this.appendPeriodIfNecessary(baseMessage)).append(" Multiple causes:\n");
        for (Exception exception : this.aggregatedExceptions) {
            message.append("    " + exception.getMessage() + "\n");
        }
        message.append("See below for the stacktrace of the first cause.");
        return message.toString();
    }

    private String appendPeriodIfNecessary(String baseMessage) {
        if (!StringUtils.hasText((String)baseMessage)) {
            return "";
        }
        if (!baseMessage.endsWith(".")) {
            return baseMessage + ".";
        }
        return baseMessage;
    }
}

