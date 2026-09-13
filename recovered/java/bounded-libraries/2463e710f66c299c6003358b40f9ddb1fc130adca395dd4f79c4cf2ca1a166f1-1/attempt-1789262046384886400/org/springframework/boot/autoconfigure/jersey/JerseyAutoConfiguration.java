/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.AnnotationIntrospector
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.cfg.MapperConfig
 *  com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector
 *  javax.servlet.DispatcherType
 *  javax.servlet.Filter
 *  javax.servlet.Servlet
 *  javax.servlet.ServletContext
 *  javax.servlet.ServletException
 *  javax.servlet.ServletRegistration
 *  javax.ws.rs.ext.ContextResolver
 *  javax.xml.bind.annotation.XmlElement
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.glassfish.jersey.jackson.JacksonFeature
 *  org.glassfish.jersey.server.ResourceConfig
 *  org.glassfish.jersey.server.spring.SpringComponentProvider
 *  org.glassfish.jersey.servlet.ServletContainer
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.boot.web.servlet.DynamicRegistrationBean
 *  org.springframework.boot.web.servlet.FilterRegistrationBean
 *  org.springframework.boot.web.servlet.ServletRegistrationBean
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.core.annotation.Order
 *  org.springframework.util.ClassUtils
 *  org.springframework.web.WebApplicationInitializer
 *  org.springframework.web.context.ServletContextAware
 *  org.springframework.web.filter.RequestContextFilter
 */
package org.springframework.boot.autoconfigure.jersey;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import java.util.Collections;
import java.util.EnumSet;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.ws.rs.ext.ContextResolver;
import javax.xml.bind.annotation.XmlElement;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.SpringComponentProvider;
import org.glassfish.jersey.servlet.ServletContainer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.jersey.JerseyProperties;
import org.springframework.boot.autoconfigure.jersey.ResourceConfigCustomizer;
import org.springframework.boot.autoconfigure.web.servlet.ConditionalOnMissingFilterBean;
import org.springframework.boot.autoconfigure.web.servlet.DefaultJerseyApplicationPath;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.JerseyApplicationPath;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.DynamicRegistrationBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.util.ClassUtils;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.filter.RequestContextFilter;

