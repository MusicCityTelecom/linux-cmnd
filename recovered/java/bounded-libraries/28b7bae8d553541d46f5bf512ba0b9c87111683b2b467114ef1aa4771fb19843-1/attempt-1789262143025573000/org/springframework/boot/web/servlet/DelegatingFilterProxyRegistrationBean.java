/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  org.springframework.beans.BeansException
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationContextAware
 *  org.springframework.util.Assert
 *  org.springframework.web.context.WebApplicationContext
 *  org.springframework.web.filter.DelegatingFilterProxy
 */
package org.springframework.boot.web.servlet;

import javax.servlet.ServletException;
import org.springframework.beans.BeansException;
import org.springframework.boot.web.servlet.AbstractFilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;

public class DelegatingFilterProxyRegistrationBean
extends AbstractFilterRegistrationBean<DelegatingFilterProxy>
implements ApplicationContextAware {
    private ApplicationContext applicationContext;
    private final String targetBeanName;

    public DelegatingFilterProxyRegistrationBean(String targetBeanName, ServletRegistrationBean<?> ... servletRegistrationBeans) {
        super(servletRegistrationBeans);
        Assert.hasLength((String)targetBeanName, (String)"TargetBeanName must not be null or empty");
        this.targetBeanName = targetBeanName;
        this.setName(targetBeanName);
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    protected String getTargetBeanName() {
        return this.targetBeanName;
    }

    @Override
    public DelegatingFilterProxy getFilter() {
        return new DelegatingFilterProxy(this.targetBeanName, this.getWebApplicationContext()){

            protected void initFilterBean() throws ServletException {
            }
        };
    }

    private WebApplicationContext getWebApplicationContext() {
        Assert.notNull((Object)this.applicationContext, (String)"ApplicationContext be injected");
        Assert.isInstanceOf(WebApplicationContext.class, (Object)this.applicationContext);
        return (WebApplicationContext)this.applicationContext;
    }
}

