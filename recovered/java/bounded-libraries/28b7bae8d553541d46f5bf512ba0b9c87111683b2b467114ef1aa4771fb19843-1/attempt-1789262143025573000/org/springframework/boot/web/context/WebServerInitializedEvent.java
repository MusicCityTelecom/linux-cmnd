/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.ApplicationEvent
 */
package org.springframework.boot.web.context;

import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.boot.web.server.WebServer;
import org.springframework.context.ApplicationEvent;

public abstract class WebServerInitializedEvent
extends ApplicationEvent {
    protected WebServerInitializedEvent(WebServer webServer) {
        super((Object)webServer);
    }

    public WebServer getWebServer() {
        return this.getSource();
    }

    public abstract WebServerApplicationContext getApplicationContext();

    public WebServer getSource() {
        return (WebServer)super.getSource();
    }
}