@AutoConfiguration(before={DispatcherServletAutoConfiguration.class}, after={JacksonAutoConfiguration.class})
@ConditionalOnClass(value={SpringComponentProvider.class, ServletRegistration.class})
@ConditionalOnBean(type={"org.glassfish.jersey.server.ResourceConfig"})
@ConditionalOnWebApplication(type=ConditionalOnWebApplication.Type.SERVLET)
@AutoConfigureOrder(value=-2147483648)
@EnableConfigurationProperties(value={JerseyProperties.class})
public class JerseyAutoConfiguration
implements ServletContextAware {
    private static final Log logger = LogFactory.getLog(JerseyAutoConfiguration.class);
    private final JerseyProperties jersey;
    private final ResourceConfig config;

    public JerseyAutoConfiguration(JerseyProperties jersey, ResourceConfig config, ObjectProvider<ResourceConfigCustomizer> customizers) {
        this.jersey = jersey;
        this.config = config;
        customizers.orderedStream().forEach(customizer -> customizer.customize(this.config));
    }

    @Bean
    @ConditionalOnMissingFilterBean(value={RequestContextFilter.class})
    public FilterRegistrationBean<RequestContextFilter> requestContextFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter((Filter)new RequestContextFilter());
        registration.setOrder(this.jersey.getFilter().getOrder() - 1);
        registration.setName("requestContextFilter");
        return registration;
    }

    @Bean
    @ConditionalOnMissingBean
    public JerseyApplicationPath jerseyApplicationPath() {
        return new DefaultJerseyApplicationPath(this.jersey.getApplicationPath(), this.config);
    }

    @Bean
    @ConditionalOnMissingBean(name={"jerseyFilterRegistration"})
    @ConditionalOnProperty(prefix="spring.jersey", name={"type"}, havingValue="filter")
    public FilterRegistrationBean<ServletContainer> jerseyFilterRegistration(JerseyApplicationPath applicationPath) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter((Filter)new ServletContainer(this.config));
        registration.setUrlPatterns(Collections.singletonList(applicationPath.getUrlMapping()));
        registration.setOrder(this.jersey.getFilter().getOrder());
        registration.addInitParameter("jersey.config.servlet.filter.contextPath", this.stripPattern(applicationPath.getPath()));
        this.addInitParameters((DynamicRegistrationBean<?>)registration);
        registration.setName("jerseyFilter");
        registration.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));
        return registration;
    }

    private String stripPattern(String path) {
        if (path.endsWith("/*")) {
            path = path.substring(0, path.lastIndexOf("/*"));
        }
        return path;
    }

    @Bean
    @ConditionalOnMissingBean(name={"jerseyServletRegistration"})
    @ConditionalOnProperty(prefix="spring.jersey", name={"type"}, havingValue="servlet", matchIfMissing=true)
    public ServletRegistrationBean<ServletContainer> jerseyServletRegistration(JerseyApplicationPath applicationPath) {
        ServletRegistrationBean registration = new ServletRegistrationBean((Servlet)new ServletContainer(this.config), new String[]{applicationPath.getUrlMapping()});
        this.addInitParameters((DynamicRegistrationBean<?>)registration);
        registration.setName(this.getServletRegistrationName());
        registration.setLoadOnStartup(this.jersey.getServlet().getLoadOnStartup());
        return registration;
    }

    private String getServletRegistrationName() {
        return ClassUtils.getUserClass(this.config.getClass()).getName();
    }

    private void addInitParameters(DynamicRegistrationBean<?> registration) {
        this.jersey.getInit().forEach((arg_0, arg_1) -> registration.addInitParameter(arg_0, arg_1));
    }

    public void setServletContext(ServletContext servletContext) {
        String servletRegistrationName = this.getServletRegistrationName();
        ServletRegistration registration = servletContext.getServletRegistration(servletRegistrationName);
        if (registration != null) {
            if (logger.isInfoEnabled()) {
                logger.info((Object)("Configuring existing registration for Jersey servlet '" + servletRegistrationName + "'"));
            }
            registration.setInitParameters(this.jersey.getInit());
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={JacksonFeature.class})
    @ConditionalOnSingleCandidate(value=ObjectMapper.class)
    static class JacksonResourceConfigCustomizer {
        JacksonResourceConfigCustomizer() {
        }

        @Bean
        ResourceConfigCustomizer resourceConfigCustomizer(ObjectMapper objectMapper) {
            return config -> {
                config.register(JacksonFeature.class);
                config.register((Object)new ObjectMapperContextResolver(objectMapper), new Class[]{ContextResolver.class});
            };
        }

        private static final class ObjectMapperContextResolver
        implements ContextResolver<ObjectMapper> {
            private final ObjectMapper objectMapper;

            private ObjectMapperContextResolver(ObjectMapper objectMapper) {
                this.objectMapper = objectMapper;
            }

            public ObjectMapper getContext(Class<?> type) {
                return this.objectMapper;
            }
        }

        @Configuration(proxyBeanMethods=false)
        @ConditionalOnClass(value={JaxbAnnotationIntrospector.class, XmlElement.class})
        static class JaxbObjectMapperCustomizer {
            JaxbObjectMapperCustomizer() {
            }

            @Autowired
            void addJaxbAnnotationIntrospector(ObjectMapper objectMapper) {
                JaxbAnnotationIntrospector jaxbAnnotationIntrospector = new JaxbAnnotationIntrospector(objectMapper.getTypeFactory());
                objectMapper.setAnnotationIntrospectors(this.createPair((MapperConfig<?>)objectMapper.getSerializationConfig(), jaxbAnnotationIntrospector), this.createPair((MapperConfig<?>)objectMapper.getDeserializationConfig(), jaxbAnnotationIntrospector));
            }

            private AnnotationIntrospector createPair(MapperConfig<?> config, JaxbAnnotationIntrospector jaxbAnnotationIntrospector) {
                return AnnotationIntrospector.pair((AnnotationIntrospector)config.getAnnotationIntrospector(), (AnnotationIntrospector)jaxbAnnotationIntrospector);
            }
        }
    }

    @Order(value=-2147483648)
    public static final class JerseyWebApplicationInitializer
    implements WebApplicationInitializer {
        public void onStartup(ServletContext servletContext) throws ServletException {
            servletContext.setInitParameter("contextConfigLocation", "<NONE>");
        }
    }
}

