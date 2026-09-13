/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.ReactiveMessageHandler
 */
package org.springframework.integration.dsl;

import java.util.Collections;
import java.util.Map;
import org.springframework.integration.dsl.ComponentsRegistration;
import org.springframework.integration.dsl.MessageHandlerSpec;
import org.springframework.integration.handler.ReactiveMessageHandlerAdapter;
import org.springframework.messaging.ReactiveMessageHandler;

public abstract class ReactiveMessageHandlerSpec<S extends ReactiveMessageHandlerSpec<S, H>, H extends ReactiveMessageHandler>
extends MessageHandlerSpec<S, ReactiveMessageHandlerAdapter>
implements ComponentsRegistration {
    protected final H reactiveMessageHandler;

    protected ReactiveMessageHandlerSpec(H reactiveMessageHandler) {
        this.reactiveMessageHandler = reactiveMessageHandler;
        this.target = new ReactiveMessageHandlerAdapter((ReactiveMessageHandler)this.reactiveMessageHandler);
    }

    @Override
    public Map<Object, String> getComponentsToRegister() {
        return Collections.singletonMap(this.reactiveMessageHandler, null);
    }
}

