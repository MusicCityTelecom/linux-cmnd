/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.SmartLifecycle
 */
package org.springframework.boot.web.servlet.context;

import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.SmartLifecycle;

class WebServerStartStopLifecycle
implements SmartLifecycle {
    private final ServletWebServerApplicationContext applicationContext;
    private final WebServer webServer;
    private volatile boolean running;

    WebServerStartStopLifecycle(ServletWebServerApplicationContext applicationContext, WebServer webServer) {
        this.applicationContext = applicationContext;
        this.webServer = webServer;
    }

    public void start() {
        this.webServer.start();
        this.running = true;
        this.applicationContext.publishEvent(new ServletWebServerInitializedEvent(this.webServer, this.applicationContext));
    }

    public void stop() {
        this.running = false;
        this.webServer.stop();
    }

    public boolean isRunning() {
        return this.running;
    }

    public int getPhase() {
        return 0x7FFFFFFE;
    }
}

