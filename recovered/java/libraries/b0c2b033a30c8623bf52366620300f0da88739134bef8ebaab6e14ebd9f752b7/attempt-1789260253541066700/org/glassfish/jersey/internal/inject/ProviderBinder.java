/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.inject;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.inject.Singleton;
import javax.ws.rs.RuntimeType;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.internal.inject.Binder;
import org.glassfish.jersey.internal.inject.ClassBinding;
import org.glassfish.jersey.internal.inject.CompositeBinder;
import org.glassfish.jersey.internal.inject.CustomAnnotationLiteral;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.internal.inject.InstanceBinding;
import org.glassfish.jersey.internal.inject.PerLookup;
import org.glassfish.jersey.internal.inject.Providers;
import org.glassfish.jersey.model.ContractProvider;
import org.glassfish.jersey.model.internal.ComponentBag;

public class ProviderBinder {
    private final InjectionManager injectionManager;

    public ProviderBinder(InjectionManager injectionManager) {
        this.injectionManager = injectionManager;
    }

    public static <T> void bindProvider(Class<T> providerClass, ContractProvider model, InjectionManager injectionManager) {
        T instance = injectionManager.getInstance(providerClass);
        if (instance != null) {
            injectionManager.register(ProviderBinder.createInstanceBinder(instance, model));
        } else {
            injectionManager.register(ProviderBinder.createClassBinder(model));
        }
    }

    private static <T> Binder createInstanceBinder(final T instance, final ContractProvider model) {
        return new AbstractBinder(){

            @Override
            protected void configure() {
                InstanceBinding binding = (InstanceBinding)((InstanceBinding)this.bind(instance).in(model.getScope())).qualifiedBy(CustomAnnotationLiteral.INSTANCE);
                binding.to(model.getContracts());
                int priority = model.getPriority(model.getImplementationClass());
                if (priority > -1) {
                    binding.ranked(priority);
                }
            }
        };
    }

    private static Binder createClassBinder(final ContractProvider model) {
        return new AbstractBinder(){

            @Override
            protected void configure() {
                ClassBinding binding = (ClassBinding)((ClassBinding)this.bind(model.getImplementationClass()).in(model.getScope())).qualifiedBy(CustomAnnotationLiteral.INSTANCE);
                binding.to(model.getContracts());
                int priority = model.getPriority(model.getImplementationClass());
                if (priority > -1) {
                    binding.ranked(priority);
                }
            }
        };
    }

    private static Collection<Binder> createProviderBinders(final Class<?> providerClass, final ContractProvider model) {
        Function<Class, Binder> binderFunction = contract -> new AbstractBinder((Class)contract){
            final /* synthetic */ Class val$contract;
            {
                this.val$contract = clazz2;
            }

            @Override
            protected void configure() {
                ClassBinding builder = (ClassBinding)((ClassBinding)((ClassBinding)this.bind(providerClass).in(model.getScope())).qualifiedBy(CustomAnnotationLiteral.INSTANCE)).to(this.val$contract);
                int priority = model.getPriority(this.val$contract);
                if (priority > -1) {
                    builder.ranked(priority);
                }
            }
        };
        return model.getContracts().stream().map(binderFunction).collect(Collectors.toList());
    }

    public static void bindProvider(Object providerInstance, ContractProvider model, InjectionManager injectionManager) {
        injectionManager.register(ProviderBinder.createInstanceBinder(providerInstance, model));
    }

    private static Collection<Binder> createProviderBinders(final Object providerInstance, ContractProvider model) {
        Function<Class, Binder> binderFunction = contract -> new AbstractBinder((Class)contract, model){
            final /* synthetic */ Class val$contract;
            final /* synthetic */ ContractProvider val$model;
            {
                this.val$contract = clazz;
                this.val$model = contractProvider;
            }

            @Override
            protected void configure() {
                InstanceBinding builder = (InstanceBinding)((InstanceBinding)this.bind(providerInstance).qualifiedBy(CustomAnnotationLiteral.INSTANCE)).to(this.val$contract);
                int priority = this.val$model.getPriority(this.val$contract);
                if (priority > -1) {
                    builder.ranked(priority);
                }
            }
        };
        return model.getContracts().stream().map(binderFunction).collect(Collectors.toList());
    }

    public static void bindProviders(ComponentBag componentBag, InjectionManager injectionManager) {
        ProviderBinder.bindProviders(componentBag, null, Collections.emptySet(), injectionManager);
    }

