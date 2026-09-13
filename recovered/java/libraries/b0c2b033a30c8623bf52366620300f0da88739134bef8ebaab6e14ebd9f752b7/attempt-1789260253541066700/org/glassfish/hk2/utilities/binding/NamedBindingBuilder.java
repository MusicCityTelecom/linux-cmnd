/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.utilities.binding;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;
import org.glassfish.hk2.api.HK2Loader;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.BindingBuilder;
import org.glassfish.hk2.utilities.binding.ScopedNamedBindingBuilder;

public interface NamedBindingBuilder<T>
extends BindingBuilder<T> {
    public NamedBindingBuilder<T> to(Class<? super T> var1);

    public NamedBindingBuilder<T> to(TypeLiteral<?> var1);

    public NamedBindingBuilder<T> loadedBy(HK2Loader var1);

    public NamedBindingBuilder<T> withMetadata(String var1, String var2);

    public NamedBindingBuilder<T> withMetadata(String var1, List<String> var2);

    public NamedBindingBuilder<T> qualifiedBy(Annotation var1);

    public ScopedNamedBindingBuilder<T> in(Class<? extends Annotation> var1);

    public void ranked(int var1);

    public NamedBindingBuilder<T> proxy(boolean var1);

    public NamedBindingBuilder<T> asType(Type var1);
}

