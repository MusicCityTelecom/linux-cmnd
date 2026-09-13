/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.convert.TypeDescriptor
 *  org.springframework.core.convert.converter.GenericConverter
 *  org.springframework.core.convert.converter.GenericConverter$ConvertiblePair
 */
package org.springframework.boot.convert;

import java.time.Period;
import java.util.Collections;
import java.util.Set;
import org.springframework.boot.convert.StringToPeriodConverter;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

final class NumberToPeriodConverter
implements GenericConverter {
    private final StringToPeriodConverter delegate = new StringToPeriodConverter();

    NumberToPeriodConverter() {
    }

    public Set<GenericConverter.ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new GenericConverter.ConvertiblePair(Number.class, Period.class));
    }

    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        return this.delegate.convert((Object)(source != null ? source.toString() : null), TypeDescriptor.valueOf(String.class), targetType);
    }
}

