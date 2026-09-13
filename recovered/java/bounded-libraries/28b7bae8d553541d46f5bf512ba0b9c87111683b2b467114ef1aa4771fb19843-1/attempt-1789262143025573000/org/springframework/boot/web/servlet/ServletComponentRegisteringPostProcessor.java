/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.annotation.AnnotatedBeanDefinition
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.BeanFactoryPostProcessor
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationContextAware
 *  org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.web.context.WebApplicationContext
 */
package org.springframework.boot.web.servlet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.web.servlet.ServletComponentHandler;
import org.springframework.boot.web.servlet.WebFilterHandler;
import org.springframework.boot.web.servlet.WebListenerHandler;
import org.springframework.boot.web.servlet.WebServletHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.context.WebApplicationContext;

class ServletComponentRegisteringPostProcessor
implements BeanFactoryPostProcessor,
ApplicationContextAware {
    private static final List<ServletComponentHandler> HANDLERS;
    private final Set<String> packagesToScan;
    private ApplicationContext applicationContext;

    ServletComponentRegisteringPostProcessor(Set<String> packagesToScan) {
        this.packagesToScan = packagesToScan;
    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        if (this.isRunningInEmbeddedWebServer()) {
            ClassPathScanningCandidateComponentProvider componentProvider = this.createComponentProvider();
            for (String packageToScan : this.packagesToScan) {
                this.scanPackage(componentProvider, packageToScan);
            }
        }
    }

    private void scanPackage(ClassPathScanningCandidateComponentProvider componentProvider, String packageToScan) {
        for (BeanDefinition candidate : componentProvider.findCandidateComponents(packageToScan)) {
            if (!(candidate instanceof AnnotatedBeanDefinition)) continue;
            for (ServletComponentHandler handler : HANDLERS) {
                handler.handle((AnnotatedBeanDefinition)candidate, (BeanDefinitionRegistry)this.applicationContext);
            }
        }
    }

    private boolean isRunningInEmbeddedWebServer() {
        return this.applicationContext instanceof WebApplicationContext && ((WebApplicationContext)this.applicationContext).getServletContext() == null;
    }

    private ClassPathScanningCandidateComponentProvider createComponentProvider() {
        ClassPathScanningCandidateComponentProvider componentProvider = new ClassPathScanningCandidateComponentProvider(false);
        componentProvider.setEnvironment(this.applicationContext.getEnvironment());
        componentProvider.setResourceLoader((ResourceLoader)this.applicationContext);
        for (ServletComponentHandler handler : HANDLERS) {
            componentProvider.addIncludeFilter(handler.getTypeFilter());
        }
        return componentProvider;
    }

    Set<String> getPackagesToScan() {
        return Collections.unmodifiableSet(this.packagesToScan);
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    static {
        ArrayList<ServletComponentHandler> servletComponentHandlers = new ArrayList<ServletComponentHandler>();
        servletComponentHandlers.add(new WebServletHandler());
        servletComponentHandlers.add(new WebFilterHandler());
        servletComponentHandlers.add(new WebListenerHandler());
        HANDLERS = Collections.unmodifiableList(servletComponentHandlers);
    }
}

