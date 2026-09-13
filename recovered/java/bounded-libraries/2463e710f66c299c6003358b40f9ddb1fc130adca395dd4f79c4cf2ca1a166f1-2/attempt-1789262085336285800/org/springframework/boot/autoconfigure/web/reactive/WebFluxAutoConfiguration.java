/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.factory.ListableBeanFactory
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.boot.convert.ApplicationConversionService
 *  org.springframework.boot.web.codec.CodecCustomizer
 *  org.springframework.boot.web.reactive.filter.OrderedHiddenHttpMethodFilter
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.Import
 *  org.springframework.core.annotation.Order
 *  org.springframework.format.FormatterRegistry
 *  org.springframework.format.support.FormattingConversionService
 *  org.springframework.http.codec.CodecConfigurer
 *  org.springframework.http.codec.ServerCodecConfigurer
 *  org.springframework.util.ClassUtils
 *  org.springframework.validation.Validator
 *  org.springframework.web.filter.reactive.HiddenHttpMethodFilter
 *  org.springframework.web.reactive.config.DelegatingWebFluxConfiguration
 *  org.springframework.web.reactive.config.ResourceHandlerRegistration
 *  org.springframework.web.reactive.config.ResourceHandlerRegistry
 *  org.springframework.web.reactive.config.ViewResolverRegistry
 *  org.springframework.web.reactive.config.WebFluxConfigurationSupport
 *  org.springframework.web.reactive.config.WebFluxConfigurer
 *  org.springframework.web.reactive.function.server.RouterFunction
 *  org.springframework.web.reactive.function.server.ServerResponse
 *  org.springframework.web.reactive.function.server.support.RouterFunctionMapping
 *  org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver
 *  org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer
 *  org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerAdapter
 *  org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping
 *  org.springframework.web.reactive.result.view.ViewResolver
 *  org.springframework.web.server.WebSession
 *  org.springframework.web.server.i18n.AcceptHeaderLocaleContextResolver
 *  org.springframework.web.server.i18n.FixedLocaleContextResolver
 *  org.springframework.web.server.i18n.LocaleContextResolver
 *  org.springframework.web.server.session.DefaultWebSessionManager
 *  org.springframework.web.server.session.InMemoryWebSessionStore
 *  org.springframework.web.server.session.WebSessionIdResolver
 *  org.springframework.web.server.session.WebSessionManager
 *  org.springframework.web.server.session.WebSessionStore
 *  reactor.core.publisher.Mono
 */
package org.springframework.boot.autoconfigure.web.reactive;

import java.time.Duration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.http.codec.CodecsAutoConfiguration;
import org.springframework.boot.autoconfigure.template.TemplateAvailabilityProviders;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.autoconfigure.validation.ValidatorAdapter;
import org.springframework.boot.autoconfigure.web.ConditionalOnEnabledResourceChain;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.format.DateTimeFormatters;
import org.springframework.boot.autoconfigure.web.format.WebConversionService;
import org.springframework.boot.autoconfigure.web.reactive.ReactiveMultipartAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.ReactiveWebServerFactoryAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.ResourceChainResourceHandlerRegistrationCustomizer;
import org.springframework.boot.autoconfigure.web.reactive.ResourceHandlerRegistrationCustomizer;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxProperties;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxRegistrations;
import org.springframework.boot.autoconfigure.web.reactive.WebSessionIdResolverAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.WelcomePageRouterFunctionFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.boot.web.codec.CodecCustomizer;
import org.springframework.boot.web.reactive.filter.OrderedHiddenHttpMethodFilter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.codec.CodecConfigurer;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.util.ClassUtils;
import org.springframework.validation.Validator;
import org.springframework.web.filter.reactive.HiddenHttpMethodFilter;
import org.springframework.web.reactive.config.DelegatingWebFluxConfiguration;
import org.springframework.web.reactive.config.ResourceHandlerRegistration;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.ViewResolverRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurationSupport;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.function.server.support.RouterFunctionMapping;
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver;
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.WebSession;
import org.springframework.web.server.i18n.AcceptHeaderLocaleContextResolver;
import org.springframework.web.server.i18n.FixedLocaleContextResolver;
import org.springframework.web.server.i18n.LocaleContextResolver;
import org.springframework.web.server.session.DefaultWebSessionManager;
import org.springframework.web.server.session.InMemoryWebSessionStore;
import org.springframework.web.server.session.WebSessionIdResolver;
import org.springframework.web.server.session.WebSessionManager;
import org.springframework.web.server.session.WebSessionStore;
import reactor.core.publisher.Mono;

