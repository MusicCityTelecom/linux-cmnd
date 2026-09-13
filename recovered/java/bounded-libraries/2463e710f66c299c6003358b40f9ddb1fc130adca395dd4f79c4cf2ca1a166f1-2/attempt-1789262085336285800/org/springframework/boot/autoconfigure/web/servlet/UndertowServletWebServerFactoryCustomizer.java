/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory
 *  org.springframework.boot.web.server.WebServerFactoryCustomizer
 */
package org.springframework.boot.autoconfigure.web.servlet;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;

public class UndertowServletWebServerFactoryCustomizer
implements WebServerFactoryCustomizer<UndertowServletWebServerFactory> {
    private final ServerProperties serverProperties;

    public UndertowServletWebServerFactoryCustomizer(ServerProperties serverProperties) {
        this.serverProperties = serverProperties;
    }

    public void customize(UndertowServletWebServerFactory factory) {
        factory.setEagerFilterInit(this.serverProperties.getUndertow().isEagerFilterInit());
        factory.setPreservePathOnForward(this.serverProperties.getUndertow().isPreservePathOnForward());
    }
}

