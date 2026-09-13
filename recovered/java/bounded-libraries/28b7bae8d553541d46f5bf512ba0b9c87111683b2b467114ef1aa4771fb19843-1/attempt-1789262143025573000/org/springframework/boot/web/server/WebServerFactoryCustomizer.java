/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.web.server;

import org.springframework.boot.web.server.WebServerFactory;

@FunctionalInterface
public interface WebServerFactoryCustomizer<T extends WebServerFactory> {
    public void customize(T var1);
}

