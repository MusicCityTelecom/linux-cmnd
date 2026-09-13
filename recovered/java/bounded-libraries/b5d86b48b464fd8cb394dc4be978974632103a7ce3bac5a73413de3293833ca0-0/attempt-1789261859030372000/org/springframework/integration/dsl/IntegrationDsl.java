/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.DslMarker
 */
package org.springframework.integration.dsl;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import kotlin.DslMarker;

@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.CLASS)
@Documented
@DslMarker
public @interface IntegrationDsl {
}

