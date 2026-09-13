/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.eclipse.jetty.server.Server
 */
package org.springframework.boot.web.embedded.jetty;

import org.eclipse.jetty.server.Server;

@FunctionalInterface
public interface JettyServerCustomizer {
    public void customize(Server var1);
}

