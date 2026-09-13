/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.utilities.binding;

import java.lang.annotation.Annotation;
import java.util.List;
import org.glassfish.hk2.api.HK2Loader;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.BindingBuilder;
import org.glassfish.hk2.utilities.binding.ScopedNamedBindingBuilder;

public interface ScopedBindingBuilder<T>
extends BindingBuilder<T> {
    public ScopedBindingBuilder<T> to(Class<? super T> var1);

    public ScopedBindingBuilder<T> to(TypeLiteral<?> var1);

    public ScopedBindingBuilder<T> loadedBy(HK2Loader var1);

    public ScopedBindingBuilder<T> withMetadata(String var1, String var2);

    public ScopedBindingBuilder<T> withMetadata(String var1, List<String> var2);

    public ScopedBindingBuilder<T> qualifiedBy(Annotation var1);

    public ScopedNamedBindingBuilder<T> named(String var1);

    public void ranked(int var1);

    public ScopedBindingBuilder<T> proxy(boolean var1);

    public ScopedBindingBuilder<T> proxyForSameScope(boolean var1);

    public ScopedBindingBuilder<T> analyzeWith(String var1);
}

