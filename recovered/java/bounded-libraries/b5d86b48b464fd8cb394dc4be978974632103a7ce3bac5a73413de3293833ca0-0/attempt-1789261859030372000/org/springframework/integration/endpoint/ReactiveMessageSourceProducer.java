/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.springframework.messaging.Message
 *  org.springframework.util.Assert
 *  reactor.core.publisher.Flux
 */
package org.springframework.integration.endpoint;

import java.time.Duration;
import org.reactivestreams.Publisher;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.util.IntegrationReactiveUtils;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;

public class ReactiveMessageSourceProducer
extends MessageProducerSupport {
    private final Flux<? extends Message<?>> messageFlux;
    private Duration delayWhenEmpty = IntegrationReactiveUtils.DEFAULT_DELAY_WHEN_EMPTY;

    public ReactiveMessageSourceProducer(MessageSource<?> messageSource) {
        Assert.notNull(messageSource, (String)"'messageSource' must not be null");
        this.messageFlux = IntegrationReactiveUtils.messageSourceToFlux(messageSource).contextWrite(ctx -> ctx.put((Object)"DELAY_WHEN_EMPTY_KEY", (Object)this.delayWhenEmpty));
    }

    public void setDelayWhenEmpty(Duration delayWhenEmpty) {
        Assert.notNull((Object)delayWhenEmpty, (String)"'delayWhenEmpty' must not be null");
        this.delayWhenEmpty = delayWhenEmpty;
    }

    @Override
    protected void doStart() {
        this.subscribeToPublisher((Publisher<? extends Message<?>>)this.messageFlux);
    }
}

