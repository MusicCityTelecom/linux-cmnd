/*
 * Decompiled with CFR 0.152.
 */
package com.fasterxml.jackson.jaxrs.json.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(value=RetentionPolicy.RUNTIME)
@JacksonAnnotation
public @interface JSONP {
    public String value() default "";

    public String prefix() default "";

    public String suffix() default "";

    public static class Def {
        public final String method;
        public final String prefix;
        public final String suffix;

        public Def(String m) {
            this.method = m;
            this.prefix = null;
            this.suffix = null;
        }

        public Def(JSONP json) {
            this.method = Def.emptyAsNull(json.value());
            this.prefix = Def.emptyAsNull(json.prefix());
            this.suffix = Def.emptyAsNull(json.suffix());
        }

        private static final String emptyAsNull(String str) {
            if (str == null || str.length() == 0) {
                return null;
            }
            return str;
        }
    }
}

