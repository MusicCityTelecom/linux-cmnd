/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.context.properties;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.TYPE, ElementType.CONSTRUCTOR})
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
public @interface ConstructorBinding {
}

