/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.Servlet
 *  javax.servlet.ServletContext
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.ListableBeanFactory
 *  org.springframework.beans.factory.NoSuchBeanDefinitionException
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.beans.factory.annotation.Qualifier
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.boot.convert.ApplicationConversionService
 *  org.springframework.boot.web.servlet.ServletRegistrationBean
 *  org.springframework.boot.web.servlet.filter.OrderedFormContentFilter
 *  org.springframework.boot.web.servlet.filter.OrderedHiddenHttpMethodFilter
 *  org.springframework.boot.web.servlet.filter.OrderedRequestContextFilter
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ResourceLoaderAware
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.Import
 *  org.springframework.core.annotation.Order
 *  org.springframework.core.io.Resource
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.core.task.AsyncTaskExecutor
 *  org.springframework.format.FormatterRegistry
 *  org.springframework.format.support.FormattingConversionService
 *  org.springframework.http.MediaType
 *  org.springframework.http.converter.HttpMessageConverter
 *  org.springframework.util.ClassUtils
 *  org.springframework.validation.DefaultMessageCodesResolver
 *  org.springframework.validation.MessageCodeFormatter
 *  org.springframework.validation.MessageCodesResolver
 *  org.springframework.validation.Validator
 *  org.springframework.web.HttpMediaTypeNotAcceptableException
 *  org.springframework.web.accept.ContentNegotiationManager
 *  org.springframework.web.accept.ContentNegotiationStrategy
 *  org.springframework.web.accept.PathExtensionContentNegotiationStrategy
 *  org.springframework.web.bind.support.ConfigurableWebBindingInitializer
 *  org.springframework.web.context.ServletContextAware
 *  org.springframework.web.context.request.NativeWebRequest
 *  org.springframework.web.context.request.RequestContextListener
 *  org.springframework.web.context.support.ServletContextResource
 *  org.springframework.web.filter.FormContentFilter
 *  org.springframework.web.filter.HiddenHttpMethodFilter
 *  org.springframework.web.filter.RequestContextFilter
 *  org.springframework.web.servlet.DispatcherServlet
 *  org.springframework.web.servlet.FlashMapManager
 *  org.springframework.web.servlet.HandlerExceptionResolver
 *  org.springframework.web.servlet.LocaleResolver
 *  org.springframework.web.servlet.ThemeResolver
 *  org.springframework.web.servlet.View
 *  org.springframework.web.servlet.ViewResolver
 *  org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer
 *  org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer
 *  org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration
 *  org.springframework.web.servlet.config.annotation.PathMatchConfigurer
 *  org.springframework.web.servlet.config.annotation.ResourceChainRegistration
 *  org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration
 *  org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
 *  org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport
 *  org.springframework.web.servlet.config.annotation.WebMvcConfigurer
 *  org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver
 *  org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver
 *  org.springframework.web.servlet.i18n.FixedLocaleResolver
 *  org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver
 *  org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter
 *  org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping
 *  org.springframework.web.servlet.resource.EncodedResourceResolver
 *  org.springframework.web.servlet.resource.ResourceResolver
 *  org.springframework.web.servlet.resource.ResourceUrlProvider
 *  org.springframework.web.servlet.resource.VersionResourceResolver
 *  org.springframework.web.servlet.view.BeanNameViewResolver
 *  org.springframework.web.servlet.view.ContentNegotiatingViewResolver
 *  org.springframework.web.servlet.view.InternalResourceViewResolver
 *  org.springframework.web.util.UrlPathHelper
 *  org.springframework.web.util.pattern.PathPatternParser
 */
package org.springframework.boot.autoconfigure.web.servlet;

