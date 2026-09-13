/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.Filter
 *  org.springframework.util.Assert
 */
package org.springframework.boot.web.servlet;

import javax.servlet.Filter;
import org.springframework.boot.web.servlet.AbstractFilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.util.Assert;

public class FilterRegistrationBean<T extends Filter>
extends AbstractFilterRegistrationBean<T> {
    private T filter;

    public FilterRegistrationBean() {
        super(new ServletRegistrationBean[0]);
    }

    public FilterRegistrationBean(T filter, ServletRegistrationBean<?> ... servletRegistrationBeans) {
        super(servletRegistrationBeans);
        Assert.notNull(filter, (String)"Filter must not be null");
        this.filter = filter;
    }

    @Override
    public T getFilter() {
        return this.filter;
    }

    public void setFilter(T filter) {
        Assert.notNull(filter, (String)"Filter must not be null");
        this.filter = filter;
    }
}

