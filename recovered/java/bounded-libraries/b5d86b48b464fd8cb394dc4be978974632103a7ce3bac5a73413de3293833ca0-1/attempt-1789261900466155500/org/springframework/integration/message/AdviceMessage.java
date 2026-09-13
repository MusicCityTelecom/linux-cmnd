/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageHeaders
 *  org.springframework.messaging.support.GenericMessage
 */
package org.springframework.integration.message;

import java.util.Map;
import java.util.Objects;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.GenericMessage;

public class AdviceMessage<T>
extends GenericMessage<T> {
    private static final long serialVersionUID = 1L;
    private final Message<?> inputMessage;

    public AdviceMessage(T payload, Message<?> inputMessage) {
        super(payload);
        this.inputMessage = inputMessage;
    }

    public AdviceMessage(T payload, Map<String, Object> headers, Message<?> inputMessage) {
        super(payload, headers);
        this.inputMessage = inputMessage;
    }

    public AdviceMessage(T payload, MessageHeaders headers, Message<?> inputMessage) {
        super(payload, headers);
        this.inputMessage = inputMessage;
    }

    public Message<?> getInputMessage() {
        return this.inputMessage;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder(super.toString());
        builder.setLength(builder.length() - 1);
        builder.append(", inputMessage=").append(this.inputMessage.toString()).append("]");
        return builder.toString();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdviceMessage)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        AdviceMessage that = (AdviceMessage)((Object)o);
        return Objects.equals(this.inputMessage, that.inputMessage);
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(), this.inputMessage);
    }
}

