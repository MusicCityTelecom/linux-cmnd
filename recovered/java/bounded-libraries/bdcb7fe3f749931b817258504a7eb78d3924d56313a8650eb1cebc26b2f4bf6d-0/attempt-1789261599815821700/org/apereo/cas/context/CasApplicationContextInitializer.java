/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.configuration.CasConfigurationPropertiesValidator
 *  org.springframework.context.ApplicationContextInitializer
 *  org.springframework.context.ConfigurableApplicationContext
 */
package org.apereo.cas.context;

import java.util.List;
import org.apereo.cas.configuration.CasConfigurationPropertiesValidator;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class CasApplicationContextInitializer
implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    public static final String SYSTEM_PROPERTY_CONFIG_VALIDATION_STATUS = "CONFIG_VALIDATION_STATUS";

    public void initialize(ConfigurableApplicationContext applicationContext) {
        if (!Boolean.getBoolean("SKIP_CONFIG_VALIDATION")) {
            CasConfigurationPropertiesValidator validator = new CasConfigurationPropertiesValidator(applicationContext);
            List results = validator.validate();
            System.setProperty(SYSTEM_PROPERTY_CONFIG_VALIDATION_STATUS, Boolean.toString(results.isEmpty()));
        }
    }
}

