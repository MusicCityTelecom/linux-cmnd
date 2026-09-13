/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.eclipse.jetty.server.ConnectionFactory
 *  org.eclipse.jetty.server.Connector
 *  org.eclipse.jetty.server.ForwardedRequestCustomizer
 *  org.eclipse.jetty.server.HttpConfiguration$ConnectionFactory
 *  org.eclipse.jetty.server.HttpConfiguration$Customizer
 *  org.eclipse.jetty.server.Server
 */
package org.springframework.boot.web.embedded.jetty;

import org.eclipse.jetty.server.ConnectionFactory;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.ForwardedRequestCustomizer;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.Server;
import org.springframework.boot.web.embedded.jetty.JettyServerCustomizer;

class ForwardHeadersCustomizer
implements JettyServerCustomizer {
    ForwardHeadersCustomizer() {
    }

    @Override
    public void customize(Server server) {
        ForwardedRequestCustomizer customizer = new ForwardedRequestCustomizer();
        for (Connector connector : server.getConnectors()) {
            for (ConnectionFactory connectionFactory : connector.getConnectionFactories()) {
                if (!(connectionFactory instanceof HttpConfiguration.ConnectionFactory)) continue;
                ((HttpConfiguration.ConnectionFactory)connectionFactory).getHttpConfiguration().addCustomizer((HttpConfiguration.Customizer)customizer);
            }
        }
    }
}

