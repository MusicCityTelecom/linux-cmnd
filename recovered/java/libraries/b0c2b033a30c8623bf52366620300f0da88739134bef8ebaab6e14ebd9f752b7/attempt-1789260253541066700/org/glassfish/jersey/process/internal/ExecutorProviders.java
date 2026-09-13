/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.process.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.inject.Qualifier;
import javax.inject.Singleton;
import org.glassfish.jersey.internal.LocalizationMessages;
import org.glassfish.jersey.internal.inject.Bindings;
import org.glassfish.jersey.internal.inject.DisposableSupplier;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.internal.inject.Providers;
import org.glassfish.jersey.internal.inject.SupplierInstanceBinding;
import org.glassfish.jersey.internal.util.ExtendedLogger;
import org.glassfish.jersey.internal.util.ReflectionHelper;
import org.glassfish.jersey.spi.ExecutorServiceProvider;
import org.glassfish.jersey.spi.ScheduledExecutorServiceProvider;

public final class ExecutorProviders {
    private static final ExtendedLogger LOGGER = new ExtendedLogger(Logger.getLogger(ExecutorProviders.class.getName()), Level.FINEST);

    private ExecutorProviders() {
        throw new AssertionError((Object)"Instantiation not allowed.");
    }

    public static void registerExecutorBindings(InjectionManager injectionManager) {
        List<ExecutorServiceProvider> executorProviders = ExecutorProviders.getExecutorProviders(injectionManager, ExecutorServiceProvider.class);
        List<ScheduledExecutorServiceProvider> scheduledProviders = ExecutorProviders.getExecutorProviders(injectionManager, ScheduledExecutorServiceProvider.class);
        ExecutorProviders.registerExecutorBindings(injectionManager, executorProviders, scheduledProviders);
    }

    private static <T> List<T> getExecutorProviders(InjectionManager injectionManager, Class<T> providerClass) {
        Set<T> customProviders = Providers.getCustomProviders(injectionManager, providerClass);
        Set<T> defaultProviders = Providers.getProviders(injectionManager, providerClass);
        defaultProviders.removeAll(customProviders);
        LinkedList<T> executorProviders = new LinkedList<T>(customProviders);
        executorProviders.addAll(defaultProviders);
        return executorProviders;
    }

    public static void registerExecutorBindings(InjectionManager injectionManager, List<ExecutorServiceProvider> executorProviders, List<ScheduledExecutorServiceProvider> scheduledProviders) {
        Map<Class<Annotation>, List<ExecutorServiceProvider>> executorProviderMap = ExecutorProviders.getQualifierToProviderMap(executorProviders);
        for (Map.Entry<Class<Annotation>, List<ExecutorServiceProvider>> qualifierToProviders : executorProviderMap.entrySet()) {
            Class<Annotation> qualifierAnnotationClass = qualifierToProviders.getKey();
            Iterator<ExecutorServiceProvider> bucketProviderIterator = qualifierToProviders.getValue().iterator();
            ExecutorServiceProvider executorProvider = bucketProviderIterator.next();
            ExecutorProviders.logExecutorServiceProvider(qualifierAnnotationClass, bucketProviderIterator, executorProvider);
            SupplierInstanceBinding descriptor = (SupplierInstanceBinding)((SupplierInstanceBinding)Bindings.supplier(new ExecutorServiceSupplier(executorProvider)).in(Singleton.class)).to((Type)((Object)ExecutorService.class));
            Annotation qualifier = executorProvider.getClass().getAnnotation(qualifierAnnotationClass);
            if (qualifier instanceof Named) {
                descriptor.named(((Named)qualifier).value());
            } else {
                descriptor.qualifiedBy(qualifier);
            }
            injectionManager.register(descriptor);
        }
        Map<Class<Annotation>, List<ScheduledExecutorServiceProvider>> schedulerProviderMap = ExecutorProviders.getQualifierToProviderMap(scheduledProviders);
        for (Map.Entry<Class<Annotation>, List<ScheduledExecutorServiceProvider>> qualifierToProviders : schedulerProviderMap.entrySet()) {
            Annotation qualifier;
            Class<Annotation> qualifierAnnotationClass = qualifierToProviders.getKey();
            Iterator<ScheduledExecutorServiceProvider> bucketProviderIterator = qualifierToProviders.getValue().iterator();
            ScheduledExecutorServiceProvider executorProvider = bucketProviderIterator.next();
            ExecutorProviders.logScheduledExecutorProvider(qualifierAnnotationClass, bucketProviderIterator, executorProvider);
            SupplierInstanceBinding descriptor = (SupplierInstanceBinding)((SupplierInstanceBinding)Bindings.supplier(new ScheduledExecutorServiceSupplier(executorProvider)).in(Singleton.class)).to((Type)((Object)ScheduledExecutorService.class));
            if (!executorProviderMap.containsKey(qualifierAnnotationClass)) {
                descriptor.to((Type)((Object)ExecutorService.class));
            }
            if ((qualifier = executorProvider.getClass().getAnnotation(qualifierAnnotationClass)) instanceof Named) {
                descriptor.named(((Named)qualifier).value());
            } else {
                descriptor.qualifiedBy(qualifier);
            }
            injectionManager.register(descriptor);
        }
    }

