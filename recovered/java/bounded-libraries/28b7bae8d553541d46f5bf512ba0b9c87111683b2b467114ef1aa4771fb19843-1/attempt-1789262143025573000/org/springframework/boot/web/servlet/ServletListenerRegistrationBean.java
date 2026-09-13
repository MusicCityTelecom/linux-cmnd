/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletContext
 *  javax.servlet.ServletContextAttributeListener
 *  javax.servlet.ServletContextListener
 *  javax.servlet.ServletRequestAttributeListener
 *  javax.servlet.ServletRequestListener
 *  javax.servlet.http.HttpSessionAttributeListener
 *  javax.servlet.http.HttpSessionIdListener
 *  javax.servlet.http.HttpSessionListener
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 */
package org.springframework.boot.web.servlet;

import java.util.Collections;
import java.util.EventListener;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionIdListener;
import javax.servlet.http.HttpSessionListener;
import org.springframework.boot.web.servlet.RegistrationBean;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

public class ServletListenerRegistrationBean<T extends EventListener>
extends RegistrationBean {
    private static final Set<Class<?>> SUPPORTED_TYPES;
    private T listener;

    public ServletListenerRegistrationBean() {
    }

    public ServletListenerRegistrationBean(T listener) {
        Assert.notNull(listener, (String)"Listener must not be null");
        Assert.isTrue((boolean)ServletListenerRegistrationBean.isSupportedType(listener), (String)"Listener is not of a supported type");
        this.listener = listener;
    }

    public void setListener(T listener) {
        Assert.notNull(listener, (String)"Listener must not be null");
        Assert.isTrue((boolean)ServletListenerRegistrationBean.isSupportedType(listener), (String)"Listener is not of a supported type");
        this.listener = listener;
    }

    public T getListener() {
        return this.listener;
    }

    @Override
    protected String getDescription() {
        Assert.notNull(this.listener, (String)"Listener must not be null");
        return "listener " + this.listener;
    }

    @Override
    protected void register(String description, ServletContext servletContext) {
        try {
            servletContext.addListener(this.listener);
        }
        catch (RuntimeException ex) {
            throw new IllegalStateException("Failed to add listener '" + this.listener + "' to servlet context", ex);
        }
    }

    public static boolean isSupportedType(EventListener listener) {
        for (Class<?> type : SUPPORTED_TYPES) {
            if (!ClassUtils.isAssignableValue(type, (Object)listener)) continue;
            return true;
        }
        return false;
    }

    public static Set<Class<?>> getSupportedTypes() {
        return SUPPORTED_TYPES;
    }

    static {
        HashSet<Class<ServletContextListener>> types = new HashSet<Class<ServletContextListener>>();
        types.add(ServletContextAttributeListener.class);
        types.add(ServletRequestListener.class);
        types.add(ServletRequestAttributeListener.class);
        types.add(HttpSessionAttributeListener.class);
        types.add(HttpSessionIdListener.class);
        types.add(HttpSessionListener.class);
        types.add(ServletContextListener.class);
        SUPPORTED_TYPES = Collections.unmodifiableSet(types);
    }
}

