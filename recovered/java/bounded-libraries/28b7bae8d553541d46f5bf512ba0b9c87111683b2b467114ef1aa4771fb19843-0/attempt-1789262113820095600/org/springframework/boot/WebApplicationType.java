/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.ClassUtils
 */
package org.springframework.boot;

import org.springframework.util.ClassUtils;

public enum WebApplicationType {
    NONE,
    SERVLET,
    REACTIVE;

    private static final String[] SERVLET_INDICATOR_CLASSES;
    private static final String WEBMVC_INDICATOR_CLASS = "org.springframework.web.servlet.DispatcherServlet";
    private static final String WEBFLUX_INDICATOR_CLASS = "org.springframework.web.reactive.DispatcherHandler";
    private static final String JERSEY_INDICATOR_CLASS = "org.glassfish.jersey.servlet.ServletContainer";

    static WebApplicationType deduceFromClasspath() {
        if (ClassUtils.isPresent((String)WEBFLUX_INDICATOR_CLASS, null) && !ClassUtils.isPresent((String)WEBMVC_INDICATOR_CLASS, null) && !ClassUtils.isPresent((String)JERSEY_INDICATOR_CLASS, null)) {
            return REACTIVE;
        }
        for (String className : SERVLET_INDICATOR_CLASSES) {
            if (ClassUtils.isPresent((String)className, null)) continue;
            return NONE;
        }
        return SERVLET;
    }

    static {
        SERVLET_INDICATOR_CLASSES = new String[]{"javax.servlet.Servlet", "org.springframework.web.context.ConfigurableWebApplicationContext"};
    }
}

