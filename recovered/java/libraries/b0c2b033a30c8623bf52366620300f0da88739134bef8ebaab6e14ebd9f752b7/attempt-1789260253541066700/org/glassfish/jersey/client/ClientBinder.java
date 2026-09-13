/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.function.Supplier;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.ws.rs.RuntimeType;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.MessageBodyReader;
import org.glassfish.jersey.client.ChunkedInputReader;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientRequest;
import org.glassfish.jersey.internal.PropertiesDelegate;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.internal.inject.ClassBinding;
import org.glassfish.jersey.internal.inject.ReferencingFactory;
import org.glassfish.jersey.internal.inject.SupplierClassBinding;
import org.glassfish.jersey.internal.inject.SupplierInstanceBinding;
import org.glassfish.jersey.internal.util.collection.Ref;
import org.glassfish.jersey.message.internal.MessagingBinders;
import org.glassfish.jersey.process.internal.RequestScoped;

class ClientBinder
extends AbstractBinder {
    private final Map<String, Object> clientRuntimeProperties;

    ClientBinder(Map<String, Object> clientRuntimeProperties) {
        this.clientRuntimeProperties = clientRuntimeProperties;
    }

    @Override
    protected void configure() {
        this.install(new MessagingBinders.MessageBodyProviders(this.clientRuntimeProperties, RuntimeType.CLIENT), new MessagingBinders.HeaderDelegateProviders());
        ((SupplierInstanceBinding)this.bindFactory(ReferencingFactory.referenceFactory()).to(new GenericType<Ref<ClientConfig>>(){})).in(RequestScoped.class);
        ((SupplierClassBinding)this.bindFactory(RequestContextInjectionFactory.class).to((Type)((Object)ClientRequest.class))).in(RequestScoped.class);
        ((SupplierClassBinding)((SupplierClassBinding)((SupplierClassBinding)this.bindFactory(RequestContextInjectionFactory.class).to((Type)((Object)HttpHeaders.class))).proxy(true)).proxyForSameScope(false)).in(RequestScoped.class);
        ((SupplierInstanceBinding)this.bindFactory(ReferencingFactory.referenceFactory()).to(new GenericType<Ref<ClientRequest>>(){})).in(RequestScoped.class);
        ((SupplierClassBinding)this.bindFactory(PropertiesDelegateFactory.class, Singleton.class).to((Type)((Object)PropertiesDelegate.class))).in(RequestScoped.class);
        ((ClassBinding)this.bind(ChunkedInputReader.class).to(MessageBodyReader.class)).in(Singleton.class);
    }

    private static class PropertiesDelegateFactory
    implements Supplier<PropertiesDelegate> {
        private final Provider<ClientRequest> requestProvider;

        @Inject
        private PropertiesDelegateFactory(Provider<ClientRequest> requestProvider) {
            this.requestProvider = requestProvider;
        }

        @Override
        public PropertiesDelegate get() {
            return this.requestProvider.get().getPropertiesDelegate();
        }
    }

    private static class RequestContextInjectionFactory
    extends ReferencingFactory<ClientRequest> {
        @Inject
        public RequestContextInjectionFactory(Provider<Ref<ClientRequest>> referenceFactory) {
            super(referenceFactory);
        }
    }
}

