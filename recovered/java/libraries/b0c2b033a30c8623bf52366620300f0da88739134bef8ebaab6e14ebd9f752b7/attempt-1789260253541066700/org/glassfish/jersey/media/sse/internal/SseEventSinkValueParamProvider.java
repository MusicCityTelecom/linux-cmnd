/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.media.sse.internal;

import java.util.function.Function;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.core.Context;
import javax.ws.rs.sse.SseEventSink;
import org.glassfish.jersey.media.sse.internal.JerseyEventSink;
import org.glassfish.jersey.model.Parameter;
import org.glassfish.jersey.server.AsyncContext;
import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.internal.inject.AbstractValueParamProvider;
import org.glassfish.jersey.server.internal.inject.MultivaluedParameterExtractorProvider;
import org.glassfish.jersey.server.model.Parameter;

public class SseEventSinkValueParamProvider
extends AbstractValueParamProvider {
    private final Provider<AsyncContext> asyncContextSupplier;

    @Inject
    public SseEventSinkValueParamProvider(Provider<MultivaluedParameterExtractorProvider> mpep, Provider<AsyncContext> asyncContextSupplier) {
        super(mpep, Parameter.Source.CONTEXT);
        this.asyncContextSupplier = asyncContextSupplier;
    }

    protected Function<ContainerRequest, SseEventSink> createValueProvider(Parameter parameter) {
        if (parameter == null) {
            return null;
        }
        Class<?> rawParameterType = parameter.getRawType();
        if (rawParameterType == SseEventSink.class && parameter.isAnnotationPresent(Context.class)) {
            return new SseEventSinkValueSupplier(this.asyncContextSupplier);
        }
        return null;
    }

    private static final class SseEventSinkValueSupplier
    implements Function<ContainerRequest, SseEventSink> {
        private final Provider<AsyncContext> asyncContextSupplier;

        public SseEventSinkValueSupplier(Provider<AsyncContext> asyncContextSupplier) {
            this.asyncContextSupplier = asyncContextSupplier;
        }

        @Override
        public SseEventSink apply(ContainerRequest containerRequest) {
            return new JerseyEventSink(this.asyncContextSupplier);
        }
    }
}

