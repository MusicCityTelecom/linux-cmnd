/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletContext
 *  org.springframework.util.Assert
 *  org.springframework.web.context.WebApplicationContext
 *  org.springframework.web.context.support.WebApplicationContextUtils
 */
package org.springframework.security.web.context.support;

import javax.servlet.ServletContext;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public abstract class SecurityWebApplicationContextUtils
extends WebApplicationContextUtils {
    public static WebApplicationContext findRequiredWebApplicationContext(ServletContext servletContext) {
        WebApplicationContext webApplicationContext = SecurityWebApplicationContextUtils.findWebApplicationContext((ServletContext)servletContext);
        Assert.state((webApplicationContext != null ? 1 : 0) != 0, (String)"No WebApplicationContext found: no ContextLoaderListener registered?");
        return webApplicationContext;
    }
}

