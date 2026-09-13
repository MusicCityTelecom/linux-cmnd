/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.log.LogMessage
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.ReactiveMessageHandler
 *  org.springframework.util.Assert
 *  reactor.core.publisher.Mono
 */
package org.springframework.integration.handler;

import org.springframework.core.log.LogMessage;
import org.springframework.integration.handler.MessageHandlerSupport;
import org.springframework.integration.history.MessageHistory;
import org.springframework.messaging.Message;
import org.springframework.messaging.ReactiveMessageHandler;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

public abstract class AbstractReactiveMessageHandler
extends MessageHandlerSupport
implements ReactiveMessageHandler {
    public Mono<Void> handleMessage(Message<?> message) {
        Assert.notNull(message, (String)"message must not be null");
        if (this.isLoggingEnabled()) {
            this.logger.debug((CharSequence)LogMessage.format((String)"%s received message: %s", (Object)this, message));
        }
        Message<?> messageToUse = this.shouldTrack() ? MessageHistory.write(message, this, this.getMessageBuilderFactory()) : message;
        return this.handleMessageInternal(messageToUse).doOnError(ex -> this.logger.error(ex, (CharSequence)LogMessage.format((String)"An error occurred in message handler [%s] on message [%s]", (Object)this, (Object)messageToUse)));
    }

    protected abstract Mono<Void> handleMessageInternal(Message<?> var1);
}

