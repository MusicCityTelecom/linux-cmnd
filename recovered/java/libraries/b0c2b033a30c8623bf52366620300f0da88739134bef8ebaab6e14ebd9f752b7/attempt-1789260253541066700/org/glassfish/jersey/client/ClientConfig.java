/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import javax.ws.rs.RuntimeType;
import javax.ws.rs.core.Configurable;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Feature;
import org.glassfish.jersey.CommonProperties;
import org.glassfish.jersey.ExtendedConfig;
import org.glassfish.jersey.client.ClientBinder;
import org.glassfish.jersey.client.ClientBootstrapBag;
import org.glassfish.jersey.client.ClientExecutor;
import org.glassfish.jersey.client.ClientExecutorProvidersConfigurator;
import org.glassfish.jersey.client.ClientRuntime;
import org.glassfish.jersey.client.HttpUrlConnectorProvider;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.internal.LocalizationMessages;
import org.glassfish.jersey.client.internal.inject.ParameterUpdaterConfigurator;
import org.glassfish.jersey.client.spi.Connector;
import org.glassfish.jersey.client.spi.ConnectorProvider;
import org.glassfish.jersey.internal.AutoDiscoverableConfigurator;
import org.glassfish.jersey.internal.BootstrapBag;
import org.glassfish.jersey.internal.BootstrapConfigurator;
import org.glassfish.jersey.internal.ContextResolverFactory;
import org.glassfish.jersey.internal.ExceptionMapperFactory;
import org.glassfish.jersey.internal.JaxrsProviders;
import org.glassfish.jersey.internal.ServiceFinder;
import org.glassfish.jersey.internal.inject.Binding;
import org.glassfish.jersey.internal.inject.Bindings;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.internal.inject.Injections;
import org.glassfish.jersey.internal.inject.ParamConverterConfigurator;
import org.glassfish.jersey.internal.inject.ProviderBinder;
import org.glassfish.jersey.internal.spi.AutoDiscoverable;
import org.glassfish.jersey.internal.util.collection.LazyValue;
import org.glassfish.jersey.internal.util.collection.Values;
import org.glassfish.jersey.message.internal.MessageBodyFactory;
import org.glassfish.jersey.model.internal.CommonConfig;
import org.glassfish.jersey.model.internal.ComponentBag;
import org.glassfish.jersey.model.internal.ManagedObjectsFinalizer;
import org.glassfish.jersey.process.internal.RequestScope;

