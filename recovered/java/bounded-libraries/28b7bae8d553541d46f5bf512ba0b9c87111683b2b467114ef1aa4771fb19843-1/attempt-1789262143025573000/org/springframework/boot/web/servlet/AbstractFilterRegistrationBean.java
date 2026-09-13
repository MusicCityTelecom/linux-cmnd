/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.DispatcherType
 *  javax.servlet.Filter
 *  javax.servlet.FilterRegistration$Dynamic
 *  javax.servlet.ServletContext
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.StringUtils
 *  org.springframework.web.filter.OncePerRequestFilter
 */
package org.springframework.boot.web.servlet;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import org.springframework.boot.web.servlet.DynamicRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public abstract class AbstractFilterRegistrationBean<T extends Filter>
extends DynamicRegistrationBean<FilterRegistration.Dynamic> {
    private static final String[] DEFAULT_URL_MAPPINGS = new String[]{"/*"};
    private Set<ServletRegistrationBean<?>> servletRegistrationBeans = new LinkedHashSet();
    private Set<String> servletNames = new LinkedHashSet<String>();
    private Set<String> urlPatterns = new LinkedHashSet<String>();
    private EnumSet<DispatcherType> dispatcherTypes;
    private boolean matchAfter = false;

    AbstractFilterRegistrationBean(ServletRegistrationBean<?> ... servletRegistrationBeans) {
        Assert.notNull(servletRegistrationBeans, (String)"ServletRegistrationBeans must not be null");
        Collections.addAll(this.servletRegistrationBeans, servletRegistrationBeans);
    }

    public void setServletRegistrationBeans(Collection<? extends ServletRegistrationBean<?>> servletRegistrationBeans) {
        Assert.notNull(servletRegistrationBeans, (String)"ServletRegistrationBeans must not be null");
        this.servletRegistrationBeans = new LinkedHashSet(servletRegistrationBeans);
    }

    public Collection<ServletRegistrationBean<?>> getServletRegistrationBeans() {
        return this.servletRegistrationBeans;
    }

    public void addServletRegistrationBeans(ServletRegistrationBean<?> ... servletRegistrationBeans) {
        Assert.notNull(servletRegistrationBeans, (String)"ServletRegistrationBeans must not be null");
        Collections.addAll(this.servletRegistrationBeans, servletRegistrationBeans);
    }

    public void setServletNames(Collection<String> servletNames) {
        Assert.notNull(servletNames, (String)"ServletNames must not be null");
        this.servletNames = new LinkedHashSet<String>(servletNames);
    }

    public Collection<String> getServletNames() {
        return this.servletNames;
    }

    public void addServletNames(String ... servletNames) {
        Assert.notNull((Object)servletNames, (String)"ServletNames must not be null");
        this.servletNames.addAll(Arrays.asList(servletNames));
    }

    public void setUrlPatterns(Collection<String> urlPatterns) {
        Assert.notNull(urlPatterns, (String)"UrlPatterns must not be null");
        this.urlPatterns = new LinkedHashSet<String>(urlPatterns);
    }

    public Collection<String> getUrlPatterns() {
        return this.urlPatterns;
    }

    public void addUrlPatterns(String ... urlPatterns) {
        Assert.notNull((Object)urlPatterns, (String)"UrlPatterns must not be null");
        Collections.addAll(this.urlPatterns, urlPatterns);
    }

    public void setDispatcherTypes(DispatcherType first, DispatcherType ... rest) {
        this.dispatcherTypes = EnumSet.of(first, rest);
    }

    public void setDispatcherTypes(EnumSet<DispatcherType> dispatcherTypes) {
        this.dispatcherTypes = dispatcherTypes;
    }

    public void setMatchAfter(boolean matchAfter) {
        this.matchAfter = matchAfter;
    }

    public boolean isMatchAfter() {
        return this.matchAfter;
    }

    @Override
    protected String getDescription() {
        T filter = this.getFilter();
        Assert.notNull(filter, (String)"Filter must not be null");
        return "filter " + this.getOrDeduceName(filter);
    }

    @Override
    protected FilterRegistration.Dynamic addRegistration(String description, ServletContext servletContext) {
        T filter = this.getFilter();
        return servletContext.addFilter(this.getOrDeduceName(filter), filter);
    }

    @Override
    protected void configure(FilterRegistration.Dynamic registration) {
        super.configure(registration);
        EnumSet<DispatcherType> dispatcherTypes = this.dispatcherTypes;
        if (dispatcherTypes == null) {
            T filter = this.getFilter();
            dispatcherTypes = ClassUtils.isPresent((String)"org.springframework.web.filter.OncePerRequestFilter", (ClassLoader)filter.getClass().getClassLoader()) && filter instanceof OncePerRequestFilter ? EnumSet.allOf(DispatcherType.class) : EnumSet.of(DispatcherType.REQUEST);
        }
        LinkedHashSet<String> servletNames = new LinkedHashSet<String>();
        for (ServletRegistrationBean<?> servletRegistrationBean : this.servletRegistrationBeans) {
            servletNames.add(servletRegistrationBean.getServletName());
        }
        servletNames.addAll(this.servletNames);
        if (servletNames.isEmpty() && this.urlPatterns.isEmpty()) {
            registration.addMappingForUrlPatterns(dispatcherTypes, this.matchAfter, DEFAULT_URL_MAPPINGS);
        } else {
            if (!servletNames.isEmpty()) {
                registration.addMappingForServletNames(dispatcherTypes, this.matchAfter, StringUtils.toStringArray(servletNames));
            }
            if (!this.urlPatterns.isEmpty()) {
                registration.addMappingForUrlPatterns(dispatcherTypes, this.matchAfter, StringUtils.toStringArray(this.urlPatterns));
            }
        }
    }

    public abstract T getFilter();

    public String toString() {
        StringBuilder builder = new StringBuilder(this.getOrDeduceName(this));
        if (this.servletNames.isEmpty() && this.urlPatterns.isEmpty()) {
            builder.append(" urls=").append(Arrays.toString(DEFAULT_URL_MAPPINGS));
        } else {
            if (!this.servletNames.isEmpty()) {
                builder.append(" servlets=").append(this.servletNames);
            }
            if (!this.urlPatterns.isEmpty()) {
                builder.append(" urls=").append(this.urlPatterns);
            }
        }
        builder.append(" order=").append(this.getOrder());
        return builder.toString();
    }
}

