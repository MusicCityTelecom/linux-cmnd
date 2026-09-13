/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.annotation.Conditional
 */
package org.springframework.boot.autoconfigure.condition;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.autoconfigure.condition.OnBeanCondition;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.context.annotation.Conditional;

@Target(value={ElementType.TYPE, ElementType.METHOD})
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
@Conditional(value={OnBeanCondition.class})
public @interface ConditionalOnBean {
    public Class<?>[] value() default {};

    public String[] type() default {};

    public Class<? extends Annotation>[] annotation() default {};

    public String[] name() default {};

    public SearchStrategy search() default SearchStrategy.ALL;

    public Class<?>[] parameterizedContainer() default {};
}

