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

final class StringToPeriodConverter
implements GenericConverter {
    StringToPeriodConverter() {
    }

    public Set<GenericConverter.ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new GenericConverter.ConvertiblePair(String.class, Period.class));
    }

    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (ObjectUtils.isEmpty((Object)source)) {
            return null;
        }
        return this.convert(source.toString(), this.getStyle(targetType), this.getPeriodUnit(targetType));
    }

    private PeriodStyle getStyle(TypeDescriptor targetType) {
        PeriodFormat annotation = (PeriodFormat)targetType.getAnnotation(PeriodFormat.class);
        return annotation != null ? annotation.value() : null;
    }

    private ChronoUnit getPeriodUnit(TypeDescriptor targetType) {
        PeriodUnit annotation = (PeriodUnit)targetType.getAnnotation(PeriodUnit.class);
        return annotation != null ? annotation.value() : null;
    }

    private Period convert(String source, PeriodStyle style, ChronoUnit unit) {
        style = style != null ? style : PeriodStyle.detect(source);
        return style.parse(source, unit);
    }
}

