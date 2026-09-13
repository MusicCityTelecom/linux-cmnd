/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.inject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.annotation.Priority;
import javax.ws.rs.ConstrainedTo;
import javax.ws.rs.RuntimeType;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.client.RxInvokerProvider;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.core.Feature;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.WriterInterceptor;
import org.glassfish.jersey.internal.LocalizationMessages;
import org.glassfish.jersey.internal.inject.Binder;
import org.glassfish.jersey.internal.inject.CustomAnnotationLiteral;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.internal.inject.ServiceHolder;
import org.glassfish.jersey.model.ContractProvider;
import org.glassfish.jersey.model.internal.RankedComparator;
import org.glassfish.jersey.model.internal.RankedProvider;
import org.glassfish.jersey.spi.Contract;

public final class Providers {
    private static final Logger LOGGER = Logger.getLogger(Providers.class.getName());
    private static final Map<Class<?>, ProviderRuntime> JAX_RS_PROVIDER_INTERFACE_WHITELIST = Providers.getJaxRsProviderInterfaces();
    private static final Map<Class<?>, ProviderRuntime> EXTERNAL_PROVIDER_INTERFACE_WHITELIST = Providers.getExternalProviderInterfaces();

    private Providers() {
    }

    private static Map<Class<?>, ProviderRuntime> getJaxRsProviderInterfaces() {
        HashMap interfaces = new HashMap();
        interfaces.put(ContextResolver.class, ProviderRuntime.BOTH);
        interfaces.put(ExceptionMapper.class, ProviderRuntime.BOTH);
        interfaces.put(MessageBodyReader.class, ProviderRuntime.BOTH);
        interfaces.put(MessageBodyWriter.class, ProviderRuntime.BOTH);
        interfaces.put(ReaderInterceptor.class, ProviderRuntime.BOTH);
        interfaces.put(WriterInterceptor.class, ProviderRuntime.BOTH);
        interfaces.put(ParamConverterProvider.class, ProviderRuntime.BOTH);
        interfaces.put(ContainerRequestFilter.class, ProviderRuntime.SERVER);
        interfaces.put(ContainerResponseFilter.class, ProviderRuntime.SERVER);
        interfaces.put(DynamicFeature.class, ProviderRuntime.SERVER);
        interfaces.put(ClientResponseFilter.class, ProviderRuntime.CLIENT);
        interfaces.put(ClientRequestFilter.class, ProviderRuntime.CLIENT);
        interfaces.put(RxInvokerProvider.class, ProviderRuntime.CLIENT);
        return interfaces;
    }

    private static Map<Class<?>, ProviderRuntime> getExternalProviderInterfaces() {
        HashMap interfaces = new HashMap();
        interfaces.putAll(JAX_RS_PROVIDER_INTERFACE_WHITELIST);
        interfaces.put(Feature.class, ProviderRuntime.BOTH);
        interfaces.put(Binder.class, ProviderRuntime.BOTH);
        return interfaces;
    }

    public static <T> Set<T> getProviders(InjectionManager injectionManager, Class<T> contract) {
        List<ServiceHolder<T>> providers = Providers.getServiceHolders(injectionManager, contract, new Annotation[0]);
        return Providers.getProviderClasses(providers);
    }

    public static <T> Set<T> getCustomProviders(InjectionManager injectionManager, Class<T> contract) {
        List<ServiceHolder<T>> providers = Providers.getServiceHolders(injectionManager, contract, Comparator.comparingInt(Providers::getPriority), CustomAnnotationLiteral.INSTANCE);
        return Providers.getProviderClasses(providers);
    }

    public static <T> Iterable<T> getAllProviders(InjectionManager injectionManager, Class<T> contract) {
        return Providers.getAllProviders(injectionManager, contract, (Comparator)null);
    }

