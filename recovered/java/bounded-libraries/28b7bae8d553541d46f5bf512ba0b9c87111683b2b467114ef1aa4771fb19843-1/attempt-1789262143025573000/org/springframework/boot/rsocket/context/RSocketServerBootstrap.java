/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.rsocket.SocketAcceptor
 *  org.springframework.context.ApplicationEvent
 *  org.springframework.context.ApplicationEventPublisher
 *  org.springframework.context.ApplicationEventPublisherAware
 *  org.springframework.context.SmartLifecycle
 *  org.springframework.util.Assert
 */
package org.springframework.boot.rsocket.context;

import io.rsocket.SocketAcceptor;
import org.springframework.boot.rsocket.context.RSocketServerInitializedEvent;
import org.springframework.boot.rsocket.server.RSocketServer;
import org.springframework.boot.rsocket.server.RSocketServerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.SmartLifecycle;
import org.springframework.util.Assert;

public class RSocketServerBootstrap
implements ApplicationEventPublisherAware,
SmartLifecycle {
    private final RSocketServer server;
    private ApplicationEventPublisher eventPublisher;

    public RSocketServerBootstrap(RSocketServerFactory serverFactory, SocketAcceptor socketAcceptor) {
        Assert.notNull((Object)serverFactory, (String)"ServerFactory must not be null");
        this.server = serverFactory.create(socketAcceptor);
    }

    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }

    public void start() {
        this.server.start();
        this.eventPublisher.publishEvent((ApplicationEvent)new RSocketServerInitializedEvent(this.server));
    }

    public void stop() {
        this.server.stop();
    }

    public boolean isRunning() {
        RSocketServer server = this.server;
        if (server != null) {
            return server.address() != null;
        }
        return false;
    }
}

