/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 *  reactor.util.function.Tuple2
 */
package org.springframework.integration.dsl;

import org.springframework.integration.aggregator.BarrierMessageHandler;
import org.springframework.integration.aggregator.CorrelationStrategy;
import org.springframework.integration.aggregator.DefaultAggregatingMessageGroupProcessor;
import org.springframework.integration.aggregator.HeaderAttributeCorrelationStrategy;
import org.springframework.integration.aggregator.MessageGroupProcessor;
import org.springframework.integration.config.ConsumerEndpointFactoryBean;
import org.springframework.integration.dsl.ConsumerEndpointSpec;
import org.springframework.util.Assert;
import reactor.util.function.Tuple2;

public class BarrierSpec
extends ConsumerEndpointSpec<BarrierSpec, BarrierMessageHandler> {
    private final long timeout;
    private MessageGroupProcessor outputProcessor = new DefaultAggregatingMessageGroupProcessor();
    private CorrelationStrategy correlationStrategy = new HeaderAttributeCorrelationStrategy("correlationId");
    private boolean requiresReply;
    private long sendTimeout = -1L;
    private int order = Integer.MAX_VALUE;
    private boolean async;

    protected BarrierSpec(long timeout) {
        super(null);
        this.timeout = timeout;
    }

    public BarrierSpec outputProcessor(MessageGroupProcessor outputProcessor) {
        Assert.notNull((Object)outputProcessor, (String)"'outputProcessor' must not be null.");
        this.outputProcessor = outputProcessor;
        return this;
    }

    public BarrierSpec correlationStrategy(CorrelationStrategy correlationStrategy) {
        Assert.notNull((Object)correlationStrategy, (String)"'correlationStrategy' must not be null.");
        this.correlationStrategy = correlationStrategy;
        return this;
    }

    @Override
    public BarrierSpec requiresReply(boolean requiresReply) {
        this.requiresReply = requiresReply;
        return this;
    }

    @Override
    public BarrierSpec sendTimeout(long sendTimeout) {
        this.sendTimeout = sendTimeout;
        return this;
    }

    @Override
    public BarrierSpec order(int order) {
        this.order = order;
        return this;
    }

    @Override
    public BarrierSpec async(boolean async) {
        this.async = async;
        return this;
    }

    @Override
    public Tuple2<ConsumerEndpointFactoryBean, BarrierMessageHandler> doGet() {
        this.handler = new BarrierMessageHandler(this.timeout, this.outputProcessor, this.correlationStrategy);
        if (!this.adviceChain.isEmpty()) {
            ((BarrierMessageHandler)this.handler).setAdviceChain(this.adviceChain);
        }
        ((BarrierMessageHandler)this.handler).setRequiresReply(this.requiresReply);
        ((BarrierMessageHandler)this.handler).setSendTimeout(this.sendTimeout);
        ((BarrierMessageHandler)this.handler).setAsync(this.async);
        ((BarrierMessageHandler)this.handler).setOrder(this.order);
        return super.doGet();
    }
}

