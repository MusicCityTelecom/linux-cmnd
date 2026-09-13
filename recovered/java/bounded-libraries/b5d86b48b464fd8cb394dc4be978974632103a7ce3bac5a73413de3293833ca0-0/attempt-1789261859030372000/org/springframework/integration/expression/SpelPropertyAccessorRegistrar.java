/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.expression.PropertyAccessor
 *  org.springframework.util.Assert
 */
package org.springframework.integration.expression;

import java.beans.Introspector;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.expression.PropertyAccessor;
import org.springframework.util.Assert;

public class SpelPropertyAccessorRegistrar {
    private final Map<String, PropertyAccessor> propertyAccessors = new LinkedHashMap<String, PropertyAccessor>();

    public SpelPropertyAccessorRegistrar() {
    }

    public SpelPropertyAccessorRegistrar(PropertyAccessor ... propertyAccessors) {
        Assert.notEmpty((Object[])propertyAccessors, (String)"'propertyAccessors' must not be empty");
        for (PropertyAccessor propertyAccessor : propertyAccessors) {
            this.propertyAccessors.put(SpelPropertyAccessorRegistrar.obtainAccessorKey(propertyAccessor), propertyAccessor);
        }
    }

    public SpelPropertyAccessorRegistrar(Map<String, PropertyAccessor> propertyAccessors) {
        Assert.notEmpty(propertyAccessors, (String)"'propertyAccessors' must not be empty");
        this.propertyAccessors.putAll(propertyAccessors);
    }

    public Map<String, PropertyAccessor> getPropertyAccessors() {
        return this.propertyAccessors;
    }

    public SpelPropertyAccessorRegistrar add(String name, PropertyAccessor propertyAccessor) {
        Assert.hasText((String)name, (String)"'name' must not be empty");
        Assert.notNull((Object)propertyAccessor, (String)"'propertyAccessor' must not be null");
        this.propertyAccessors.put(name, propertyAccessor);
        return this;
    }

    public SpelPropertyAccessorRegistrar add(PropertyAccessor ... propertyAccessors) {
        Assert.notEmpty((Object[])propertyAccessors, (String)"'propertyAccessors' must not be empty");
        for (PropertyAccessor propertyAccessor : propertyAccessors) {
            this.propertyAccessors.put(SpelPropertyAccessorRegistrar.obtainAccessorKey(propertyAccessor), propertyAccessor);
        }
        return this;
    }

    private static String obtainAccessorKey(PropertyAccessor propertyAccessor) {
        return Introspector.decapitalize(propertyAccessor.getClass().getSimpleName());
    }
}

