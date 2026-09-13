/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.support.DefaultListableBeanFactory
 *  org.springframework.context.ApplicationContextException
 *  org.springframework.core.metrics.StartupStep
 *  org.springframework.http.server.reactive.HttpHandler
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.web.reactive.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.boot.web.context.ConfigurableWebServerApplicationContext;
import org.springframework.boot.web.context.MissingWebServerFactoryBeanException;
import org.springframework.boot.web.context.WebServerGracefulShutdownLifecycle;
import org.springframework.boot.web.reactive.context.GenericReactiveWebApplicationContext;
import org.springframework.boot.web.reactive.context.WebServerManager;
import org.springframework.boot.web.reactive.context.WebServerStartStopLifecycle;
import org.springframework.boot.web.reactive.server.ReactiveWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.context.ApplicationContextException;
import org.springframework.core.metrics.StartupStep;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.util.StringUtils;

public class ReactiveWebServerApplicationContext
extends GenericReactiveWebApplicationContext
implements ConfigurableWebServerApplicationContext {
    private volatile WebServerManager serverManager;
    private String serverNamespace;

    public ReactiveWebServerApplicationContext() {
    }

    public ReactiveWebServerApplicationContext(DefaultListableBeanFactory beanFactory) {
        super(beanFactory);
    }

    public final void refresh() throws BeansException, IllegalStateException {
        try {
            super.refresh();
        }
        catch (RuntimeException ex) {
            WebServerManager serverManager = this.serverManager;
            if (serverManager != null) {
                serverManager.getWebServer().stop();
            }
            throw ex;
        }
    }

    protected void onRefresh() {
        super.onRefresh();
        try {
            this.createWebServer();
        }
        catch (Throwable ex) {
            throw new ApplicationContextException("Unable to start reactive web server", ex);
        }
    }

    private void createWebServer() {
        WebServerManager serverManager = this.serverManager;
        if (serverManager == null) {
            StartupStep createWebServer = this.getApplicationStartup().start("spring.boot.webserver.create");
            String webServerFactoryBeanName = this.getWebServerFactoryBeanName();
            ReactiveWebServerFactory webServerFactory = this.getWebServerFactory(webServerFactoryBeanName);
            createWebServer.tag("factory", webServerFactory.getClass().toString());
            boolean lazyInit = this.getBeanFactory().getBeanDefinition(webServerFactoryBeanName).isLazyInit();
            this.serverManager = new WebServerManager(this, webServerFactory, this::getHttpHandler, lazyInit);
            this.getBeanFactory().registerSingleton("webServerGracefulShutdown", (Object)new WebServerGracefulShutdownLifecycle(this.serverManager.getWebServer()));
            this.getBeanFactory().registerSingleton("webServerStartStop", (Object)new WebServerStartStopLifecycle(this.serverManager));
            createWebServer.end();
        }
        this.initPropertySources();
    }

    protected String getWebServerFactoryBeanName() {
        Object[] beanNames = this.getBeanFactory().getBeanNamesForType(ReactiveWebServerFactory.class);
        if (beanNames.length == 0) {
            throw new MissingWebServerFactoryBeanException(this.getClass(), ReactiveWebServerFactory.class, WebApplicationType.REACTIVE);
        }
        if (beanNames.length > 1) {
            throw new ApplicationContextException("Unable to start ReactiveWebApplicationContext due to multiple ReactiveWebServerFactory beans : " + StringUtils.arrayToCommaDelimitedString((Object[])beanNames));
        }
        return beanNames[0];
    }

    protected ReactiveWebServerFactory getWebServerFactory(String factoryBeanName) {
        return (ReactiveWebServerFactory)this.getBeanFactory().getBean(factoryBeanName, ReactiveWebServerFactory.class);
    }

    protected HttpHandler getHttpHandler() {
        Object[] beanNames = this.getBeanFactory().getBeanNamesForType(HttpHandler.class);
        if (beanNames.length == 0) {
            throw new ApplicationContextException("Unable to start ReactiveWebApplicationContext due to missing HttpHandler bean.");
        }
        if (beanNames.length > 1) {
            throw new ApplicationContextException("Unable to start ReactiveWebApplicationContext due to multiple HttpHandler beans : " + StringUtils.arrayToCommaDelimitedString((Object[])beanNames));
        }
        return (HttpHandler)this.getBeanFactory().getBean(beanNames[0], HttpHandler.class);
    }

    protected void doClose() {
        if (this.isActive()) {
            AvailabilityChangeEvent.publish(this, ReadinessState.REFUSING_TRAFFIC);
        }
        super.doClose();
    }

    @Override
    public WebServer getWebServer() {
        WebServerManager serverManager = this.serverManager;
        return serverManager != null ? serverManager.getWebServer() : null;
    }

    @Override
    public String getServerNamespace() {
        return this.serverNamespace;
    }

    @Override
    public void setServerNamespace(String serverNamespace) {
        this.serverNamespace = serverNamespace;
    }
}

