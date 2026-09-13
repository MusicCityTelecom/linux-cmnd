/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.context.properties.PropertyMapper
 *  org.springframework.boot.web.reactive.server.ConfigurableReactiveWebServerFactory
 *  org.springframework.boot.web.server.WebServerFactoryCustomizer
 *  org.springframework.core.Ordered
 */
package org.springframework.boot.autoconfigure.web.reactive;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.boot.web.reactive.server.ConfigurableReactiveWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.core.Ordered;

public class ReactiveWebServerFactoryCustomizer
implements WebServerFactoryCustomizer<ConfigurableReactiveWebServerFactory>,
Ordered {
    private final ServerProperties serverProperties;

    public ReactiveWebServerFactoryCustomizer(ServerProperties serverProperties) {
        this.serverProperties = serverProperties;
    }

    public int getOrder() {
        return 0;
    }

    public void customize(ConfigurableReactiveWebServerFactory factory) {
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        map.from(this.serverProperties::getPort).to(arg_0 -> ((ConfigurableReactiveWebServerFactory)factory).setPort(arg_0));
        map.from(this.serverProperties::getAddress).to(arg_0 -> ((ConfigurableReactiveWebServerFactory)factory).setAddress(arg_0));
        map.from(this.serverProperties::getSsl).to(arg_0 -> ((ConfigurableReactiveWebServerFactory)factory).setSsl(arg_0));
        map.from(this.serverProperties::getCompression).to(arg_0 -> ((ConfigurableReactiveWebServerFactory)factory).setCompression(arg_0));
        map.from(this.serverProperties::getHttp2).to(arg_0 -> ((ConfigurableReactiveWebServerFactory)factory).setHttp2(arg_0));
        map.from((Object)this.serverProperties.getShutdown()).to(arg_0 -> ((ConfigurableReactiveWebServerFactory)factory).setShutdown(arg_0));
    }
}

