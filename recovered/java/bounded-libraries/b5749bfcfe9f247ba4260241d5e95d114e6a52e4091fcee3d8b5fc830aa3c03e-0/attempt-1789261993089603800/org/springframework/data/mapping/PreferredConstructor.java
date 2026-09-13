/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.annotation.MergedAnnotations
 *  org.springframework.util.Assert
 *  org.springframework.util.ReflectionUtils
 */
package org.springframework.data.mapping;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mapping.InstanceCreatorMetadataSupport;
import org.springframework.data.mapping.Parameter;
import org.springframework.data.mapping.PersistentProperty;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

public final class PreferredConstructor<T, P extends PersistentProperty<P>>
extends InstanceCreatorMetadataSupport<T, P> {
    private final List<Parameter<Object, P>> parameters;

    @SafeVarargs
    public PreferredConstructor(Constructor<T> constructor, Parameter<Object, P> ... parameters) {
        super(constructor, parameters);
        ReflectionUtils.makeAccessible(constructor);
        this.parameters = Arrays.asList(parameters);
    }

    public Constructor<T> getConstructor() {
        return (Constructor)this.getExecutable();
    }

    public boolean isNoArgConstructor() {
        return !this.hasParameters();
    }

    public boolean isExplicitlyAnnotated() {
        return MergedAnnotations.from((AnnotatedElement)this.getExecutable()).isPresent(PersistenceConstructor.class);
    }

    @Deprecated
    public boolean isConstructorParameter(PersistentProperty<?> property) {
        return this.isCreatorParameter((PersistentProperty)property);
    }

    @Override
    public boolean isParentParameter(Parameter<?, P> parameter) {
        return this.isEnclosingClassParameter(parameter);
    }

    public boolean isEnclosingClassParameter(Parameter<?, P> parameter) {
        Assert.notNull(parameter, (String)"Parameter must not be null");
        if (this.parameters.isEmpty() || !parameter.isEnclosingClassParameter()) {
            return false;
        }
        return this.parameters.get(0).equals(parameter);
    }
}

