/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.context.properties.bind;

import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.bind.DataObjectPropertyBinder;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;

interface DataObjectBinder {
    public <T> T bind(ConfigurationPropertyName var1, Bindable<T> var2, Binder.Context var3, DataObjectPropertyBinder var4);

    public <T> T create(Bindable<T> var1, Binder.Context var2);
}

