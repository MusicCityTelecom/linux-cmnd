/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.context.properties.bind;

import java.lang.reflect.Constructor;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.DefaultBindConstructorProvider;

@FunctionalInterface
public interface BindConstructorProvider {
    public static final BindConstructorProvider DEFAULT = new DefaultBindConstructorProvider();

    public Constructor<?> getBindConstructor(Bindable<?> var1, boolean var2);
}

