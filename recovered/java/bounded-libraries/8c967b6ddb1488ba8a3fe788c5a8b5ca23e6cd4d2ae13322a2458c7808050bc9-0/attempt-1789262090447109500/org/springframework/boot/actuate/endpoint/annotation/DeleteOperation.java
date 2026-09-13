/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.endpoint.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.actuate.endpoint.Producible;

@Target(value={ElementType.METHOD})
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
public @interface DeleteOperation {
    public String[] produces() default {};

    public Class<? extends Producible> producesFrom() default Producible.class;
}

