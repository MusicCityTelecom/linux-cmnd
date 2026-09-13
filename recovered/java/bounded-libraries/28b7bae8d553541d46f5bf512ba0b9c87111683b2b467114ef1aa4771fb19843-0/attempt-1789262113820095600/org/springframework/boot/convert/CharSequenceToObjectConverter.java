/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.convert.ConversionService
 *  org.springframework.core.convert.TypeDescriptor
 *  org.springframework.core.convert.converter.ConditionalGenericConverter
 *  org.springframework.core.convert.converter.GenericConverter$ConvertiblePair
 */
package org.springframework.boot.convert;

import java.util.Collections;
import java.util.Set;
import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;
import org.springframework.core.convert.converter.GenericConverter;

class CharSequenceToObjectConverter
implements ConditionalGenericConverter {
    private static final TypeDescriptor STRING = TypeDescriptor.valueOf(String.class);
    private static final TypeDescriptor BYTE_ARRAY = TypeDescriptor.valueOf(byte[].class);
    private static final Set<GenericConverter.ConvertiblePair> TYPES = Collections.singleton(new GenericConverter.ConvertiblePair(CharSequence.class, Object.class));
    private final ThreadLocal<Boolean> disable = new ThreadLocal();
    private final ConversionService conversionService;

    CharSequenceToObjectConverter(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    public Set<GenericConverter.ConvertiblePair> getConvertibleTypes() {
        return TYPES;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (sourceType.getType() == String.class || this.disable.get() == Boolean.TRUE) {
            return false;
        }
        this.disable.set(Boolean.TRUE);
        try {
            boolean canDirectlyConvertCharSequence = this.conversionService.canConvert(sourceType, targetType);
            if (canDirectlyConvertCharSequence && !this.isStringConversionBetter(sourceType, targetType)) {
                boolean bl = false;
                return bl;
            }
            boolean bl = this.conversionService.canConvert(STRING, targetType);
            return bl;
        }
        finally {
            this.disable.set(null);
        }
    }

    private boolean isStringConversionBetter(TypeDescriptor sourceType, TypeDescriptor targetType) {
        ApplicationConversionService applicationConversionService;
        if (this.conversionService instanceof ApplicationConversionService && (applicationConversionService = (ApplicationConversionService)this.conversionService).isConvertViaObjectSourceType(sourceType, targetType)) {
            return true;
        }
        return (targetType.isArray() || targetType.isCollection()) && !targetType.equals((Object)BYTE_ARRAY);
    }

    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        return this.conversionService.convert((Object)source.toString(), STRING, targetType);
    }
}