public class ClientConfig
implements Configurable<ClientConfig>,
ExtendedConfig {
    private State state;

    public ClientConfig() {
        this.state = new State(null);
    }

    public ClientConfig(Class<?> ... providerClasses) {
        this();
        for (Class<?> providerClass : providerClasses) {
            this.state.register((Class)providerClass);
        }
    }

    public ClientConfig(Object ... providers) {
        this();
        for (Object provider : providers) {
            this.state.register(provider);
        }
    }

    ClientConfig(JerseyClient parent) {
        this.state = new State(parent);
    }

    ClientConfig(JerseyClient parent, Configuration that) {
        if (that instanceof ClientConfig) {
            this.state = ((ClientConfig)that).state.copy(parent);
        } else {
            this.state = new State(parent);
            this.state.loadFrom(that);
        }
    }

    private ClientConfig(State state) {
        this.state = state;
    }

    ClientConfig snapshot() {
        this.state.markAsShared();
        return new ClientConfig(this.state);
    }

    public ClientConfig loadFrom(Configuration config) {
        if (config instanceof ClientConfig) {
            this.state = ((ClientConfig)config).state.copy();
        } else {
            this.state.loadFrom(config);
        }
        return this;
    }

    @Override
    public ClientConfig register(Class<?> providerClass) {
        this.state = this.state.register((Class)providerClass);
        return this;
    }

    @Override
    public ClientConfig register(Object provider) {
        this.state = this.state.register(provider);
        return this;
    }

    @Override
    public ClientConfig register(Class<?> providerClass, int bindingPriority) {
        this.state = this.state.register((Class)providerClass, bindingPriority);
        return this;
    }

    @Override
    public ClientConfig register(Class<?> providerClass, Class<?> ... contracts) {
        this.state = this.state.register((Class)providerClass, (Class[])contracts);
        return this;
    }

    @Override
    public ClientConfig register(Class<?> providerClass, Map<Class<?>, Integer> contracts) {
        this.state = this.state.register((Class)providerClass, (Map)contracts);
        return this;
    }

    @Override
    public ClientConfig register(Object provider, int bindingPriority) {
        this.state = this.state.register(provider, bindingPriority);
        return this;
    }

    @Override
    public ClientConfig register(Object provider, Class<?> ... contracts) {
        this.state = this.state.register(provider, (Class[])contracts);
        return this;
    }

    @Override
    public ClientConfig register(Object provider, Map<Class<?>, Integer> contracts) {
        this.state = this.state.register(provider, (Map)contracts);
        return this;
    }

    @Override
    public ClientConfig property(String name, Object value) {
        this.state = this.state.property(name, value);
        return this;
    }

    @Override
    public ClientConfig getConfiguration() {
        return this;
    }

    @Override
    public RuntimeType getRuntimeType() {
        return this.state.getRuntimeType();
    }

    @Override
    public Map<String, Object> getProperties() {
        return this.state.getProperties();
    }

    @Override
    public Object getProperty(String name) {
        return this.state.getProperty(name);
    }

    @Override
    public Collection<String> getPropertyNames() {
        return this.state.getPropertyNames();
    }

    @Override
    public boolean isProperty(String name) {
        return this.state.isProperty(name);
    }

    @Override
    public boolean isEnabled(Feature feature) {
        return this.state.isEnabled(feature);
    }

    @Override
    public boolean isEnabled(Class<? extends Feature> featureClass) {
        return this.state.isEnabled(featureClass);
    }

    @Override
    public boolean isRegistered(Object component) {
        return this.state.isRegistered(component);
    }

    @Override
    public Map<Class<?>, Integer> getContracts(Class<?> componentClass) {
        return this.state.getContracts(componentClass);
    }

    @Override
    public boolean isRegistered(Class<?> componentClass) {
        return this.state.isRegistered(componentClass);
    }

    @Override
    public Set<Class<?>> getClasses() {
        return this.state.getClasses();
    }

    @Override
    public Set<Object> getInstances() {
        return this.state.getInstances();
    }

    public ClientConfig connectorProvider(ConnectorProvider connectorProvider) {
        this.state = this.state.connectorProvider(connectorProvider);
        return this;
    }

    public ClientConfig executorService(ExecutorService executorService) {
        this.state = this.state.executorService(executorService);
        return this;
    }

    public ClientConfig scheduledExecutorService(ScheduledExecutorService scheduledExecutorService) {
        this.state = this.state.scheduledExecutorService(scheduledExecutorService);
        return this;
    }

    public Connector getConnector() {
        return this.state.getConnector();
    }

    public ConnectorProvider getConnectorProvider() {
        return this.state.getConnectorProvider();
    }

    public ExecutorService getExecutorService() {
        return this.state.getExecutorService();
    }

    public ScheduledExecutorService getScheduledExecutorService() {
        return this.state.getScheduledExecutorService();
    }

    ClientRuntime getRuntime() {
        return (ClientRuntime)this.state.runtime.get();
    }

    public ClientExecutor getClientExecutor() {
        return (ClientExecutor)this.state.runtime.get();
    }

    public JerseyClient getClient() {
        return this.state.getClient();
    }

    ClientConfig preInitialize() {
        this.state = this.state.preInitialize();
        return this;
    }

    void checkClient() throws IllegalStateException {
        if (this.getClient() == null) {
            throw new IllegalStateException("Client configuration does not contain a parent client instance.");
        }
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        ClientConfig other = (ClientConfig)obj;
        return this.state == other.state || this.state != null && this.state.equals(other.state);
    }

    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + (this.state != null ? this.state.hashCode() : 0);
        return hash;
    }

    private static class State
    implements Configurable<State>,
    ExtendedConfig {
        private static final StateChangeStrategy IDENTITY = state -> state;
        private static final StateChangeStrategy COPY_ON_CHANGE = State::copy;
        private volatile StateChangeStrategy strategy;
        private final CommonConfig commonConfig;
        private final JerseyClient client;
        private volatile ConnectorProvider connectorProvider;
        private volatile ExecutorService executorService;
        private volatile ScheduledExecutorService scheduledExecutorService;
        private final LazyValue<ClientRuntime> runtime = Values.lazy(this::initRuntime);

        State(JerseyClient client) {
            this.strategy = IDENTITY;
            this.commonConfig = new CommonConfig(RuntimeType.CLIENT, ComponentBag.EXCLUDE_EMPTY);
            this.client = client;
            Iterator<ConnectorProvider> iterator = ServiceFinder.find(ConnectorProvider.class).iterator();
            this.connectorProvider = iterator.hasNext() ? iterator.next() : new HttpUrlConnectorProvider();
        }

        private State(JerseyClient client, State original) {
            this.strategy = IDENTITY;
            this.client = client;
            this.commonConfig = new CommonConfig(original.commonConfig);
            this.connectorProvider = original.connectorProvider;
            this.executorService = original.executorService;
            this.scheduledExecutorService = original.scheduledExecutorService;
        }

        State copy() {
            return new State(this.client, this);
        }

        State copy(JerseyClient client) {
            return new State(client, this);
        }

        void markAsShared() {
            this.strategy = COPY_ON_CHANGE;
        }

        State preInitialize() {
            State state = this.strategy.onChange(this);
            state.strategy = COPY_ON_CHANGE;
            ((ClientRuntime)state.runtime.get()).preInitialize();
            return state;
        }

        @Override
        public State property(String name, Object value) {
            State state = this.strategy.onChange(this);
            state.commonConfig.property(name, value);
            return state;
        }

        public State loadFrom(Configuration config) {
            State state = this.strategy.onChange(this);
            state.commonConfig.loadFrom(config);
            return state;
        }

        @Override
        public State register(Class<?> providerClass) {
            State state = this.strategy.onChange(this);
            state.commonConfig.register((Class)providerClass);
            return state;
        }

        @Override
        public State register(Object provider) {
            State state = this.strategy.onChange(this);
            state.commonConfig.register(provider);
            return state;
        }

        @Override
        public State register(Class<?> providerClass, int bindingPriority) {
            State state = this.strategy.onChange(this);
            state.commonConfig.register((Class)providerClass, bindingPriority);
            return state;
        }

        @Override
        public State register(Class<?> providerClass, Class<?> ... contracts) {
            State state = this.strategy.onChange(this);
            state.commonConfig.register((Class)providerClass, (Class[])contracts);
            return state;
        }

        @Override
        public State register(Class<?> providerClass, Map<Class<?>, Integer> contracts) {
            State state = this.strategy.onChange(this);
            state.commonConfig.register((Class)providerClass, (Map)contracts);
            return state;
        }

        @Override
        public State register(Object provider, int bindingPriority) {
            State state = this.strategy.onChange(this);
            state.commonConfig.register(provider, bindingPriority);
            return state;
        }

        @Override
        public State register(Object provider, Class<?> ... contracts) {
            State state = this.strategy.onChange(this);
            state.commonConfig.register(provider, (Class[])contracts);
            return state;
        }

        @Override
        public State register(Object provider, Map<Class<?>, Integer> contracts) {
            State state = this.strategy.onChange(this);
            state.commonConfig.register(provider, (Map)contracts);
            return state;
        }

        State connectorProvider(ConnectorProvider provider) {
            if (provider == null) {
                throw new NullPointerException(LocalizationMessages.NULL_CONNECTOR_PROVIDER());
            }
            State state = this.strategy.onChange(this);
            state.connectorProvider = provider;
            return state;
        }

        State executorService(ExecutorService executorService) {
            if (executorService == null) {
                throw new NullPointerException(LocalizationMessages.NULL_EXECUTOR_SERVICE());
            }
            State state = this.strategy.onChange(this);
            state.executorService = executorService;
            return state;
        }

        State scheduledExecutorService(ScheduledExecutorService scheduledExecutorService) {
            if (scheduledExecutorService == null) {
                throw new NullPointerException(LocalizationMessages.NULL_SCHEDULED_EXECUTOR_SERVICE());
            }
            State state = this.strategy.onChange(this);
            state.scheduledExecutorService = scheduledExecutorService;
            return state;
        }

        Connector getConnector() {
            return this.runtime.isInitialized() ? ((ClientRuntime)this.runtime.get()).getConnector() : null;
        }

        ConnectorProvider getConnectorProvider() {
            return this.connectorProvider;
        }

        ExecutorService getExecutorService() {
            return this.executorService;
        }

        ScheduledExecutorService getScheduledExecutorService() {
            return this.scheduledExecutorService;
        }

        JerseyClient getClient() {
            return this.client;
        }

        @Override
        public State getConfiguration() {
            return this;
        }

        @Override
        public RuntimeType getRuntimeType() {
            return this.commonConfig.getConfiguration().getRuntimeType();
        }

        @Override
        public Map<String, Object> getProperties() {
            return this.commonConfig.getConfiguration().getProperties();
        }

        @Override
        public Object getProperty(String name) {
            return this.commonConfig.getConfiguration().getProperty(name);
        }

        @Override
        public Collection<String> getPropertyNames() {
            return this.commonConfig.getConfiguration().getPropertyNames();
        }

        @Override
        public boolean isProperty(String name) {
            return this.commonConfig.getConfiguration().isProperty(name);
        }

        @Override
        public boolean isEnabled(Feature feature) {
            return this.commonConfig.getConfiguration().isEnabled(feature);
        }

        @Override
        public boolean isEnabled(Class<? extends Feature> featureClass) {
            return this.commonConfig.getConfiguration().isEnabled(featureClass);
        }

        @Override
        public boolean isRegistered(Object component) {
            return this.commonConfig.getConfiguration().isRegistered(component);
        }

        @Override
        public boolean isRegistered(Class<?> componentClass) {
            return this.commonConfig.getConfiguration().isRegistered(componentClass);
        }

        @Override
        public Map<Class<?>, Integer> getContracts(Class<?> componentClass) {
            return this.commonConfig.getConfiguration().getContracts(componentClass);
        }

        @Override
        public Set<Class<?>> getClasses() {
            return this.commonConfig.getConfiguration().getClasses();
        }

        @Override
        public Set<Object> getInstances() {
            return this.commonConfig.getConfiguration().getInstances();
        }

        public void configureAutoDiscoverableProviders(InjectionManager injectionManager, List<AutoDiscoverable> autoDiscoverables) {
            this.commonConfig.configureAutoDiscoverableProviders(injectionManager, autoDiscoverables, false);
        }

        public void configureForcedAutoDiscoverableProviders(InjectionManager injectionManager) {
            this.commonConfig.configureAutoDiscoverableProviders(injectionManager, Collections.emptyList(), true);
        }

        public void configureMetaProviders(InjectionManager injectionManager, ManagedObjectsFinalizer finalizer) {
            this.commonConfig.configureMetaProviders(injectionManager, finalizer);
        }

        public ComponentBag getComponentBag() {
            return this.commonConfig.getComponentBag();
        }

        private ClientRuntime initRuntime() {
            this.markAsShared();
            State runtimeCfgState = this.copy();
            runtimeCfgState.markAsShared();
            InjectionManager injectionManager = Injections.createInjectionManager();
            injectionManager.register(new ClientBinder(runtimeCfgState.getProperties()));
            ClientBootstrapBag bootstrapBag = new ClientBootstrapBag();
            bootstrapBag.setManagedObjectsFinalizer(new ManagedObjectsFinalizer(injectionManager));
            List<BootstrapConfigurator> bootstrapConfigurators = Arrays.asList(new RequestScope.RequestScopeConfigurator(), new ParamConverterConfigurator(), new ParameterUpdaterConfigurator(), new RuntimeConfigConfigurator(runtimeCfgState), new ContextResolverFactory.ContextResolversConfigurator(), new MessageBodyFactory.MessageBodyWorkersConfigurator(), new ExceptionMapperFactory.ExceptionMappersConfigurator(), new JaxrsProviders.ProvidersConfigurator(), new AutoDiscoverableConfigurator(RuntimeType.CLIENT));
            bootstrapConfigurators.forEach(configurator -> configurator.init(injectionManager, bootstrapBag));
            if (!CommonProperties.getValue(runtimeCfgState.getProperties(), RuntimeType.CLIENT, "jersey.config.disableAutoDiscovery", Boolean.FALSE, Boolean.class).booleanValue()) {
                runtimeCfgState.configureAutoDiscoverableProviders(injectionManager, bootstrapBag.getAutoDiscoverables());
            } else {
                runtimeCfgState.configureForcedAutoDiscoverableProviders(injectionManager);
            }
            runtimeCfgState.configureMetaProviders(injectionManager, bootstrapBag.getManagedObjectsFinalizer());
            ProviderBinder.bindProviders(runtimeCfgState.getComponentBag(), RuntimeType.CLIENT, null, injectionManager);
            ClientExecutorProvidersConfigurator executorProvidersConfigurator = new ClientExecutorProvidersConfigurator(runtimeCfgState.getComponentBag(), runtimeCfgState.client, this.executorService, this.scheduledExecutorService);
            executorProvidersConfigurator.init(injectionManager, bootstrapBag);
            injectionManager.completeRegistration();
            bootstrapConfigurators.forEach(configurator -> configurator.postInit(injectionManager, bootstrapBag));
            ClientConfig configuration = new ClientConfig(runtimeCfgState);
            Connector connector = this.connectorProvider.getConnector(this.client, configuration);
            ClientRuntime crt = new ClientRuntime(configuration, connector, injectionManager, bootstrapBag);
            this.client.registerShutdownHook(crt);
            return crt;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            State state = (State)o;
            if (this.client != null ? !this.client.equals(state.client) : state.client != null) {
                return false;
            }
            if (!this.commonConfig.equals(state.commonConfig)) {
                return false;
            }
            return this.connectorProvider == null ? state.connectorProvider == null : this.connectorProvider.equals(state.connectorProvider);
        }

        public int hashCode() {
            int result = this.commonConfig.hashCode();
            result = 31 * result + (this.client != null ? this.client.hashCode() : 0);
            result = 31 * result + (this.connectorProvider != null ? this.connectorProvider.hashCode() : 0);
            return result;
        }

        private static interface StateChangeStrategy {
            public State onChange(State var1);
        }
    }

    private static class RuntimeConfigConfigurator
    implements BootstrapConfigurator {
        private final State runtimeConfig;

        private RuntimeConfigConfigurator(State runtimeConfig) {
            this.runtimeConfig = runtimeConfig;
        }

        @Override
        public void init(InjectionManager injectionManager, BootstrapBag bootstrapBag) {
            bootstrapBag.setConfiguration(this.runtimeConfig);
            injectionManager.register((Binding)Bindings.service(this.runtimeConfig).to(Configuration.class));
        }
    }
}

