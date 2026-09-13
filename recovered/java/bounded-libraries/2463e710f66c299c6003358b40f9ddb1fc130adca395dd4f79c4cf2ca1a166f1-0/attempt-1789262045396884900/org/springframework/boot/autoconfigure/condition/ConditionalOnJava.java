/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.system.JavaVersion
 *  org.springframework.context.annotation.Conditional
 */
package org.springframework.boot.autoconfigure.condition;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.autoconfigure.condition.OnJavaCondition;
import org.springframework.boot.system.JavaVersion;
import org.springframework.context.annotation.Conditional;

@Target(value={ElementType.TYPE, ElementType.METHOD})
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
@Conditional(value={OnJavaCondition.class})
public @interface ConditionalOnJava {
    public Range range() default Range.EQUAL_OR_NEWER;

    public JavaVersion value();

    public static enum Range {
        EQUAL_OR_NEWER,
        OLDER_THAN;

    }
}

