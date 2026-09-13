/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.configuration.features.CasFeatureModule$FeatureCatalog
 *  org.springframework.context.annotation.Conditional
 */
package org.apereo.cas.util.spring.boot;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.apereo.cas.configuration.features.CasFeatureModule;
import org.apereo.cas.util.spring.boot.CasFeatureEnabledCondition;
import org.springframework.context.annotation.Conditional;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE, ElementType.METHOD})
@Documented
@Conditional(value={CasFeatureEnabledCondition.class})
public @interface ConditionalOnFeatureEnabled {
    public CasFeatureModule.FeatureCatalog feature();

    public String module() default "";

    public boolean enabledByDefault() default true;
}

