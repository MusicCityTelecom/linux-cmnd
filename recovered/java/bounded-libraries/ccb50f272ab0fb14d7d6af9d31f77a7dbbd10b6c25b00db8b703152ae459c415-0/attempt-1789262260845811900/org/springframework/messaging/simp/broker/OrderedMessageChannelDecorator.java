/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 */
package org.springframework.messaging.simp.broker;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.commons.logging.Log;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.support.ExecutorChannelInterceptor;
import org.springframework.messaging.support.ExecutorSubscribableChannel;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.util.Assert;

public class OrderedMessageChannelDecorator
implements MessageChannel {
    private static final String NEXT_MESSAGE_TASK_HEADER = "simpNextMessageTask";
    private final MessageChannel channel;
    private final Log logger;
    private final Queue<Message<?>> messages = new ConcurrentLinkedQueue();
    private final AtomicBoolean sendInProgress = new AtomicBoolean();

    public OrderedMessageChannelDecorator(MessageChannel channel, Log logger) {
        this.channel = channel;
        this.logger = logger;
    }

    @Override
    public boolean send(Message<?> message) {
        return this.send(message, -1L);
    }

    @Override
    public boolean send(Message<?> message, long timeout) {
        this.messages.add(message);
        this.trySend();
        return true;
    }

    private void trySend() {
        if (this.messages.isEmpty()) {
            return;
        }
        if (this.sendInProgress.compareAndSet(false, true)) {
            this.sendNextMessage();
        }
    }

    private void sendNextMessage() {
        Message<?> message;
        while ((message = this.messages.peek()) != null) {
            block4: {
                try {
                    OrderedMessageChannelDecorator.addNextMessageTaskHeader(message, () -> {
                        if (this.removeMessage(message)) {
                            this.sendNextMessage();
                        }
                    });
                    if (this.channel.send(message)) {
                        return;
                    }
                }
                catch (Throwable ex) {
                    if (!this.logger.isErrorEnabled()) break block4;
                    this.logger.error((Object)("Failed to send " + message), ex);
                }
            }
            this.removeMessage(message);
        }
        this.sendInProgress.set(false);
        this.trySend();
    }

    private boolean removeMessage(Message<?> message) {
        Message<?> next = this.messages.peek();
        if (next == message) {
            this.messages.remove();
            return true;
        }
        return false;
    }

    private static void addNextMessageTaskHeader(Message<?> message, Runnable task) {
        SimpMessageHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, SimpMessageHeaderAccessor.class);
        Assert.isTrue((accessor != null && accessor.isMutable() ? 1 : 0) != 0, (String)"Expected mutable SimpMessageHeaderAccessor");
        accessor.setHeader(NEXT_MESSAGE_TASK_HEADER, task);
    }

    @Nullable
    public static Runnable getNextMessageTask(Message<?> message) {
        return (Runnable)message.getHeaders().get(NEXT_MESSAGE_TASK_HEADER);
    }

    public static void configureInterceptor(MessageChannel channel, boolean preserveOrder) {
        if (preserveOrder) {
            Assert.isInstanceOf(ExecutorSubscribableChannel.class, (Object)channel, (String)"An ExecutorSubscribableChannel is required for `preservePublishOrder`");
            ExecutorSubscribableChannel execChannel = (ExecutorSubscribableChannel)channel;
            if (execChannel.getInterceptors().stream().noneMatch(i2 -> i2 instanceof CallbackInterceptor)) {
                execChannel.addInterceptor(0, new CallbackInterceptor());
            }
        } else if (channel instanceof ExecutorSubscribableChannel) {
            ExecutorSubscribableChannel execChannel = (ExecutorSubscribableChannel)channel;
            execChannel.getInterceptors().stream().filter(i2 -> i2 instanceof CallbackInterceptor).findFirst().map(execChannel::removeInterceptor);
        }
    }

    private static class CallbackInterceptor
    implements ExecutorChannelInterceptor {
        private CallbackInterceptor() {
        }

        @Override
        public void afterMessageHandled(Message<?> message, MessageChannel ch, MessageHandler handler, @Nullable Exception ex) {
            Runnable task = OrderedMessageChannelDecorator.getNextMessageTask(message);
            if (task != null) {
                task.run();
            }
        }
    }
}

