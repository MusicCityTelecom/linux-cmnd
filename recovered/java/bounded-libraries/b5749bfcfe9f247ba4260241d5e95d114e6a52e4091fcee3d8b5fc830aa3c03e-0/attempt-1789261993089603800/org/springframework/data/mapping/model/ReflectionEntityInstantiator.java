/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeanInstantiationException
 *  org.springframework.beans.BeanUtils
 *  org.springframework.util.ReflectionUtils
 */
package org.springframework.data.mapping.model;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mapping.FactoryMethod;
import org.springframework.data.mapping.InstanceCreatorMetadata;
import org.springframework.data.mapping.Parameter;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.mapping.PersistentProperty;
import org.springframework.data.mapping.PreferredConstructor;
import org.springframework.data.mapping.model.EntityInstantiator;
import org.springframework.data.mapping.model.MappingInstantiationException;
import org.springframework.data.mapping.model.ParameterValueProvider;
import org.springframework.util.ReflectionUtils;

enum ReflectionEntityInstantiator implements EntityInstantiator
{
    INSTANCE;

    private static final Object[] EMPTY_ARGS;

    @Override
    public <T, E extends PersistentEntity<? extends T, P>, P extends PersistentProperty<P>> T createInstance(E entity, ParameterValueProvider<P> provider) {
        InstanceCreatorMetadata<P> creator = entity.getInstanceCreatorMetadata();
        if (creator == null) {
            return this.instantiateClass(entity);
        }
        int parameterCount = creator.getParameterCount();
        Object[] params = parameterCount == 0 ? EMPTY_ARGS : new Object[parameterCount];
        int i = 0;
        for (Parameter<Object, P> parameter : creator.getParameters()) {
            params[i++] = provider.getParameterValue(parameter);
        }
        if (creator instanceof FactoryMethod) {
            FactoryMethod method = (FactoryMethod)creator;
            try {
                Object t = ReflectionUtils.invokeMethod((Method)method.getFactoryMethod(), null, (Object[])params);
                if (t == null) {
                    throw new IllegalStateException(String.format("Method %s returned null", method.getFactoryMethod()));
                }
                return (T)t;
            }
            catch (Exception e) {
                throw new MappingInstantiationException(entity, new ArrayList<Object>(Arrays.asList(params)), e);
            }
        }
        try {
            return (T)BeanUtils.instantiateClass(((PreferredConstructor)creator).getConstructor(), (Object[])params);
        }
        catch (BeanInstantiationException e) {
            throw new MappingInstantiationException(entity, new ArrayList<Object>(Arrays.asList(params)), (Exception)((Object)e));
        }
    }

    private <T, E extends PersistentEntity<? extends T, P>, P extends PersistentProperty<P>> T instantiateClass(E entity) {
        try {
            Class<T> clazz = entity.getType();
            if (clazz.isArray()) {
                Class<Object> ctype = clazz;
                int dims = 0;
                while (ctype.isArray()) {
                    ctype = ctype.getComponentType();
                    ++dims;
                }
                return (T)Array.newInstance(clazz, dims);
            }
            return (T)BeanUtils.instantiateClass(entity.getType());
        }
        catch (BeanInstantiationException e) {
            throw new MappingInstantiationException(entity, Collections.emptyList(), (Exception)((Object)e));
        }
    }

    static {
        EMPTY_ARGS = new Object[0];
    }
}

