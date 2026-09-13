/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.eclipse.jetty.alpn.server.ALPNServerConnectionFactory
 *  org.eclipse.jetty.http.HttpVersion
 *  org.eclipse.jetty.http2.HTTP2Cipher
 *  org.eclipse.jetty.http2.server.HTTP2ServerConnectionFactory
 *  org.eclipse.jetty.server.ConnectionFactory
 *  org.eclipse.jetty.server.Connector
 *  org.eclipse.jetty.server.HttpConfiguration
 *  org.eclipse.jetty.server.HttpConfiguration$Customizer
 *  org.eclipse.jetty.server.HttpConnectionFactory
 *  org.eclipse.jetty.server.SecureRequestCustomizer
 *  org.eclipse.jetty.server.Server
 *  org.eclipse.jetty.server.ServerConnector
 *  org.eclipse.jetty.server.SslConnectionFactory
 *  org.eclipse.jetty.util.resource.Resource
 *  org.eclipse.jetty.util.ssl.SslContextFactory
 *  org.eclipse.jetty.util.ssl.SslContextFactory$Server
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.ObjectUtils
 *  org.springframework.util.ResourceUtils
 */
package org.springframework.boot.web.embedded.jetty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URL;
import org.eclipse.jetty.alpn.server.ALPNServerConnectionFactory;
import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.http2.HTTP2Cipher;
import org.eclipse.jetty.http2.server.HTTP2ServerConnectionFactory;
import org.eclipse.jetty.server.ConnectionFactory;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.springframework.boot.web.embedded.jetty.JettyServerCustomizer;
import org.springframework.boot.web.server.Http2;
import org.springframework.boot.web.server.Ssl;
import org.springframework.boot.web.server.SslConfigurationValidator;
import org.springframework.boot.web.server.SslStoreProvider;
import org.springframework.boot.web.server.WebServerException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ResourceUtils;

