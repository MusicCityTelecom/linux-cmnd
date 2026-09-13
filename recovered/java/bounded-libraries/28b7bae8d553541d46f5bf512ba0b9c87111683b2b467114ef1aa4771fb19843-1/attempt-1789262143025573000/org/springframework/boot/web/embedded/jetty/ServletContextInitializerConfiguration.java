/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletContext
 *  javax.servlet.ServletException
 *  org.eclipse.jetty.webapp.AbstractConfiguration
 *  org.eclipse.jetty.webapp.WebAppContext
 *  org.springframework.util.Assert
 */
package org.springframework.boot.web.embedded.jetty;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.eclipse.jetty.webapp.AbstractConfiguration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.util.Assert;

public class ServletContextInitializerConfiguration
extends AbstractConfiguration {
    private final ServletContextInitializer[] initializers;

    public ServletContextInitializerConfiguration(ServletContextInitializer ... initializers) {
        Assert.notNull((Object)initializers, (String)"Initializers must not be null");
        this.initializers = initializers;
    }

    public void configure(WebAppContext context) throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(context.getClassLoader());
        try {
            this.callInitializers(context);
        }
        finally {
            Thread.currentThread().setContextClassLoader(classLoader);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void callInitializers(WebAppContext context) throws ServletException {
        try {
            this.setExtendedListenerTypes(context, true);
            for (ServletContextInitializer initializer : this.initializers) {
                initializer.onStartup((ServletContext)context.getServletContext());
            }
        }
        finally {
            this.setExtendedListenerTypes(context, false);
        }
    }

    private void setExtendedListenerTypes(WebAppContext context, boolean extended) {
        try {
            context.getServletContext().setExtendedListenerTypes(extended);
        }
        catch (NoSuchMethodError noSuchMethodError) {
            // empty catch block
        }
    }
}

