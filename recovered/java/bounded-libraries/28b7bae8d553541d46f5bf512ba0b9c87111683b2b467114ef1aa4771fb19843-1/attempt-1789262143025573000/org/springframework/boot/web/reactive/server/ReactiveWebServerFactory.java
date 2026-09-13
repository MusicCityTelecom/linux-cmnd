/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.http.server.reactive.HttpHandler
 */
package org.springframework.boot.web.reactive.server;

import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.http.server.reactive.HttpHandler;

@FunctionalInterface
public interface ReactiveWebServerFactory
extends WebServerFactory {
    public WebServer getWebServer(HttpHandler var1);
}

