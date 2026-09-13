/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.MessageDeliveryException
 *  org.springframework.messaging.MessageHandler
 *  org.springframework.messaging.MessagingException
 *  org.springframework.messaging.support.ChannelInterceptor
 *  org.springframework.messaging.support.ExecutorChannelInterceptor
 *  org.springframework.messaging.support.MessageHandlingRunnable
 *  org.springframework.util.Assert
 *  org.springframework.util.CollectionUtils
 */
package org.springframework.integration.channel;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import org.springframework.integration.IntegrationPatternType;
import org.springframework.integration.channel.AbstractSubscribableChannel;
import org.springframework.integration.channel.ExecutorChannelInterceptorAware;
import org.springframework.integration.dispatcher.AbstractDispatcher;
import org.springframework.integration.support.MessagingExceptionWrapper;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.ExecutorChannelInterceptor;
import org.springframework.messaging.support.MessageHandlingRunnable;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

public abstract class AbstractExecutorChannel
extends AbstractSubscribableChannel
implements ExecutorChannelInterceptorAware {
    protected Executor executor;
    protected AbstractDispatcher dispatcher;
    protected Integer maxSubscribers;
    protected int executorInterceptorsSize;

    public AbstractExecutorChannel(@Nullable Executor executor) {
        this.executor = executor;
    }

    public void setMaxSubscribers(int maxSubscribers) {
        this.maxSubscribers = maxSubscribers;
        this.dispatcher.setMaxSubscribers(maxSubscribers);
    }

    @Override
    public void setInterceptors(List<ChannelInterceptor> interceptors) {
        super.setInterceptors(interceptors);
        for (ChannelInterceptor interceptor : interceptors) {
            if (!(interceptor instanceof ExecutorChannelInterceptor)) continue;
            ++this.executorInterceptorsSize;
        }
    }

    @Override
    public void addInterceptor(ChannelInterceptor interceptor) {
        super.addInterceptor(interceptor);
        if (interceptor instanceof ExecutorChannelInterceptor) {
            ++this.executorInterceptorsSize;
        }
    }

    @Override
    public void addInterceptor(int index, ChannelInterceptor interceptor) {
        super.addInterceptor(index, interceptor);
        if (interceptor instanceof ExecutorChannelInterceptor) {
            ++this.executorInterceptorsSize;
        }
    }

    @Override
    public boolean removeInterceptor(ChannelInterceptor interceptor) {
        boolean removed = super.removeInterceptor(interceptor);
        if (removed && interceptor instanceof ExecutorChannelInterceptor) {
            --this.executorInterceptorsSize;
        }
        return removed;
    }

    @Override
    @Nullable
    public ChannelInterceptor removeInterceptor(int index) {
        ChannelInterceptor interceptor = super.removeInterceptor(index);
        if (interceptor instanceof ExecutorChannelInterceptor) {
            --this.executorInterceptorsSize;
        }
        return interceptor;
    }

    @Override
    public boolean hasExecutorInterceptors() {
        return this.executorInterceptorsSize > 0;
    }

    @Override
    public IntegrationPatternType getIntegrationPatternType() {
        return IntegrationPatternType.executor_channel;
    }

    protected class MessageHandlingTask
    implements Runnable {
        private final MessageHandlingRunnable delegate;

        public MessageHandlingTask(MessageHandlingRunnable task) {
            this.delegate = task;
        }

        @Override
        public void run() {
            Message<?> message = this.delegate.getMessage();
            MessageHandler messageHandler = this.delegate.getMessageHandler();
            Assert.notNull((Object)messageHandler, (String)"'messageHandler' must not be null");
            ArrayDeque<ExecutorChannelInterceptor> interceptorStack = null;
            try {
                if (AbstractExecutorChannel.this.executorInterceptorsSize > 0 && (message = this.applyBeforeHandle(message, interceptorStack = new ArrayDeque<ExecutorChannelInterceptor>())) == null) {
                    return;
                }
                messageHandler.handleMessage((Message)message);
                if (!CollectionUtils.isEmpty(interceptorStack)) {
                    this.triggerAfterMessageHandled(message, null, interceptorStack);
                }
            }
            catch (Exception ex) {
                if (!CollectionUtils.isEmpty(interceptorStack)) {
                    this.triggerAfterMessageHandled(message, ex, interceptorStack);
                }
                if (ex instanceof MessagingException) {
                    throw new MessagingExceptionWrapper(message, (MessagingException)ex);
                }
                String description = "Failed to handle " + message + " to " + this + " in " + messageHandler;
                throw new MessageDeliveryException(message, description, (Throwable)ex);
            }
            catch (Error ex) {
                if (!CollectionUtils.isEmpty(interceptorStack)) {
                    String description = "Failed to handle " + message + " to " + this + " in " + messageHandler;
                    this.triggerAfterMessageHandled(message, (Exception)((Object)new MessageDeliveryException(message, description, (Throwable)ex)), interceptorStack);
                }
                throw ex;
            }
        }

        @Nullable
        private Message<?> applyBeforeHandle(Message<?> message, Deque<ExecutorChannelInterceptor> interceptorStack) {
            Message theMessage = message;
            for (ChannelInterceptor interceptor : AbstractExecutorChannel.this.interceptors.interceptors) {
                if (!(interceptor instanceof ExecutorChannelInterceptor)) continue;
                ExecutorChannelInterceptor executorInterceptor = (ExecutorChannelInterceptor)interceptor;
                if ((theMessage = executorInterceptor.beforeHandle(theMessage, (MessageChannel)AbstractExecutorChannel.this, this.delegate.getMessageHandler())) == null) {
                    if (AbstractExecutorChannel.this.isLoggingEnabled()) {
                        AbstractExecutorChannel.this.logger.debug(() -> executorInterceptor.getClass().getSimpleName() + " returned null from beforeHandle, i.e. precluding the send.");
                    }
                    this.triggerAfterMessageHandled(null, null, interceptorStack);
                    return null;
                }
                interceptorStack.add(executorInterceptor);
            }
            return theMessage;
        }

        private void triggerAfterMessageHandled(@Nullable Message<?> message, @Nullable Exception ex, Deque<ExecutorChannelInterceptor> interceptorStack) {
            Iterator<ExecutorChannelInterceptor> iterator = interceptorStack.descendingIterator();
            while (iterator.hasNext()) {
                ExecutorChannelInterceptor interceptor = iterator.next();
                try {
                    interceptor.afterMessageHandled(message, (MessageChannel)AbstractExecutorChannel.this, this.delegate.getMessageHandler(), ex);
                }
                catch (Throwable ex2) {
                    AbstractExecutorChannel.this.logger.error(ex2, () -> "Exception from afterMessageHandled in " + interceptor);
                }
            }
        }
    }
}