@AutoConfiguration(after={ReactiveWebServerFactoryAutoConfiguration.class, CodecsAutoConfiguration.class, ReactiveMultipartAutoConfiguration.class, ValidationAutoConfiguration.class, WebSessionIdResolverAutoConfiguration.class})
@ConditionalOnWebApplication(type=ConditionalOnWebApplication.Type.REACTIVE)
@ConditionalOnClass(value={WebFluxConfigurer.class})
@ConditionalOnMissingBean(value={WebFluxConfigurationSupport.class})
@AutoConfigureOrder(value=-2147483638)
public class WebFluxAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(value={HiddenHttpMethodFilter.class})
    @ConditionalOnProperty(prefix="spring.webflux.hiddenmethod.filter", name={"enabled"})
    public OrderedHiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new OrderedHiddenHttpMethodFilter();
    }

    static final class MaxIdleTimeInMemoryWebSessionStore
    extends InMemoryWebSessionStore {
        private final Duration timeout;

        private MaxIdleTimeInMemoryWebSessionStore(Duration timeout) {
            this.timeout = timeout;
        }

        public Mono<WebSession> createWebSession() {
            return super.createWebSession().doOnSuccess(this::setMaxIdleTime);
        }

        private void setMaxIdleTime(WebSession session) {
            session.setMaxIdleTime(this.timeout);
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnEnabledResourceChain
    static class ResourceChainCustomizerConfiguration {
        ResourceChainCustomizerConfiguration() {
        }

        @Bean
        ResourceChainResourceHandlerRegistrationCustomizer resourceHandlerRegistrationCustomizer(WebProperties webProperties) {
            return new ResourceChainResourceHandlerRegistrationCustomizer(webProperties.getResources());
        }
    }

    @Configuration(proxyBeanMethods=false)
    @EnableConfigurationProperties(value={WebProperties.class, ServerProperties.class})
    public static class EnableWebFluxConfiguration
    extends DelegatingWebFluxConfiguration {
        private final WebFluxProperties webFluxProperties;
        private final WebProperties webProperties;
        private final ServerProperties serverProperties;
        private final WebFluxRegistrations webFluxRegistrations;

        public EnableWebFluxConfiguration(WebFluxProperties webFluxProperties, WebProperties webProperties, ServerProperties serverProperties, ObjectProvider<WebFluxRegistrations> webFluxRegistrations) {
            this.webFluxProperties = webFluxProperties;
            this.webProperties = webProperties;
            this.serverProperties = serverProperties;
            this.webFluxRegistrations = (WebFluxRegistrations)webFluxRegistrations.getIfUnique();
        }

        @Bean
        public FormattingConversionService webFluxConversionService() {
            WebFluxProperties.Format format = this.webFluxProperties.getFormat();
            WebConversionService conversionService = new WebConversionService(new DateTimeFormatters().dateFormat(format.getDate()).timeFormat(format.getTime()).dateTimeFormat(format.getDateTime()));
            this.addFormatters((FormatterRegistry)conversionService);
            return conversionService;
        }

        @Bean
        public Validator webFluxValidator() {
            if (!ClassUtils.isPresent((String)"javax.validation.Validator", (ClassLoader)((Object)((Object)this)).getClass().getClassLoader())) {
                return super.webFluxValidator();
            }
            return ValidatorAdapter.get(this.getApplicationContext(), this.getValidator());
        }

        protected RequestMappingHandlerAdapter createRequestMappingHandlerAdapter() {
            RequestMappingHandlerAdapter adapter;
            if (this.webFluxRegistrations != null && (adapter = this.webFluxRegistrations.getRequestMappingHandlerAdapter()) != null) {
                return adapter;
            }
            return super.createRequestMappingHandlerAdapter();
        }

        protected RequestMappingHandlerMapping createRequestMappingHandlerMapping() {
            RequestMappingHandlerMapping mapping;
            if (this.webFluxRegistrations != null && (mapping = this.webFluxRegistrations.getRequestMappingHandlerMapping()) != null) {
                return mapping;
            }
            return super.createRequestMappingHandlerMapping();
        }

        @Bean
        @ConditionalOnMissingBean(name={"localeContextResolver"})
        public LocaleContextResolver localeContextResolver() {
            if (this.webProperties.getLocaleResolver() == WebProperties.LocaleResolver.FIXED) {
                return new FixedLocaleContextResolver(this.webProperties.getLocale());
            }
            AcceptHeaderLocaleContextResolver localeContextResolver = new AcceptHeaderLocaleContextResolver();
            localeContextResolver.setDefaultLocale(this.webProperties.getLocale());
            return localeContextResolver;
        }

        @Bean
        @ConditionalOnMissingBean(name={"webSessionManager"})
        public WebSessionManager webSessionManager(ObjectProvider<WebSessionIdResolver> webSessionIdResolver) {
            DefaultWebSessionManager webSessionManager = new DefaultWebSessionManager();
            Duration timeout = this.serverProperties.getReactive().getSession().getTimeout();
            webSessionManager.setSessionStore((WebSessionStore)new MaxIdleTimeInMemoryWebSessionStore(timeout));
            webSessionIdResolver.ifAvailable(arg_0 -> ((DefaultWebSessionManager)webSessionManager).setSessionIdResolver(arg_0));
            return webSessionManager;
        }
    }

    @Configuration(proxyBeanMethods=false)
    @EnableConfigurationProperties(value={WebProperties.class, WebFluxProperties.class})
    @Import(value={EnableWebFluxConfiguration.class})
    @Order(value=0)
    public static class WebFluxConfig
    implements WebFluxConfigurer {
        private static final Log logger = LogFactory.getLog(WebFluxConfig.class);
        private final WebProperties.Resources resourceProperties;
        private final WebFluxProperties webFluxProperties;
        private final ListableBeanFactory beanFactory;
        private final ObjectProvider<HandlerMethodArgumentResolver> argumentResolvers;
        private final ObjectProvider<CodecCustomizer> codecCustomizers;
        private final ResourceHandlerRegistrationCustomizer resourceHandlerRegistrationCustomizer;
        private final ObjectProvider<ViewResolver> viewResolvers;

        public WebFluxConfig(WebProperties webProperties, WebFluxProperties webFluxProperties, ListableBeanFactory beanFactory, ObjectProvider<HandlerMethodArgumentResolver> resolvers, ObjectProvider<CodecCustomizer> codecCustomizers, ObjectProvider<ResourceHandlerRegistrationCustomizer> resourceHandlerRegistrationCustomizer, ObjectProvider<ViewResolver> viewResolvers) {
            this.resourceProperties = webProperties.getResources();
            this.webFluxProperties = webFluxProperties;
            this.beanFactory = beanFactory;
            this.argumentResolvers = resolvers;
            this.codecCustomizers = codecCustomizers;
            this.resourceHandlerRegistrationCustomizer = (ResourceHandlerRegistrationCustomizer)resourceHandlerRegistrationCustomizer.getIfAvailable();
            this.viewResolvers = viewResolvers;
        }

        public void configureArgumentResolvers(ArgumentResolverConfigurer configurer) {
            this.argumentResolvers.orderedStream().forEach(xva$0 -> configurer.addCustomResolver(new HandlerMethodArgumentResolver[]{xva$0}));
        }

        public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
            this.codecCustomizers.orderedStream().forEach(customizer -> customizer.customize((CodecConfigurer)configurer));
        }

        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            String staticPathPattern;
            if (!this.resourceProperties.isAddMappings()) {
                logger.debug((Object)"Default resource handling disabled");
                return;
            }
            if (!registry.hasMappingForPattern("/webjars/**")) {
                ResourceHandlerRegistration registration = registry.addResourceHandler(new String[]{"/webjars/**"}).addResourceLocations(new String[]{"classpath:/META-INF/resources/webjars/"});
                this.configureResourceCaching(registration);
                this.customizeResourceHandlerRegistration(registration);
            }
            if (!registry.hasMappingForPattern(staticPathPattern = this.webFluxProperties.getStaticPathPattern())) {
                ResourceHandlerRegistration registration = registry.addResourceHandler(new String[]{staticPathPattern}).addResourceLocations(this.resourceProperties.getStaticLocations());
                this.configureResourceCaching(registration);
                this.customizeResourceHandlerRegistration(registration);
            }
        }

        private void configureResourceCaching(ResourceHandlerRegistration registration) {
            Duration cachePeriod = this.resourceProperties.getCache().getPeriod();
            WebProperties.Resources.Cache.Cachecontrol cacheControl = this.resourceProperties.getCache().getCachecontrol();
            if (cachePeriod != null && cacheControl.getMaxAge() == null) {
                cacheControl.setMaxAge(cachePeriod);
            }
            registration.setCacheControl(cacheControl.toHttpCacheControl());
            registration.setUseLastModified(this.resourceProperties.getCache().isUseLastModified());
        }

        public void configureViewResolvers(ViewResolverRegistry registry) {
            this.viewResolvers.orderedStream().forEach(arg_0 -> ((ViewResolverRegistry)registry).viewResolver(arg_0));
        }

        public void addFormatters(FormatterRegistry registry) {
            ApplicationConversionService.addBeans((FormatterRegistry)registry, (ListableBeanFactory)this.beanFactory);
        }

        private void customizeResourceHandlerRegistration(ResourceHandlerRegistration registration) {
            if (this.resourceHandlerRegistrationCustomizer != null) {
                this.resourceHandlerRegistrationCustomizer.customize(registration);
            }
        }
    }

    @Configuration(proxyBeanMethods=false)
    public static class WelcomePageConfiguration {
        @Bean
        public RouterFunctionMapping welcomePageRouterFunctionMapping(ApplicationContext applicationContext, WebFluxProperties webFluxProperties, WebProperties webProperties) {
            String[] staticLocations = webProperties.getResources().getStaticLocations();
            WelcomePageRouterFunctionFactory factory = new WelcomePageRouterFunctionFactory(new TemplateAvailabilityProviders(applicationContext), applicationContext, staticLocations, webFluxProperties.getStaticPathPattern());
            RouterFunction<ServerResponse> routerFunction = factory.createRouterFunction();
            if (routerFunction != null) {
                RouterFunctionMapping routerFunctionMapping = new RouterFunctionMapping(routerFunction);
                routerFunctionMapping.setOrder(1);
                return routerFunctionMapping;
            }
            return null;
        }
    }
}