    private static void logScheduledExecutorProvider(Class<? extends Annotation> qualifierAnnotationClass, Iterator<ScheduledExecutorServiceProvider> bucketProviderIterator, ScheduledExecutorServiceProvider executorProvider) {
        if (LOGGER.isLoggable(Level.CONFIG)) {
            LOGGER.config(LocalizationMessages.USING_SCHEDULER_PROVIDER(executorProvider.getClass().getName(), qualifierAnnotationClass.getName()));
            if (bucketProviderIterator.hasNext()) {
                StringBuilder msg = new StringBuilder(bucketProviderIterator.next().getClass().getName());
                while (bucketProviderIterator.hasNext()) {
                    msg.append(", ").append(bucketProviderIterator.next().getClass().getName());
                }
                LOGGER.config(LocalizationMessages.IGNORED_SCHEDULER_PROVIDERS(msg.toString(), qualifierAnnotationClass.getName()));
            }
        }
    }

    private static void logExecutorServiceProvider(Class<? extends Annotation> qualifierAnnotationClass, Iterator<ExecutorServiceProvider> bucketProviderIterator, ExecutorServiceProvider executorProvider) {
        if (LOGGER.isLoggable(Level.CONFIG)) {
            LOGGER.config(LocalizationMessages.USING_EXECUTOR_PROVIDER(executorProvider.getClass().getName(), qualifierAnnotationClass.getName()));
            if (bucketProviderIterator.hasNext()) {
                StringBuilder msg = new StringBuilder(bucketProviderIterator.next().getClass().getName());
                while (bucketProviderIterator.hasNext()) {
                    msg.append(", ").append(bucketProviderIterator.next().getClass().getName());
                }
                LOGGER.config(LocalizationMessages.IGNORED_EXECUTOR_PROVIDERS(msg.toString(), qualifierAnnotationClass.getName()));
            }
        }
    }

    private static <T extends ExecutorServiceProvider> Map<Class<? extends Annotation>, List<T>> getQualifierToProviderMap(List<T> executorProviders) {
        HashMap<Class<Annotation>, List<T>> executorProviderMap = new HashMap<Class<Annotation>, List<T>>();
        for (ExecutorServiceProvider provider : executorProviders) {
            for (Class<? extends Annotation> qualifier : ReflectionHelper.getAnnotationTypes(provider.getClass(), Qualifier.class)) {
                List<ExecutorServiceProvider> providersForQualifier;
                if (!executorProviderMap.containsKey(qualifier)) {
                    providersForQualifier = new LinkedList();
                    executorProviderMap.put(qualifier, providersForQualifier);
                } else {
                    providersForQualifier = (List)executorProviderMap.get(qualifier);
                }
                providersForQualifier.add(provider);
            }
        }
        return executorProviderMap;
    }

    private static class ScheduledExecutorServiceSupplier
    implements DisposableSupplier<ScheduledExecutorService> {
        private final ScheduledExecutorServiceProvider executorProvider;

        private ScheduledExecutorServiceSupplier(ScheduledExecutorServiceProvider executorServiceProvider) {
            this.executorProvider = executorServiceProvider;
        }

        @Override
        public ScheduledExecutorService get() {
            return this.executorProvider.getExecutorService();
        }

        @Override
        public void dispose(ScheduledExecutorService instance) {
            this.executorProvider.dispose(instance);
        }
    }

    private static class ExecutorServiceSupplier
    implements DisposableSupplier<ExecutorService> {
        private final ExecutorServiceProvider executorProvider;

        private ExecutorServiceSupplier(ExecutorServiceProvider executorServiceProvider) {
            this.executorProvider = executorServiceProvider;
        }

        @Override
        public ExecutorService get() {
            return this.executorProvider.getExecutorService();
        }

        @Override
        public void dispose(ExecutorService instance) {
            this.executorProvider.dispose(instance);
        }
    }
}

