/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletConfig
 *  javax.servlet.ServletContext
 *  javax.servlet.ServletException
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.ListableBeanFactory
 *  org.springframework.beans.factory.config.BeanPostProcessor
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.beans.factory.config.Scope
 *  org.springframework.beans.factory.support.DefaultListableBeanFactory
 *  org.springframework.context.ApplicationContextException
 *  org.springframework.core.io.DefaultResourceLoader$ClassPathContextResource
 *  org.springframework.core.io.Resource
 *  org.springframework.core.metrics.StartupStep
 *  org.springframework.util.StringUtils
 *  org.springframework.web.context.ConfigurableWebApplicationContext
 *  org.springframework.web.context.ServletContextAware
 *  org.springframework.web.context.WebApplicationContext
 *  org.springframework.web.context.support.GenericWebApplicationContext
 *  org.springframework.web.context.support.ServletContextResource
 *  org.springframework.web.context.support.ServletContextScope
 *  org.springframework.web.context.support.WebApplicationContextUtils
 */
package org.springframework.boot.web.servlet.context;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.boot.web.context.ConfigurableWebServerApplicationContext;
import org.springframework.boot.web.context.MissingWebServerFactoryBeanException;
import org.springframework.boot.web.context.WebServerGracefulShutdownLifecycle;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletContextInitializerBeans;
import org.springframework.boot.web.servlet.context.WebApplicationContextServletContextAwareProcessor;
import org.springframework.boot.web.servlet.context.WebServerStartStopLifecycle;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ApplicationContextException;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.metrics.StartupStep;
import org.springframework.util.StringUtils;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.context.support.ServletContextScope;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ServletWebServerApplicationContext
extends GenericWebApplicationContext
implements ConfigurableWebServerApplicationContext {
    private static final Log logger = LogFactory.getLog(ServletWebServerApplicationContext.class);
    public static final String DISPATCHER_SERVLET_NAME = "dispatcherServlet";
    private volatile WebServer webServer;
    private ServletConfig servletConfig;
    private String serverNamespace;

    public ServletWebServerApplicationContext() {
    }

    public ServletWebServerApplicationContext(DefaultListableBeanFactory beanFactory) {
        super(beanFactory);
    }

    protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        beanFactory.addBeanPostProcessor((BeanPostProcessor)new WebApplicationContextServletContextAwareProcessor((ConfigurableWebApplicationContext)this));
        beanFactory.ignoreDependencyInterface(ServletContextAware.class);
        this.registerWebApplicationScopes();
    }

    public final void refresh() throws BeansException, IllegalStateException {
        try {
            super.refresh();
        }
        catch (RuntimeException ex) {
            WebServer webServer = this.webServer;
            if (webServer != null) {
                webServer.stop();
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
            throw new ApplicationContextException("Unable to start web server", ex);
        }
    }

    protected void doClose() {
        if (this.isActive()) {
            AvailabilityChangeEvent.publish(this, ReadinessState.REFUSING_TRAFFIC);
        }
        super.doClose();
    }

    private void createWebServer() {
        WebServer webServer = this.webServer;
        ServletContext servletContext = this.getServletContext();
        if (webServer == null && servletContext == null) {
            StartupStep createWebServer = this.getApplicationStartup().start("spring.boot.webserver.create");
            ServletWebServerFactory factory = this.getWebServerFactory();
            createWebServer.tag("factory", factory.getClass().toString());
            this.webServer = factory.getWebServer(this.getSelfInitializer());
            createWebServer.end();
            this.getBeanFactory().registerSingleton("webServerGracefulShutdown", (Object)new WebServerGracefulShutdownLifecycle(this.webServer));
            this.getBeanFactory().registerSingleton("webServerStartStop", (Object)new WebServerStartStopLifecycle(this, this.webServer));
        } else if (servletContext != null) {
            try {
                this.getSelfInitializer().onStartup(servletContext);
            }
            catch (ServletException ex) {
                throw new ApplicationContextException("Cannot initialize servlet context", (Throwable)ex);
            }
        }
        this.initPropertySources();
    }

    protected ServletWebServerFactory getWebServerFactory() {
        Object[] beanNames = this.getBeanFactory().getBeanNamesForType(ServletWebServerFactory.class);
        if (beanNames.length == 0) {
            throw new MissingWebServerFactoryBeanException(this.getClass(), ServletWebServerFactory.class, WebApplicationType.SERVLET);
        }
        if (beanNames.length > 1) {
            throw new ApplicationContextException("Unable to start ServletWebServerApplicationContext due to multiple ServletWebServerFactory beans : " + StringUtils.arrayToCommaDelimitedString((Object[])beanNames));
        }
        return (ServletWebServerFactory)this.getBeanFactory().getBean(beanNames[0], ServletWebServerFactory.class);
    }

    private ServletContextInitializer getSelfInitializer() {
        return this::selfInitialize;
    }

    private void selfInitialize(ServletContext servletContext) throws ServletException {
        this.prepareWebApplicationContext(servletContext);
        this.registerApplicationScope(servletContext);
        WebApplicationContextUtils.registerEnvironmentBeans((ConfigurableListableBeanFactory)this.getBeanFactory(), (ServletContext)servletContext);
        for (ServletContextInitializer beans : this.getServletContextInitializerBeans()) {
            beans.onStartup(servletContext);
        }
    }

    private void registerApplicationScope(ServletContext servletContext) {
        ServletContextScope appScope = new ServletContextScope(servletContext);
        this.getBeanFactory().registerScope("application", (Scope)appScope);
        servletContext.setAttribute(ServletContextScope.class.getName(), (Object)appScope);
    }

    private void registerWebApplicationScopes() {
        ExistingWebApplicationScopes existingScopes = new ExistingWebApplicationScopes(this.getBeanFactory());
        WebApplicationContextUtils.registerWebApplicationScopes((ConfigurableListableBeanFactory)this.getBeanFactory());
        existingScopes.restore();
    }

    protected Collection<ServletContextInitializer> getServletContextInitializerBeans() {
        return new ServletContextInitializerBeans((ListableBeanFactory)this.getBeanFactory(), new Class[0]);
    }

    protected void prepareWebApplicationContext(ServletContext servletContext) {
        Object rootContext = servletContext.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        if (rootContext != null) {
            if (rootContext == this) {
                throw new IllegalStateException("Cannot initialize context because there is already a root application context present - check whether you have multiple ServletContextInitializers!");
            }
            return;
        }
        servletContext.log("Initializing Spring embedded WebApplicationContext");
        try {
            servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, (Object)this);
            if (logger.isDebugEnabled()) {
                logger.debug((Object)("Published root WebApplicationContext as ServletContext attribute with name [" + WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE + "]"));
            }
            this.setServletContext(servletContext);
            if (logger.isInfoEnabled()) {
                long elapsedTime = System.currentTimeMillis() - this.getStartupDate();
                logger.info((Object)("Root WebApplicationContext: initialization completed in " + elapsedTime + " ms"));
            }
        }
        catch (Error | RuntimeException ex) {
            logger.error((Object)"Context initialization failed", ex);
            servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, (Object)ex);
            throw ex;
        }
    }

    protected Resource getResourceByPath(String path) {
        if (this.getServletContext() == null) {
            return new DefaultResourceLoader.ClassPathContextResource(path, this.getClassLoader());
        }
        return new ServletContextResource(this.getServletContext(), path);
    }

    @Override
    public String getServerNamespace() {
        return this.serverNamespace;
    }

    @Override
    public void setServerNamespace(String serverNamespace) {
        this.serverNamespace = serverNamespace;
    }

    public void setServletConfig(ServletConfig servletConfig) {
        this.servletConfig = servletConfig;
    }

    public ServletConfig getServletConfig() {
        return this.servletConfig;
    }

    @Override
    public WebServer getWebServer() {
        return this.webServer;
    }

    public static class ExistingWebApplicationScopes {
        private static final Set<String> SCOPES;
        private final ConfigurableListableBeanFactory beanFactory;
        private final Map<String, Scope> scopes = new HashMap<String, Scope>();

        public ExistingWebApplicationScopes(ConfigurableListableBeanFactory beanFactory) {
            this.beanFactory = beanFactory;
            for (String scopeName : SCOPES) {
                Scope scope = beanFactory.getRegisteredScope(scopeName);
                if (scope == null) continue;
                this.scopes.put(scopeName, scope);
            }
        }

        public void restore() {
            this.scopes.forEach((key, value) -> {
                if (logger.isInfoEnabled()) {
                    logger.info((Object)("Restoring user defined scope " + key));
                }
                this.beanFactory.registerScope(key, value);
            });
        }

        static {
            LinkedHashSet<String> scopes = new LinkedHashSet<String>();
            scopes.add("request");
            scopes.add("session");
            SCOPES = Collections.unmodifiableSet(scopes);
        }
    }
}

