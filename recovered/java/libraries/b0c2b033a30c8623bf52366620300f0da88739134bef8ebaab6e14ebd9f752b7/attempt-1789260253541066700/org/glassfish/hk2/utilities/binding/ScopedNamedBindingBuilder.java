/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.utilities.binding;

import java.lang.annotation.Annotation;
import java.util.List;
import org.glassfish.hk2.api.HK2Loader;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.BindingBuilder;

public interface ScopedNamedBindingBuilder<T>
extends BindingBuilder<T> {
    public ScopedNamedBindingBuilder<T> to(Class<? super T> var1);

    public ScopedNamedBindingBuilder<T> to(TypeLiteral<?> var1);

    public ScopedNamedBindingBuilder<T> loadedBy(HK2Loader var1);

    public ScopedNamedBindingBuilder<T> withMetadata(String var1, String var2);

    public ScopedNamedBindingBuilder<T> withMetadata(String var1, List<String> var2);

    public ScopedNamedBindingBuilder<T> qualifiedBy(Annotation var1);

    public void ranked(int var1);

    public ScopedNamedBindingBuilder<T> proxy(boolean var1);

    public ScopedNamedBindingBuilder<T> analyzeWith(String var1);
}

