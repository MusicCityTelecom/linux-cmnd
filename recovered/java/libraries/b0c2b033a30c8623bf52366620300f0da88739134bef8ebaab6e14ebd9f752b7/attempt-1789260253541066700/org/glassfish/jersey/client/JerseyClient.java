/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.net.URI;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.jersey.SslConfigurator;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.Initializable;
import org.glassfish.jersey.client.JerseyInvocation;
import org.glassfish.jersey.client.JerseyWebTarget;
import org.glassfish.jersey.client.internal.LocalizationMessages;
import org.glassfish.jersey.client.spi.DefaultSslContextProvider;
import org.glassfish.jersey.internal.ServiceFinder;
import org.glassfish.jersey.internal.guava.Preconditions;
import org.glassfish.jersey.internal.util.collection.UnsafeValue;
import org.glassfish.jersey.internal.util.collection.Values;

public class JerseyClient
implements Client,
Initializable<JerseyClient> {
    private static final Logger LOG = Logger.getLogger(JerseyClient.class.getName());
    private static final DefaultSslContextProvider DEFAULT_SSL_CONTEXT_PROVIDER = new DefaultSslContextProvider(){

        @Override
        public SSLContext getDefaultSslContext() {
            return SslConfigurator.getDefaultContext();
        }
    };
    private final AtomicBoolean closedFlag = new AtomicBoolean(false);
    private final boolean isDefaultSslContext;
    private final ClientConfig config;
    private final HostnameVerifier hostnameVerifier;
    private final UnsafeValue<SSLContext, IllegalStateException> sslContext;
    private final LinkedBlockingDeque<WeakReference<ShutdownHook>> shutdownHooks = new LinkedBlockingDeque();
    private final ReferenceQueue<ShutdownHook> shReferenceQueue = new ReferenceQueue();

    protected JerseyClient() {
        this(null, (UnsafeValue<SSLContext, IllegalStateException>)null, null, null);
    }

    protected JerseyClient(Configuration config, SSLContext sslContext, HostnameVerifier verifier) {
        this(config, sslContext, verifier, null);
    }

    protected JerseyClient(Configuration config, SSLContext sslContext, HostnameVerifier verifier, DefaultSslContextProvider defaultSslContextProvider) {
        this(config, sslContext == null ? null : Values.unsafe(sslContext), verifier, defaultSslContextProvider);
    }

    protected JerseyClient(Configuration config, UnsafeValue<SSLContext, IllegalStateException> sslContextProvider, HostnameVerifier verifier) {
        this(config, sslContextProvider, verifier, null);
    }

    protected JerseyClient(Configuration config, UnsafeValue<SSLContext, IllegalStateException> sslContextProvider, HostnameVerifier verifier, DefaultSslContextProvider defaultSslContextProvider) {
        ClientConfig clientConfig = this.config = config == null ? new ClientConfig(this) : new ClientConfig(this, config);
        if (sslContextProvider == null) {
            this.isDefaultSslContext = true;
            if (defaultSslContextProvider != null) {
                this.sslContext = this.createLazySslContext(defaultSslContextProvider);
            } else {
                Iterator<DefaultSslContextProvider> iterator = ServiceFinder.find(DefaultSslContextProvider.class).iterator();
                DefaultSslContextProvider lookedUpSslContextProvider = iterator.hasNext() ? iterator.next() : DEFAULT_SSL_CONTEXT_PROVIDER;
                this.sslContext = this.createLazySslContext(lookedUpSslContextProvider);
            }
        } else {
            this.isDefaultSslContext = false;
            this.sslContext = Values.lazy(sslContextProvider);
        }
        this.hostnameVerifier = verifier;
    }

    @Override
    public void close() {
        if (this.closedFlag.compareAndSet(false, true)) {
            this.release();
        }
    }

    private void release() {
        Reference listenerRef;
        while ((listenerRef = (Reference)this.shutdownHooks.pollFirst()) != null) {
            ShutdownHook listener = (ShutdownHook)listenerRef.get();
            if (listener == null) continue;
            try {
                listener.onShutdown();
            }
            catch (Throwable t) {
                LOG.log(Level.WARNING, LocalizationMessages.ERROR_SHUTDOWNHOOK_CLOSE(listenerRef.getClass().getName()), t);
            }
        }
    }

    private UnsafeValue<SSLContext, IllegalStateException> createLazySslContext(final DefaultSslContextProvider provider) {
        return Values.lazy(new UnsafeValue<SSLContext, IllegalStateException>(){

            @Override
            public SSLContext get() {
                return provider.getDefaultSslContext();
            }
        });
    }

    void registerShutdownHook(ShutdownHook shutdownHook) {
        this.checkNotClosed();
        this.shutdownHooks.push(new WeakReference<ShutdownHook>(shutdownHook, this.shReferenceQueue));
        this.cleanUpShutdownHooks();
    }

    private void cleanUpShutdownHooks() {
        Reference<ShutdownHook> reference;
        while ((reference = this.shReferenceQueue.poll()) != null) {
            this.shutdownHooks.remove(reference);
            ShutdownHook shutdownHook = reference.get();
            if (shutdownHook == null) continue;
            shutdownHook.onShutdown();
        }
    }

    private ScheduledExecutorService getDefaultScheduledExecutorService() {
        return Executors.newScheduledThreadPool(8);
    }

    public boolean isClosed() {
        return this.closedFlag.get();
    }

    void checkNotClosed() {
        Preconditions.checkState(!this.closedFlag.get(), LocalizationMessages.CLIENT_INSTANCE_CLOSED());
    }

    public boolean isDefaultSslContext() {
        return this.isDefaultSslContext;
    }

    @Override
    public JerseyWebTarget target(String uri) {
        this.checkNotClosed();
        Preconditions.checkNotNull(uri, LocalizationMessages.CLIENT_URI_TEMPLATE_NULL());
        return new JerseyWebTarget(uri, this);
    }

    @Override
    public JerseyWebTarget target(URI uri) {
        this.checkNotClosed();
        Preconditions.checkNotNull(uri, LocalizationMessages.CLIENT_URI_NULL());
        return new JerseyWebTarget(uri, this);
    }

    @Override
    public JerseyWebTarget target(UriBuilder uriBuilder) {
        this.checkNotClosed();
        Preconditions.checkNotNull(uriBuilder, LocalizationMessages.CLIENT_URI_BUILDER_NULL());
        return new JerseyWebTarget(uriBuilder, this);
    }

    @Override
    public JerseyWebTarget target(Link link) {
        this.checkNotClosed();
        Preconditions.checkNotNull(link, LocalizationMessages.CLIENT_TARGET_LINK_NULL());
        return new JerseyWebTarget(link, this);
    }

    @Override
    public JerseyInvocation.Builder invocation(Link link) {
        this.checkNotClosed();
        Preconditions.checkNotNull(link, LocalizationMessages.CLIENT_INVOCATION_LINK_NULL());
        JerseyWebTarget t = new JerseyWebTarget(link, this);
        String acceptType = link.getType();
        return acceptType != null ? t.request(acceptType) : t.request();
    }

    @Override
    public JerseyClient register(Class<?> providerClass) {
        this.checkNotClosed();
        this.config.register((Class)providerClass);
        return this;
    }

    @Override
    public JerseyClient register(Object provider) {
        this.checkNotClosed();
        this.config.register(provider);
        return this;
    }

    @Override
    public JerseyClient register(Class<?> providerClass, int bindingPriority) {
        this.checkNotClosed();
        this.config.register((Class)providerClass, bindingPriority);
        return this;
    }

    @Override
    public JerseyClient register(Class<?> providerClass, Class<?> ... contracts) {
        this.checkNotClosed();
        this.config.register((Class)providerClass, (Class[])contracts);
        return this;
    }

    @Override
    public JerseyClient register(Class<?> providerClass, Map<Class<?>, Integer> contracts) {
        this.checkNotClosed();
        this.config.register((Class)providerClass, (Map)contracts);
        return this;
    }

    @Override
    public JerseyClient register(Object provider, int bindingPriority) {
        this.checkNotClosed();
        this.config.register(provider, bindingPriority);
        return this;
    }

    @Override
    public JerseyClient register(Object provider, Class<?> ... contracts) {
        this.checkNotClosed();
        this.config.register(provider, (Class[])contracts);
        return this;
    }

    @Override
    public JerseyClient register(Object provider, Map<Class<?>, Integer> contracts) {
        this.checkNotClosed();
        this.config.register(provider, (Map)contracts);
        return this;
    }

    @Override
    public JerseyClient property(String name, Object value) {
        this.checkNotClosed();
        this.config.property(name, value);
        return this;
    }

    @Override
    public ClientConfig getConfiguration() {
        this.checkNotClosed();
        return this.config.getConfiguration();
    }

    @Override
    public SSLContext getSslContext() {
        return this.sslContext.get();
    }

    @Override
    public HostnameVerifier getHostnameVerifier() {
        return this.hostnameVerifier;
    }

    public ExecutorService getExecutorService() {
        return this.config.getExecutorService();
    }

    public ScheduledExecutorService getScheduledExecutorService() {
        return this.config.getScheduledExecutorService();
    }

    @Override
    public JerseyClient preInitialize() {
        this.config.preInitialize();
        return this;
    }

    static interface ShutdownHook {
        public void onShutdown();
    }
}

