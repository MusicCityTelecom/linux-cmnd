/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 */
package org.springframework.data.convert;

import org.springframework.data.convert.PropertyValueConverter;
import org.springframework.data.convert.ValueConversionContext;
import org.springframework.data.convert.ValueConverter;
import org.springframework.data.mapping.PersistentProperty;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

class AnnotatedPropertyValueConverterAccessor {
    private final ValueConverter annotation;

    public AnnotatedPropertyValueConverterAccessor(PersistentProperty<?> property) {
        Assert.notNull(property, (String)"PersistentProperty must not be null");
        this.annotation = property.findAnnotation(ValueConverter.class);
    }

    @Nullable
    public Class<? extends PropertyValueConverter<?, ?, ? extends ValueConversionContext<? extends PersistentProperty<?>>>> getValueConverterType() {
        return this.annotation != null ? this.annotation.value() : null;
    }

    public boolean hasValueConverter() {
        return this.annotation != null;
    }
}

