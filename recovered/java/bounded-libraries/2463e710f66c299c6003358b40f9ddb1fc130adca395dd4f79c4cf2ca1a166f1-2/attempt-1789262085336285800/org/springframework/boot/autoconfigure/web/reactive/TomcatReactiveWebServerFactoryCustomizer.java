/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.web.embedded.tomcat.TomcatReactiveWebServerFactory
 *  org.springframework.boot.web.server.WebServerFactoryCustomizer
 */
package org.springframework.boot.autoconfigure.web.reactive;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatReactiveWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;

public class TomcatReactiveWebServerFactoryCustomizer
implements WebServerFactoryCustomizer<TomcatReactiveWebServerFactory> {
    private final ServerProperties serverProperties;

    public TomcatReactiveWebServerFactoryCustomizer(ServerProperties serverProperties) {
        this.serverProperties = serverProperties;
    }

    public void customize(TomcatReactiveWebServerFactory factory) {
        factory.setDisableMBeanRegistry(!this.serverProperties.getTomcat().getMbeanregistry().isEnabled());
    }
}

