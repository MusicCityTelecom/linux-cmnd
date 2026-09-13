/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.annotation.Conditional
 */
package org.springframework.boot.autoconfigure.data;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.autoconfigure.data.OnRepositoryTypeCondition;
import org.springframework.boot.autoconfigure.data.RepositoryType;
import org.springframework.context.annotation.Conditional;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE, ElementType.METHOD})
@Documented
@Conditional(value={OnRepositoryTypeCondition.class})
public @interface ConditionalOnRepositoryType {
    public String store();

    public RepositoryType type();
}