import java.time.Duration;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.function.Consumer;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration;
import org.springframework.boot.autoconfigure.template.TemplateAvailabilityProviders;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.autoconfigure.validation.ValidatorAdapter;
import org.springframework.boot.autoconfigure.web.ConditionalOnEnabledResourceChain;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.format.DateTimeFormatters;
import org.springframework.boot.autoconfigure.web.format.WebConversionService;
import org.springframework.boot.autoconfigure.web.servlet.ConditionalOnMissingFilterBean;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletPath;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.boot.autoconfigure.web.servlet.WelcomePageHandlerMapping;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.filter.OrderedFormContentFilter;
import org.springframework.boot.web.servlet.filter.OrderedHiddenHttpMethodFilter;
import org.springframework.boot.web.servlet.filter.OrderedRequestContextFilter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.ClassUtils;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodeFormatter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.PathExtensionContentNegotiationStrategy;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.filter.FormContentFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.filter.RequestContextFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ThemeResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceChainRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.resource.EncodedResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolver;
import org.springframework.web.servlet.resource.ResourceUrlProvider;
import org.springframework.web.servlet.resource.VersionResourceResolver;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.util.UrlPathHelper;
import org.springframework.web.util.pattern.PathPatternParser;

@AutoConfiguration(after={DispatcherServletAutoConfiguration.class, TaskExecutionAutoConfiguration.class, ValidationAutoConfiguration.class})
@ConditionalOnWebApplication(type=ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnClass(value={Servlet.class, DispatcherServlet.class, WebMvcConfigurer.class})
@ConditionalOnMissingBean(value={WebMvcConfigurationSupport.class})
@AutoConfigureOrder(value=-2147483638)
public class WebMvcAutoConfiguration {
    public static final String DEFAULT_PREFIX = "";
    public static final String DEFAULT_SUFFIX = "";
    public static final PathPatternParser pathPatternParser = new PathPatternParser();
    private static final String SERVLET_LOCATION = "/";

    @Bean
    @ConditionalOnMissingBean(value={HiddenHttpMethodFilter.class})
    @ConditionalOnProperty(prefix="spring.mvc.hiddenmethod.filter", name={"enabled"})
    public OrderedHiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new OrderedHiddenHttpMethodFilter();
    }

    @Bean
    @ConditionalOnMissingBean(value={FormContentFilter.class})
    @ConditionalOnProperty(prefix="spring.mvc.formcontent.filter", name={"enabled"}, matchIfMissing=true)
    public OrderedFormContentFilter formContentFilter() {
        return new OrderedFormContentFilter();
    }

    static class OptionalPathExtensionContentNegotiationStrategy
    implements ContentNegotiationStrategy {
        private static final String SKIP_ATTRIBUTE = PathExtensionContentNegotiationStrategy.class.getName() + ".SKIP";
        private final ContentNegotiationStrategy delegate;

        OptionalPathExtensionContentNegotiationStrategy(ContentNegotiationStrategy delegate) {
            this.delegate = delegate;
        }

        public List<MediaType> resolveMediaTypes(NativeWebRequest webRequest) throws HttpMediaTypeNotAcceptableException {
            Object skip = webRequest.getAttribute(SKIP_ATTRIBUTE, 0);
            if (skip != null && Boolean.parseBoolean(skip.toString())) {
                return MEDIA_TYPE_ALL_LIST;
            }
            return this.delegate.resolveMediaTypes(webRequest);
        }
    }

    static class ResourceChainResourceHandlerRegistrationCustomizer
    implements ResourceHandlerRegistrationCustomizer {
        private final WebProperties.Resources resourceProperties;

        ResourceChainResourceHandlerRegistrationCustomizer(WebProperties.Resources resourceProperties) {
            this.resourceProperties = resourceProperties;
        }

        @Override
        public void customize(ResourceHandlerRegistration registration) {
            WebProperties.Resources.Chain properties = this.resourceProperties.getChain();
            this.configureResourceChain(properties, registration.resourceChain(properties.isCache()));
        }

        private void configureResourceChain(WebProperties.Resources.Chain properties, ResourceChainRegistration chain) {
            WebProperties.Resources.Chain.Strategy strategy = properties.getStrategy();
            if (properties.isCompressed()) {
                chain.addResolver((ResourceResolver)new EncodedResourceResolver());
            }
            if (strategy.getFixed().isEnabled() || strategy.getContent().isEnabled()) {
                chain.addResolver(this.getVersionResourceResolver(strategy));
            }
        }

        private ResourceResolver getVersionResourceResolver(WebProperties.Resources.Chain.Strategy properties) {
            VersionResourceResolver resolver = new VersionResourceResolver();
            if (properties.getFixed().isEnabled()) {
                String version = properties.getFixed().getVersion();
                String[] paths = properties.getFixed().getPaths();
                resolver.addFixedVersionStrategy(version, paths);
            }
            if (properties.getContent().isEnabled()) {
                String[] paths = properties.getContent().getPaths();
                resolver.addContentVersionStrategy(paths);
            }
            return resolver;
        }
    }

    static interface ResourceHandlerRegistrationCustomizer {
        public void customize(ResourceHandlerRegistration var1);
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
    @EnableConfigurationProperties(value={WebProperties.class})
    public static class EnableWebMvcConfiguration
    extends DelegatingWebMvcConfiguration
    implements ResourceLoaderAware {
        private final WebProperties.Resources resourceProperties;
        private final WebMvcProperties mvcProperties;
        private final WebProperties webProperties;
        private final ListableBeanFactory beanFactory;
        private final WebMvcRegistrations mvcRegistrations;
        private ResourceLoader resourceLoader;

        public EnableWebMvcConfiguration(WebMvcProperties mvcProperties, WebProperties webProperties, ObjectProvider<WebMvcRegistrations> mvcRegistrationsProvider, ObjectProvider<ResourceHandlerRegistrationCustomizer> resourceHandlerRegistrationCustomizerProvider, ListableBeanFactory beanFactory) {
            this.resourceProperties = webProperties.getResources();
            this.mvcProperties = mvcProperties;
            this.webProperties = webProperties;
            this.mvcRegistrations = (WebMvcRegistrations)mvcRegistrationsProvider.getIfUnique();
            this.beanFactory = beanFactory;
        }

        @Bean
        public RequestMappingHandlerAdapter requestMappingHandlerAdapter(@Qualifier(value="mvcContentNegotiationManager") ContentNegotiationManager contentNegotiationManager, @Qualifier(value="mvcConversionService") FormattingConversionService conversionService, @Qualifier(value="mvcValidator") Validator validator) {
            RequestMappingHandlerAdapter adapter = super.requestMappingHandlerAdapter(contentNegotiationManager, conversionService, validator);
            adapter.setIgnoreDefaultModelOnRedirect(this.mvcProperties == null || this.mvcProperties.isIgnoreDefaultModelOnRedirect());
            return adapter;
        }

        protected RequestMappingHandlerAdapter createRequestMappingHandlerAdapter() {
            RequestMappingHandlerAdapter adapter;
            if (this.mvcRegistrations != null && (adapter = this.mvcRegistrations.getRequestMappingHandlerAdapter()) != null) {
                return adapter;
            }
            return super.createRequestMappingHandlerAdapter();
        }

        @Bean
        public WelcomePageHandlerMapping welcomePageHandlerMapping(ApplicationContext applicationContext, FormattingConversionService mvcConversionService, ResourceUrlProvider mvcResourceUrlProvider) {
            WelcomePageHandlerMapping welcomePageHandlerMapping = new WelcomePageHandlerMapping(new TemplateAvailabilityProviders(applicationContext), applicationContext, this.getWelcomePage(), this.mvcProperties.getStaticPathPattern());
            welcomePageHandlerMapping.setInterceptors(this.getInterceptors(mvcConversionService, mvcResourceUrlProvider));
            welcomePageHandlerMapping.setCorsConfigurations(this.getCorsConfigurations());
            return welcomePageHandlerMapping;
        }

        @Bean
        @ConditionalOnMissingBean(name={"localeResolver"})
        public LocaleResolver localeResolver() {
            if (this.webProperties.getLocaleResolver() == WebProperties.LocaleResolver.FIXED) {
                return new FixedLocaleResolver(this.webProperties.getLocale());
            }
            AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
            localeResolver.setDefaultLocale(this.webProperties.getLocale());
            return localeResolver;
        }

        @Bean
        @ConditionalOnMissingBean(name={"themeResolver"})
        public ThemeResolver themeResolver() {
            return super.themeResolver();
        }

        @Bean
        @ConditionalOnMissingBean(name={"flashMapManager"})
        public FlashMapManager flashMapManager() {
            return super.flashMapManager();
        }

        private Resource getWelcomePage() {
            for (String location : this.resourceProperties.getStaticLocations()) {
                Resource indexHtml = this.getIndexHtml(location);
                if (indexHtml == null) continue;
                return indexHtml;
            }
            ServletContext servletContext = this.getServletContext();
            if (servletContext != null) {
                return this.getIndexHtml((Resource)new ServletContextResource(servletContext, WebMvcAutoConfiguration.SERVLET_LOCATION));
            }
            return null;
        }

        private Resource getIndexHtml(String location) {
            return this.getIndexHtml(this.resourceLoader.getResource(location));
        }

        private Resource getIndexHtml(Resource location) {
            try {
                Resource resource = location.createRelative("index.html");
                if (resource.exists() && resource.getURL() != null) {
                    return resource;
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
            return null;
        }

        @Bean
        public FormattingConversionService mvcConversionService() {
            WebMvcProperties.Format format = this.mvcProperties.getFormat();
            WebConversionService conversionService = new WebConversionService(new DateTimeFormatters().dateFormat(format.getDate()).timeFormat(format.getTime()).dateTimeFormat(format.getDateTime()));
            this.addFormatters((FormatterRegistry)conversionService);
            return conversionService;
        }

        @Bean
        public Validator mvcValidator() {
            if (!ClassUtils.isPresent((String)"javax.validation.Validator", (ClassLoader)((Object)((Object)this)).getClass().getClassLoader())) {
                return super.mvcValidator();
            }
            return ValidatorAdapter.get(this.getApplicationContext(), this.getValidator());
        }

        protected RequestMappingHandlerMapping createRequestMappingHandlerMapping() {
            RequestMappingHandlerMapping mapping;
            if (this.mvcRegistrations != null && (mapping = this.mvcRegistrations.getRequestMappingHandlerMapping()) != null) {
                return mapping;
            }
            return super.createRequestMappingHandlerMapping();
        }

        protected ConfigurableWebBindingInitializer getConfigurableWebBindingInitializer(FormattingConversionService mvcConversionService, Validator mvcValidator) {
            try {
                return (ConfigurableWebBindingInitializer)this.beanFactory.getBean(ConfigurableWebBindingInitializer.class);
            }
            catch (NoSuchBeanDefinitionException ex) {
                return super.getConfigurableWebBindingInitializer(mvcConversionService, mvcValidator);
            }
        }

        protected ExceptionHandlerExceptionResolver createExceptionHandlerExceptionResolver() {
            ExceptionHandlerExceptionResolver resolver;
            if (this.mvcRegistrations != null && (resolver = this.mvcRegistrations.getExceptionHandlerExceptionResolver()) != null) {
                return resolver;
            }
            return super.createExceptionHandlerExceptionResolver();
        }

        protected void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
            super.extendHandlerExceptionResolvers(exceptionResolvers);
            if (this.mvcProperties.isLogResolvedException()) {
                for (HandlerExceptionResolver resolver : exceptionResolvers) {
                    if (!(resolver instanceof AbstractHandlerExceptionResolver)) continue;
                    ((AbstractHandlerExceptionResolver)resolver).setWarnLogCategory(resolver.getClass().getName());
                }
            }
        }

        @Bean
        public ContentNegotiationManager mvcContentNegotiationManager() {
            ContentNegotiationManager manager = super.mvcContentNegotiationManager();
            List strategies = manager.getStrategies();
            ListIterator<OptionalPathExtensionContentNegotiationStrategy> iterator = strategies.listIterator();
            while (iterator.hasNext()) {
                ContentNegotiationStrategy strategy = (ContentNegotiationStrategy)iterator.next();
                if (!(strategy instanceof PathExtensionContentNegotiationStrategy)) continue;
                iterator.set(new OptionalPathExtensionContentNegotiationStrategy(strategy));
            }
            return manager;
        }

        public void setResourceLoader(ResourceLoader resourceLoader) {
            this.resourceLoader = resourceLoader;
        }
    }

    @Configuration(proxyBeanMethods=false)
    @Import(value={EnableWebMvcConfiguration.class})
    @EnableConfigurationProperties(value={WebMvcProperties.class, WebProperties.class})
    @Order(value=0)
    public static class WebMvcAutoConfigurationAdapter
    implements WebMvcConfigurer,
    ServletContextAware {
        private static final Log logger = LogFactory.getLog(WebMvcConfigurer.class);
        private final WebProperties.Resources resourceProperties;
        private final WebMvcProperties mvcProperties;
        private final ListableBeanFactory beanFactory;
        private final ObjectProvider<HttpMessageConverters> messageConvertersProvider;
        private final ObjectProvider<DispatcherServletPath> dispatcherServletPath;
        private final ObjectProvider<ServletRegistrationBean<?>> servletRegistrations;
        private final ResourceHandlerRegistrationCustomizer resourceHandlerRegistrationCustomizer;
        private ServletContext servletContext;

        public WebMvcAutoConfigurationAdapter(WebProperties webProperties, WebMvcProperties mvcProperties, ListableBeanFactory beanFactory, ObjectProvider<HttpMessageConverters> messageConvertersProvider, ObjectProvider<ResourceHandlerRegistrationCustomizer> resourceHandlerRegistrationCustomizerProvider, ObjectProvider<DispatcherServletPath> dispatcherServletPath, ObjectProvider<ServletRegistrationBean<?>> servletRegistrations) {
            this.resourceProperties = webProperties.getResources();
            this.mvcProperties = mvcProperties;
            this.beanFactory = beanFactory;
            this.messageConvertersProvider = messageConvertersProvider;
            this.resourceHandlerRegistrationCustomizer = (ResourceHandlerRegistrationCustomizer)resourceHandlerRegistrationCustomizerProvider.getIfAvailable();
            this.dispatcherServletPath = dispatcherServletPath;
            this.servletRegistrations = servletRegistrations;
            this.mvcProperties.checkConfiguration();
        }

        public void setServletContext(ServletContext servletContext) {
            this.servletContext = servletContext;
        }

        public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
            this.messageConvertersProvider.ifAvailable(customConverters -> converters.addAll(customConverters.getConverters()));
        }

        public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
            Duration timeout;
            Object taskExecutor;
            if (this.beanFactory.containsBean("applicationTaskExecutor") && (taskExecutor = this.beanFactory.getBean("applicationTaskExecutor")) instanceof AsyncTaskExecutor) {
                configurer.setTaskExecutor((AsyncTaskExecutor)taskExecutor);
            }
            if ((timeout = this.mvcProperties.getAsync().getRequestTimeout()) != null) {
                configurer.setDefaultTimeout(timeout.toMillis());
            }
        }

        public void configurePathMatch(PathMatchConfigurer configurer) {
            if (this.mvcProperties.getPathmatch().getMatchingStrategy() == WebMvcProperties.MatchingStrategy.PATH_PATTERN_PARSER) {
                configurer.setPatternParser(pathPatternParser);
            }
            configurer.setUseSuffixPatternMatch(Boolean.valueOf(this.mvcProperties.getPathmatch().isUseSuffixPattern()));
            configurer.setUseRegisteredSuffixPatternMatch(Boolean.valueOf(this.mvcProperties.getPathmatch().isUseRegisteredSuffixPattern()));
            this.dispatcherServletPath.ifAvailable(dispatcherPath -> {
                String servletUrlMapping = dispatcherPath.getServletUrlMapping();
                if (servletUrlMapping.equals(WebMvcAutoConfiguration.SERVLET_LOCATION) && this.singleDispatcherServlet()) {
                    UrlPathHelper urlPathHelper = new UrlPathHelper();
                    urlPathHelper.setAlwaysUseFullPath(true);
                    configurer.setUrlPathHelper(urlPathHelper);
                }
            });
        }

        private boolean singleDispatcherServlet() {
            return this.servletRegistrations.stream().map(ServletRegistrationBean::getServlet).filter(DispatcherServlet.class::isInstance).count() == 1L;
        }

        public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
            WebMvcProperties.Contentnegotiation contentnegotiation = this.mvcProperties.getContentnegotiation();
            configurer.favorPathExtension(contentnegotiation.isFavorPathExtension());
            configurer.favorParameter(contentnegotiation.isFavorParameter());
            if (contentnegotiation.getParameterName() != null) {
                configurer.parameterName(contentnegotiation.getParameterName());
            }
            Map<String, MediaType> mediaTypes = this.mvcProperties.getContentnegotiation().getMediaTypes();
            mediaTypes.forEach((arg_0, arg_1) -> ((ContentNegotiationConfigurer)configurer).mediaType(arg_0, arg_1));
        }

        @Bean
        @ConditionalOnMissingBean
        public InternalResourceViewResolver defaultViewResolver() {
            InternalResourceViewResolver resolver = new InternalResourceViewResolver();
            resolver.setPrefix(this.mvcProperties.getView().getPrefix());
            resolver.setSuffix(this.mvcProperties.getView().getSuffix());
            return resolver;
        }

        @Bean
        @ConditionalOnBean(value={View.class})
        @ConditionalOnMissingBean
        public BeanNameViewResolver beanNameViewResolver() {
            BeanNameViewResolver resolver = new BeanNameViewResolver();
            resolver.setOrder(0x7FFFFFF5);
            return resolver;
        }

        @Bean
        @ConditionalOnBean(value={ViewResolver.class})
        @ConditionalOnMissingBean(name={"viewResolver"}, value={ContentNegotiatingViewResolver.class})
        public ContentNegotiatingViewResolver viewResolver(BeanFactory beanFactory) {
            ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
            resolver.setContentNegotiationManager((ContentNegotiationManager)beanFactory.getBean(ContentNegotiationManager.class));
            resolver.setOrder(Integer.MIN_VALUE);
            return resolver;
        }

        public MessageCodesResolver getMessageCodesResolver() {
            if (this.mvcProperties.getMessageCodesResolverFormat() != null) {
                DefaultMessageCodesResolver resolver = new DefaultMessageCodesResolver();
                resolver.setMessageCodeFormatter((MessageCodeFormatter)this.mvcProperties.getMessageCodesResolverFormat());
                return resolver;
            }
            return null;
        }

        public void addFormatters(FormatterRegistry registry) {
            ApplicationConversionService.addBeans((FormatterRegistry)registry, (ListableBeanFactory)this.beanFactory);
        }

        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            if (!this.resourceProperties.isAddMappings()) {
                logger.debug((Object)"Default resource handling disabled");
                return;
            }
            this.addResourceHandler(registry, "/webjars/**", "classpath:/META-INF/resources/webjars/");
            this.addResourceHandler(registry, this.mvcProperties.getStaticPathPattern(), (ResourceHandlerRegistration registration) -> {
                registration.addResourceLocations(this.resourceProperties.getStaticLocations());
                if (this.servletContext != null) {
                    ServletContextResource resource = new ServletContextResource(this.servletContext, WebMvcAutoConfiguration.SERVLET_LOCATION);
                    registration.addResourceLocations(new Resource[]{resource});
                }
            });
        }

        private void addResourceHandler(ResourceHandlerRegistry registry, String pattern, String ... locations) {
            this.addResourceHandler(registry, pattern, (ResourceHandlerRegistration registration) -> registration.addResourceLocations(locations));
        }

        private void addResourceHandler(ResourceHandlerRegistry registry, String pattern, Consumer<ResourceHandlerRegistration> customizer) {
            if (registry.hasMappingForPattern(pattern)) {
                return;
            }
            ResourceHandlerRegistration registration = registry.addResourceHandler(new String[]{pattern});
            customizer.accept(registration);
            registration.setCachePeriod(this.getSeconds(this.resourceProperties.getCache().getPeriod()));
            registration.setCacheControl(this.resourceProperties.getCache().getCachecontrol().toHttpCacheControl());
            registration.setUseLastModified(this.resourceProperties.getCache().isUseLastModified());
            this.customizeResourceHandlerRegistration(registration);
        }

        private Integer getSeconds(Duration cachePeriod) {
            return cachePeriod != null ? Integer.valueOf((int)cachePeriod.getSeconds()) : null;
        }

        private void customizeResourceHandlerRegistration(ResourceHandlerRegistration registration) {
            if (this.resourceHandlerRegistrationCustomizer != null) {
                this.resourceHandlerRegistrationCustomizer.customize(registration);
            }
        }

        @Bean
        @ConditionalOnMissingBean(value={RequestContextListener.class, RequestContextFilter.class})
        @ConditionalOnMissingFilterBean(value={RequestContextFilter.class})
        public static RequestContextFilter requestContextFilter() {
            return new OrderedRequestContextFilter();
        }
    }
}

