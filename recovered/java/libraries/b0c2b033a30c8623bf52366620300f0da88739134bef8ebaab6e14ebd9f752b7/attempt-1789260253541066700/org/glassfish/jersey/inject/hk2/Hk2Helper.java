/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.inject.hk2;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.DynamicConfiguration;
import org.glassfish.hk2.api.DynamicConfigurationService;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.PerLookup;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.AbstractActiveDescriptor;
import org.glassfish.hk2.utilities.ActiveDescriptorBuilder;
import org.glassfish.hk2.utilities.AliasDescriptor;
import org.glassfish.hk2.utilities.BuilderHelper;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.hk2.utilities.binding.ServiceBindingBuilder;
import org.glassfish.hk2.utilities.reflection.ParameterizedTypeImpl;
import org.glassfish.jersey.inject.hk2.AbstractHk2InjectionManager;
import org.glassfish.jersey.inject.hk2.InjectionResolverWrapper;
import org.glassfish.jersey.inject.hk2.InstanceSupplierFactoryBridge;
import org.glassfish.jersey.inject.hk2.SupplierFactoryBridge;
import org.glassfish.jersey.internal.LocalizationMessages;
import org.glassfish.jersey.internal.inject.AliasBinding;
import org.glassfish.jersey.internal.inject.Binder;
import org.glassfish.jersey.internal.inject.Binding;
import org.glassfish.jersey.internal.inject.Bindings;
import org.glassfish.jersey.internal.inject.ClassBinding;
import org.glassfish.jersey.internal.inject.DisposableSupplier;
import org.glassfish.jersey.internal.inject.InjectionResolverBinding;
import org.glassfish.jersey.internal.inject.InstanceBinding;
import org.glassfish.jersey.internal.inject.PerThread;
import org.glassfish.jersey.internal.inject.SupplierClassBinding;
import org.glassfish.jersey.internal.inject.SupplierInstanceBinding;

class Hk2Helper {
    Hk2Helper() {
    }

    static void bind(AbstractHk2InjectionManager injectionManager, Binder jerseyBinder) {
        Hk2Helper.bind(injectionManager.getServiceLocator(), Bindings.getBindings(injectionManager, jerseyBinder));
    }

    static void bind(ServiceLocator locator, Binding binding) {
        Hk2Helper.bindBinding(locator, binding);
    }

    static void bind(ServiceLocator locator, Iterable<Binding> descriptors) {
        DynamicConfiguration dc = Hk2Helper.getDynamicConfiguration(locator);
        for (Binding binding : descriptors) {
            Hk2Helper.bindBinding(locator, dc, binding);
        }
        dc.commit();
    }

    private static DynamicConfiguration getDynamicConfiguration(ServiceLocator locator) {
        DynamicConfigurationService dcs = locator.getService(DynamicConfigurationService.class, new Annotation[0]);
        return dcs.createDynamicConfiguration();
    }

    private static void bindBinding(ServiceLocator locator, Binding<?, ?> binding) {
        DynamicConfiguration dc = Hk2Helper.getDynamicConfiguration(locator);
        Hk2Helper.bindBinding(locator, dc, binding);
        dc.commit();
    }

    private static void bindBinding(ServiceLocator locator, DynamicConfiguration dc, Binding<?, ?> binding) {
        if (ClassBinding.class.isAssignableFrom(binding.getClass())) {
            ActiveDescriptor<?> activeDescriptor = Hk2Helper.translateToActiveDescriptor((ClassBinding)binding);
            Hk2Helper.bindBinding(locator, dc, activeDescriptor, binding.getAliases());
        } else if (InstanceBinding.class.isAssignableFrom(binding.getClass())) {
            ActiveDescriptor<?> activeDescriptor = Hk2Helper.translateToActiveDescriptor((InstanceBinding)binding, new Type[0]);
            Hk2Helper.bindBinding(locator, dc, activeDescriptor, binding.getAliases());
        } else if (InjectionResolverBinding.class.isAssignableFrom(binding.getClass())) {
            InjectionResolverBinding resolverDescriptor = (InjectionResolverBinding)binding;
            Hk2Helper.bindBinding(locator, dc, Hk2Helper.wrapInjectionResolver(resolverDescriptor), binding.getAliases());
            Hk2Helper.bindBinding(locator, dc, Hk2Helper.translateToActiveDescriptor(resolverDescriptor), binding.getAliases());
        } else if (SupplierClassBinding.class.isAssignableFrom(binding.getClass())) {
            Hk2Helper.bindSupplierClassBinding(locator, (SupplierClassBinding)binding);
        } else if (SupplierInstanceBinding.class.isAssignableFrom(binding.getClass())) {
            Hk2Helper.bindSupplierInstanceBinding(locator, (SupplierInstanceBinding)binding);
        } else {
            throw new RuntimeException(LocalizationMessages.UNKNOWN_DESCRIPTOR_TYPE(binding.getClass().getSimpleName()));
        }
    }

