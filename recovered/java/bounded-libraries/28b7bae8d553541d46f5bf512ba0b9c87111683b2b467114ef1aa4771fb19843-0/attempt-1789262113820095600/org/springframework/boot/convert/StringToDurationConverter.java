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

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Set;
import org.springframework.boot.convert.DurationFormat;
import org.springframework.boot.convert.DurationStyle;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.util.ObjectUtils;

final class StringToDurationConverter
implements GenericConverter {
    StringToDurationConverter() {
    }

    public Set<GenericConverter.ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new GenericConverter.ConvertiblePair(String.class, Duration.class));
    }

    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (ObjectUtils.isEmpty((Object)source)) {
            return null;
        }
        return this.convert(source.toString(), this.getStyle(targetType), this.getDurationUnit(targetType));
    }

    private DurationStyle getStyle(TypeDescriptor targetType) {
        DurationFormat annotation = (DurationFormat)targetType.getAnnotation(DurationFormat.class);
        return annotation != null ? annotation.value() : null;
    }

    private ChronoUnit getDurationUnit(TypeDescriptor targetType) {
        DurationUnit annotation = (DurationUnit)targetType.getAnnotation(DurationUnit.class);
        return annotation != null ? annotation.value() : null;
    }

    private Duration convert(String source, DurationStyle style, ChronoUnit unit) {
        style = style != null ? style : DurationStyle.detect(source);
        return style.parse(source, unit);
    }
}

