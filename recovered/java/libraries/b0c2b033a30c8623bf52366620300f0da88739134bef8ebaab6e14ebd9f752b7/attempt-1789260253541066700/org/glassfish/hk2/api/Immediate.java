/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.inject.Scope;

@Documented
@Retention(value=RetentionPolicy.RUNTIME)
@Scope
@Target(value={ElementType.TYPE, ElementType.METHOD})
public @interface Immediate {
}

