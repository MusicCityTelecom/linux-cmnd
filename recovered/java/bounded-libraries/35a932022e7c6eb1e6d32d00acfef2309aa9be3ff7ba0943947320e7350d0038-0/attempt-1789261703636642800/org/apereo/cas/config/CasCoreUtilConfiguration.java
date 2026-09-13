/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  javax.validation.MessageInterpolator
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.configuration.features.CasFeatureModule$FeatureCatalog
 *  org.apereo.cas.ticket.TicketCatalog
 *  org.apereo.cas.util.feature.CasRuntimeModuleLoader
 *  org.apereo.cas.util.feature.DefaultCasRuntimeModuleLoader
 *  org.apereo.cas.util.scripting.ExecutableCompiledGroovyScript
 *  org.apereo.cas.util.scripting.GroovyScriptResourceCacheManager
 *  org.apereo.cas.util.scripting.ScriptResourceCacheManager
 *  org.apereo.cas.util.serialization.JacksonObjectMapperFactory
 *  org.apereo.cas.util.spring.ApplicationContextProvider
 *  org.apereo.cas.util.spring.Converters$ZonedDateTimeToStringConverter
 *  org.apereo.cas.util.spring.SpringAwareMessageMessageInterpolator
 *  org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled
 *  org.apereo.cas.util.text.DefaultMessageSanitizer
 *  org.apereo.cas.util.text.MessageSanitationContributor
 *  org.apereo.cas.util.text.MessageSanitizer
 *  org.apereo.cas.util.text.TicketCatalogMessageSanitationContributor
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.beans.factory.annotation.Qualifier
 *  org.springframework.beans.factory.config.BeanPostProcessor
 *  org.springframework.boot.autoconfigure.AutoConfiguration
 *  org.springframework.boot.autoconfigure.AutoConfigureOrder
 *  org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.Scope
 *  org.springframework.context.annotation.ScopedProxyMode
 *  org.springframework.core.convert.converter.Converter
 *  org.springframework.core.convert.converter.ConverterRegistry
 *  org.springframework.core.convert.support.DefaultConversionService
 *  org.springframework.scheduling.annotation.EnableScheduling
 *  org.springframework.util.Assert
 *  org.springframework.validation.beanvalidation.BeanValidationPostProcessor
 */
package org.apereo.cas.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.validation.MessageInterpolator;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.features.CasFeatureModule;
import org.apereo.cas.ticket.TicketCatalog;
import org.apereo.cas.util.feature.CasRuntimeModuleLoader;
import org.apereo.cas.util.feature.DefaultCasRuntimeModuleLoader;
import org.apereo.cas.util.scripting.ExecutableCompiledGroovyScript;
import org.apereo.cas.util.scripting.GroovyScriptResourceCacheManager;
import org.apereo.cas.util.scripting.ScriptResourceCacheManager;
import org.apereo.cas.util.serialization.JacksonObjectMapperFactory;
import org.apereo.cas.util.spring.ApplicationContextProvider;
import org.apereo.cas.util.spring.Converters;
import org.apereo.cas.util.spring.SpringAwareMessageMessageInterpolator;
import org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled;
import org.apereo.cas.util.text.DefaultMessageSanitizer;
import org.apereo.cas.util.text.MessageSanitationContributor;
import org.apereo.cas.util.text.MessageSanitizer;
import org.apereo.cas.util.text.TicketCatalogMessageSanitationContributor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterRegistry;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.Assert;
import org.springframework.validation.beanvalidation.BeanValidationPostProcessor;

@AutoConfigureOrder(value=-2147483648)
@EnableScheduling
@EnableConfigurationProperties(value={CasConfigurationProperties.class})
@ConditionalOnFeatureEnabled(feature=CasFeatureModule.FeatureCatalog.Core)
@AutoConfiguration
public class CasCoreUtilConfiguration {
    @Bean
    @Scope(value="prototype")
    @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
    public ApplicationContextProvider casApplicationContextProvider() {
        return new ApplicationContextProvider();
    }

    @Configuration(value="CasCoreUtilEssentialConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreUtilEssentialConfiguration {
        @Bean
        @ConditionalOnMissingBean(name={"casBeanValidationPostProcessor"})
        public static BeanPostProcessor casBeanValidationPostProcessor() {
            return new BeanValidationPostProcessor();
        }

        @Bean
        @ConditionalOnMissingBean(name={"scriptResourceCacheManager"})
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public ScriptResourceCacheManager<String, ExecutableCompiledGroovyScript> scriptResourceCacheManager() {
            return new GroovyScriptResourceCacheManager();
        }

        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public CasRuntimeModuleLoader casRuntimeModuleLoader() {
            return new DefaultCasRuntimeModuleLoader();
        }

        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        @ConditionalOnMissingBean(name={"ticketCatalogMessageSanitationContributor"})
        public MessageSanitationContributor defaultMessageSanitationContributor(@Qualifier(value="ticketCatalog") ObjectProvider<TicketCatalog> ticketCatalog) {
            return new TicketCatalogMessageSanitationContributor(ticketCatalog);
        }

        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        @ConditionalOnMissingBean(name={"proxyGrantingTicketIouMessageSanitationContributor"})
        public MessageSanitationContributor proxyGrantingTicketIouMessageSanitationContributor() {
            return () -> List.of("PGTIOU");
        }

        @Bean
        @ConditionalOnMissingBean(name={"messageSanitizer"})
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public MessageSanitizer messageSanitizer(List<MessageSanitationContributor> contributors) {
            String prefixes = contributors.stream().map(MessageSanitationContributor::getTicketIdentifierPrefixes).filter(Objects::nonNull).flatMap(Collection::stream).collect(Collectors.joining("|"));
            Pattern pattern = Pattern.compile("(?:(?:" + prefixes + ")-\\d+-)([\\w.-]+)");
            return new DefaultMessageSanitizer(pattern);
        }
    }

    @Configuration(value="CasCoreUtilConverterConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreUtilConverterConfiguration {
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public MessageInterpolator messageInterpolator() {
            return new SpringAwareMessageMessageInterpolator();
        }

        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public Converter<ZonedDateTime, String> zonedDateTimeToStringConverter() {
            return new Converters.ZonedDateTimeToStringConverter();
        }

        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public ObjectMapper objectMapper() {
            return JacksonObjectMapperFactory.builder().build().toObjectMapper();
        }
    }

    @Configuration(value="CasCoreUtilContextConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreUtilContextConfiguration {
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public InitializingBean casCoreUtilInitialization(@Qualifier(value="casApplicationContextProvider") ApplicationContextProvider casApplicationContextProvider, @Qualifier(value="zonedDateTimeToStringConverter") Converter<ZonedDateTime, String> zonedDateTimeToStringConverter) {
            return () -> {
                Assert.notNull((Object)casApplicationContextProvider, (String)"Application context cannot be initialized");
                Assert.notNull((Object)ApplicationContextProvider.getConfigurableApplicationContext(), (String)"Application context cannot be initialized");
                ConverterRegistry registry = (ConverterRegistry)DefaultConversionService.getSharedInstance();
                registry.addConverter(zonedDateTimeToStringConverter);
            };
        }
    }
}

