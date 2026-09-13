/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageHandler
 *  org.springframework.messaging.support.MessageHandlingRunnable
 *  org.springframework.util.Assert
 */
package org.springframework.integration.dispatcher;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import org.springframework.integration.MessageDispatchingException;
import org.springframework.integration.dispatcher.AbstractDispatcher;
import org.springframework.integration.dispatcher.AggregateMessageDeliveryException;
import org.springframework.integration.dispatcher.LoadBalancingStrategy;
import org.springframework.integration.dispatcher.MessageHandlingTaskDecorator;
import org.springframework.integration.support.utils.IntegrationUtils;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.support.MessageHandlingRunnable;
import org.springframework.util.Assert;

public class UnicastingDispatcher
extends AbstractDispatcher {
    private final MessageHandler dispatchHandler = this::doDispatch;
    private final Executor executor;
    private boolean failover = true;
    private LoadBalancingStrategy loadBalancingStrategy;
    private MessageHandlingTaskDecorator messageHandlingTaskDecorator = task -> task;

    public UnicastingDispatcher() {
        this.executor = null;
    }

    public UnicastingDispatcher(@Nullable Executor executor) {
        this.executor = executor;
    }

    public void setFailover(boolean failover) {
        this.failover = failover;
    }

    public void setLoadBalancingStrategy(@Nullable LoadBalancingStrategy loadBalancingStrategy) {
        this.loadBalancingStrategy = loadBalancingStrategy;
    }

    public void setMessageHandlingTaskDecorator(MessageHandlingTaskDecorator messageHandlingTaskDecorator) {
        Assert.notNull((Object)messageHandlingTaskDecorator, (String)"'messageHandlingTaskDecorator' must not be null.");
        this.messageHandlingTaskDecorator = messageHandlingTaskDecorator;
    }

    @Override
    public final boolean dispatch(Message<?> message) {
        if (this.executor != null) {
            Runnable task = this.createMessageHandlingTask(message);
            this.executor.execute(task);
            return true;
        }
        return this.doDispatch(message);
    }

    private Runnable createMessageHandlingTask(final Message<?> message) {
        MessageHandlingRunnable task = new MessageHandlingRunnable(){

            public void run() {
                UnicastingDispatcher.this.doDispatch(message);
            }

            public Message<?> getMessage() {
                return message;
            }

            public MessageHandler getMessageHandler() {
                return UnicastingDispatcher.this.dispatchHandler;
            }
        };
        return this.messageHandlingTaskDecorator.decorate(task);
    }

    private boolean doDispatch(Message<?> message) {
        if (this.tryOptimizedDispatch(message)) {
            return true;
        }
        boolean success = false;
        Iterator<MessageHandler> handlerIterator = this.getHandlerIterator(message);
        if (!handlerIterator.hasNext()) {
            throw new MessageDispatchingException(message, "Dispatcher has no subscribers");
        }
        ArrayList<RuntimeException> exceptions = null;
        while (!success && handlerIterator.hasNext()) {
            MessageHandler handler = handlerIterator.next();
            try {
                handler.handleMessage(message);
                success = true;
            }
            catch (Exception ex) {
                boolean isLast;
                RuntimeException runtimeException = IntegrationUtils.wrapInDeliveryExceptionIfNecessary(message, () -> "Dispatcher failed to deliver Message", ex);
                if (exceptions == null) {
                    exceptions = new ArrayList<RuntimeException>();
                }
                exceptions.add(runtimeException);
                boolean bl = isLast = !handlerIterator.hasNext();
                if (!isLast && this.failover) {
                    this.logExceptionBeforeFailOver(ex, handler, message);
                }
                this.handleExceptions(exceptions, message, isLast);
            }
        }
        return success;
    }

    private Iterator<MessageHandler> getHandlerIterator(Message<?> message) {
        Set<MessageHandler> handlers = this.getHandlers();
        if (this.loadBalancingStrategy != null) {
            return this.loadBalancingStrategy.getHandlerIterator(message, handlers);
        }
        return handlers.iterator();
    }

    private void logExceptionBeforeFailOver(Exception ex, MessageHandler handler, Message<?> message) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug((Object)("An exception was thrown by '" + handler + "' while handling '" + message + "'. Failing over to the next subscriber."), (Throwable)ex);
        } else if (this.logger.isInfoEnabled()) {
            this.logger.info((Object)("An exception was thrown by '" + handler + "' while handling '" + message + "': " + ex.getMessage() + ". Failing over to the next subscriber."));
        }
    }

    private void handleExceptions(List<RuntimeException> allExceptions, Message<?> message, boolean isLast) {
        if (isLast || !this.failover) {
            if (allExceptions.size() == 1) {
                throw allExceptions.get(0);
            }
            throw new AggregateMessageDeliveryException(message, "All attempts to deliver Message to MessageHandlers failed.", allExceptions);
        }
    }
}

