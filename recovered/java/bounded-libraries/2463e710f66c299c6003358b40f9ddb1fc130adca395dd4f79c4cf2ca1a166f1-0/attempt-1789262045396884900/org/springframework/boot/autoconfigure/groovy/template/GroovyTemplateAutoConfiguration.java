/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.text.markup.MarkupTemplateEngine
 *  javax.servlet.Servlet
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.boot.context.properties.ConfigurationProperties
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.i18n.LocaleContextHolder
 *  org.springframework.core.io.support.ResourcePatternResolver
 *  org.springframework.core.log.LogMessage
 *  org.springframework.web.servlet.view.UrlBasedViewResolver
 *  org.springframework.web.servlet.view.groovy.GroovyMarkupConfig
 *  org.springframework.web.servlet.view.groovy.GroovyMarkupConfigurer
 *  org.springframework.web.servlet.view.groovy.GroovyMarkupViewResolver
 */
package org.springframework.boot.autoconfigure.groovy.template;

import groovy.text.markup.MarkupTemplateEngine;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import javax.servlet.Servlet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.groovy.template.GroovyTemplateProperties;
import org.springframework.boot.autoconfigure.template.TemplateLocation;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.log.LogMessage;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.groovy.GroovyMarkupConfig;
import org.springframework.web.servlet.view.groovy.GroovyMarkupConfigurer;
import org.springframework.web.servlet.view.groovy.GroovyMarkupViewResolver;

@AutoConfiguration(after={WebMvcAutoConfiguration.class})
@ConditionalOnClass(value={MarkupTemplateEngine.class})
@EnableConfigurationProperties(value={GroovyTemplateProperties.class})
public class GroovyTemplateAutoConfiguration {
    private static final Log logger = LogFactory.getLog(GroovyTemplateAutoConfiguration.class);

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={Servlet.class, LocaleContextHolder.class, UrlBasedViewResolver.class})
    @ConditionalOnWebApplication(type=ConditionalOnWebApplication.Type.SERVLET)
    @ConditionalOnProperty(name={"spring.groovy.template.enabled"}, matchIfMissing=true)
    public static class GroovyWebConfiguration {
        @Bean
        @ConditionalOnMissingBean(name={"groovyMarkupViewResolver"})
        public GroovyMarkupViewResolver groovyMarkupViewResolver(GroovyTemplateProperties properties) {
            GroovyMarkupViewResolver resolver = new GroovyMarkupViewResolver();
            properties.applyToMvcViewResolver(resolver);
            return resolver;
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={GroovyMarkupConfigurer.class})
    public static class GroovyMarkupConfiguration {
        private final ApplicationContext applicationContext;
        private final GroovyTemplateProperties properties;

        public GroovyMarkupConfiguration(ApplicationContext applicationContext, GroovyTemplateProperties properties) {
            this.applicationContext = applicationContext;
            this.properties = properties;
            this.checkTemplateLocationExists();
        }

        public void checkTemplateLocationExists() {
            TemplateLocation location;
            if (this.properties.isCheckTemplateLocation() && !this.isUsingGroovyAllJar() && !(location = new TemplateLocation(this.properties.getResourceLoaderPath())).exists((ResourcePatternResolver)this.applicationContext)) {
                logger.warn((Object)LogMessage.format((String)"Cannot find template location: %s (please add some templates, check your Groovy configuration, or set spring.groovy.template.check-template-location=false)", (Object)location));
            }
        }

        private boolean isUsingGroovyAllJar() {
            try {
                ProtectionDomain domain = MarkupTemplateEngine.class.getProtectionDomain();
                CodeSource codeSource = domain.getCodeSource();
                return codeSource != null && codeSource.getLocation().toString().contains("-all");
            }
            catch (Exception ex) {
                return false;
            }
        }

        @Bean
        @ConditionalOnMissingBean(value={GroovyMarkupConfig.class})
        @ConfigurationProperties(prefix="spring.groovy.template.configuration")
        public GroovyMarkupConfigurer groovyMarkupConfigurer(ObjectProvider<MarkupTemplateEngine> templateEngine) {
            GroovyMarkupConfigurer configurer = new GroovyMarkupConfigurer();
            configurer.setResourceLoaderPath(this.properties.getResourceLoaderPath());
            configurer.setCacheTemplates(this.properties.isCache());
            templateEngine.ifAvailable(arg_0 -> ((GroovyMarkupConfigurer)configurer).setTemplateEngine(arg_0));
            return configurer;
        }
    }
}

