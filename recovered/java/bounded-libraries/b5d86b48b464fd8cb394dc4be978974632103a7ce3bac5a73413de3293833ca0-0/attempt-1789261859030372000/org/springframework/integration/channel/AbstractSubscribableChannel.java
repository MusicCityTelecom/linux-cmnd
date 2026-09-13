/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageDeliveryException
 *  org.springframework.messaging.MessageHandler
 *  org.springframework.messaging.SubscribableChannel
 *  org.springframework.util.Assert
 */
package org.springframework.integration.channel;

import org.springframework.integration.MessageDispatchingException;
import org.springframework.integration.channel.AbstractMessageChannel;
import org.springframework.integration.dispatcher.MessageDispatcher;
import org.springframework.integration.support.management.SubscribableChannelManagement;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.util.Assert;

public abstract class AbstractSubscribableChannel
extends AbstractMessageChannel
implements SubscribableChannel,
SubscribableChannelManagement {
    @Override
    public int getSubscriberCount() {
        return this.getRequiredDispatcher().getHandlerCount();
    }

    public boolean subscribe(MessageHandler handler) {
        MessageDispatcher dispatcher;
        boolean added = (dispatcher = this.getRequiredDispatcher()).addHandler(handler);
        this.adjustCounterIfNecessary(dispatcher, added ? 1 : 0);
        return added;
    }

    public boolean unsubscribe(MessageHandler handle2) {
        MessageDispatcher dispatcher;
        boolean removed = (dispatcher = this.getRequiredDispatcher()).removeHandler(handle2);
        this.adjustCounterIfNecessary(dispatcher, removed ? -1 : 0);
        return removed;
    }

    private void adjustCounterIfNecessary(MessageDispatcher dispatcher, int delta) {
        if (delta != 0 && this.logger.isInfoEnabled()) {
            this.logger.info((CharSequence)("Channel '" + this.getFullChannelName() + "' has " + dispatcher.getHandlerCount() + " subscriber(s)."));
        }
    }

    @Override
    protected boolean doSend(Message<?> message, long timeout) {
        try {
            return this.getRequiredDispatcher().dispatch(message);
        }
        catch (MessageDispatchingException ex) {
            String description = ex.getMessage() + " for channel '" + this.getFullChannelName() + "'.";
            throw new MessageDeliveryException(message, description, (Throwable)((Object)ex));
        }
    }

    private MessageDispatcher getRequiredDispatcher() {
        MessageDispatcher dispatcher = this.getDispatcher();
        Assert.state((dispatcher != null ? 1 : 0) != 0, (String)"'dispatcher' must not be null");
        return dispatcher;
    }

    protected abstract MessageDispatcher getDispatcher();
}

