/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.annotation.Import
 */
package org.springframework.integration.config;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;
import org.springframework.integration.config.IntegrationManagementConfiguration;
import org.springframework.integration.support.management.micrometer.MicrometerMetricsCaptorImportSelector;

@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
@Import(value={MicrometerMetricsCaptorImportSelector.class, IntegrationManagementConfiguration.class})
public @interface EnableIntegrationManagement {
    @Deprecated
    public String[] metersEnabled() default {"*"};

    @Deprecated
    public String defaultCountsEnabled() default "false";

    public String defaultLoggingEnabled() default "true";
}

