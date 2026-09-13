/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client;

import java.util.Collections;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.inject.Provider;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.WriterInterceptor;
import org.glassfish.jersey.client.ClientRequest;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.internal.inject.Providers;
import org.glassfish.jersey.internal.util.collection.Ref;
import org.glassfish.jersey.message.MessageBodyWorkers;
import org.glassfish.jersey.model.internal.RankedComparator;

public class RequestProcessingInitializationStage
implements Function<ClientRequest, ClientRequest> {
    private final Provider<Ref<ClientRequest>> requestRefProvider;
    private final MessageBodyWorkers workersProvider;
    private final Iterable<WriterInterceptor> writerInterceptors;
    private final Iterable<ReaderInterceptor> readerInterceptors;

    public RequestProcessingInitializationStage(Provider<Ref<ClientRequest>> requestRefProvider, MessageBodyWorkers workersProvider, InjectionManager injectionManager) {
        this.requestRefProvider = requestRefProvider;
        this.workersProvider = workersProvider;
        this.writerInterceptors = Collections.unmodifiableList(StreamSupport.stream(Providers.getAllProviders(injectionManager, WriterInterceptor.class, new RankedComparator()).spliterator(), false).collect(Collectors.toList()));
        this.readerInterceptors = Collections.unmodifiableList(StreamSupport.stream(Providers.getAllProviders(injectionManager, ReaderInterceptor.class, new RankedComparator()).spliterator(), false).collect(Collectors.toList()));
    }

    @Override
    public ClientRequest apply(ClientRequest requestContext) {
        this.requestRefProvider.get().set(requestContext);
        requestContext.setWorkers(this.workersProvider);
        requestContext.setWriterInterceptors(this.writerInterceptors);
        requestContext.setReaderInterceptors(this.readerInterceptors);
        return requestContext;
    }
}

