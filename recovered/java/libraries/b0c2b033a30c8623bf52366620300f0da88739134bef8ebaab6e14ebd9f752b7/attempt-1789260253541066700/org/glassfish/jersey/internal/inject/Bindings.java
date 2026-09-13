/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.inject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.function.Supplier;
import javax.ws.rs.core.GenericType;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.internal.inject.Binder;
import org.glassfish.jersey.internal.inject.Binding;
import org.glassfish.jersey.internal.inject.ClassBinding;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.internal.inject.InjectionResolver;
import org.glassfish.jersey.internal.inject.InjectionResolverBinding;
import org.glassfish.jersey.internal.inject.InstanceBinding;
import org.glassfish.jersey.internal.inject.SupplierClassBinding;
import org.glassfish.jersey.internal.inject.SupplierInstanceBinding;
import org.glassfish.jersey.internal.util.ReflectionHelper;

public final class Bindings {
    private Bindings() {
        throw new AssertionError((Object)"Utility class instantiation forbidden.");
    }

    public static Collection<Binding> getBindings(InjectionManager injectionManager, Binder binder) {
        if (binder instanceof AbstractBinder) {
            ((AbstractBinder)binder).setInjectionManager(injectionManager);
        }
        return binder.getBindings();
    }

    public static <T> ClassBinding<T> service(Class<T> serviceType) {
        return new ClassBinding<T>(serviceType);
    }

    public static <T> ClassBinding<T> serviceAsContract(Class<T> serviceType) {
        return (ClassBinding)new ClassBinding<T>(serviceType).to(serviceType);
    }

    public static <T> ClassBinding<T> service(GenericType<T> serviceType) {
        return (ClassBinding)new ClassBinding(serviceType.getRawType()).asType((Class)serviceType.getType());
    }

    public static <T> ClassBinding<T> serviceAsContract(GenericType<T> serviceType) {
        return (ClassBinding)((ClassBinding)new ClassBinding(serviceType.getRawType()).asType((Class)serviceType.getType())).to(serviceType.getType());
    }

    public static <T> ClassBinding<T> serviceAsContract(Type serviceType) {
        return (ClassBinding)((ClassBinding)new ClassBinding(ReflectionHelper.getRawClass(serviceType)).asType((Class)serviceType)).to(serviceType);
    }

    public static <T> InstanceBinding<T> service(T service) {
        return new InstanceBinding<T>(service);
    }

    public static <T> InstanceBinding<T> serviceAsContract(T service) {
        return new InstanceBinding<T>(service, service.getClass());
    }

    public static <T> SupplierClassBinding<T> supplier(Class<? extends Supplier<T>> supplierType, Class<? extends Annotation> supplierScope) {
        return new SupplierClassBinding(supplierType, supplierScope);
    }

    public static <T> SupplierClassBinding<T> supplier(Class<? extends Supplier<T>> supplierType) {
        return new SupplierClassBinding(supplierType, null);
    }

    public static <T> SupplierInstanceBinding<T> supplier(Supplier<T> supplier) {
        return new SupplierInstanceBinding<T>(supplier);
    }

    public static <T extends InjectionResolver> InjectionResolverBinding<T> injectionResolver(T resolver) {
        return new InjectionResolverBinding<T>(resolver);
    }
}

