/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.web.servlet.context;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;

public class ServletWebServerInitializedEvent
extends WebServerInitializedEvent {
    private final ServletWebServerApplicationContext applicationContext;

    public ServletWebServerInitializedEvent(WebServer webServer, ServletWebServerApplicationContext applicationContext) {
        super(webServer);
        this.applicationContext = applicationContext;
    }

    @Override
    public ServletWebServerApplicationContext getApplicationContext() {
        return this.applicationContext;
    }
}

