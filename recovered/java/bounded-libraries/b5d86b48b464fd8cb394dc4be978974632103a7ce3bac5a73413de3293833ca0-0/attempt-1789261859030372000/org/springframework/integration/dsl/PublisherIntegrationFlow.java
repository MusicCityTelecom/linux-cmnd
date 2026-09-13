/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.reactivestreams.Subscriber
 *  org.springframework.messaging.Message
 *  reactor.core.publisher.Flux
 */
package org.springframework.integration.dsl;

import java.util.Map;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.springframework.integration.dsl.EndpointSpec;
import org.springframework.integration.dsl.StandardIntegrationFlow;
import org.springframework.messaging.Message;
import reactor.core.publisher.Flux;

class PublisherIntegrationFlow<T>
extends StandardIntegrationFlow
implements Publisher<Message<T>> {
    private final Publisher<Message<T>> delegate;

    PublisherIntegrationFlow(Map<Object, String> integrationComponents, Publisher<Message<T>> publisher, boolean autoStartOnSubscribe) {
        super(integrationComponents);
        Flux flux = Flux.from(publisher).doOnCancel(this::stop).doOnTerminate(this::stop);
        if (autoStartOnSubscribe) {
            flux = flux.doOnSubscribe(sub -> this.start());
            for (Object component : integrationComponents.keySet()) {
                if (!(component instanceof EndpointSpec)) continue;
                ((EndpointSpec)component).autoStartup(false);
            }
        }
        this.delegate = flux;
    }

    public void subscribe(Subscriber<? super Message<T>> subscriber) {
        this.delegate.subscribe(subscriber);
    }
}

