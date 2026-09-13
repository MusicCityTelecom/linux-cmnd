/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.web.reactive.context;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.boot.web.reactive.context.ReactiveWebServerApplicationContext;
import org.springframework.boot.web.server.WebServer;

public class ReactiveWebServerInitializedEvent
extends WebServerInitializedEvent {
    private final ReactiveWebServerApplicationContext applicationContext;

    public ReactiveWebServerInitializedEvent(WebServer webServer, ReactiveWebServerApplicationContext applicationContext) {
        super(webServer);
        this.applicationContext = applicationContext;
    }

    @Override
    public ReactiveWebServerApplicationContext getApplicationContext() {
        return this.applicationContext;
    }
}