class SslServerCustomizer
implements JettyServerCustomizer {
    private final InetSocketAddress address;
    private final Ssl ssl;
    private final SslStoreProvider sslStoreProvider;
    private final Http2 http2;

    SslServerCustomizer(InetSocketAddress address, Ssl ssl, SslStoreProvider sslStoreProvider, Http2 http2) {
        this.address = address;
        this.ssl = ssl;
        this.sslStoreProvider = sslStoreProvider;
        this.http2 = http2;
    }

    @Override
    public void customize(Server server) {
        SslContextFactory.Server sslContextFactory = new SslContextFactory.Server();
        sslContextFactory.setEndpointIdentificationAlgorithm(null);
        this.configureSsl(sslContextFactory, this.ssl, this.sslStoreProvider);
        ServerConnector connector = this.createConnector(server, sslContextFactory, this.address);
        server.setConnectors(new Connector[]{connector});
    }

    private ServerConnector createConnector(Server server, SslContextFactory.Server sslContextFactory, InetSocketAddress address) {
        HttpConfiguration config = new HttpConfiguration();
        config.setSendServerVersion(false);
        config.setSecureScheme("https");
        config.setSecurePort(address.getPort());
        config.addCustomizer((HttpConfiguration.Customizer)new SecureRequestCustomizer());
        ServerConnector connector = this.createServerConnector(server, sslContextFactory, config);
        connector.setPort(address.getPort());
        connector.setHost(address.getHostString());
        return connector;
    }

    private ServerConnector createServerConnector(Server server, SslContextFactory.Server sslContextFactory, HttpConfiguration config) {
        if (this.http2 == null || !this.http2.isEnabled()) {
            return this.createHttp11ServerConnector(server, config, sslContextFactory);
        }
        Assert.state((boolean)this.isJettyAlpnPresent(), () -> "An 'org.eclipse.jetty:jetty-alpn-*-server' dependency is required for HTTP/2 support.");
        Assert.state((boolean)this.isJettyHttp2Present(), () -> "The 'org.eclipse.jetty.http2:http2-server' dependency is required for HTTP/2 support.");
        return this.createHttp2ServerConnector(server, config, sslContextFactory);
    }

    private ServerConnector createHttp11ServerConnector(Server server, HttpConfiguration config, SslContextFactory.Server sslContextFactory) {
        HttpConnectionFactory connectionFactory = new HttpConnectionFactory(config);
        return new SslValidatingServerConnector(server, (SslContextFactory)sslContextFactory, this.ssl.getKeyAlias(), this.createSslConnectionFactory(sslContextFactory, HttpVersion.HTTP_1_1.asString()), connectionFactory);
    }

    private SslConnectionFactory createSslConnectionFactory(SslContextFactory.Server sslContextFactory, String protocol) {
        try {
            return new SslConnectionFactory((SslContextFactory)sslContextFactory, protocol);
        }
        catch (NoSuchMethodError ex) {
            try {
                return (SslConnectionFactory)SslConnectionFactory.class.getConstructor(SslContextFactory.Server.class, String.class).newInstance(sslContextFactory, protocol);
            }
            catch (Exception ex2) {
                throw new RuntimeException(ex2);
            }
        }
    }

    private boolean isJettyAlpnPresent() {
        return ClassUtils.isPresent((String)"org.eclipse.jetty.alpn.server.ALPNServerConnectionFactory", null);
    }

    private boolean isJettyHttp2Present() {
        return ClassUtils.isPresent((String)"org.eclipse.jetty.http2.server.HTTP2ServerConnectionFactory", null);
    }

    private ServerConnector createHttp2ServerConnector(Server server, HttpConfiguration config, SslContextFactory.Server sslContextFactory) {
        HttpConnectionFactory http = new HttpConnectionFactory(config);
        HTTP2ServerConnectionFactory h2 = new HTTP2ServerConnectionFactory(config);
        ALPNServerConnectionFactory alpn = this.createAlpnServerConnectionFactory();
        sslContextFactory.setCipherComparator(HTTP2Cipher.COMPARATOR);
        if (this.isConscryptPresent()) {
            sslContextFactory.setProvider("Conscrypt");
        }
        SslConnectionFactory ssl = this.createSslConnectionFactory(sslContextFactory, alpn.getProtocol());
        return new SslValidatingServerConnector(server, (SslContextFactory)sslContextFactory, this.ssl.getKeyAlias(), new ConnectionFactory[]{ssl, alpn, h2, http});
    }

    private ALPNServerConnectionFactory createAlpnServerConnectionFactory() {
        try {
            return new ALPNServerConnectionFactory(new String[0]);
        }
        catch (IllegalStateException ex) {
            throw new IllegalStateException("An 'org.eclipse.jetty:jetty-alpn-*-server' dependency is required for HTTP/2 support.", ex);
        }
    }

    private boolean isConscryptPresent() {
        return ClassUtils.isPresent((String)"org.conscrypt.Conscrypt", null) && ClassUtils.isPresent((String)"org.eclipse.jetty.alpn.conscrypt.server.ConscryptServerALPNProcessor", null);
    }

    protected void configureSsl(SslContextFactory.Server factory, Ssl ssl, SslStoreProvider sslStoreProvider) {
        factory.setProtocol(ssl.getProtocol());
        this.configureSslClientAuth(factory, ssl);
        this.configureSslPasswords(factory, ssl);
        factory.setCertAlias(ssl.getKeyAlias());
        if (!ObjectUtils.isEmpty((Object[])ssl.getCiphers())) {
            factory.setIncludeCipherSuites(ssl.getCiphers());
            factory.setExcludeCipherSuites(new String[0]);
        }
        if (ssl.getEnabledProtocols() != null) {
            factory.setIncludeProtocols(ssl.getEnabledProtocols());
        }
        if (sslStoreProvider != null) {
            try {
                String keyPassword = sslStoreProvider.getKeyPassword();
                if (keyPassword != null) {
                    factory.setKeyManagerPassword(keyPassword);
                }
                factory.setKeyStore(sslStoreProvider.getKeyStore());
                factory.setTrustStore(sslStoreProvider.getTrustStore());
            }
            catch (Exception ex) {
                throw new IllegalStateException("Unable to set SSL store", ex);
            }
        } else {
            this.configureSslKeyStore(factory, ssl);
            this.configureSslTrustStore(factory, ssl);
        }
    }

    private void configureSslClientAuth(SslContextFactory.Server factory, Ssl ssl) {
        if (ssl.getClientAuth() == Ssl.ClientAuth.NEED) {
            factory.setNeedClientAuth(true);
            factory.setWantClientAuth(true);
        } else if (ssl.getClientAuth() == Ssl.ClientAuth.WANT) {
            factory.setWantClientAuth(true);
        }
    }

    private void configureSslPasswords(SslContextFactory.Server factory, Ssl ssl) {
        if (ssl.getKeyStorePassword() != null) {
            factory.setKeyStorePassword(ssl.getKeyStorePassword());
        }
        if (ssl.getKeyPassword() != null) {
            factory.setKeyManagerPassword(ssl.getKeyPassword());
        }
    }

    private void configureSslKeyStore(SslContextFactory.Server factory, Ssl ssl) {
        try {
            URL url = ResourceUtils.getURL((String)ssl.getKeyStore());
            factory.setKeyStoreResource(Resource.newResource((URL)url));
        }
        catch (Exception ex) {
            throw new WebServerException("Could not load key store '" + ssl.getKeyStore() + "'", ex);
        }
        if (ssl.getKeyStoreType() != null) {
            factory.setKeyStoreType(ssl.getKeyStoreType());
        }
        if (ssl.getKeyStoreProvider() != null) {
            factory.setKeyStoreProvider(ssl.getKeyStoreProvider());
        }
    }

    private void configureSslTrustStore(SslContextFactory.Server factory, Ssl ssl) {
        if (ssl.getTrustStorePassword() != null) {
            factory.setTrustStorePassword(ssl.getTrustStorePassword());
        }
        if (ssl.getTrustStore() != null) {
            try {
                URL url = ResourceUtils.getURL((String)ssl.getTrustStore());
                factory.setTrustStoreResource(Resource.newResource((URL)url));
            }
            catch (IOException ex) {
                throw new WebServerException("Could not find trust store '" + ssl.getTrustStore() + "'", ex);
            }
        }
        if (ssl.getTrustStoreType() != null) {
            factory.setTrustStoreType(ssl.getTrustStoreType());
        }
        if (ssl.getTrustStoreProvider() != null) {
            factory.setTrustStoreProvider(ssl.getTrustStoreProvider());
        }
    }

    static class SslValidatingServerConnector
    extends ServerConnector {
        private final SslContextFactory sslContextFactory;
        private final String keyAlias;

        SslValidatingServerConnector(Server server, SslContextFactory sslContextFactory, String keyAlias, SslConnectionFactory sslConnectionFactory, HttpConnectionFactory connectionFactory) {
            super(server, new ConnectionFactory[]{sslConnectionFactory, connectionFactory});
            this.sslContextFactory = sslContextFactory;
            this.keyAlias = keyAlias;
        }

        SslValidatingServerConnector(Server server, SslContextFactory sslContextFactory, String keyAlias, ConnectionFactory ... factories) {
            super(server, factories);
            this.sslContextFactory = sslContextFactory;
            this.keyAlias = keyAlias;
        }

        protected void doStart() throws Exception {
            super.doStart();
            SslConfigurationValidator.validateKeyAlias(this.sslContextFactory.getKeyStore(), this.keyAlias);
        }
    }
}

