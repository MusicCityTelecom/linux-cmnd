/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.Servlet
 *  org.springframework.boot.web.servlet.ServletRegistrationBean
 *  org.springframework.util.Assert
 *  org.springframework.web.servlet.DispatcherServlet
 */
package org.springframework.boot.autoconfigure.web.servlet;

import java.util.Collection;
import javax.servlet.Servlet;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletPath;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.util.Assert;
import org.springframework.web.servlet.DispatcherServlet;

public class DispatcherServletRegistrationBean
extends ServletRegistrationBean<DispatcherServlet>
implements DispatcherServletPath {
    private final String path;

    public DispatcherServletRegistrationBean(DispatcherServlet servlet, String path) {
        super((Servlet)servlet, new String[0]);
        Assert.notNull((Object)path, (String)"Path must not be null");
        this.path = path;
        super.addUrlMappings(new String[]{this.getServletUrlMapping()});
    }

    @Override
    public String getPath() {
        return this.path;
    }

    public void setUrlMappings(Collection<String> urlMappings) {
        throw new UnsupportedOperationException("URL Mapping cannot be changed on a DispatcherServlet registration");
    }

    public void addUrlMappings(String ... urlMappings) {
        throw new UnsupportedOperationException("URL Mapping cannot be changed on a DispatcherServlet registration");
    }
}