    private static ActiveDescriptor<?> wrapInjectionResolver(InjectionResolverBinding resolverDescriptor) {
        InjectionResolverWrapper wrappedResolver = new InjectionResolverWrapper(resolverDescriptor.getResolver());
        return Hk2Helper.translateToActiveDescriptor(Bindings.service(wrappedResolver), new ParameterizedTypeImpl((Type)((Object)InjectionResolver.class), resolverDescriptor.getResolver().getAnnotation()));
    }

    private static void bindSupplierInstanceBinding(ServiceLocator locator, SupplierInstanceBinding<?> binding) {
        Consumer<AbstractBinder> bindConsumer = binder -> {
            Supplier supplier = binding.getSupplier();
            boolean disposable = DisposableSupplier.class.isAssignableFrom(supplier.getClass());
            AbstractActiveDescriptor supplierBuilder = BuilderHelper.createConstantDescriptor(supplier);
            binding.getContracts().forEach(contract -> {
                supplierBuilder.addContractType(new ParameterizedTypeImpl((Type)((Object)Supplier.class), (Type)contract));
                if (disposable) {
                    supplierBuilder.addContractType(new ParameterizedTypeImpl((Type)((Object)DisposableSupplier.class), (Type)contract));
                }
            });
            supplierBuilder.setName(binding.getName());
            binding.getQualifiers().forEach(supplierBuilder::addQualifierAnnotation);
            binder.bind(supplierBuilder);
            ServiceBindingBuilder builder = binder.bindFactory(new InstanceSupplierFactoryBridge(supplier, disposable));
            Hk2Helper.setupSupplierFactoryBridge(binding, builder);
        };
        ServiceLocatorUtilities.bind(locator, Hk2Helper.createBinder(bindConsumer));
    }

    private static void bindSupplierClassBinding(ServiceLocator locator, SupplierClassBinding<?> binding) {
        Consumer<AbstractBinder> bindConsumer = binder -> {
            boolean disposable = DisposableSupplier.class.isAssignableFrom(binding.getSupplierClass());
            ServiceBindingBuilder supplierBuilder = binder.bind(binding.getSupplierClass());
            binding.getContracts().forEach(contract -> {
                supplierBuilder.to(new ParameterizedTypeImpl((Type)((Object)Supplier.class), (Type)contract));
                if (disposable) {
                    supplierBuilder.to(new ParameterizedTypeImpl((Type)((Object)DisposableSupplier.class), (Type)contract));
                }
            });
            binding.getQualifiers().forEach(supplierBuilder::qualifiedBy);
            supplierBuilder.named(binding.getName());
            supplierBuilder.in(Hk2Helper.transformScope(binding.getSupplierScope()));
            binder.bind(supplierBuilder);
            Type contract2 = null;
            if (binding.getContracts().iterator().hasNext()) {
                contract2 = binding.getContracts().iterator().next();
            }
            ServiceBindingBuilder builder = binder.bindFactory(new SupplierFactoryBridge(locator, contract2, binding.getName(), disposable));
            Hk2Helper.setupSupplierFactoryBridge(binding, builder);
            if (binding.getImplementationType() != null) {
                builder.asType(binding.getImplementationType());
            }
        };
        ServiceLocatorUtilities.bind(locator, Hk2Helper.createBinder(bindConsumer));
    }

    private static void setupSupplierFactoryBridge(Binding<?, ?> binding, ServiceBindingBuilder<?> builder) {
        builder.named(binding.getName());
        binding.getContracts().forEach(builder::to);
        binding.getQualifiers().forEach(builder::qualifiedBy);
        builder.in(Hk2Helper.transformScope(binding.getScope()));
        if (binding.getRank() != null) {
            builder.ranked(binding.getRank());
        }
        if (binding.isProxiable() != null) {
            builder.proxy(binding.isProxiable());
        }
        if (binding.isProxiedForSameScope() != null) {
            builder.proxyForSameScope(binding.isProxiedForSameScope());
        }
    }

