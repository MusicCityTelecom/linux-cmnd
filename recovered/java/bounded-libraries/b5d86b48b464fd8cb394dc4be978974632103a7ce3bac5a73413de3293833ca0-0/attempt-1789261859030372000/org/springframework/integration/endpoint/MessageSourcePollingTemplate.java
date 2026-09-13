/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageHandler
 *  org.springframework.util.Assert
 */
package org.springframework.integration.endpoint;

import org.springframework.integration.StaticMessageHeaderAccessor;
import org.springframework.integration.acks.AckUtils;
import org.springframework.integration.acks.AcknowledgmentCallback;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.endpoint.PollingOperations;
import org.springframework.integration.support.utils.IntegrationUtils;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.util.Assert;

public class MessageSourcePollingTemplate
implements PollingOperations {
    private final MessageSource<?> source;

    public MessageSourcePollingTemplate(MessageSource<?> source) {
        Assert.notNull(source, (String)"'source' cannot be null");
        this.source = source;
    }

    @Override
    public boolean poll(MessageHandler handler) {
        Assert.notNull((Object)handler, (String)"'handler' cannot be null");
        Message<?> message = this.source.receive();
        if (message != null) {
            AcknowledgmentCallback ackCallback = StaticMessageHeaderAccessor.getAcknowledgmentCallback(message);
            try {
                handler.handleMessage(message);
                AckUtils.autoAck(ackCallback);
            }
            catch (Exception e) {
                AckUtils.autoNack(ackCallback);
                throw IntegrationUtils.wrapInHandlingExceptionIfNecessary(message, () -> "error occurred during handling message in 'MessageSourcePollingTemplate' [" + this + "]", e);
            }
            return true;
        }
        return false;
    }
}

