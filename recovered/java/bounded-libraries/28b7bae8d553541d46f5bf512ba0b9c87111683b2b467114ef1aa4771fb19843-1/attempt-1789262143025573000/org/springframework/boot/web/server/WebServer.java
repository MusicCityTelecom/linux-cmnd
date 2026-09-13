/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.web.server;

import org.springframework.boot.web.server.GracefulShutdownCallback;
import org.springframework.boot.web.server.GracefulShutdownResult;
import org.springframework.boot.web.server.WebServerException;

public interface WebServer {
    public void start() throws WebServerException;

    public void stop() throws WebServerException;

    public int getPort();

    default public void shutDownGracefully(GracefulShutdownCallback callback) {
        callback.shutdownComplete(GracefulShutdownResult.IMMEDIATE);
    }
}

