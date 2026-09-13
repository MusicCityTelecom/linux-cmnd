/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.factory.BeanNameAware
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageHandler
 *  org.springframework.messaging.MessagingException
 *  org.springframework.messaging.SubscribableChannel
 */
package org.springframework.integration.channel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.integration.support.context.NamedComponent;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.SubscribableChannel;

public final class FixedSubscriberChannel
implements SubscribableChannel,
BeanNameAware,
NamedComponent {
    private static final Log LOGGER = LogFactory.getLog(FixedSubscriberChannel.class);
    private final MessageHandler handler;
    private String beanName;

    public FixedSubscriberChannel() {
        throw new IllegalArgumentException("Cannot instantiate a " + this.getClass().getSimpleName() + " without a MessageHandler.");
    }

    public FixedSubscriberChannel(MessageHandler handler) {
        this.handler = handler;
    }

    public void setBeanName(String name) {
        this.beanName = name;
    }

    @Override
    public String getBeanName() {
        return this.beanName;
    }

    public boolean send(Message<?> message) {
        return this.send(message, 0L);
    }

    public boolean send(Message<?> message, long timeout) {
        try {
            this.handler.handleMessage(message);
            return true;
        }
        catch (MessagingException ex) {
            if (ex.getFailedMessage() == null) {
                throw new MessagingException(message, "Failed to handle Message", (Throwable)ex);
            }
            throw ex;
        }
    }

    public boolean subscribe(MessageHandler handler) {
        if (!this.handler.equals(handler) && LOGGER.isDebugEnabled()) {
            LOGGER.debug((Object)(this.getComponentName() + ": cannot be subscribed to (it has a fixed single subscriber)."));
        }
        return false;
    }

    public boolean unsubscribe(MessageHandler handler) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug((Object)(this.getComponentName() + ": cannot be unsubscribed from (it has a fixed single subscriber)."));
        }
        return false;
    }

    @Override
    public String getComponentType() {
        return "fixed-subscriber-channel";
    }

    @Override
    public String getComponentName() {
        if (this.beanName != null) {
            return this.beanName;
        }
        return "Unnamed fixed subscriber channel";
    }
}

