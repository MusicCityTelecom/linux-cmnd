/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.boot.web.client.RestTemplateBuilder
 *  org.springframework.boot.web.client.RestTemplateCustomizer
 *  org.springframework.boot.web.client.RestTemplateRequestCustomizer
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Conditional
 *  org.springframework.context.annotation.ConfigurationCondition$ConfigurationPhase
 *  org.springframework.context.annotation.Lazy
 *  org.springframework.web.client.RestTemplate
 */
package org.springframework.boot.autoconfigure.web.client;

import java.util.stream.Collectors;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.NoneNestedConditions;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.web.client.RestTemplateBuilderConfigurer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.boot.web.client.RestTemplateRequestCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.ConfigurationCondition;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.client.RestTemplate;

@AutoConfiguration(after={HttpMessageConvertersAutoConfiguration.class})
@ConditionalOnClass(value={RestTemplate.class})
@Conditional(value={NotReactiveWebApplicationCondition.class})
public class RestTemplateAutoConfiguration {
    @Bean
    @Lazy
    @ConditionalOnMissingBean
    public RestTemplateBuilderConfigurer restTemplateBuilderConfigurer(ObjectProvider<HttpMessageConverters> messageConverters, ObjectProvider<RestTemplateCustomizer> restTemplateCustomizers, ObjectProvider<RestTemplateRequestCustomizer<?>> restTemplateRequestCustomizers) {
        RestTemplateBuilderConfigurer configurer = new RestTemplateBuilderConfigurer();
        configurer.setHttpMessageConverters((HttpMessageConverters)messageConverters.getIfUnique());
        configurer.setRestTemplateCustomizers(restTemplateCustomizers.orderedStream().collect(Collectors.toList()));
        configurer.setRestTemplateRequestCustomizers(restTemplateRequestCustomizers.orderedStream().collect(Collectors.toList()));
        return configurer;
    }

    @Bean
    @Lazy
    @ConditionalOnMissingBean
    public RestTemplateBuilder restTemplateBuilder(RestTemplateBuilderConfigurer restTemplateBuilderConfigurer) {
        RestTemplateBuilder builder = new RestTemplateBuilder(new RestTemplateCustomizer[0]);
        return restTemplateBuilderConfigurer.configure(builder);
    }

    static class NotReactiveWebApplicationCondition
    extends NoneNestedConditions {
        NotReactiveWebApplicationCondition() {
            super(ConfigurationCondition.ConfigurationPhase.PARSE_CONFIGURATION);
        }

        @ConditionalOnWebApplication(type=ConditionalOnWebApplication.Type.REACTIVE)
        private static class ReactiveWebApplication {
            private ReactiveWebApplication() {
            }
        }
    }
}

