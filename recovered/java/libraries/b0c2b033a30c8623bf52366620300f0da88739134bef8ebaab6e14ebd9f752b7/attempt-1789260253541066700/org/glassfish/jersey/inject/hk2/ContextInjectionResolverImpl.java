/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.inject.hk2;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.core.Context;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.AbstractActiveDescriptor;
import org.glassfish.hk2.utilities.BuilderHelper;
import org.glassfish.hk2.utilities.InjecteeImpl;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.internal.inject.ContextInjectionResolver;
import org.glassfish.jersey.internal.inject.ForeignRequestScopeBridge;
import org.glassfish.jersey.internal.util.ReflectionHelper;
import org.glassfish.jersey.internal.util.collection.Cache;
import org.glassfish.jersey.internal.util.collection.LazyValue;
import org.glassfish.jersey.internal.util.collection.Values;
import org.glassfish.jersey.process.internal.RequestScoped;

@Singleton
public class ContextInjectionResolverImpl
implements InjectionResolver<Context>,
ContextInjectionResolver {
    @Inject
    private ServiceLocator serviceLocator;
    private final Cache<CacheKey, ActiveDescriptor<?>> descriptorCache = new Cache<CacheKey, ActiveDescriptor>(cacheKey -> this.serviceLocator.getInjecteeDescriptor(((CacheKey)cacheKey).injectee));
    private final Cache<CacheKey, Injectee> foreignRequestScopedInjecteeCache = new Cache<CacheKey, Injectee>(new Function<CacheKey, Injectee>(){

        @Override
        public Injectee apply(CacheKey cacheKey) {
            Injectee injectee = cacheKey.getInjectee();
            if (injectee.getParent() != null && Field.class.isAssignableFrom(injectee.getParent().getClass())) {
                Field f = (Field)injectee.getParent();
                if (((Set)ContextInjectionResolverImpl.this.foreignRequestScopedComponents.get()).contains(f.getDeclaringClass())) {
                    Class<?> clazz = f.getType();
                    if (ContextInjectionResolverImpl.this.serviceLocator.getServiceHandle(clazz, new Annotation[0]).getActiveDescriptor().getScopeAnnotation() == RequestScoped.class) {
                        AbstractActiveDescriptor descriptor = BuilderHelper.activeLink(clazz).to(clazz).in(RequestScoped.class).build();
                        return new DescriptorOverridingInjectee(injectee, descriptor);
                    }
                }
            }
            return injectee;
        }
    });
    private LazyValue<Set<Class<?>>> foreignRequestScopedComponents = Values.lazy(this::getForeignRequestScopedComponents);

    @Override
    public Object resolve(Injectee injectee, ServiceHandle<?> root) {
        Type requiredType = injectee.getRequiredType();
        boolean isHk2Factory = ReflectionHelper.isSubClassOf(requiredType, Factory.class);
        Injectee newInjectee = isHk2Factory ? this.getFactoryInjectee(injectee, ReflectionHelper.getTypeArgument(requiredType, 0)) : this.foreignRequestScopedInjecteeCache.apply(new CacheKey(injectee));
        ActiveDescriptor<?> ad = this.descriptorCache.apply(new CacheKey(newInjectee));
        if (ad != null) {
            ServiceHandle<?> handle = this.serviceLocator.getServiceHandle(ad, newInjectee);
            if (isHk2Factory) {
                return this.asFactory(handle);
            }
            return handle.getService();
        }
        return null;
    }

    @Override
    public Object resolve(org.glassfish.jersey.internal.inject.Injectee injectee) {
        InjecteeImpl hk2injectee = ContextInjectionResolverImpl.toInjecteeImpl(injectee);
        return this.resolve(hk2injectee, null);
    }

    private static InjecteeImpl toInjecteeImpl(final org.glassfish.jersey.internal.inject.Injectee injectee) {
        InjecteeImpl hk2injectee = new InjecteeImpl(){

            @Override
            public Class<?> getInjecteeClass() {
                return injectee.getInjecteeClass();
            }
        };
        hk2injectee.setRequiredType(injectee.getRequiredType());
        hk2injectee.setRequiredQualifiers(injectee.getRequiredQualifiers());
        hk2injectee.setParent(injectee.getParent());
        if (injectee.getInjecteeDescriptor() != null) {
            hk2injectee.setInjecteeDescriptor((ActiveDescriptor)injectee.getInjecteeDescriptor().get());
        }
        return hk2injectee;
    }

    private Factory asFactory(final ServiceHandle handle) {
        return new Factory(){

            public Object provide() {
                return handle.getService();
            }

            public void dispose(Object instance) {
            }
        };
    }

    private Injectee getFactoryInjectee(Injectee injectee, Type requiredType) {
        return new RequiredTypeOverridingInjectee(injectee, requiredType);
    }

    @Override
    public boolean isConstructorParameterIndicator() {
        return true;
    }

    @Override
    public boolean isMethodParameterIndicator() {
        return false;
    }

    @Override
    public Class<Context> getAnnotation() {
        return Context.class;
    }

    private Set<Class<?>> getForeignRequestScopedComponents() {
        List<ForeignRequestScopeBridge> scopeBridges = this.serviceLocator.getAllServices(ForeignRequestScopeBridge.class, new Annotation[0]);
        HashSet result = new HashSet();
        for (ForeignRequestScopeBridge bridge : scopeBridges) {
            Set<Class<?>> requestScopedComponents = bridge.getRequestScopedComponents();
            if (requestScopedComponents == null) continue;
            result.addAll(requestScopedComponents);
        }
        return result;
    }

    private static class CacheKey {
        private final Injectee injectee;
        private final int hash;

        private CacheKey(Injectee injectee) {
            this.injectee = injectee;
            this.hash = Objects.hash(injectee.getInjecteeClass(), injectee.getInjecteeDescriptor(), injectee.getParent(), injectee.getRequiredQualifiers(), injectee.getRequiredType(), injectee.getPosition());
        }

        private Injectee getInjectee() {
            return this.injectee;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof CacheKey)) {
                return false;
            }
            CacheKey cacheKey = (CacheKey)o;
            return this.hash == cacheKey.hash;
        }

        public int hashCode() {
            return this.hash;
        }
    }

    private static class DescriptorOverridingInjectee
    extends InjecteeImpl {
        private DescriptorOverridingInjectee(Injectee injectee, ActiveDescriptor descriptor) {
            super(injectee);
            this.setInjecteeDescriptor(descriptor);
        }
    }

    private static class RequiredTypeOverridingInjectee
    extends InjecteeImpl {
        private RequiredTypeOverridingInjectee(Injectee injectee, Type requiredType) {
            super(injectee);
            this.setRequiredType(requiredType);
        }
    }

    public static final class Binder
    extends AbstractBinder {
        @Override
        protected void configure() {
            this.bind(ContextInjectionResolverImpl.class).to(new TypeLiteral<InjectionResolver<Context>>(){}).to(new TypeLiteral<org.glassfish.jersey.internal.inject.InjectionResolver<Context>>(){}).to(ContextInjectionResolver.class).in(Singleton.class);
        }
    }
}

