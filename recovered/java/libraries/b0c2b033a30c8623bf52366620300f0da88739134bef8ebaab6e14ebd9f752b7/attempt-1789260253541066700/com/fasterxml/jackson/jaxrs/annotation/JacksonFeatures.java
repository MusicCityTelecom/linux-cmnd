/*
 * Decompiled with CFR 0.152.
 */
package com.fasterxml.jackson.jaxrs.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotation;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.PARAMETER})
@Retention(value=RetentionPolicy.RUNTIME)
@JacksonAnnotation
public @interface JacksonFeatures {
    public DeserializationFeature[] deserializationEnable() default {};

    public DeserializationFeature[] deserializationDisable() default {};

    public SerializationFeature[] serializationEnable() default {};

    public SerializationFeature[] serializationDisable() default {};
}

