/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 */
package org.springframework.messaging.support;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.AbstractSubscribableChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.ExecutorChannelInterceptor;
import org.springframework.messaging.support.MessageHandlingRunnable;

public class ExecutorSubscribableChannel
extends AbstractSubscribableChannel {
    @Nullable
    private final Executor executor;
    private final List<ExecutorChannelInterceptor> executorInterceptors = new ArrayList<ExecutorChannelInterceptor>(4);

    public ExecutorSubscribableChannel() {
        this(null);
    }

    public ExecutorSubscribableChannel(@Nullable Executor executor) {
        this.executor = executor;
    }

    @Nullable
    public Executor getExecutor() {
        return this.executor;
    }

    @Override
    public void setInterceptors(List<ChannelInterceptor> interceptors) {
        super.setInterceptors(interceptors);
        this.executorInterceptors.clear();
        interceptors.forEach(this::updateExecutorInterceptorsFor);
    }

    @Override
    public void addInterceptor(ChannelInterceptor interceptor) {
        super.addInterceptor(interceptor);
        this.updateExecutorInterceptorsFor(interceptor);
    }

    @Override
    public void addInterceptor(int index, ChannelInterceptor interceptor) {
        super.addInterceptor(index, interceptor);
        this.updateExecutorInterceptorsFor(interceptor);
    }

    private void updateExecutorInterceptorsFor(ChannelInterceptor interceptor) {
        if (interceptor instanceof ExecutorChannelInterceptor) {
            this.executorInterceptors.add((ExecutorChannelInterceptor)interceptor);
        }
    }

    @Override
    public boolean sendInternal(Message<?> message, long timeout) {
        for (MessageHandler handler : this.getSubscribers()) {
            SendTask sendTask = new SendTask(message, handler);
            if (this.executor == null) {
                sendTask.run();
                continue;
            }
            this.executor.execute(sendTask);
        }
        return true;
    }

    private class SendTask
    implements MessageHandlingRunnable {
        private final Message<?> inputMessage;
        private final MessageHandler messageHandler;
        private int interceptorIndex = -1;

        public SendTask(Message<?> message, MessageHandler messageHandler) {
            this.inputMessage = message;
            this.messageHandler = messageHandler;
        }

        @Override
        public Message<?> getMessage() {
            return this.inputMessage;
        }

        @Override
        public MessageHandler getMessageHandler() {
            return this.messageHandler;
        }

        @Override
        public void run() {
            Message<?> message = this.inputMessage;
            try {
                message = this.applyBeforeHandle(message);
                if (message == null) {
                    return;
                }
                this.messageHandler.handleMessage(message);
                this.triggerAfterMessageHandled(message, null);
            }
            catch (Exception ex) {
                this.triggerAfterMessageHandled(message, ex);
                if (ex instanceof MessagingException) {
                    throw (MessagingException)((Object)ex);
                }
                String description = "Failed to handle " + message + " to " + this + " in " + this.messageHandler;
                throw new MessageDeliveryException(message, description, ex);
            }
            catch (Throwable err) {
                String description = "Failed to handle " + message + " to " + this + " in " + this.messageHandler;
                MessageDeliveryException ex2 = new MessageDeliveryException(message, description, err);
                this.triggerAfterMessageHandled(message, (Exception)((Object)ex2));
                throw ex2;
            }
        }

        @Nullable
        private Message<?> applyBeforeHandle(Message<?> message) {
            Message<?> messageToUse = message;
            for (ExecutorChannelInterceptor interceptor : ExecutorSubscribableChannel.this.executorInterceptors) {
                messageToUse = interceptor.beforeHandle(messageToUse, ExecutorSubscribableChannel.this, this.messageHandler);
                if (messageToUse == null) {
                    String name = interceptor.getClass().getSimpleName();
                    if (ExecutorSubscribableChannel.this.logger.isDebugEnabled()) {
                        ExecutorSubscribableChannel.this.logger.debug((Object)(name + " returned null from beforeHandle, i.e. precluding the send."));
                    }
                    this.triggerAfterMessageHandled(message, null);
                    return null;
                }
                ++this.interceptorIndex;
            }
            return messageToUse;
        }

        private void triggerAfterMessageHandled(Message<?> message, @Nullable Exception ex) {
            for (int i2 = this.interceptorIndex; i2 >= 0; --i2) {
                ExecutorChannelInterceptor interceptor = (ExecutorChannelInterceptor)ExecutorSubscribableChannel.this.executorInterceptors.get(i2);
                try {
                    interceptor.afterMessageHandled(message, ExecutorSubscribableChannel.this, this.messageHandler, ex);
                    continue;
                }
                catch (Throwable ex2) {
                    ExecutorSubscribableChannel.this.logger.error((Object)("Exception from afterMessageHandled in " + interceptor), ex2);
                }
            }
        }
    }
}

