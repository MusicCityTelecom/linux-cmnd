/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscriber
 *  org.springframework.context.Lifecycle
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.MessageDeliveryException
 *  org.springframework.messaging.MessageHandler
 *  org.springframework.messaging.PollableChannel
 *  org.springframework.messaging.support.ChannelInterceptor
 *  org.springframework.messaging.support.ExecutorChannelInterceptor
 *  org.springframework.util.Assert
 *  org.springframework.util.CollectionUtils
 */
package org.springframework.integration.endpoint;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import org.reactivestreams.Subscriber;
import org.springframework.context.Lifecycle;
import org.springframework.integration.channel.ExecutorChannelInterceptorAware;
import org.springframework.integration.channel.NullChannel;
import org.springframework.integration.channel.ReactiveStreamsSubscribableChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.endpoint.AbstractPollingEndpoint;
import org.springframework.integration.endpoint.IntegrationConsumer;
import org.springframework.integration.router.MessageRouter;
import org.springframework.integration.support.utils.IntegrationUtils;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.PollableChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.ExecutorChannelInterceptor;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

public class PollingConsumer
extends AbstractPollingEndpoint
implements IntegrationConsumer {
    public static final long DEFAULT_RECEIVE_TIMEOUT = 1000L;
    private final MessageHandler handler;
    private final List<ChannelInterceptor> channelInterceptors;
    private PollableChannel inputChannel;
    private long receiveTimeout = 1000L;

    public PollingConsumer(PollableChannel inputChannel, MessageHandler handler) {
        Assert.notNull((Object)inputChannel, (String)"inputChannel must not be null");
        Assert.notNull((Object)handler, (String)"handler must not be null");
        if (inputChannel instanceof NullChannel) {
            this.logger.warn((CharSequence)"The polling from the NullChannel does not have any effects: it doesn't forward messages sent to it. A NullChannel is the end of the flow.");
        }
        this.inputChannel = inputChannel;
        this.handler = handler;
        this.channelInterceptors = this.inputChannel instanceof ExecutorChannelInterceptorAware ? ((ExecutorChannelInterceptorAware)this.inputChannel).getInterceptors() : null;
    }

    public void setReceiveTimeout(long receiveTimeout) {
        this.receiveTimeout = receiveTimeout;
    }

    @Override
    public MessageChannel getInputChannel() {
        return this.inputChannel;
    }

    @Override
    public MessageChannel getOutputChannel() {
        if (this.handler instanceof MessageProducer) {
            return ((MessageProducer)this.handler).getOutputChannel();
        }
        if (this.handler instanceof MessageRouter) {
            return ((MessageRouter)this.handler).getDefaultOutputChannel();
        }
        return null;
    }

    @Override
    public MessageHandler getHandler() {
        return this.handler;
    }

    @Override
    protected Object getReceiveMessageSource() {
        return this.inputChannel;
    }

    @Override
    protected void setReceiveMessageSource(Object source) {
        this.inputChannel = (PollableChannel)source;
    }

    @Override
    protected boolean isReactive() {
        return this.getOutputChannel() instanceof ReactiveStreamsSubscribableChannel && this.handler instanceof Subscriber;
    }

    @Override
    protected void doStart() {
        if (this.handler instanceof Lifecycle) {
            ((Lifecycle)this.handler).start();
        }
        super.doStart();
    }

    @Override
    protected void doStop() {
        if (this.handler instanceof Lifecycle) {
            ((Lifecycle)this.handler).stop();
        }
        super.doStop();
    }

    @Override
    protected void handleMessage(Message<?> message) {
        Message<?> theMessage = message;
        ArrayDeque<ExecutorChannelInterceptor> interceptorStack = null;
        try {
            if (this.channelInterceptors != null && ((ExecutorChannelInterceptorAware)this.inputChannel).hasExecutorInterceptors() && (theMessage = this.applyBeforeHandle(theMessage, interceptorStack = new ArrayDeque<ExecutorChannelInterceptor>())) == null) {
                return;
            }
            this.handler.handleMessage(theMessage);
            if (!CollectionUtils.isEmpty(interceptorStack)) {
                this.triggerAfterMessageHandled(theMessage, null, interceptorStack);
            }
        }
        catch (Exception ex) {
            if (!CollectionUtils.isEmpty(interceptorStack)) {
                this.triggerAfterMessageHandled(theMessage, ex, interceptorStack);
            }
            throw IntegrationUtils.wrapInDeliveryExceptionIfNecessary(theMessage, () -> "Failed to handle message to " + this + " in " + this.handler, ex);
        }
        catch (Error ex) {
            if (!CollectionUtils.isEmpty(interceptorStack)) {
                String description = "Failed to handle message to " + this + " in " + this.handler;
                this.triggerAfterMessageHandled(theMessage, (Exception)((Object)new MessageDeliveryException(theMessage, description, (Throwable)ex)), interceptorStack);
            }
            throw ex;
        }
    }

    private Message<?> applyBeforeHandle(Message<?> message, Deque<ExecutorChannelInterceptor> interceptorStack) {
        Message theMessage = message;
        for (ChannelInterceptor interceptor : this.channelInterceptors) {
            if (!(interceptor instanceof ExecutorChannelInterceptor)) continue;
            ExecutorChannelInterceptor executorInterceptor = (ExecutorChannelInterceptor)interceptor;
            if ((theMessage = executorInterceptor.beforeHandle(theMessage, (MessageChannel)this.inputChannel, this.handler)) == null) {
                this.logger.debug(() -> executorInterceptor.getClass().getSimpleName() + " returned null from beforeHandle, i.e. precluding the send.");
                this.triggerAfterMessageHandled(null, null, interceptorStack);
                return null;
            }
            interceptorStack.add(executorInterceptor);
        }
        return theMessage;
    }

    private void triggerAfterMessageHandled(Message<?> message, Exception ex, Deque<ExecutorChannelInterceptor> interceptorStack) {
        Iterator<ExecutorChannelInterceptor> iterator = interceptorStack.descendingIterator();
        while (iterator.hasNext()) {
            ExecutorChannelInterceptor interceptor = iterator.next();
            try {
                interceptor.afterMessageHandled(message, (MessageChannel)this.inputChannel, this.handler, ex);
            }
            catch (Throwable ex2) {
                this.logger.error(ex2, () -> "Exception from afterMessageHandled in " + interceptor);
            }
        }
    }

    @Override
    protected Message<?> receiveMessage() {
        return this.receiveTimeout >= 0L ? this.inputChannel.receive(this.receiveTimeout) : this.inputChannel.receive();
    }

    @Override
    protected Object getResourceToBind() {
        return this.inputChannel;
    }

    @Override
    protected String getResourceKey() {
        return "inputChannel";
    }
}

