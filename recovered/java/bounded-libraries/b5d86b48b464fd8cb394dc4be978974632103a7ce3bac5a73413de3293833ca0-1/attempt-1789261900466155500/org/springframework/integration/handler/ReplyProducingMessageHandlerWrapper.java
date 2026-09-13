/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.Lifecycle
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageHandler
 *  org.springframework.util.Assert
 */
package org.springframework.integration.handler;

import org.springframework.context.Lifecycle;
import org.springframework.integration.IntegrationPattern;
import org.springframework.integration.IntegrationPatternType;
import org.springframework.integration.handler.AbstractReplyProducingMessageHandler;
import org.springframework.integration.support.management.ManageableLifecycle;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.util.Assert;

public class ReplyProducingMessageHandlerWrapper
extends AbstractReplyProducingMessageHandler
implements ManageableLifecycle {
    private final MessageHandler target;

    public ReplyProducingMessageHandlerWrapper(MessageHandler target) {
        Assert.notNull((Object)target, (String)"'target' must not be null");
        this.target = target;
    }

    @Override
    public IntegrationPatternType getIntegrationPatternType() {
        return this.target instanceof IntegrationPattern ? ((IntegrationPattern)this.target).getIntegrationPatternType() : IntegrationPatternType.service_activator;
    }

    @Override
    protected Object handleRequestMessage(Message<?> requestMessage) {
        this.target.handleMessage(requestMessage);
        return null;
    }

    @Override
    public void start() {
        if (this.target instanceof Lifecycle) {
            ((Lifecycle)this.target).start();
        }
    }

    @Override
    public void stop() {
        if (this.target instanceof Lifecycle) {
            ((Lifecycle)this.target).stop();
        }
    }

    @Override
    public boolean isRunning() {
        return !(this.target instanceof Lifecycle) || ((Lifecycle)this.target).isRunning();
    }
}

