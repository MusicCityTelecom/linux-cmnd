/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client;

import java.lang.reflect.Method;
import java.security.AccessController;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.jersey.client.ClientAsyncExecutor;
import org.glassfish.jersey.client.ClientBackgroundScheduler;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.DefaultClientAsyncExecutorProvider;
import org.glassfish.jersey.client.DefaultClientBackgroundSchedulerProvider;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.internal.BootstrapBag;
import org.glassfish.jersey.internal.inject.Bindings;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.internal.inject.InstanceBinding;
import org.glassfish.jersey.internal.util.ReflectionHelper;
import org.glassfish.jersey.internal.util.collection.Value;
import org.glassfish.jersey.internal.util.collection.Values;
import org.glassfish.jersey.model.internal.ComponentBag;
import org.glassfish.jersey.model.internal.ManagedObjectsFinalizer;
import org.glassfish.jersey.process.internal.AbstractExecutorProvidersConfigurator;
import org.glassfish.jersey.spi.ExecutorServiceProvider;
import org.glassfish.jersey.spi.ScheduledExecutorServiceProvider;

class ClientExecutorProvidersConfigurator
extends AbstractExecutorProvidersConfigurator {
    private static final Logger LOGGER = Logger.getLogger(ClientExecutorProvidersConfigurator.class.getName());
    private static final ExecutorService MANAGED_EXECUTOR_SERVICE = ClientExecutorProvidersConfigurator.lookupManagedExecutorService();
    private final ComponentBag componentBag;
    private final JerseyClient client;
    private final ExecutorService customExecutorService;
    private final ScheduledExecutorService customScheduledExecutorService;

    ClientExecutorProvidersConfigurator(ComponentBag componentBag, JerseyClient client, ExecutorService customExecutorService, ScheduledExecutorService customScheduledExecutorService) {
        this.componentBag = componentBag;
        this.client = client;
        this.customExecutorService = customExecutorService;
        this.customScheduledExecutorService = customScheduledExecutorService;
    }

    @Override
    public void init(InjectionManager injectionManager, BootstrapBag bootstrapBag) {
        ScheduledExecutorService scheduledExecutorService;
        ScheduledExecutorService clientScheduledExecutorService;
        ExecutorServiceProvider defaultAsyncExecutorProvider;
        ExecutorService clientExecutorService;
        Map<String, Object> runtimeProperties = bootstrapBag.getConfiguration().getProperties();
        ManagedObjectsFinalizer finalizer = bootstrapBag.getManagedObjectsFinalizer();
        ExecutorService executorService = clientExecutorService = this.client.getExecutorService() == null ? this.customExecutorService : this.client.getExecutorService();
        if (clientExecutorService != null) {
            defaultAsyncExecutorProvider = new ClientExecutorServiceProvider(clientExecutorService);
        } else {
            Integer asyncThreadPoolSize = ClientProperties.getValue(runtimeProperties, "jersey.config.client.async.threadPoolSize", Integer.class);
            if (asyncThreadPoolSize != null) {
                asyncThreadPoolSize = asyncThreadPoolSize < 0 ? 0 : asyncThreadPoolSize;
                InstanceBinding asyncThreadPoolSizeBinding = (InstanceBinding)Bindings.service(asyncThreadPoolSize).named("ClientAsyncThreadPoolSize");
                injectionManager.register(asyncThreadPoolSizeBinding);
                defaultAsyncExecutorProvider = new DefaultClientAsyncExecutorProvider(asyncThreadPoolSize);
            } else {
                defaultAsyncExecutorProvider = MANAGED_EXECUTOR_SERVICE != null ? new ClientExecutorServiceProvider(MANAGED_EXECUTOR_SERVICE) : new DefaultClientAsyncExecutorProvider(0);
            }
        }
        InstanceBinding executorBinding = (InstanceBinding)Bindings.service(defaultAsyncExecutorProvider).to(ExecutorServiceProvider.class);
        injectionManager.register(executorBinding);
        finalizer.registerForPreDestroyCall(defaultAsyncExecutorProvider);
        ScheduledExecutorService scheduledExecutorService2 = clientScheduledExecutorService = this.client.getScheduledExecutorService() == null ? this.customScheduledExecutorService : this.client.getScheduledExecutorService();
        ScheduledExecutorServiceProvider defaultScheduledExecutorProvider = clientScheduledExecutorService != null ? new ClientScheduledExecutorServiceProvider(Values.of(clientScheduledExecutorService)) : ((scheduledExecutorService = this.lookupManagedScheduledExecutorService()) == null ? new DefaultClientBackgroundSchedulerProvider() : new ClientScheduledExecutorServiceProvider(Values.of(scheduledExecutorService)));
        InstanceBinding schedulerBinding = (InstanceBinding)Bindings.service(defaultScheduledExecutorProvider).to(ScheduledExecutorServiceProvider.class);
        injectionManager.register(schedulerBinding);
        finalizer.registerForPreDestroyCall(defaultScheduledExecutorProvider);
        this.registerExecutors(injectionManager, this.componentBag, defaultAsyncExecutorProvider, defaultScheduledExecutorProvider);
    }

    private static ExecutorService lookupManagedExecutorService() {
        try {
            Class aClass = AccessController.doPrivileged(ReflectionHelper.classForNamePA("javax.naming.InitialContext"));
            Object initialContext = aClass.newInstance();
            Method lookupMethod = aClass.getMethod("lookup", String.class);
            return (ExecutorService)lookupMethod.invoke(initialContext, "java:comp/DefaultManagedExecutorService");
        }
        catch (Exception e) {
            if (LOGGER.isLoggable(Level.FINE)) {
                LOGGER.log(Level.FINE, e.getMessage(), e);
            }
        }
        catch (LinkageError linkageError) {
            // empty catch block
        }
        return null;
    }

    private ScheduledExecutorService lookupManagedScheduledExecutorService() {
        try {
            Class aClass = AccessController.doPrivileged(ReflectionHelper.classForNamePA("javax.naming.InitialContext"));
            Object initialContext = aClass.newInstance();
            Method lookupMethod = aClass.getMethod("lookup", String.class);
            return (ScheduledExecutorService)lookupMethod.invoke(initialContext, "java:comp/DefaultManagedScheduledExecutorService");
        }
        catch (Exception e) {
            if (LOGGER.isLoggable(Level.FINE)) {
                LOGGER.log(Level.FINE, e.getMessage(), e);
            }
        }
        catch (LinkageError linkageError) {
            // empty catch block
        }
        return null;
    }

    @ClientBackgroundScheduler
    public static class ClientScheduledExecutorServiceProvider
    implements ScheduledExecutorServiceProvider {
        private final Value<ScheduledExecutorService> executorService;

        ClientScheduledExecutorServiceProvider(Value<ScheduledExecutorService> executorService) {
            this.executorService = executorService;
        }

        @Override
        public ScheduledExecutorService getExecutorService() {
            return this.executorService.get();
        }

        @Override
        public void dispose(ExecutorService executorService) {
        }
    }

    @ClientAsyncExecutor
    public static class ClientExecutorServiceProvider
    implements ExecutorServiceProvider {
        private final ExecutorService executorService;

        ClientExecutorServiceProvider(ExecutorService executorService) {
            this.executorService = executorService;
        }

        @Override
        public ExecutorService getExecutorService() {
            return this.executorService;
        }

        @Override
        public void dispose(ExecutorService executorService) {
        }
    }
}