    public static <T> Iterable<RankedProvider<T>> getAllRankedProviders(InjectionManager injectionManager, Class<T> contract) {
        List<ServiceHolder<T>> providers = Providers.getServiceHolders(injectionManager, contract, CustomAnnotationLiteral.INSTANCE);
        providers.addAll(Providers.getServiceHolders(injectionManager, contract, new Annotation[0]));
        LinkedHashMap<Class<T>, RankedProvider<T>> providerMap = new LinkedHashMap<Class<T>, RankedProvider<T>>();
        for (ServiceHolder<T> provider : providers) {
            Class<T> implClass = Providers.getImplementationClass(contract, provider);
            if (providerMap.containsKey(implClass)) continue;
            Set<Type> contracts = Providers.isProxyGenerated(contract, provider) ? provider.getContractTypes() : null;
            providerMap.put(implClass, new RankedProvider<T>(provider.getInstance(), provider.getRank(), contracts));
        }
        return providerMap.values();
    }

    public static <T> Iterable<T> sortRankedProviders(RankedComparator<T> comparator, Iterable<RankedProvider<T>> providers) {
        return StreamSupport.stream(providers.spliterator(), false).sorted(comparator).map(RankedProvider::getProvider).collect(Collectors.toList());
    }

    public static <T> Iterable<T> getAllRankedSortedProviders(InjectionManager injectionManager, Class<T> contract) {
        Iterable<RankedProvider<T>> allRankedProviders = Providers.getAllRankedProviders(injectionManager, contract);
        return Providers.sortRankedProviders(new RankedComparator(), allRankedProviders);
    }

    public static <T> Iterable<T> mergeAndSortRankedProviders(RankedComparator<T> comparator, Iterable<Iterable<RankedProvider<T>>> providerIterables) {
        return StreamSupport.stream(providerIterables.spliterator(), false).flatMap(rankedProviders -> StreamSupport.stream(rankedProviders.spliterator(), false)).sorted(comparator).map(RankedProvider::getProvider).collect(Collectors.toList());
    }

    public static <T> Iterable<T> getAllProviders(InjectionManager injectionManager, Class<T> contract, RankedComparator<T> comparator) {
        return Providers.sortRankedProviders(comparator, Providers.getAllRankedProviders(injectionManager, contract));
    }

    public static <T> Collection<ServiceHolder<T>> getAllServiceHolders(InjectionManager injectionManager, Class<T> contract) {
        List<ServiceHolder<T>> providers = Providers.getServiceHolders(injectionManager, contract, Comparator.comparingInt(Providers::getPriority), CustomAnnotationLiteral.INSTANCE);
        providers.addAll(Providers.getServiceHolders(injectionManager, contract, new Annotation[0]));
        LinkedHashMap<Class<T>, ServiceHolder<T>> providerMap = new LinkedHashMap<Class<T>, ServiceHolder<T>>();
        for (ServiceHolder<T> provider : providers) {
            Class<T> implClass = Providers.getImplementationClass(contract, provider);
            if (providerMap.containsKey(implClass)) continue;
            providerMap.put(implClass, provider);
        }
        return providerMap.values();
    }

    private static <T> List<ServiceHolder<T>> getServiceHolders(InjectionManager bm, Class<T> contract, Annotation ... qualifiers) {
        return bm.getAllServiceHolders(contract, qualifiers);
    }

    private static <T> List<ServiceHolder<T>> getServiceHolders(InjectionManager injectionManager, Class<T> contract, Comparator<Class<?>> objectComparator, Annotation ... qualifiers) {
        List<ServiceHolder<T>> serviceHolders = injectionManager.getAllServiceHolders(contract, qualifiers);
        serviceHolders.sort((o1, o2) -> objectComparator.compare(Providers.getImplementationClass(contract, o1), Providers.getImplementationClass(contract, o2)));
        return serviceHolders;
    }

    public static boolean isJaxRsProvider(Class<?> clazz) {
        for (Class<?> providerType : JAX_RS_PROVIDER_INTERFACE_WHITELIST.keySet()) {
            if (!providerType.isAssignableFrom(clazz)) continue;
            return true;
        }
        return false;
    }

