/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.Servlet
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.beans.factory.support.AbstractBeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.boot.context.properties.bind.Bindable
 *  org.springframework.boot.context.properties.bind.Binder
 *  org.springframework.boot.web.servlet.ServletRegistrationBean
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationContextAware
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Conditional
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.Role
 *  org.springframework.core.env.Environment
 *  org.springframework.core.io.Resource
 *  org.springframework.util.StringUtils
 *  org.springframework.ws.config.annotation.EnableWs
 *  org.springframework.ws.config.annotation.WsConfigurationSupport
 *  org.springframework.ws.transport.http.MessageDispatcherServlet
 *  org.springframework.ws.wsdl.wsdl11.SimpleWsdl11Definition
 *  org.springframework.xml.xsd.SimpleXsdSchema
 */
package org.springframework.boot.autoconfigure.webservices;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import javax.servlet.Servlet;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.boot.autoconfigure.webservices.OnWsdlLocationsCondition;
import org.springframework.boot.autoconfigure.webservices.WebServicesProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurationSupport;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.SimpleWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;

@AutoConfiguration(after={ServletWebServerFactoryAutoConfiguration.class})
@ConditionalOnWebApplication(type=ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnClass(value={MessageDispatcherServlet.class})
@ConditionalOnMissingBean(value={WsConfigurationSupport.class})
@EnableConfigurationProperties(value={WebServicesProperties.class})
public class WebServicesAutoConfiguration {
    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext, WebServicesProperties properties) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        String path = properties.getPath();
        String urlMapping = path + (path.endsWith("/") ? "*" : "/*");
        ServletRegistrationBean registration = new ServletRegistrationBean((Servlet)servlet, new String[]{urlMapping});
        WebServicesProperties.Servlet servletProperties = properties.getServlet();
        registration.setLoadOnStartup(servletProperties.getLoadOnStartup());
        servletProperties.getInit().forEach((arg_0, arg_1) -> ((ServletRegistrationBean)registration).addInitParameter(arg_0, arg_1));
        return registration;
    }

    @Bean
    @Role(value=2)
    @Conditional(value={OnWsdlLocationsCondition.class})
    public static WsdlDefinitionBeanFactoryPostProcessor wsdlDefinitionBeanFactoryPostProcessor() {
        return new WsdlDefinitionBeanFactoryPostProcessor();
    }

    static class WsdlDefinitionBeanFactoryPostProcessor
    implements BeanDefinitionRegistryPostProcessor,
    ApplicationContextAware {
        private ApplicationContext applicationContext;

        WsdlDefinitionBeanFactoryPostProcessor() {
        }

        public void setApplicationContext(ApplicationContext applicationContext) {
            this.applicationContext = applicationContext;
        }

        public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
            Binder binder = Binder.get((Environment)this.applicationContext.getEnvironment());
            List wsdlLocations = (List)binder.bind("spring.webservices.wsdl-locations", Bindable.listOf(String.class)).orElse(Collections.emptyList());
            for (String wsdlLocation : wsdlLocations) {
                this.registerBeans(wsdlLocation, "*.wsdl", SimpleWsdl11Definition.class, SimpleWsdl11Definition::new, registry);
                this.registerBeans(wsdlLocation, "*.xsd", SimpleXsdSchema.class, SimpleXsdSchema::new, registry);
            }
        }

        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        }

        private <T> void registerBeans(String location, String pattern, Class<T> type, Function<Resource, T> beanSupplier, BeanDefinitionRegistry registry) {
            for (Resource resource : this.getResources(location, pattern)) {
                AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(type, () -> beanSupplier.apply(resource)).getBeanDefinition();
                registry.registerBeanDefinition(StringUtils.stripFilenameExtension((String)resource.getFilename()), (BeanDefinition)beanDefinition);
            }
        }

        private Resource[] getResources(String location, String pattern) {
            try {
                return this.applicationContext.getResources(this.ensureTrailingSlash(location) + pattern);
            }
            catch (IOException ex) {
                return new Resource[0];
            }
        }

        private String ensureTrailingSlash(String path) {
            return path.endsWith("/") ? path : path + "/";
        }
    }

    @Configuration(proxyBeanMethods=false)
    @EnableWs
    protected static class WsConfiguration {
        protected WsConfiguration() {
        }
    }
}

