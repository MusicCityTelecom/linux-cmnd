/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  freemarker.template.Configuration
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.ui.freemarker.FreeMarkerConfigurationFactory
 *  org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfig
 *  org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfigurer
 *  org.springframework.web.reactive.result.view.freemarker.FreeMarkerViewResolver
 */
package org.springframework.boot.autoconfigure.freemarker;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.freemarker.AbstractFreeMarkerConfiguration;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerProperties;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfig;
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerViewResolver;

@Configuration(proxyBeanMethods=false)
@ConditionalOnWebApplication(type=ConditionalOnWebApplication.Type.REACTIVE)
@AutoConfigureAfter(value={WebFluxAutoConfiguration.class})
class FreeMarkerReactiveWebConfiguration
extends AbstractFreeMarkerConfiguration {
    FreeMarkerReactiveWebConfiguration(FreeMarkerProperties properties) {
        super(properties);
    }

    @Bean
    @ConditionalOnMissingBean(value={FreeMarkerConfig.class})
    FreeMarkerConfigurer freeMarkerConfigurer() {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        this.applyProperties((FreeMarkerConfigurationFactory)configurer);
        return configurer;
    }

    @Bean
    freemarker.template.Configuration freeMarkerConfiguration(FreeMarkerConfig configurer) {
        return configurer.getConfiguration();
    }

    @Bean
    @ConditionalOnMissingBean(name={"freeMarkerViewResolver"})
    @ConditionalOnProperty(name={"spring.freemarker.enabled"}, matchIfMissing=true)
    FreeMarkerViewResolver freeMarkerViewResolver() {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setPrefix(this.getProperties().getPrefix());
        resolver.setSuffix(this.getProperties().getSuffix());
        resolver.setRequestContextAttribute(this.getProperties().getRequestContextAttribute());
        resolver.setViewNames(this.getProperties().getViewNames());
        return resolver;
    }
}

