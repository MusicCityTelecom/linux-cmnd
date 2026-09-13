/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.web.servlet.server;

import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.boot.web.servlet.ServletContextInitializer;

@FunctionalInterface
public interface ServletWebServerFactory
extends WebServerFactory {
    public WebServer getWebServer(ServletContextInitializer ... var1);
}

