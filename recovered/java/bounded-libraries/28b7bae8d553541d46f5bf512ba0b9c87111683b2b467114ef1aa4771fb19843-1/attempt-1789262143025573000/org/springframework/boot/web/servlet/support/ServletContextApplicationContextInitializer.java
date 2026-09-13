/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletContext
 *  org.springframework.context.ApplicationContextInitializer
 *  org.springframework.core.Ordered
 *  org.springframework.web.context.ConfigurableWebApplicationContext
 *  org.springframework.web.context.WebApplicationContext
 */
package org.springframework.boot.web.servlet.support;

import javax.servlet.ServletContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.core.Ordered;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.WebApplicationContext;

public class ServletContextApplicationContextInitializer
implements ApplicationContextInitializer<ConfigurableWebApplicationContext>,
Ordered {
    private int order = Integer.MIN_VALUE;
    private final ServletContext servletContext;
    private final boolean addApplicationContextAttribute;

    public ServletContextApplicationContextInitializer(ServletContext servletContext) {
        this(servletContext, false);
    }

    public ServletContextApplicationContextInitializer(ServletContext servletContext, boolean addApplicationContextAttribute) {
        this.servletContext = servletContext;
        this.addApplicationContextAttribute = addApplicationContextAttribute;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getOrder() {
        return this.order;
    }

    public void initialize(ConfigurableWebApplicationContext applicationContext) {
        applicationContext.setServletContext(this.servletContext);
        if (this.addApplicationContextAttribute) {
            this.servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, (Object)applicationContext);
        }
    }
}

