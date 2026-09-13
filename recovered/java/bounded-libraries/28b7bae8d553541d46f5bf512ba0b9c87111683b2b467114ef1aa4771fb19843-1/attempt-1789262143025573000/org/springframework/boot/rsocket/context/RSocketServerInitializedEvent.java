/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.ApplicationEvent
 */
package org.springframework.boot.rsocket.context;

import org.springframework.boot.rsocket.server.RSocketServer;
import org.springframework.context.ApplicationEvent;

public class RSocketServerInitializedEvent
extends ApplicationEvent {
    public RSocketServerInitializedEvent(RSocketServer server) {
        super((Object)server);
    }

    public RSocketServer getServer() {
        return this.getSource();
    }

    public RSocketServer getSource() {
        return (RSocketServer)super.getSource();
    }
}

