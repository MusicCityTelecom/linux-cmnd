/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.convert.converter.ConverterRegistry
 */
package org.springframework.data.util;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;
import org.springframework.core.convert.converter.ConverterRegistry;

public interface CustomCollectionRegistrar {
    default public boolean isAvailable() {
        return true;
    }

    public Collection<Class<?>> getMapTypes();

    public Collection<Class<?>> getCollectionTypes();

    default public Collection<Class<?>> getAllowedPaginationReturnTypes() {
        return Collections.emptyList();
    }

    public void registerConvertersIn(ConverterRegistry var1);

    public Function<Object, Object> toJavaNativeCollection();
}

