/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.annotation.Conditional
 */
package org.springframework.boot.autoconfigure.condition;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.autoconfigure.condition.OnPropertyCondition;
import org.springframework.context.annotation.Conditional;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE, ElementType.METHOD})
@Documented
@Conditional(value={OnPropertyCondition.class})
public @interface ConditionalOnProperty {
    public String[] value() default {};

    public String prefix() default "";

    public String[] name() default {};

    public String havingValue() default "";

    public boolean matchIfMissing() default false;
}

