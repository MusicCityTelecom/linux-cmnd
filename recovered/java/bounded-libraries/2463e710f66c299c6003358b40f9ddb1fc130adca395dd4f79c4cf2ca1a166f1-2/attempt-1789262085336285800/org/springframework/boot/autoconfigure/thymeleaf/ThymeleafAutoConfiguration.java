/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.mxab.thymeleaf.extras.dataattribute.dialect.DataAttributeDialect
 *  javax.servlet.DispatcherType
 *  javax.servlet.Filter
 *  nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.boot.context.properties.PropertyMapper
 *  org.springframework.boot.web.servlet.FilterRegistrationBean
 *  org.springframework.boot.web.servlet.ServletRegistrationBean
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.Import
 *  org.springframework.core.io.support.ResourcePatternResolver
 *  org.springframework.security.web.server.csrf.CsrfToken
 *  org.springframework.util.MimeType
 *  org.springframework.util.unit.DataSize
 *  org.springframework.web.servlet.resource.ResourceUrlEncodingFilter
 *  org.thymeleaf.extras.java8time.dialect.Java8TimeDialect
 *  org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect
 *  org.thymeleaf.spring5.ISpringTemplateEngine
 *  org.thymeleaf.spring5.ISpringWebFluxTemplateEngine
 *  org.thymeleaf.spring5.SpringTemplateEngine
 *  org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver
 *  org.thymeleaf.spring5.view.ThymeleafViewResolver
 *  org.thymeleaf.spring5.view.reactive.ThymeleafReactiveViewResolver
 *  org.thymeleaf.templatemode.TemplateMode
 */
package org.springframework.boot.autoconfigure.thymeleaf;

import com.github.mxab.thymeleaf.extras.dataattribute.dialect.DataAttributeDialect;
import java.util.LinkedHashMap;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.template.TemplateLocation;
import org.springframework.boot.autoconfigure.thymeleaf.TemplateEngineConfigurations;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties;
import org.springframework.boot.autoconfigure.web.ConditionalOnEnabledResourceChain;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.ConditionalOnMissingFilterBean;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.util.MimeType;
import org.springframework.util.unit.DataSize;
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.ISpringTemplateEngine;
import org.thymeleaf.spring5.ISpringWebFluxTemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.spring5.view.reactive.ThymeleafReactiveViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

