/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.web.server;

import org.springframework.boot.web.server.ErrorPage;

@FunctionalInterface
public interface ErrorPageRegistry {
    public void addErrorPages(ErrorPage ... var1);
}

