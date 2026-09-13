/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.Filter
 *  javax.servlet.Servlet
 *  javax.servlet.ServletContext
 *  javax.servlet.ServletException
 *  javax.servlet.ServletRegistration$Dynamic
 */
package com.tpvision.smartcms.init;

import com.tpvision.smartcms.init.WebAppConfig;
import java.util.EventListener;
import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

public class Initializer
implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext container) throws ServletException {
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(WebAppConfig.class);
        container.addListener((EventListener)((Object)new ContextLoaderListener(ctx)));
        ctx.setServletContext(container);
        ServletRegistration.Dynamic servlet = container.addServlet("dispatcher", (Servlet)new DispatcherServlet(ctx));
        servlet.addMapping(new String[]{"/"});
        servlet.setLoadOnStartup(1);
    }

    private void addFilter(ServletContext container) {
        String filterName = "WhatEverYouWantToNameYourFilter";
        String filterBeanName = "mdcInsertingServletFilter";
        container.addFilter(filterName, (Filter)new DelegatingFilterProxy(filterBeanName)).addMappingForUrlPatterns(null, false, new String[]{"/"});
    }
}

