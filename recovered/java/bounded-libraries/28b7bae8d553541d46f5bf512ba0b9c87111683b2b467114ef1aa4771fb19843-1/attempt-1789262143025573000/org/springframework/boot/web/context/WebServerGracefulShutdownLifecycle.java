/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.SmartLifecycle
 */
package org.springframework.boot.web.context;

import org.springframework.boot.web.server.WebServer;
import org.springframework.context.SmartLifecycle;

public final class WebServerGracefulShutdownLifecycle
implements SmartLifecycle {
    public static final int SMART_LIFECYCLE_PHASE = Integer.MAX_VALUE;
    private final WebServer webServer;
    private volatile boolean running;

    public WebServerGracefulShutdownLifecycle(WebServer webServer) {
        this.webServer = webServer;
    }

    public void start() {
        this.running = true;
    }

    public void stop() {
        throw new UnsupportedOperationException("Stop must not be invoked directly");
    }

    public void stop(Runnable callback) {
        this.running = false;
        this.webServer.shutDownGracefully(result -> callback.run());
    }

    public boolean isRunning() {
        return this.running;
    }

    public int getPhase() {
        return Integer.MAX_VALUE;
    }
}

