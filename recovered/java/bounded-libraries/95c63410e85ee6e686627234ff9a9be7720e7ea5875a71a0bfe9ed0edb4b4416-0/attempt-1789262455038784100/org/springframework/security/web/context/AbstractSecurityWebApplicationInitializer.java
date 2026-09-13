/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.DispatcherType
 *  javax.servlet.Filter
 *  javax.servlet.FilterRegistration$Dynamic
 *  javax.servlet.ServletContext
 *  javax.servlet.SessionTrackingMode
 *  org.springframework.core.Conventions
 *  org.springframework.util.Assert
 *  org.springframework.web.WebApplicationInitializer
 *  org.springframework.web.context.ContextLoaderListener
 *  org.springframework.web.context.WebApplicationContext
 *  org.springframework.web.context.support.AnnotationConfigWebApplicationContext
 *  org.springframework.web.filter.DelegatingFilterProxy
 */
package org.springframework.security.web.context;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.EventListener;
import java.util.Set;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.SessionTrackingMode;
import org.springframework.core.Conventions;
import org.springframework.util.Assert;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;

public abstract class AbstractSecurityWebApplicationInitializer
implements WebApplicationInitializer {
    private static final String SERVLET_CONTEXT_PREFIX = "org.springframework.web.servlet.FrameworkServlet.CONTEXT.";
    public static final String DEFAULT_FILTER_NAME = "springSecurityFilterChain";
    private final Class<?>[] configurationClasses;

    protected AbstractSecurityWebApplicationInitializer() {
        this.configurationClasses = null;
    }

    protected AbstractSecurityWebApplicationInitializer(Class<?> ... configurationClasses) {
        this.configurationClasses = configurationClasses;
    }

    public final void onStartup(ServletContext servletContext) {
        this.beforeSpringSecurityFilterChain(servletContext);
        if (this.configurationClasses != null) {
            AnnotationConfigWebApplicationContext rootAppContext = new AnnotationConfigWebApplicationContext();
            rootAppContext.register((Class[])this.configurationClasses);
            servletContext.addListener((EventListener)new ContextLoaderListener((WebApplicationContext)rootAppContext));
        }
        if (this.enableHttpSessionEventPublisher()) {
            servletContext.addListener("org.springframework.security.web.session.HttpSessionEventPublisher");
        }
        servletContext.setSessionTrackingModes(this.getSessionTrackingModes());
        this.insertSpringSecurityFilterChain(servletContext);
        this.afterSpringSecurityFilterChain(servletContext);
    }

    protected boolean enableHttpSessionEventPublisher() {
        return false;
    }

    private void insertSpringSecurityFilterChain(ServletContext servletContext) {
        String filterName = DEFAULT_FILTER_NAME;
        DelegatingFilterProxy springSecurityFilterChain = new DelegatingFilterProxy(filterName);
        String contextAttribute = this.getWebApplicationContextAttribute();
        if (contextAttribute != null) {
            springSecurityFilterChain.setContextAttribute(contextAttribute);
        }
        this.registerFilter(servletContext, true, filterName, (Filter)springSecurityFilterChain);
    }

    protected final void insertFilters(ServletContext servletContext, Filter ... filters) {
        this.registerFilters(servletContext, true, filters);
    }

    protected final void appendFilters(ServletContext servletContext, Filter ... filters) {
        this.registerFilters(servletContext, false, filters);
    }

    private void registerFilters(ServletContext servletContext, boolean insertBeforeOtherFilters, Filter ... filters) {
        Assert.notEmpty((Object[])filters, (String)"filters cannot be null or empty");
        for (Filter filter : filters) {
            Assert.notNull((Object)filter, () -> "filters cannot contain null values. Got " + Arrays.asList(filters));
            String filterName = Conventions.getVariableName((Object)filter);
            this.registerFilter(servletContext, insertBeforeOtherFilters, filterName, filter);
        }
    }

    private void registerFilter(ServletContext servletContext, boolean insertBeforeOtherFilters, String filterName, Filter filter) {
        FilterRegistration.Dynamic registration = servletContext.addFilter(filterName, filter);
        Assert.state((registration != null ? 1 : 0) != 0, () -> "Duplicate Filter registration for '" + filterName + "'. Check to ensure the Filter is only configured once.");
        registration.setAsyncSupported(this.isAsyncSecuritySupported());
        EnumSet<DispatcherType> dispatcherTypes = this.getSecurityDispatcherTypes();
        registration.addMappingForUrlPatterns(dispatcherTypes, !insertBeforeOtherFilters, new String[]{"/*"});
    }

    private String getWebApplicationContextAttribute() {
        String dispatcherServletName = this.getDispatcherWebApplicationContextSuffix();
        if (dispatcherServletName == null) {
            return null;
        }
        return SERVLET_CONTEXT_PREFIX + dispatcherServletName;
    }

    protected Set<SessionTrackingMode> getSessionTrackingModes() {
        return EnumSet.of(SessionTrackingMode.COOKIE);
    }

    protected String getDispatcherWebApplicationContextSuffix() {
        return null;
    }

    protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
    }

    protected void afterSpringSecurityFilterChain(ServletContext servletContext) {
    }

    protected EnumSet<DispatcherType> getSecurityDispatcherTypes() {
        return EnumSet.of(DispatcherType.REQUEST, DispatcherType.ERROR, DispatcherType.ASYNC);
    }

    protected boolean isAsyncSecuritySupported() {
        return true;
    }
}

