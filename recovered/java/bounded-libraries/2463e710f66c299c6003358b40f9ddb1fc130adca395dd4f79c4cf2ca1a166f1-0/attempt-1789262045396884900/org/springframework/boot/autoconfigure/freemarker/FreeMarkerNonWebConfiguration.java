/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.ui.freemarker.FreeMarkerConfigurationFactory
 *  org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean
 */
package org.springframework.boot.autoconfigure.freemarker;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;
import org.springframework.boot.autoconfigure.freemarker.AbstractFreeMarkerConfiguration;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

@Configuration(proxyBeanMethods=false)
@ConditionalOnNotWebApplication
class FreeMarkerNonWebConfiguration
extends AbstractFreeMarkerConfiguration {
    FreeMarkerNonWebConfiguration(FreeMarkerProperties properties) {
        super(properties);
    }

    @Bean
    @ConditionalOnMissingBean
    FreeMarkerConfigurationFactoryBean freeMarkerConfiguration() {
        FreeMarkerConfigurationFactoryBean freeMarkerFactoryBean = new FreeMarkerConfigurationFactoryBean();
        this.applyProperties((FreeMarkerConfigurationFactory)freeMarkerFactoryBean);
        return freeMarkerFactoryBean;
    }
}