    public static void bindProviders(ComponentBag componentBag, RuntimeType constrainedTo, Set<Class<?>> registeredClasses, InjectionManager injectionManager) {
        Predicate<ContractProvider> filter = ComponentBag.EXCLUDE_EMPTY.and(ComponentBag.excludeMetaProviders(injectionManager));
        Predicate<Class> correctlyConfigured = componentClass -> Providers.checkProviderRuntime(componentClass, componentBag.getModel((Class<?>)componentClass), constrainedTo, registeredClasses == null || !registeredClasses.contains(componentClass), false);
        ArrayList<Binder> binderToRegister = new ArrayList<Binder>();
        Set<Class<Object>> classes = new LinkedHashSet(componentBag.getClasses(filter));
        if (constrainedTo != null) {
            classes = classes.stream().filter(correctlyConfigured).collect(Collectors.toSet());
        }
        for (Class clazz : classes) {
            ContractProvider model = componentBag.getModel(clazz);
            binderToRegister.addAll(ProviderBinder.createProviderBinders(clazz, model));
        }
        Set<Object> instances = componentBag.getInstances(filter);
        if (constrainedTo != null) {
            instances = instances.stream().filter(component -> correctlyConfigured.test(component.getClass())).collect(Collectors.toSet());
        }
        for (Object provider : instances) {
            ContractProvider model = componentBag.getModel(provider.getClass());
            binderToRegister.addAll(ProviderBinder.createProviderBinders(provider, model));
        }
        injectionManager.register(CompositeBinder.wrap(binderToRegister));
    }

    private static <T> Collection<Binder> createInstanceBinders(final T instance) {
        Function<Class, Binder> binderFunction = contract -> new AbstractBinder((Class)contract){
            final /* synthetic */ Class val$contract;
            {
                this.val$contract = clazz;
            }

            @Override
            protected void configure() {
                ((InstanceBinding)this.bind(instance).to(this.val$contract)).qualifiedBy(CustomAnnotationLiteral.INSTANCE);
            }
        };
        return Providers.getProviderContracts(instance.getClass()).stream().map(binderFunction).collect(Collectors.toList());
    }

    public void bindInstances(Iterable<Object> instances) {
        ArrayList<Object> instancesList = new ArrayList<Object>();
        instances.forEach(instancesList::add);
        this.bindInstances((Collection<Object>)instancesList);
    }

    public void bindInstances(Collection<Object> instances) {
        List<Binder> binders = instances.stream().map(ProviderBinder::createInstanceBinders).flatMap(Collection::stream).collect(Collectors.toList());
        this.injectionManager.register(CompositeBinder.wrap(binders));
    }

    public void bindClasses(Class<?> ... classes) {
        this.bindClasses(Arrays.asList(classes), false);
    }

    public void bindClasses(Iterable<Class<?>> classes) {
        ArrayList classesList = new ArrayList();
        classes.forEach(classesList::add);
        this.bindClasses(classesList, false);
    }

    public void bindClasses(Collection<Class<?>> classes) {
        this.bindClasses(classes, false);
    }

    public void bindClasses(Collection<Class<?>> classes, boolean bindResources) {
        List<Binder> binders = classes.stream().map(clazz -> this.createClassBinders((Class)clazz, bindResources)).collect(Collectors.toList());
        this.injectionManager.register(CompositeBinder.wrap(binders));
    }

    private <T> Binder createClassBinders(final Class<T> clazz, boolean isResource) {
        final Class<? extends Annotation> scope = this.getProviderScope(clazz);
        if (isResource) {
            return new AbstractBinder(){

                @Override
                protected void configure() {
                    ClassBinding descriptor = (ClassBinding)this.bindAsContract(clazz).in(scope);
                    for (Class<?> contract : Providers.getProviderContracts(clazz)) {
                        descriptor.addAlias(contract).in(scope.getName()).qualifiedBy(CustomAnnotationLiteral.INSTANCE);
                    }
                }
            };
        }
        return new AbstractBinder(){

            @Override
            protected void configure() {
                ClassBinding builder = (ClassBinding)((ClassBinding)this.bind(clazz).in(scope)).qualifiedBy(CustomAnnotationLiteral.INSTANCE);
                Providers.getProviderContracts(clazz).forEach(contract -> {
                    ClassBinding cfr_ignored_0 = (ClassBinding)builder.to(contract);
                });
            }
        };
    }

    private Class<? extends Annotation> getProviderScope(Class<?> clazz) {
        Class scope = Singleton.class;
        if (clazz.isAnnotationPresent(PerLookup.class)) {
            scope = PerLookup.class;
        }
        return scope;
    }
}

