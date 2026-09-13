/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.context.properties.PropertyMapper
 *  org.springframework.boot.web.server.WebServerFactoryCustomizer
 *  org.springframework.boot.web.servlet.WebListenerRegistrar
 *  org.springframework.boot.web.servlet.WebListenerRegistry
 *  org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory
 *  org.springframework.boot.web.servlet.server.CookieSameSiteSupplier
 *  org.springframework.core.Ordered
 *  org.springframework.util.CollectionUtils
 */
package org.springframework.boot.autoconfigure.web.servlet;

import java.util.Collections;
import java.util.List;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.WebListenerRegistrar;
import org.springframework.boot.web.servlet.WebListenerRegistry;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.boot.web.servlet.server.CookieSameSiteSupplier;
import org.springframework.core.Ordered;
import org.springframework.util.CollectionUtils;

public class ServletWebServerFactoryCustomizer
implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>,
Ordered {
    private final ServerProperties serverProperties;
    private final List<WebListenerRegistrar> webListenerRegistrars;
    private final List<CookieSameSiteSupplier> cookieSameSiteSuppliers;

    public ServletWebServerFactoryCustomizer(ServerProperties serverProperties) {
        this(serverProperties, Collections.emptyList());
    }

    public ServletWebServerFactoryCustomizer(ServerProperties serverProperties, List<WebListenerRegistrar> webListenerRegistrars) {
        this(serverProperties, webListenerRegistrars, null);
    }

    ServletWebServerFactoryCustomizer(ServerProperties serverProperties, List<WebListenerRegistrar> webListenerRegistrars, List<CookieSameSiteSupplier> cookieSameSiteSuppliers) {
        this.serverProperties = serverProperties;
        this.webListenerRegistrars = webListenerRegistrars;
        this.cookieSameSiteSuppliers = cookieSameSiteSuppliers;
    }

    public int getOrder() {
        return 0;
    }

    public void customize(ConfigurableServletWebServerFactory factory) {
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        map.from(this.serverProperties::getPort).to(arg_0 -> ((ConfigurableServletWebServerFactory)factory).setPort(arg_0));
        map.from(this.serverProperties::getAddress).to(arg_0 -> ((ConfigurableServletWebServerFactory)factory).setAddress(arg_0));
        map.from(this.serverProperties.getServlet()::getContextPath).to(arg_0 -> ((ConfigurableServletWebServerFactory)factory).setContextPath(arg_0));
        map.from(this.serverProperties.getServlet()::getApplicationDisplayName).to(arg_0 -> ((ConfigurableServletWebServerFactory)factory).setDisplayName(arg_0));
        map.from(this.serverProperties.getServlet()::isRegisterDefaultServlet).to(arg_0 -> ((ConfigurableServletWebServerFactory)factory).setRegisterDefaultServlet(arg_0));
        map.from(this.serverProperties.getServlet()::getSession).to(arg_0 -> ((ConfigurableServletWebServerFactory)factory).setSession(arg_0));
        map.from(this.serverProperties::getSsl).to(arg_0 -> ((ConfigurableServletWebServerFactory)factory).setSsl(arg_0));
        map.from(this.serverProperties.getServlet()::getJsp).to(arg_0 -> ((ConfigurableServletWebServerFactory)factory).setJsp(arg_0));
        map.from(this.serverProperties::getCompression).to(arg_0 -> ((ConfigurableServletWebServerFactory)factory).setCompression(arg_0));
        map.from(this.serverProperties::getHttp2).to(arg_0 -> ((ConfigurableServletWebServerFactory)factory).setHttp2(arg_0));
        map.from(this.serverProperties::getServerHeader).to(arg_0 -> ((ConfigurableServletWebServerFactory)factory).setServerHeader(arg_0));
        map.from(this.serverProperties.getServlet()::getContextParameters).to(arg_0 -> ((ConfigurableServletWebServerFactory)factory).setInitParameters(arg_0));
        map.from((Object)this.serverProperties.getShutdown()).to(arg_0 -> ((ConfigurableServletWebServerFactory)factory).setShutdown(arg_0));
        for (WebListenerRegistrar registrar : this.webListenerRegistrars) {
            registrar.register((WebListenerRegistry)factory);
        }
        if (!CollectionUtils.isEmpty(this.cookieSameSiteSuppliers)) {
            factory.setCookieSameSiteSuppliers(this.cookieSameSiteSuppliers);
        }
    }
}

