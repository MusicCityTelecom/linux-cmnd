/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.inject.hk2;

import java.lang.annotation.Annotation;
import javax.inject.Singleton;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.jersey.internal.inject.ForeignDescriptorImpl;
import org.glassfish.jersey.internal.inject.InjecteeImpl;
import org.glassfish.jersey.internal.util.ReflectionHelper;

@Singleton
public class InjectionResolverWrapper<T extends Annotation>
implements InjectionResolver<T> {
    private final org.glassfish.jersey.internal.inject.InjectionResolver jerseyResolver;

    InjectionResolverWrapper(org.glassfish.jersey.internal.inject.InjectionResolver<T> jerseyResolver) {
        this.jerseyResolver = jerseyResolver;
    }

    @Override
    public Object resolve(Injectee injectee, ServiceHandle root) {
        InjecteeImpl injecteeWrapper = new InjecteeImpl();
        injecteeWrapper.setRequiredType(injectee.getRequiredType());
        injecteeWrapper.setParent(injectee.getParent());
        injecteeWrapper.setRequiredQualifiers(injectee.getRequiredQualifiers());
        injecteeWrapper.setOptional(injectee.isOptional());
        injecteeWrapper.setPosition(injectee.getPosition());
        injecteeWrapper.setFactory(ReflectionHelper.isSubClassOf(injectee.getRequiredType(), Factory.class));
        injecteeWrapper.setInjecteeDescriptor(new ForeignDescriptorImpl(injectee.getInjecteeDescriptor()));
        Object instance = this.jerseyResolver.resolve(injecteeWrapper);
        if (injecteeWrapper.isFactory()) {
            return this.asFactory(instance);
        }
        return instance;
    }

    private Factory asFactory(final Object instance) {
        return new Factory(){

            public Object provide() {
                return instance;
            }

            public void dispose(Object instance2) {
            }
        };
    }

    @Override
    public boolean isConstructorParameterIndicator() {
        return this.jerseyResolver.isConstructorParameterIndicator();
    }

    @Override
    public boolean isMethodParameterIndicator() {
        return this.jerseyResolver.isMethodParameterIndicator();
    }
}

