/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.web.reactive.server;

import org.springframework.boot.web.reactive.server.ConfigurableReactiveWebServerFactory;
import org.springframework.boot.web.server.AbstractConfigurableWebServerFactory;

public abstract class AbstractReactiveWebServerFactory
extends AbstractConfigurableWebServerFactory
implements ConfigurableReactiveWebServerFactory {
    public AbstractReactiveWebServerFactory() {
    }

    public AbstractReactiveWebServerFactory(int port) {
        super(port);
    }
}

