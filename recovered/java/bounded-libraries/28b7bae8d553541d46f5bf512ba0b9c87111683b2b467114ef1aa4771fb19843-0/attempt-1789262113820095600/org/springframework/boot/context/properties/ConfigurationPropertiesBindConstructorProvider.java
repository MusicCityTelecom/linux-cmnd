/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeanUtils
 *  org.springframework.core.KotlinDetector
 *  org.springframework.core.annotation.MergedAnnotations
 *  org.springframework.core.annotation.MergedAnnotations$SearchStrategy
 *  org.springframework.util.Assert
 */
package org.springframework.boot.context.properties;

import java.lang.reflect.Constructor;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.bind.BindConstructorProvider;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.core.KotlinDetector;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.util.Assert;

class ConfigurationPropertiesBindConstructorProvider
implements BindConstructorProvider {
    static final ConfigurationPropertiesBindConstructorProvider INSTANCE = new ConfigurationPropertiesBindConstructorProvider();

    ConfigurationPropertiesBindConstructorProvider() {
    }

    @Override
    public Constructor<?> getBindConstructor(Bindable<?> bindable, boolean isNestedConstructorBinding) {
        return this.getBindConstructor(bindable.getType().resolve(), isNestedConstructorBinding);
    }

    Constructor<?> getBindConstructor(Class<?> type, boolean isNestedConstructorBinding) {
        if (type == null) {
            return null;
        }
        Constructor<?> constructor = this.findConstructorBindingAnnotatedConstructor(type);
        if (constructor == null && (this.isConstructorBindingType(type) || isNestedConstructorBinding)) {
            constructor = this.deduceBindConstructor(type);
        }
        return constructor;
    }

    private Constructor<?> findConstructorBindingAnnotatedConstructor(Class<?> type) {
        Constructor constructor;
        if (this.isKotlinType(type) && (constructor = BeanUtils.findPrimaryConstructor(type)) != null) {
            return this.findAnnotatedConstructor(type, constructor);
        }
        return this.findAnnotatedConstructor(type, type.getDeclaredConstructors());
    }

    private Constructor<?> findAnnotatedConstructor(Class<?> type, Constructor<?> ... candidates) {
        Constructor<?> constructor = null;
        for (Constructor<?> candidate : candidates) {
            if (!MergedAnnotations.from(candidate).isPresent(ConstructorBinding.class)) continue;
            Assert.state((candidate.getParameterCount() > 0 ? 1 : 0) != 0, () -> type.getName() + " declares @ConstructorBinding on a no-args constructor");
            Assert.state((constructor == null ? 1 : 0) != 0, () -> type.getName() + " has more than one @ConstructorBinding constructor");
            constructor = candidate;
        }
        return constructor;
    }

    private boolean isConstructorBindingType(Class<?> type) {
        return this.isImplicitConstructorBindingType(type) || this.isConstructorBindingAnnotatedType(type);
    }

    private boolean isImplicitConstructorBindingType(Class<?> type) {
        Class<?> superclass = type.getSuperclass();
        return superclass != null && "java.lang.Record".equals(superclass.getName());
    }

    private boolean isConstructorBindingAnnotatedType(Class<?> type) {
        return MergedAnnotations.from(type, (MergedAnnotations.SearchStrategy)MergedAnnotations.SearchStrategy.TYPE_HIERARCHY_AND_ENCLOSING_CLASSES).isPresent(ConstructorBinding.class);
    }

    private Constructor<?> deduceBindConstructor(Class<?> type) {
        if (this.isKotlinType(type)) {
            return this.deducedKotlinBindConstructor(type);
        }
        Constructor<?>[] constructors = type.getDeclaredConstructors();
        if (constructors.length == 1 && constructors[0].getParameterCount() > 0) {
            return constructors[0];
        }
        return null;
    }

    private Constructor<?> deducedKotlinBindConstructor(Class<?> type) {
        Constructor primaryConstructor = BeanUtils.findPrimaryConstructor(type);
        if (primaryConstructor != null && primaryConstructor.getParameterCount() > 0) {
            return primaryConstructor;
        }
        return null;
    }

    private boolean isKotlinType(Class<?> type) {
        return KotlinDetector.isKotlinPresent() && KotlinDetector.isKotlinType(type);
    }
}

