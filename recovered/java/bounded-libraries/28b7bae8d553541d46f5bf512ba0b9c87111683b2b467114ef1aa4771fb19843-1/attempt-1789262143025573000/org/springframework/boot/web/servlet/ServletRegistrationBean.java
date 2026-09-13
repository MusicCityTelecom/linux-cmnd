/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.MultipartConfigElement
 *  javax.servlet.Servlet
 *  javax.servlet.ServletContext
 *  javax.servlet.ServletRegistration$Dynamic
 *  org.springframework.util.Assert
 *  org.springframework.util.ObjectUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.web.servlet;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.servlet.MultipartConfigElement;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import org.springframework.boot.web.servlet.DynamicRegistrationBean;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class ServletRegistrationBean<T extends Servlet>
extends DynamicRegistrationBean<ServletRegistration.Dynamic> {
    private static final String[] DEFAULT_MAPPINGS = new String[]{"/*"};
    private T servlet;
    private Set<String> urlMappings = new LinkedHashSet<String>();
    private boolean alwaysMapUrl = true;
    private int loadOnStartup = -1;
    private MultipartConfigElement multipartConfig;

    public ServletRegistrationBean() {
    }

    public ServletRegistrationBean(T servlet, String ... urlMappings) {
        this(servlet, true, urlMappings);
    }

    public ServletRegistrationBean(T servlet, boolean alwaysMapUrl, String ... urlMappings) {
        Assert.notNull(servlet, (String)"Servlet must not be null");
        Assert.notNull((Object)urlMappings, (String)"UrlMappings must not be null");
        this.servlet = servlet;
        this.alwaysMapUrl = alwaysMapUrl;
        this.urlMappings.addAll(Arrays.asList(urlMappings));
    }

    public void setServlet(T servlet) {
        Assert.notNull(servlet, (String)"Servlet must not be null");
        this.servlet = servlet;
    }

    public T getServlet() {
        return this.servlet;
    }

    public void setUrlMappings(Collection<String> urlMappings) {
        Assert.notNull(urlMappings, (String)"UrlMappings must not be null");
        this.urlMappings = new LinkedHashSet<String>(urlMappings);
    }

    public Collection<String> getUrlMappings() {
        return this.urlMappings;
    }

    public void addUrlMappings(String ... urlMappings) {
        Assert.notNull((Object)urlMappings, (String)"UrlMappings must not be null");
        this.urlMappings.addAll(Arrays.asList(urlMappings));
    }

    public void setLoadOnStartup(int loadOnStartup) {
        this.loadOnStartup = loadOnStartup;
    }

    public void setMultipartConfig(MultipartConfigElement multipartConfig) {
        this.multipartConfig = multipartConfig;
    }

    public MultipartConfigElement getMultipartConfig() {
        return this.multipartConfig;
    }

    @Override
    protected String getDescription() {
        Assert.notNull(this.servlet, (String)"Servlet must not be null");
        return "servlet " + this.getServletName();
    }

    @Override
    protected ServletRegistration.Dynamic addRegistration(String description, ServletContext servletContext) {
        String name = this.getServletName();
        return servletContext.addServlet(name, this.servlet);
    }

    @Override
    protected void configure(ServletRegistration.Dynamic registration) {
        super.configure(registration);
        Object[] urlMapping = StringUtils.toStringArray(this.urlMappings);
        if (urlMapping.length == 0 && this.alwaysMapUrl) {
            urlMapping = DEFAULT_MAPPINGS;
        }
        if (!ObjectUtils.isEmpty((Object[])urlMapping)) {
            registration.addMapping((String[])urlMapping);
        }
        registration.setLoadOnStartup(this.loadOnStartup);
        if (this.multipartConfig != null) {
            registration.setMultipartConfig(this.multipartConfig);
        }
    }

    public String getServletName() {
        return this.getOrDeduceName(this.servlet);
    }

    public String toString() {
        return this.getServletName() + " urls=" + this.getUrlMappings();
    }
}

