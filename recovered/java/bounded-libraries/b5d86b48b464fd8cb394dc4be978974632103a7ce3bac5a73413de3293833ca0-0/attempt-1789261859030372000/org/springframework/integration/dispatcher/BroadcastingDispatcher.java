/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageHandler
 *  org.springframework.messaging.MessagingException
 *  org.springframework.messaging.support.MessageHandlingRunnable
 *  org.springframework.util.Assert
 */
package org.springframework.integration.dispatcher;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.integration.MessageDispatchingException;
import org.springframework.integration.dispatcher.AbstractDispatcher;
import org.springframework.integration.dispatcher.MessageHandlingTaskDecorator;
import org.springframework.integration.support.DefaultMessageBuilderFactory;
import org.springframework.integration.support.MessageBuilderFactory;
import org.springframework.integration.support.MessageDecorator;
import org.springframework.integration.support.utils.IntegrationUtils;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.MessageHandlingRunnable;
import org.springframework.util.Assert;

public class BroadcastingDispatcher
extends AbstractDispatcher
implements BeanFactoryAware {
    private final boolean requireSubscribers;
    private volatile boolean ignoreFailures;
    private volatile boolean applySequence;
    private final Executor executor;
    private volatile int minSubscribers;
    private MessageHandlingTaskDecorator messageHandlingTaskDecorator = task -> task;
    private BeanFactory beanFactory;
    private volatile MessageBuilderFactory messageBuilderFactory = new DefaultMessageBuilderFactory();
    private volatile boolean messageBuilderFactorySet;

    public BroadcastingDispatcher() {
        this(null, false);
    }

    public BroadcastingDispatcher(Executor executor) {
        this(executor, false);
    }

    public BroadcastingDispatcher(boolean requireSubscribers) {
        this(null, requireSubscribers);
    }

    public BroadcastingDispatcher(Executor executor, boolean requireSubscribers) {
        this.requireSubscribers = requireSubscribers;
        this.executor = executor;
    }

    public void setIgnoreFailures(boolean ignoreFailures) {
        this.ignoreFailures = ignoreFailures;
    }

    public void setApplySequence(boolean applySequence) {
        this.applySequence = applySequence;
    }

    public void setMinSubscribers(int minSubscribers) {
        this.minSubscribers = minSubscribers;
    }

    public void setMessageHandlingTaskDecorator(MessageHandlingTaskDecorator messageHandlingTaskDecorator) {
        Assert.notNull((Object)messageHandlingTaskDecorator, (String)"'messageHandlingTaskDecorator' must not be null.");
        this.messageHandlingTaskDecorator = messageHandlingTaskDecorator;
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    protected MessageBuilderFactory getMessageBuilderFactory() {
        if (!this.messageBuilderFactorySet) {
            if (this.beanFactory != null) {
                this.messageBuilderFactory = IntegrationUtils.getMessageBuilderFactory(this.beanFactory);
            }
            this.messageBuilderFactorySet = true;
        }
        return this.messageBuilderFactory;
    }

    @Override
    public boolean dispatch(Message<?> message) {
        int dispatched = 0;
        int sequenceNumber = 1;
        Set<MessageHandler> handlers = this.getHandlers();
        if (this.requireSubscribers && handlers.size() == 0) {
            throw new MessageDispatchingException(message, "Dispatcher has no subscribers");
        }
        int sequenceSize = handlers.size();
        Message<?> messageToSend = message;
        UUID sequenceId = null;
        if (this.applySequence) {
            sequenceId = message.getHeaders().getId();
        }
        for (MessageHandler handler : handlers) {
            if (this.applySequence) {
                messageToSend = this.getMessageBuilderFactory().fromMessage(message).pushSequenceDetails(sequenceId, sequenceNumber++, sequenceSize).build();
                if (message instanceof MessageDecorator) {
                    messageToSend = ((MessageDecorator)message).decorateMessage(messageToSend);
                }
            }
            if (this.executor != null) {
                Runnable task = this.createMessageHandlingTask(handler, messageToSend);
                this.executor.execute(task);
                ++dispatched;
                continue;
            }
            if (!this.invokeHandler(handler, messageToSend)) continue;
            ++dispatched;
        }
        if (dispatched == 0 && this.minSubscribers == 0 && this.logger.isDebugEnabled()) {
            if (sequenceSize > 0) {
                this.logger.debug((Object)"No subscribers received message, default behavior is ignore");
            } else {
                this.logger.debug((Object)"No subscribers, default behavior is ignore");
            }
        }
        return dispatched >= this.minSubscribers;
    }

    private Runnable createMessageHandlingTask(final MessageHandler handler, final Message<?> message) {
        MessageHandlingRunnable task = new MessageHandlingRunnable(){
            private final MessageHandler delegate = message1 -> BroadcastingDispatcher.access$000(BroadcastingDispatcher.this, handler, message1);

            public void run() {
                BroadcastingDispatcher.this.invokeHandler(handler, message);
            }

            public Message<?> getMessage() {
                return message;
            }

            public MessageHandler getMessageHandler() {
                return this.delegate;
            }
        };
        return this.messageHandlingTaskDecorator.decorate(task);
    }

    private boolean invokeHandler(MessageHandler handler, Message<?> message) {
        try {
            handler.handleMessage(message);
            return true;
        }
        catch (RuntimeException e) {
            if (!this.ignoreFailures) {
                if (e instanceof MessagingException && ((MessagingException)((Object)e)).getFailedMessage() == null) {
                    throw new MessagingException(message, "Failed to handle Message", (Throwable)e);
                }
                throw e;
            }
            if (this.logger.isWarnEnabled()) {
                this.logger.warn((Object)"Suppressing Exception since 'ignoreFailures' is set to TRUE.", (Throwable)e);
            }
            return false;
        }
    }
}

