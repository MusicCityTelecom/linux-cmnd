/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.web.server;

import org.springframework.boot.web.server.GracefulShutdownResult;

@FunctionalInterface
public interface GracefulShutdownCallback {
    public void shutdownComplete(GracefulShutdownResult var1);
}

