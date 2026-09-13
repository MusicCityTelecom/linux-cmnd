/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.convert.TypeDescriptor
 *  org.springframework.core.convert.converter.GenericConverter
 *  org.springframework.core.convert.converter.GenericConverter$ConvertiblePair
 *  org.springframework.util.ObjectUtils
 */
package org.springframework.boot.convert;

import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Set;
import org.springframework.boot.convert.PeriodFormat;
import org.springframework.boot.convert.PeriodStyle;
import org.springframework.boot.convert.PeriodUnit;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.util.ObjectUtils;

final class PeriodToStringConverter
implements GenericConverter {
    PeriodToStringConverter() {
    }

    public Set<GenericConverter.ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new GenericConverter.ConvertiblePair(Period.class, String.class));
    }

    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (ObjectUtils.isEmpty((Object)source)) {
            return null;
        }
        return this.convert((Period)source, this.getPeriodStyle(sourceType), this.getPeriodUnit(sourceType));
    }

    private PeriodStyle getPeriodStyle(TypeDescriptor sourceType) {
        PeriodFormat annotation = (PeriodFormat)sourceType.getAnnotation(PeriodFormat.class);
        return annotation != null ? annotation.value() : null;
    }

    private String convert(Period source, PeriodStyle style, ChronoUnit unit) {
        style = style != null ? style : PeriodStyle.ISO8601;
        return style.print(source, unit);
    }

    private ChronoUnit getPeriodUnit(TypeDescriptor sourceType) {
        PeriodUnit annotation = (PeriodUnit)sourceType.getAnnotation(PeriodUnit.class);
        return annotation != null ? annotation.value() : null;
    }
}

