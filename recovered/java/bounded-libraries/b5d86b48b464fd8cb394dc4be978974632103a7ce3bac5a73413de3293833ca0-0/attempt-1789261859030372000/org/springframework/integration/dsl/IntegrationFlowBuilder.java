/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.dsl;

import org.reactivestreams.Publisher;
import org.springframework.integration.dsl.IntegrationFlowDefinition;
import org.springframework.integration.dsl.StandardIntegrationFlow;
import org.springframework.messaging.Message;

public final class IntegrationFlowBuilder
extends IntegrationFlowDefinition<IntegrationFlowBuilder> {
    IntegrationFlowBuilder() {
    }

    @Override
    public StandardIntegrationFlow get() {
        return super.get();
    }

    @Override
    public <T> Publisher<Message<T>> toReactivePublisher() {
        return super.toReactivePublisher();
    }

    @Override
    public <T> Publisher<Message<T>> toReactivePublisher(boolean autoStartOnSubscribe) {
        return super.toReactivePublisher(autoStartOnSubscribe);
    }
}

