/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletContext
 *  javax.servlet.http.HttpServletRequest
 *  org.springframework.security.web.util.matcher.RequestMatcher
 *  org.springframework.util.Assert
 *  org.springframework.web.context.WebApplicationContext
 *  org.springframework.web.context.support.WebApplicationContextUtils
 */
package org.springframework.boot.security.servlet;

import java.util.function.Supplier;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public abstract class ApplicationContextRequestMatcher<C>
implements RequestMatcher {
    private final Class<? extends C> contextClass;
    private volatile boolean initialized;
    private final Object initializeLock = new Object();

    public ApplicationContextRequestMatcher(Class<? extends C> contextClass) {
        Assert.notNull(contextClass, (String)"Context class must not be null");
        this.contextClass = contextClass;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final boolean matches(HttpServletRequest request) {
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext((ServletContext)request.getServletContext());
        if (this.ignoreApplicationContext(webApplicationContext)) {
            return false;
        }
        Supplier<Object> context = () -> this.getContext(webApplicationContext);
        if (!this.initialized) {
            Object object = this.initializeLock;
            synchronized (object) {
                if (!this.initialized) {
                    this.initialized(context);
                    this.initialized = true;
                }
            }
        }
        return this.matches(request, context);
    }

    private C getContext(WebApplicationContext webApplicationContext) {
        if (this.contextClass.isInstance(webApplicationContext)) {
            return (C)webApplicationContext;
        }
        return (C)webApplicationContext.getBean(this.contextClass);
    }

    protected boolean ignoreApplicationContext(WebApplicationContext webApplicationContext) {
        return false;
    }

    protected void initialized(Supplier<C> context) {
    }

    protected abstract boolean matches(HttpServletRequest var1, Supplier<C> var2);
}

