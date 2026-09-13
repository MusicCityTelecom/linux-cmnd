/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 */
package org.springframework.data.convert;

import org.springframework.data.convert.PropertyValueConverter;
import org.springframework.data.convert.SimplePropertyValueConverterRegistry;
import org.springframework.data.convert.ValueConversionContext;
import org.springframework.data.mapping.PersistentProperty;
import org.springframework.lang.Nullable;

public interface ValueConverterRegistry<P extends PersistentProperty<P>> {
    public void registerConverter(Class<?> var1, String var2, PropertyValueConverter<?, ?, ? extends ValueConversionContext<P>> var3);

    @Nullable
    public <DV, SV> PropertyValueConverter<DV, SV, ? extends ValueConversionContext<P>> getConverter(Class<?> var1, String var2);

    default public boolean containsConverterFor(Class<?> type, String path) {
        return this.getConverter(type, path) != null;
    }

    public boolean isEmpty();

    public static <P extends PersistentProperty<P>> ValueConverterRegistry<P> simple() {
        return new SimplePropertyValueConverterRegistry();
    }
}

