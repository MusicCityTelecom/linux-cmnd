/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeanUtils
 *  org.springframework.core.KotlinDetector
 */
package org.springframework.boot.context.properties.bind;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.context.properties.bind.BindConstructorProvider;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.core.KotlinDetector;

class DefaultBindConstructorProvider
implements BindConstructorProvider {
    DefaultBindConstructorProvider() {
    }

    @Override
    public Constructor<?> getBindConstructor(Bindable<?> bindable, boolean isNestedConstructorBinding) {
        Class type = bindable.getType().resolve();
        if (bindable.getValue() != null || type == null) {
            return null;
        }
        if (KotlinDetector.isKotlinPresent() && KotlinDetector.isKotlinType((Class)type)) {
            return this.getDeducedKotlinConstructor(type);
        }
        Constructor<?>[] constructors = type.getDeclaredConstructors();
        if (constructors.length == 1 && constructors[0].getParameterCount() > 0) {
            return constructors[0];
        }
        Constructor<?> constructor = null;
        for (Constructor<?> candidate : constructors) {
            if (Modifier.isPrivate(candidate.getModifiers())) continue;
            if (constructor != null) {
                return null;
            }
            constructor = candidate;
        }
        if (constructor != null && constructor.getParameterCount() > 0) {
            return constructor;
        }
        return null;
    }

    private Constructor<?> getDeducedKotlinConstructor(Class<?> type) {
        Constructor primaryConstructor = BeanUtils.findPrimaryConstructor(type);
        if (primaryConstructor != null && primaryConstructor.getParameterCount() > 0) {
            return primaryConstructor;
        }
        return null;
    }
}

