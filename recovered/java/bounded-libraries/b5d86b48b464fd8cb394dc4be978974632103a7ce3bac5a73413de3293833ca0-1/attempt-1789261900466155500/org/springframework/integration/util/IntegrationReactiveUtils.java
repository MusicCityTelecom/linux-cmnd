/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.reactivestreams.Publisher
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.MessageHandler
 *  org.springframework.messaging.MessagingException
 *  org.springframework.messaging.PollableChannel
 *  org.springframework.messaging.SubscribableChannel
 *  reactor.core.publisher.Flux
 *  reactor.core.publisher.Mono
 *  reactor.core.publisher.Sinks
 *  reactor.core.publisher.Sinks$Many
 *  reactor.core.scheduler.Schedulers
 *  reactor.util.retry.Retry
 */
package org.springframework.integration.util;

import java.time.Duration;
import java.util.concurrent.locks.LockSupport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.reactivestreams.Publisher;
import org.springframework.integration.StaticMessageHeaderAccessor;
import org.springframework.integration.acks.AckUtils;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.PollableChannel;
import org.springframework.messaging.SubscribableChannel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

public final class IntegrationReactiveUtils {
    private static final Log LOGGER = LogFactory.getLog(IntegrationReactiveUtils.class);
    public static final String DELAY_WHEN_EMPTY_KEY = "DELAY_WHEN_EMPTY_KEY";
    public static final Duration DEFAULT_DELAY_WHEN_EMPTY = Duration.ofSeconds(1L);

    private IntegrationReactiveUtils() {
    }

    public static <T> Flux<Message<T>> messageSourceToFlux(MessageSource<T> messageSource) {
        return Mono.create(monoSink -> monoSink.onRequest(value -> monoSink.success(messageSource.receive()))).doOnSuccess(message -> {
            if (message != null) {
                AckUtils.autoAck(StaticMessageHeaderAccessor.getAcknowledgmentCallback(message));
            }
        }).doOnError(MessagingException.class, ex -> {
            Message failedMessage = ex.getFailedMessage();
            if (failedMessage != null) {
                AckUtils.autoNack(StaticMessageHeaderAccessor.getAcknowledgmentCallback(failedMessage));
            }
            LOGGER.error((Object)("Error from Flux for : " + messageSource), (Throwable)ex);
        }).subscribeOn(Schedulers.boundedElastic()).repeatWhenEmpty(repeat -> repeat.flatMap(increment -> Mono.deferContextual(ctx -> Mono.delay((Duration)((Duration)ctx.getOrDefault((Object)DELAY_WHEN_EMPTY_KEY, (Object)DEFAULT_DELAY_WHEN_EMPTY)))))).repeat().retryWhen((Retry)Retry.indefinitely().filter(MessagingException.class::isInstance));
    }

    public static <T> Flux<Message<T>> messageChannelToFlux(MessageChannel messageChannel) {
        if (messageChannel instanceof Publisher) {
            return Flux.from((Publisher)((Publisher)messageChannel));
        }
        if (messageChannel instanceof SubscribableChannel) {
            return IntegrationReactiveUtils.adaptSubscribableChannelToPublisher((SubscribableChannel)messageChannel);
        }
        if (messageChannel instanceof PollableChannel) {
            return IntegrationReactiveUtils.messageSourceToFlux(() -> ((PollableChannel)messageChannel).receive(0L));
        }
        throw new IllegalArgumentException("The 'messageChannel' must be an instance of Publisher, SubscribableChannel or PollableChannel, not: " + messageChannel);
    }

    private static <T> Flux<Message<T>> adaptSubscribableChannelToPublisher(SubscribableChannel inputChannel) {
        return Flux.defer(() -> {
            Sinks.Many sink = Sinks.many().unicast().onBackpressureError();
            MessageHandler messageHandler = message -> {
                block5: while (true) {
                    switch (sink.tryEmitNext((Object)message)) {
                        case FAIL_NON_SERIALIZED: 
                        case FAIL_OVERFLOW: {
                            LockSupport.parkNanos(1000L);
                            continue block5;
                        }
                        case FAIL_ZERO_SUBSCRIBER: {
                            throw new IllegalStateException("The [" + sink + "] doesn't have subscribers to accept messages");
                        }
                        case FAIL_TERMINATED: 
                        case FAIL_CANCELLED: {
                            throw new IllegalStateException("Cannot emit messages into the cancelled or terminated sink for message channel: " + inputChannel);
                        }
                    }
                    break;
                }
            };
            inputChannel.subscribe(messageHandler);
            return sink.asFlux().doOnCancel(() -> inputChannel.unsubscribe(messageHandler));
        });
    }
}

