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
import org.glassfish.hk2.utilities.binding.NamedBindingBuilder;
import org.glassfish.hk2.utilities.binding.ScopedBindingBuilder;

public interface ServiceBindingBuilder<T>
extends BindingBuilder<T> {
    public ServiceBindingBuilder<T> to(Class<? super T> var1);

    public ServiceBindingBuilder<T> to(TypeLiteral<?> var1);

    public ServiceBindingBuilder<T> to(Type var1);

    public ServiceBindingBuilder<T> loadedBy(HK2Loader var1);

    public ServiceBindingBuilder<T> withMetadata(String var1, String var2);

    public ServiceBindingBuilder<T> withMetadata(String var1, List<String> var2);

    public ServiceBindingBuilder<T> qualifiedBy(Annotation var1);

    public ScopedBindingBuilder<T> in(Annotation var1);

    public ScopedBindingBuilder<T> in(Class<? extends Annotation> var1);

    public NamedBindingBuilder<T> named(String var1);

    public void ranked(int var1);

    public ServiceBindingBuilder<T> proxy(boolean var1);

    public ServiceBindingBuilder<T> proxyForSameScope(boolean var1);

    public ServiceBindingBuilder<T> analyzeWith(String var1);

    public ServiceBindingBuilder<T> asType(Type var1);
}