@AutoConfiguration(after={WebMvcAutoConfiguration.class, WebFluxAutoConfiguration.class})
@EnableConfigurationProperties(value={ThymeleafProperties.class})
@ConditionalOnClass(value={TemplateMode.class, SpringTemplateEngine.class})
@Import(value={TemplateEngineConfigurations.ReactiveTemplateEngineConfiguration.class, TemplateEngineConfigurations.DefaultTemplateEngineConfiguration.class})
public class ThymeleafAutoConfiguration {

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={Java8TimeDialect.class})
    static class ThymeleafJava8TimeDialect {
        ThymeleafJava8TimeDialect() {
        }

        @Bean
        @ConditionalOnMissingBean
        Java8TimeDialect java8TimeDialect() {
            return new Java8TimeDialect();
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={SpringSecurityDialect.class, CsrfToken.class})
    static class ThymeleafSecurityDialectConfiguration {
        ThymeleafSecurityDialectConfiguration() {
        }

        @Bean
        @ConditionalOnMissingBean
        SpringSecurityDialect securityDialect() {
            return new SpringSecurityDialect();
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={DataAttributeDialect.class})
    static class DataAttributeDialectConfiguration {
        DataAttributeDialectConfiguration() {
        }

        @Bean
        @ConditionalOnMissingBean
        DataAttributeDialect dialect() {
            return new DataAttributeDialect();
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={LayoutDialect.class})
    static class ThymeleafWebLayoutConfiguration {
        ThymeleafWebLayoutConfiguration() {
        }

        @Bean
        @ConditionalOnMissingBean
        LayoutDialect layoutDialect() {
            return new LayoutDialect();
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnWebApplication(type=ConditionalOnWebApplication.Type.REACTIVE)
    @ConditionalOnProperty(name={"spring.thymeleaf.enabled"}, matchIfMissing=true)
    static class ThymeleafWebFluxConfiguration {
        ThymeleafWebFluxConfiguration() {
        }

        @Bean
        @ConditionalOnMissingBean(name={"thymeleafReactiveViewResolver"})
        ThymeleafReactiveViewResolver thymeleafViewResolver(ISpringWebFluxTemplateEngine templateEngine, ThymeleafProperties properties) {
            ThymeleafReactiveViewResolver resolver = new ThymeleafReactiveViewResolver();
            resolver.setTemplateEngine(templateEngine);
            this.mapProperties(properties, resolver);
            this.mapReactiveProperties(properties.getReactive(), resolver);
            resolver.setOrder(0x7FFFFFFA);
            return resolver;
        }

        private void mapProperties(ThymeleafProperties properties, ThymeleafReactiveViewResolver resolver) {
            PropertyMapper map = PropertyMapper.get();
            map.from(properties::getEncoding).to(arg_0 -> ((ThymeleafReactiveViewResolver)resolver).setDefaultCharset(arg_0));
            resolver.setExcludedViewNames(properties.getExcludedViewNames());
            resolver.setViewNames(properties.getViewNames());
        }

        private void mapReactiveProperties(ThymeleafProperties.Reactive properties, ThymeleafReactiveViewResolver resolver) {
            PropertyMapper map = PropertyMapper.get();
            map.from(properties::getMediaTypes).whenNonNull().to(arg_0 -> ((ThymeleafReactiveViewResolver)resolver).setSupportedMediaTypes(arg_0));
            map.from(properties::getMaxChunkSize).asInt(DataSize::toBytes).when(size -> size > 0).to(arg_0 -> ((ThymeleafReactiveViewResolver)resolver).setResponseMaxChunkSizeBytes(arg_0));
            map.from(properties::getFullModeViewNames).to(arg_0 -> ((ThymeleafReactiveViewResolver)resolver).setFullModeViewNames(arg_0));
            map.from(properties::getChunkedModeViewNames).to(arg_0 -> ((ThymeleafReactiveViewResolver)resolver).setChunkedModeViewNames(arg_0));
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnWebApplication(type=ConditionalOnWebApplication.Type.SERVLET)
    @ConditionalOnProperty(name={"spring.thymeleaf.enabled"}, matchIfMissing=true)
    static class ThymeleafWebMvcConfiguration {
        ThymeleafWebMvcConfiguration() {
        }

        @Bean
        @ConditionalOnEnabledResourceChain
        @ConditionalOnMissingFilterBean(value={ResourceUrlEncodingFilter.class})
        FilterRegistrationBean<ResourceUrlEncodingFilter> resourceUrlEncodingFilter() {
            FilterRegistrationBean registration = new FilterRegistrationBean((Filter)new ResourceUrlEncodingFilter(), new ServletRegistrationBean[0]);
            registration.setDispatcherTypes(DispatcherType.REQUEST, new DispatcherType[]{DispatcherType.ERROR});
            return registration;
        }

        @Configuration(proxyBeanMethods=false)
        static class ThymeleafViewResolverConfiguration {
            ThymeleafViewResolverConfiguration() {
            }

            @Bean
            @ConditionalOnMissingBean(name={"thymeleafViewResolver"})
            ThymeleafViewResolver thymeleafViewResolver(ThymeleafProperties properties, SpringTemplateEngine templateEngine) {
                ThymeleafViewResolver resolver = new ThymeleafViewResolver();
                resolver.setTemplateEngine((ISpringTemplateEngine)templateEngine);
                resolver.setCharacterEncoding(properties.getEncoding().name());
                resolver.setContentType(this.appendCharset(properties.getServlet().getContentType(), resolver.getCharacterEncoding()));
                resolver.setProducePartialOutputWhileProcessing(properties.getServlet().isProducePartialOutputWhileProcessing());
                resolver.setExcludedViewNames(properties.getExcludedViewNames());
                resolver.setViewNames(properties.getViewNames());
                resolver.setOrder(0x7FFFFFFA);
                resolver.setCache(properties.isCache());
                return resolver;
            }

            private String appendCharset(MimeType type, String charset) {
                if (type.getCharset() != null) {
                    return type.toString();
                }
                LinkedHashMap<String, String> parameters = new LinkedHashMap<String, String>();
                parameters.put("charset", charset);
                parameters.putAll(type.getParameters());
                return new MimeType(type, parameters).toString();
            }
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnMissingBean(name={"defaultTemplateResolver"})
    static class DefaultTemplateResolverConfiguration {
        private static final Log logger = LogFactory.getLog(DefaultTemplateResolverConfiguration.class);
        private final ThymeleafProperties properties;
        private final ApplicationContext applicationContext;

        DefaultTemplateResolverConfiguration(ThymeleafProperties properties, ApplicationContext applicationContext) {
            this.properties = properties;
            this.applicationContext = applicationContext;
            this.checkTemplateLocationExists();
        }

        private void checkTemplateLocationExists() {
            TemplateLocation location;
            boolean checkTemplateLocation = this.properties.isCheckTemplateLocation();
            if (checkTemplateLocation && !(location = new TemplateLocation(this.properties.getPrefix())).exists((ResourcePatternResolver)this.applicationContext)) {
                logger.warn((Object)("Cannot find template location: " + location + " (please add some templates or check your Thymeleaf configuration)"));
            }
        }

        @Bean
        SpringResourceTemplateResolver defaultTemplateResolver() {
            SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
            resolver.setApplicationContext(this.applicationContext);
            resolver.setPrefix(this.properties.getPrefix());
            resolver.setSuffix(this.properties.getSuffix());
            resolver.setTemplateMode(this.properties.getMode());
            if (this.properties.getEncoding() != null) {
                resolver.setCharacterEncoding(this.properties.getEncoding().name());
            }
            resolver.setCacheable(this.properties.isCache());
            Integer order = this.properties.getTemplateResolverOrder();
            if (order != null) {
                resolver.setOrder(order);
            }
            resolver.setCheckExistence(this.properties.isCheckTemplate());
            return resolver;
        }
    }
}

