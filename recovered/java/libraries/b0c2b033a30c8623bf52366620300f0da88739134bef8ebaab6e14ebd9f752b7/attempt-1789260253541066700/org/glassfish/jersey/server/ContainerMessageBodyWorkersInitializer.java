/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server;

import java.util.function.Function;
import javax.inject.Inject;
import javax.inject.Provider;
import org.glassfish.jersey.message.MessageBodyWorkers;
import org.glassfish.jersey.server.internal.process.RequestProcessingContext;

public class ContainerMessageBodyWorkersInitializer
implements Function<RequestProcessingContext, RequestProcessingContext> {
    private final Provider<MessageBodyWorkers> workersFactory;

    @Inject
    public ContainerMessageBodyWorkersInitializer(Provider<MessageBodyWorkers> workersFactory) {
        this.workersFactory = workersFactory;
    }

    @Override
    public RequestProcessingContext apply(RequestProcessingContext requestContext) {
        requestContext.request().setWorkers(this.workersFactory.get());
        return requestContext;
    }
}

