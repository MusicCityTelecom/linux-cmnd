/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.data.convert;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.data.convert.PropertyValueConverter;

@Target(value={ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Documented
@Retention(value=RetentionPolicy.RUNTIME)
public @interface ValueConverter {
    public Class<? extends PropertyValueConverter> value() default PropertyValueConverter.ObjectToObjectPropertyValueConverter.class;
}