    public static <T> Iterable<T> getAllProviders(InjectionManager injectionManager, Class<T> contract, Comparator<T> comparator) {
        ArrayList<T> providerList = new ArrayList<T>(Providers.getProviderClasses(Providers.getAllServiceHolders(injectionManager, contract)));
        if (comparator != null) {
            providerList.sort(comparator);
        }
        return providerList;
    }

    private static <T> Set<T> getProviderClasses(Collection<ServiceHolder<T>> providers) {
        return providers.stream().map(Providers::holder2service).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private static <T> T holder2service(ServiceHolder<T> holder) {
        return holder != null ? (T)holder.getInstance() : null;
    }

    private static int getPriority(Class<?> serviceClass) {
        Priority annotation = serviceClass.getAnnotation(Priority.class);
        if (annotation != null) {
            return annotation.value();
        }
        return 5000;
    }

    private static <T> Class<T> getImplementationClass(Class<T> contract, ServiceHolder<T> serviceHolder) {
        return Providers.isProxyGenerated(contract, serviceHolder) ? serviceHolder.getContractTypes().stream().filter(a -> Class.class.isInstance(a)).map(a -> (Class)a).reduce(contract, (a, b) -> a.isAssignableFrom((Class<?>)b) ? b : a) : serviceHolder.getImplementationClass();
    }

    private static <T> boolean isProxyGenerated(Class<T> contract, ServiceHolder<T> serviceHolder) {
        return !contract.isAssignableFrom(serviceHolder.getImplementationClass());
    }

    public static Set<Class<?>> getProviderContracts(Class<?> clazz) {
        Set<Class<?>> contracts = Collections.newSetFromMap(new IdentityHashMap());
        Providers.computeProviderContracts(clazz, contracts);
        return contracts;
    }

    private static void computeProviderContracts(Class<?> clazz, Set<Class<?>> contracts) {
        for (Class<?> contract : Providers.getImplementedContracts(clazz)) {
            if (Providers.isSupportedContract(contract)) {
                contracts.add(contract);
            }
            Providers.computeProviderContracts(contract, contracts);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean checkProviderRuntime(Class<?> component, ContractProvider model, RuntimeType runtimeConstraint, boolean scanned, boolean isResource) {
        RuntimeType componentConstraint;
        Set<Class<?>> contracts = model.getContracts();
        ConstrainedTo constrainedTo = component.getAnnotation(ConstrainedTo.class);
        RuntimeType runtimeType = componentConstraint = constrainedTo == null ? null : constrainedTo.value();
        if (Feature.class.isAssignableFrom(component)) {
            return true;
        }
        StringBuilder warnings = new StringBuilder();
        try {
            boolean isProviderRuntimeCompatible;
            boolean foundComponentCompatible = componentConstraint == null;
            boolean foundRuntimeCompatibleContract = isResource && runtimeConstraint == RuntimeType.SERVER;
            for (Class<?> contract : contracts) {
                RuntimeType contractConstraint = Providers.getContractConstraint(contract, componentConstraint);
                foundRuntimeCompatibleContract |= contractConstraint == null || contractConstraint == runtimeConstraint;
                if (componentConstraint == null) continue;
                if (contractConstraint != componentConstraint) {
                    warnings.append(LocalizationMessages.WARNING_PROVIDER_CONSTRAINED_TO_WRONG_PACKAGE(component.getName(), componentConstraint.name(), contract.getName(), contractConstraint.name())).append(" ");
                    continue;
                }
                foundComponentCompatible = true;
            }
            if (!foundComponentCompatible) {
                warnings.append(LocalizationMessages.ERROR_PROVIDER_CONSTRAINED_TO_WRONG_PACKAGE(component.getName(), componentConstraint.name())).append(" ");
                Providers.logProviderSkipped(warnings, component, isResource);
                boolean bl = false;
                return bl;
            }
            boolean bl = isProviderRuntimeCompatible = componentConstraint == null || componentConstraint == runtimeConstraint;
            if (!isProviderRuntimeCompatible && !scanned) {
                warnings.append(LocalizationMessages.ERROR_PROVIDER_CONSTRAINED_TO_WRONG_RUNTIME(component.getName(), componentConstraint.name(), runtimeConstraint.name())).append(" ");
                Providers.logProviderSkipped(warnings, component, isResource);
            }
            if (!foundRuntimeCompatibleContract && !scanned) {
                warnings.append(LocalizationMessages.ERROR_PROVIDER_REGISTERED_WRONG_RUNTIME(component.getName(), runtimeConstraint.name())).append(" ");
                Providers.logProviderSkipped(warnings, component, isResource);
                boolean bl2 = false;
                return bl2;
            }
            boolean bl3 = isProviderRuntimeCompatible && foundRuntimeCompatibleContract;
            return bl3;
        }
        finally {
            if (warnings.length() > 0) {
                LOGGER.log(Level.WARNING, warnings.toString());
            }
        }
    }

    private static void logProviderSkipped(StringBuilder sb, Class<?> provider, boolean alsoResourceClass) {
        sb.append(alsoResourceClass ? LocalizationMessages.ERROR_PROVIDER_AND_RESOURCE_CONSTRAINED_TO_IGNORED(provider.getName()) : LocalizationMessages.ERROR_PROVIDER_CONSTRAINED_TO_IGNORED(provider.getName())).append(" ");
    }

    public static boolean isSupportedContract(Class<?> type) {
        return EXTERNAL_PROVIDER_INTERFACE_WHITELIST.get(type) != null || type.isAnnotationPresent(Contract.class);
    }

    private static RuntimeType getContractConstraint(Class<?> clazz, RuntimeType defaultConstraint) {
        ConstrainedTo constrainedToAnnotation;
        ProviderRuntime jaxRsProvider = EXTERNAL_PROVIDER_INTERFACE_WHITELIST.get(clazz);
        RuntimeType result = null;
        if (jaxRsProvider != null) {
            result = jaxRsProvider.getRuntime();
        } else if (clazz.getAnnotation(Contract.class) != null && (constrainedToAnnotation = clazz.getAnnotation(ConstrainedTo.class)) != null) {
            result = constrainedToAnnotation.value();
        }
        return result == null ? defaultConstraint : result;
    }

    private static Iterable<Class<?>> getImplementedContracts(Class<?> clazz) {
        LinkedList list = new LinkedList();
        Collections.addAll(list, clazz.getInterfaces());
        Class<?> superclass = clazz.getSuperclass();
        if (superclass != null) {
            list.add(superclass);
        }
        return list;
    }

    public static boolean isProvider(Class<?> clazz) {
        return Providers.findFirstProviderContract(clazz);
    }

    public static void ensureContract(Class<?> contract, Class<?> ... implementations) {
        if (implementations == null || implementations.length <= 0) {
            return;
        }
        StringBuilder invalidClassNames = new StringBuilder();
        for (Class<?> impl : implementations) {
            if (contract.isAssignableFrom(impl)) continue;
            if (invalidClassNames.length() > 0) {
                invalidClassNames.append(", ");
            }
            invalidClassNames.append(impl.getName());
        }
        if (invalidClassNames.length() > 0) {
            throw new IllegalArgumentException(LocalizationMessages.INVALID_SPI_CLASSES(contract.getName(), invalidClassNames.toString()));
        }
    }

    private static boolean findFirstProviderContract(Class<?> clazz) {
        for (Class<?> contract : Providers.getImplementedContracts(clazz)) {
            if (Providers.isSupportedContract(contract)) {
                return true;
            }
            if (!Providers.findFirstProviderContract(contract)) continue;
            return true;
        }
        return false;
    }

    private static enum ProviderRuntime {
        BOTH(null),
        SERVER(RuntimeType.SERVER),
        CLIENT(RuntimeType.CLIENT);

        private final RuntimeType runtime;

        private ProviderRuntime(RuntimeType runtime) {
            this.runtime = runtime;
        }

        public RuntimeType getRuntime() {
            return this.runtime;
        }
    }
}

