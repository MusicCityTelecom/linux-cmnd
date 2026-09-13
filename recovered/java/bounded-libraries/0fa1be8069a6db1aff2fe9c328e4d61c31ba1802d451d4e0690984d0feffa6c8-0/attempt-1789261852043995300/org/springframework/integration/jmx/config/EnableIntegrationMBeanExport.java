/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.annotation.Import
 *  org.springframework.jmx.support.RegistrationPolicy
 */
package org.springframework.integration.jmx.config;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;
import org.springframework.integration.jmx.config.IntegrationMBeanExportConfiguration;
import org.springframework.jmx.support.RegistrationPolicy;

@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
@Import(value={IntegrationMBeanExportConfiguration.class})
public @interface EnableIntegrationMBeanExport {
    public String defaultDomain() default "";

    public String server() default "";

    public RegistrationPolicy registration() default RegistrationPolicy.FAIL_ON_EXISTING;

    public String[] managedComponents() default {"*"};
}

