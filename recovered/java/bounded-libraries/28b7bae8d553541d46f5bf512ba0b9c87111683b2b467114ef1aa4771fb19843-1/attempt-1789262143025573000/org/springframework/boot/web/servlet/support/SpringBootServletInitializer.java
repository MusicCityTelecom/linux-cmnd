/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletContext
 *  javax.servlet.ServletContextEvent
 *  javax.servlet.ServletException
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationListener
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.core.Ordered
 *  org.springframework.core.annotation.MergedAnnotations
 *  org.springframework.core.annotation.MergedAnnotations$SearchStrategy
 *  org.springframework.core.env.ConfigurableEnvironment
 *  org.springframework.util.Assert
 *  org.springframework.web.WebApplicationInitializer
 *  org.springframework.web.context.ConfigurableWebEnvironment
 *  org.springframework.web.context.ContextLoaderListener
 *  org.springframework.web.context.WebApplicationContext
 */
package org.springframework.boot.web.servlet.support;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collections;
import java.util.EventListener;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.ParentContextApplicationContextInitializer;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.boot.web.servlet.support.ErrorPageFilterConfiguration;
import org.springframework.boot.web.servlet.support.ServletContextApplicationContextInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.Assert;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ConfigurableWebEnvironment;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

public abstract class SpringBootServletInitializer
implements WebApplicationInitializer {
    protected Log logger;
    private boolean registerErrorPageFilter = true;

    protected final void setRegisterErrorPageFilter(boolean registerErrorPageFilter) {
        this.registerErrorPageFilter = registerErrorPageFilter;
    }

    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.setAttribute("logging.register-shutdown-hook", (Object)false);
        this.logger = LogFactory.getLog(this.getClass());
        WebApplicationContext rootApplicationContext = this.createRootApplicationContext(servletContext);
        if (rootApplicationContext != null) {
            servletContext.addListener((EventListener)((Object)new SpringBootContextLoaderListener(rootApplicationContext, servletContext)));
        } else {
            this.logger.debug((Object)"No ContextLoaderListener registered, as createRootApplicationContext() did not return an application context");
        }
    }

    protected void deregisterJdbcDrivers(ServletContext servletContext) {
        for (Driver driver : Collections.list(DriverManager.getDrivers())) {
            if (driver.getClass().getClassLoader() != servletContext.getClassLoader()) continue;
            try {
                DriverManager.deregisterDriver(driver);
            }
            catch (SQLException sQLException) {}
        }
    }

    protected WebApplicationContext createRootApplicationContext(ServletContext servletContext) {
        SpringApplicationBuilder builder = this.createSpringApplicationBuilder();
        builder.main(this.getClass());
        ApplicationContext parent = this.getExistingRootWebApplicationContext(servletContext);
        if (parent != null) {
            this.logger.info((Object)"Root context already created (using as parent).");
            servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, null);
            builder.initializers(new ParentContextApplicationContextInitializer(parent));
        }
        builder.initializers(new ServletContextApplicationContextInitializer(servletContext));
        builder.contextFactory(webApplicationType -> new AnnotationConfigServletWebServerApplicationContext());
        builder = this.configure(builder);
        builder.listeners(new WebEnvironmentPropertySourceInitializer(servletContext));
        SpringApplication application = builder.build();
        if (application.getAllSources().isEmpty() && MergedAnnotations.from(this.getClass(), (MergedAnnotations.SearchStrategy)MergedAnnotations.SearchStrategy.TYPE_HIERARCHY).isPresent(Configuration.class)) {
            application.addPrimarySources(Collections.singleton(this.getClass()));
        }
        Assert.state((!application.getAllSources().isEmpty() ? 1 : 0) != 0, (String)"No SpringApplication sources have been defined. Either override the configure method or add an @Configuration annotation");
        if (this.registerErrorPageFilter) {
            application.addPrimarySources(Collections.singleton(ErrorPageFilterConfiguration.class));
        }
        application.setRegisterShutdownHook(false);
        return this.run(application);
    }

    protected SpringApplicationBuilder createSpringApplicationBuilder() {
        return new SpringApplicationBuilder(new Class[0]);
    }

    protected WebApplicationContext run(SpringApplication application) {
        return (WebApplicationContext)application.run(new String[0]);
    }

    private ApplicationContext getExistingRootWebApplicationContext(ServletContext servletContext) {
        Object context = servletContext.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        if (context instanceof ApplicationContext) {
            return (ApplicationContext)context;
        }
        return null;
    }

    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder;
    }

    private class SpringBootContextLoaderListener
    extends ContextLoaderListener {
        private final ServletContext servletContext;

        SpringBootContextLoaderListener(WebApplicationContext applicationContext, ServletContext servletContext) {
            super(applicationContext);
            this.servletContext = servletContext;
        }

        public void contextInitialized(ServletContextEvent event) {
        }

        public void contextDestroyed(ServletContextEvent event) {
            try {
                super.contextDestroyed(event);
            }
            finally {
                SpringBootServletInitializer.this.deregisterJdbcDrivers(this.servletContext);
            }
        }
    }

    private static final class WebEnvironmentPropertySourceInitializer
    implements ApplicationListener<ApplicationEnvironmentPreparedEvent>,
    Ordered {
        private final ServletContext servletContext;

        private WebEnvironmentPropertySourceInitializer(ServletContext servletContext) {
            this.servletContext = servletContext;
        }

        public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
            ConfigurableEnvironment environment = event.getEnvironment();
            if (environment instanceof ConfigurableWebEnvironment) {
                ((ConfigurableWebEnvironment)environment).initPropertySources(this.servletContext, null);
            }
        }

        public int getOrder() {
            return Integer.MIN_VALUE;
        }
    }
}

