/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.web.server;

import org.springframework.boot.web.server.ErrorPageRegistry;

@FunctionalInterface
public interface ErrorPageRegistrar {
    public void registerErrorPages(ErrorPageRegistry var1);
}

