/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.undertow.server.HttpHandler
 */
package org.springframework.boot.web.embedded.undertow;

import io.undertow.server.HttpHandler;

@FunctionalInterface
public interface HttpHandlerFactory {
    public HttpHandler getHandler(HttpHandler var1);
}

