/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.SmartLifecycle
 */
package org.springframework.boot.web.reactive.context;

import org.springframework.boot.web.reactive.context.WebServerManager;
import org.springframework.context.SmartLifecycle;

class WebServerStartStopLifecycle
implements SmartLifecycle {
    private final WebServerManager weServerManager;
    private volatile boolean running;

    WebServerStartStopLifecycle(WebServerManager weServerManager) {
        this.weServerManager = weServerManager;
    }

    public void start() {
        this.weServerManager.start();
        this.running = true;
    }

    public void stop() {
        this.running = false;
        this.weServerManager.stop();
    }

    public boolean isRunning() {
        return this.running;
    }

    public int getPhase() {
        return 0x7FFFFFFE;
    }
}

