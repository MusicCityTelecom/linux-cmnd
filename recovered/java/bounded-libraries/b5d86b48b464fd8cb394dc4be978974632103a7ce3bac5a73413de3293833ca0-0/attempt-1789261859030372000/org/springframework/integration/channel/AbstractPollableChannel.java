/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.log.LogMessage
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.PollableChannel
 *  org.springframework.messaging.support.ChannelInterceptor
 *  org.springframework.messaging.support.ExecutorChannelInterceptor
 */
package org.springframework.integration.channel;

import java.util.ArrayDeque;
import java.util.List;
import org.springframework.core.log.LogMessage;
import org.springframework.integration.IntegrationPatternType;
import org.springframework.integration.channel.AbstractMessageChannel;
import org.springframework.integration.channel.ExecutorChannelInterceptorAware;
import org.springframework.integration.support.management.metrics.CounterFacade;
import org.springframework.integration.support.management.metrics.MetricsCaptor;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.PollableChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.ExecutorChannelInterceptor;

public abstract class AbstractPollableChannel
extends AbstractMessageChannel
implements PollableChannel,
ExecutorChannelInterceptorAware {
    private int executorInterceptorsSize;
    private CounterFacade receiveCounter;

    @Override
    public IntegrationPatternType getIntegrationPatternType() {
        return IntegrationPatternType.pollable_channel;
    }

    @Nullable
    public Message<?> receive() {
        return this.receive(-1L);
    }

    @Nullable
    public Message<?> receive(long timeout) {
        AbstractMessageChannel.ChannelInterceptorList interceptorList = this.getIChannelInterceptorList();
        ArrayDeque<ChannelInterceptor> interceptorStack = null;
        boolean counted = false;
        boolean traceEnabled = this.isLoggingEnabled() && this.logger.isTraceEnabled();
        try {
            if (traceEnabled) {
                this.logger.trace((CharSequence)("preReceive on channel '" + this + "'"));
            }
            if (interceptorList.getSize() > 0 && !interceptorList.preReceive(this, interceptorStack = new ArrayDeque<ChannelInterceptor>())) {
                return null;
            }
            Message<?> message = this.doReceive(timeout);
            if (message == null) {
                if (traceEnabled) {
                    this.logger.trace((CharSequence)("postReceive on channel '" + this + "', message is null"));
                }
            } else {
                this.incrementReceiveCounter();
                counted = true;
                this.logger.debug((CharSequence)LogMessage.format((String)"postReceive on channel '%s', message: %s", (Object)this, message));
            }
            if (interceptorStack != null && message != null) {
                message = interceptorList.postReceive(message, this);
            }
            interceptorList.afterReceiveCompletion(message, this, null, interceptorStack);
            return message;
        }
        catch (RuntimeException ex) {
            if (!counted) {
                this.incrementReceiveErrorCounter(ex);
            }
            interceptorList.afterReceiveCompletion(null, this, ex, interceptorStack);
            throw ex;
        }
    }

    private void incrementReceiveCounter() {
        MetricsCaptor metricsCaptor = this.getMetricsCaptor();
        if (metricsCaptor != null) {
            if (this.receiveCounter == null) {
                this.receiveCounter = this.buildReceiveCounter(metricsCaptor, null);
            }
            this.receiveCounter.increment();
        }
    }

    private void incrementReceiveErrorCounter(Exception ex) {
        MetricsCaptor metricsCaptor = this.getMetricsCaptor();
        if (metricsCaptor != null) {
            this.buildReceiveCounter(metricsCaptor, ex).increment();
        }
    }

    private CounterFacade buildReceiveCounter(MetricsCaptor metricsCaptor, @Nullable Exception ex) {
        CounterFacade counterFacade = metricsCaptor.counterBuilder("spring.integration.receive").tag("name", this.getComponentName() == null ? "unknown" : this.getComponentName()).tag("type", "channel").tag("result", ex == null ? "success" : "failure").tag("exception", ex == null ? "none" : ex.getClass().getSimpleName()).description("Messages received").build();
        this.meters.add(counterFacade);
        return counterFacade;
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

    @Nullable
    protected abstract Message<?> doReceive(long var1);
}