    static ActiveDescriptor<?> translateToActiveDescriptor(ClassBinding<?> desc) {
        ActiveDescriptorBuilder binding = BuilderHelper.activeLink(desc.getService()).named(desc.getName()).analyzeWith(desc.getAnalyzer());
        if (desc.getScope() != null) {
            binding.in(Hk2Helper.transformScope(desc.getScope()));
        }
        if (desc.getRank() != null) {
            binding.ofRank(desc.getRank());
        }
        for (Annotation annotation : desc.getQualifiers()) {
            binding.qualifiedBy(annotation);
        }
        for (Type contract : desc.getContracts()) {
            binding.to(contract);
        }
        if (desc.isProxiable() != null) {
            binding.proxy(desc.isProxiable());
        }
        if (desc.isProxiedForSameScope() != null) {
            binding.proxyForSameScope(desc.isProxiedForSameScope());
        }
        if (desc.getImplementationType() != null) {
            binding.asType(desc.getImplementationType());
        }
        return binding.build();
    }

    private static void bindBinding(ServiceLocator locator, DynamicConfiguration dc, ActiveDescriptor<?> activeDescriptor, Set<AliasBinding> aliases) {
        ActiveDescriptor boundDescriptor = dc.bind(activeDescriptor);
        for (AliasBinding alias : aliases) {
            dc.bind(Hk2Helper.createAlias(locator, boundDescriptor, alias));
        }
    }

    static ActiveDescriptor<?> translateToActiveDescriptor(InstanceBinding<?> desc, Type ... contracts) {
        AbstractActiveDescriptor<?> binding = contracts.length == 0 ? BuilderHelper.createConstantDescriptor(desc.getService()) : BuilderHelper.createConstantDescriptor(desc.getService(), null, contracts);
        binding.setName(desc.getName());
        binding.setClassAnalysisName(desc.getAnalyzer());
        if (desc.getScope() != null) {
            binding.setScope(desc.getScope().getName());
        }
        if (desc.getRank() != null) {
            binding.setRanking(desc.getRank());
        }
        for (Annotation annotation : desc.getQualifiers()) {
            binding.addQualifierAnnotation(annotation);
        }
        for (Type contract : desc.getContracts()) {
            binding.addContractType(contract);
        }
        if (desc.isProxiable() != null) {
            binding.setProxiable(desc.isProxiable());
        }
        if (desc.isProxiedForSameScope() != null) {
            binding.setProxyForSameScope(desc.isProxiedForSameScope());
        }
        return binding;
    }

    private static ActiveDescriptor<?> translateToActiveDescriptor(InjectionResolverBinding<?> desc) {
        ParameterizedTypeImpl parameterizedType = new ParameterizedTypeImpl((Type)((Object)org.glassfish.jersey.internal.inject.InjectionResolver.class), desc.getResolver().getAnnotation());
        return BuilderHelper.createConstantDescriptor(desc.getResolver(), null, parameterizedType);
    }

    private static AliasDescriptor<?> createAlias(ServiceLocator locator, ActiveDescriptor<?> descriptor, AliasBinding alias) {
        AliasDescriptor hk2Alias = new AliasDescriptor(locator, descriptor, alias.getContract().getName(), null);
        alias.getQualifiers().forEach(hk2Alias::addQualifierAnnotation);
        alias.getScope().ifPresent(hk2Alias::setScope);
        alias.getRank().ifPresent(hk2Alias::setRanking);
        return hk2Alias;
    }

    private static org.glassfish.hk2.utilities.Binder createBinder(final Consumer<AbstractBinder> bindConsumer) {
        return new AbstractBinder(){

            @Override
            protected void configure() {
                bindConsumer.accept(this);
            }
        };
    }

    private static Class<? extends Annotation> transformScope(Class<? extends Annotation> scope) {
        if (scope == org.glassfish.jersey.internal.inject.PerLookup.class) {
            return PerLookup.class;
        }
        if (scope == PerThread.class) {
            return org.glassfish.hk2.api.PerThread.class;
        }
        return scope;
    }
}

