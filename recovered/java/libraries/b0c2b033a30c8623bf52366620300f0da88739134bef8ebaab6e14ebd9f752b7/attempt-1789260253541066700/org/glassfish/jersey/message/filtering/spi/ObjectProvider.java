/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.filtering.spi;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import org.glassfish.jersey.spi.Contract;

@Contract
public interface ObjectProvider<T> {
    public T getFilteringObject(Type var1, boolean var2, Annotation ... var3);
}

