/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.MessageHandler
 *  org.springframework.messaging.MessageHeaders
 *  org.springframework.messaging.support.ExecutorChannelInterceptor
 */
package org.springframework.integration.channel.interceptor;

import org.springframework.integration.support.MessageDecorator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.ExecutorChannelInterceptor;

public abstract class ThreadStatePropagationChannelInterceptor<S>
implements ExecutorChannelInterceptor {
    public final Message<?> preSend(Message<?> message, MessageChannel channel) {
        S threadContext = this.obtainPropagatingContext(message, channel);
        if (threadContext != null) {
            return new MessageWithThreadState<S>(message, threadContext);
        }
        return message;
    }

    public final Message<?> postReceive(Message<?> message, MessageChannel channel) {
        if (message instanceof MessageWithThreadState) {
            MessageWithThreadState messageWithThreadState = (MessageWithThreadState)message;
            Message messageToHandle = messageWithThreadState.message;
            this.populatePropagatedContext(messageWithThreadState.state, messageToHandle, channel);
            return messageToHandle;
        }
        return message;
    }

    public final Message<?> beforeHandle(Message<?> message, MessageChannel channel, MessageHandler handler) {
        return this.postReceive(message, channel);
    }

    public void afterMessageHandled(Message<?> message, MessageChannel channel, MessageHandler handler, Exception ex) {
    }

    protected abstract S obtainPropagatingContext(Message<?> var1, MessageChannel var2);

    protected abstract void populatePropagatedContext(S var1, Message<?> var2, MessageChannel var3);

    private static final class MessageWithThreadState<S>
    implements Message<Object>,
    MessageDecorator {
        private final Message<Object> message;
        private final S state;

        MessageWithThreadState(Message<?> message, S state) {
            this.message = message;
            this.state = state;
        }

        public Object getPayload() {
            return this.message.getPayload();
        }

        public MessageHeaders getHeaders() {
            return this.message.getHeaders();
        }

        @Override
        public Message<?> decorateMessage(Message<?> message) {
            return new MessageWithThreadState<S>(message, this.state);
        }

        public String toString() {
            return "MessageWithThreadState{message=" + this.message + ", state=" + this.state + '}';
        }
    }
}

