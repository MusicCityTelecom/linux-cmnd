/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Providers;
import org.glassfish.jersey.internal.BootstrapBag;
import org.glassfish.jersey.internal.BootstrapConfigurator;
import org.glassfish.jersey.internal.inject.Binding;
import org.glassfish.jersey.internal.inject.Bindings;
import org.glassfish.jersey.internal.inject.ClassBinding;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.internal.inject.PerLookup;
import org.glassfish.jersey.message.MessageBodyWorkers;
import org.glassfish.jersey.spi.ContextResolvers;
import org.glassfish.jersey.spi.ExceptionMappers;

public class JaxrsProviders
implements Providers {
    @Inject
    private Provider<MessageBodyWorkers> workers;
    @Inject
    private Provider<ContextResolvers> resolvers;
    @Inject
    private Provider<ExceptionMappers> mappers;

    @Override
    public <T> MessageBodyReader<T> getMessageBodyReader(Class<T> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return this.workers.get().getMessageBodyReader(type, genericType, annotations, mediaType);
    }

    @Override
    public <T> MessageBodyWriter<T> getMessageBodyWriter(Class<T> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return this.workers.get().getMessageBodyWriter(type, genericType, annotations, mediaType);
    }

    @Override
    public <T extends Throwable> ExceptionMapper<T> getExceptionMapper(Class<T> type) {
        ExceptionMappers actualMappers = this.mappers.get();
        return actualMappers != null ? actualMappers.find(type) : null;
    }

    @Override
    public <T> ContextResolver<T> getContextResolver(Class<T> contextType, MediaType mediaType) {
        return this.resolvers.get().resolve(contextType, mediaType);
    }

    public static class ProvidersConfigurator
    implements BootstrapConfigurator {
        @Override
        public void init(InjectionManager injectionManager, BootstrapBag bootstrapBag) {
            injectionManager.register((Binding)((ClassBinding)Bindings.service(JaxrsProviders.class).to(Providers.class)).in(PerLookup.class));
        }
    }
}

