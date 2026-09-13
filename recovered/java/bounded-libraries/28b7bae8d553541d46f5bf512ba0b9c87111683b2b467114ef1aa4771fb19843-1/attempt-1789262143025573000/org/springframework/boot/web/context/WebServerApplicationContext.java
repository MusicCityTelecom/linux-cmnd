/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.ApplicationContext
 *  org.springframework.util.ObjectUtils
 */
package org.springframework.boot.web.context;

import org.springframework.boot.web.server.WebServer;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ObjectUtils;

public interface WebServerApplicationContext
extends ApplicationContext {
    public WebServer getWebServer();

    public String getServerNamespace();

    public static boolean hasServerNamespace(ApplicationContext context, String serverNamespace) {
        return context instanceof WebServerApplicationContext && ObjectUtils.nullSafeEquals((Object)((WebServerApplicationContext)context).getServerNamespace(), (Object)serverNamespace);
    }

    public static String getServerNamespace(ApplicationContext context) {
        return context instanceof WebServerApplicationContext ? ((WebServerApplicationContext)context).getServerNamespace() : null;
    }
}

