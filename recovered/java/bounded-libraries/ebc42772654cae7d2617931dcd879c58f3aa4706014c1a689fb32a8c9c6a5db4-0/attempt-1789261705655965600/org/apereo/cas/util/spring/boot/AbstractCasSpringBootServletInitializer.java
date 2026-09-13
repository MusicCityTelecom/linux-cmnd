/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletContext
 *  javax.servlet.ServletException
 *  javax.servlet.SessionTrackingMode
 *  lombok.Generated
 *  org.springframework.boot.Banner
 *  org.springframework.boot.WebApplicationType
 *  org.springframework.boot.builder.SpringApplicationBuilder
 *  org.springframework.boot.web.servlet.support.SpringBootServletInitializer
 *  org.springframework.core.metrics.ApplicationStartup
 */
package org.apereo.cas.util.spring.boot;

import java.util.List;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.SessionTrackingMode;
import lombok.Generated;
import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.core.metrics.ApplicationStartup;

public abstract class AbstractCasSpringBootServletInitializer
extends SpringBootServletInitializer {
    private final List<Class<?>> sources;
    private final Banner banner;
    private final ApplicationStartup applicationStartup;

    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.setSessionTrackingModes(Set.of(SessionTrackingMode.COOKIE));
        super.onStartup(servletContext);
    }

    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.web(WebApplicationType.SERVLET).sources((Class[])this.sources.toArray(Class[]::new)).applicationStartup(this.applicationStartup).logStartupInfo(true).banner(this.banner);
    }

    @Generated
    public AbstractCasSpringBootServletInitializer(List<Class<?>> sources, Banner banner, ApplicationStartup applicationStartup) {
        this.sources = sources;
        this.banner = banner;
        this.applicationStartup = applicationStartup;
    }
}

