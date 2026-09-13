/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.context.properties.bind;

import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;

@FunctionalInterface
interface AggregateElementBinder {
    default public Object bind(ConfigurationPropertyName name, Bindable<?> target) {
        return this.bind(name, target, null);
    }

    public Object bind(ConfigurationPropertyName var1, Bindable<?> var2, ConfigurationPropertySource var3);
}

