/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.web.embedded.tomcat.ConfigurableTomcatWebServerFactory
 *  org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer
 *  org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
 *  org.springframework.boot.web.server.WebServerFactoryCustomizer
 *  org.springframework.core.Ordered
 *  org.springframework.util.ObjectUtils
 */
package org.springframework.boot.autoconfigure.web.servlet;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.embedded.tomcat.ConfigurableTomcatWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.core.Ordered;
import org.springframework.util.ObjectUtils;

public class TomcatServletWebServerFactoryCustomizer
implements WebServerFactoryCustomizer<TomcatServletWebServerFactory>,
Ordered {
    private final ServerProperties serverProperties;

    public TomcatServletWebServerFactoryCustomizer(ServerProperties serverProperties) {
        this.serverProperties = serverProperties;
    }

    public int getOrder() {
        return 0;
    }

    public void customize(TomcatServletWebServerFactory factory) {
        ServerProperties.Tomcat tomcatProperties = this.serverProperties.getTomcat();
        if (!ObjectUtils.isEmpty(tomcatProperties.getAdditionalTldSkipPatterns())) {
            factory.getTldSkipPatterns().addAll(tomcatProperties.getAdditionalTldSkipPatterns());
        }
        if (tomcatProperties.getRedirectContextRoot() != null) {
            this.customizeRedirectContextRoot((ConfigurableTomcatWebServerFactory)factory, tomcatProperties.getRedirectContextRoot());
        }
        this.customizeUseRelativeRedirects((ConfigurableTomcatWebServerFactory)factory, tomcatProperties.isUseRelativeRedirects());
        factory.setDisableMBeanRegistry(!tomcatProperties.getMbeanregistry().isEnabled());
    }

    private void customizeRedirectContextRoot(ConfigurableTomcatWebServerFactory factory, boolean redirectContextRoot) {
        factory.addContextCustomizers(new TomcatContextCustomizer[]{context -> context.setMapperContextRootRedirectEnabled(redirectContextRoot)});
    }

    private void customizeUseRelativeRedirects(ConfigurableTomcatWebServerFactory factory, boolean useRelativeRedirects) {
        factory.addContextCustomizers(new TomcatContextCustomizer[]{context -> context.setUseRelativeRedirects(useRelativeRedirects)});
    }
}

