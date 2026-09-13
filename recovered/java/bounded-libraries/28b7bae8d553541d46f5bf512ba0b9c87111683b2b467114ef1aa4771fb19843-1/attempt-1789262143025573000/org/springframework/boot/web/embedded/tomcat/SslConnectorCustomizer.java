/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.catalina.connector.Connector
 *  org.apache.coyote.ProtocolHandler
 *  org.apache.coyote.http11.AbstractHttp11JsseProtocol
 *  org.apache.coyote.http11.Http11NioProtocol
 *  org.apache.tomcat.util.net.SSLHostConfig
 *  org.apache.tomcat.util.net.SSLHostConfigCertificate
 *  org.apache.tomcat.util.net.SSLHostConfigCertificate$Type
 *  org.springframework.util.Assert
 *  org.springframework.util.ResourceUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.web.embedded.tomcat;

import java.io.FileNotFoundException;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.ProtocolHandler;
import org.apache.coyote.http11.AbstractHttp11JsseProtocol;
import org.apache.coyote.http11.Http11NioProtocol;
import org.apache.tomcat.util.net.SSLHostConfig;
import org.apache.tomcat.util.net.SSLHostConfigCertificate;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.server.Ssl;
import org.springframework.boot.web.server.SslStoreProvider;
import org.springframework.boot.web.server.WebServerException;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

class SslConnectorCustomizer
implements TomcatConnectorCustomizer {
    private final Ssl ssl;
    private final SslStoreProvider sslStoreProvider;

    SslConnectorCustomizer(Ssl ssl, SslStoreProvider sslStoreProvider) {
        Assert.notNull((Object)ssl, (String)"Ssl configuration should not be null");
        this.ssl = ssl;
        this.sslStoreProvider = sslStoreProvider;
    }

    @Override
    public void customize(Connector connector) {
        ProtocolHandler handler = connector.getProtocolHandler();
        Assert.state((boolean)(handler instanceof AbstractHttp11JsseProtocol), (String)"To use SSL, the connector's protocol handler must be an AbstractHttp11JsseProtocol subclass");
        this.configureSsl((AbstractHttp11JsseProtocol)handler, this.ssl, this.sslStoreProvider);
        connector.setScheme("https");
        connector.setSecure(true);
    }

    protected void configureSsl(AbstractHttp11JsseProtocol<?> protocol, Ssl ssl, SslStoreProvider sslStoreProvider) {
        protocol.setSSLEnabled(true);
        SSLHostConfig sslHostConfig = new SSLHostConfig();
        sslHostConfig.setHostName(protocol.getDefaultSSLHostConfigName());
        sslHostConfig.setSslProtocol(ssl.getProtocol());
        protocol.addSslHostConfig(sslHostConfig);
        this.configureSslClientAuth(sslHostConfig, ssl);
        SSLHostConfigCertificate certificate = new SSLHostConfigCertificate(sslHostConfig, SSLHostConfigCertificate.Type.UNDEFINED);
        if (ssl.getKeyStorePassword() != null) {
            certificate.setCertificateKeystorePassword(ssl.getKeyStorePassword());
        }
        if (ssl.getKeyPassword() != null) {
            certificate.setCertificateKeyPassword(ssl.getKeyPassword());
        }
        if (ssl.getKeyAlias() != null) {
            certificate.setCertificateKeyAlias(ssl.getKeyAlias());
        }
        sslHostConfig.addCertificate(certificate);
        String ciphers = StringUtils.arrayToCommaDelimitedString((Object[])ssl.getCiphers());
        if (StringUtils.hasText((String)ciphers)) {
            sslHostConfig.setCiphers(ciphers);
        }
        this.configureEnabledProtocols(protocol, ssl);
        if (sslStoreProvider != null) {
            this.configureSslStoreProvider(protocol, sslHostConfig, certificate, sslStoreProvider);
            String keyPassword = sslStoreProvider.getKeyPassword();
            if (keyPassword != null) {
                certificate.setCertificateKeyPassword(keyPassword);
            }
        } else {
            this.configureSslKeyStore(certificate, ssl);
            this.configureSslTrustStore(sslHostConfig, ssl);
        }
    }

    private void configureEnabledProtocols(AbstractHttp11JsseProtocol<?> protocol, Ssl ssl) {
        if (ssl.getEnabledProtocols() != null) {
            for (SSLHostConfig sslHostConfig : protocol.findSslHostConfigs()) {
                sslHostConfig.setProtocols(StringUtils.arrayToCommaDelimitedString((Object[])ssl.getEnabledProtocols()));
            }
        }
    }

    private void configureSslClientAuth(SSLHostConfig config, Ssl ssl) {
        if (ssl.getClientAuth() == Ssl.ClientAuth.NEED) {
            config.setCertificateVerification("required");
        } else if (ssl.getClientAuth() == Ssl.ClientAuth.WANT) {
            config.setCertificateVerification("optional");
        }
    }

    protected void configureSslStoreProvider(AbstractHttp11JsseProtocol<?> protocol, SSLHostConfig sslHostConfig, SSLHostConfigCertificate certificate, SslStoreProvider sslStoreProvider) {
        Assert.isInstanceOf(Http11NioProtocol.class, protocol, (String)"SslStoreProvider can only be used with Http11NioProtocol");
        try {
            if (sslStoreProvider.getKeyStore() != null) {
                certificate.setCertificateKeystore(sslStoreProvider.getKeyStore());
            }
            if (sslStoreProvider.getTrustStore() != null) {
                sslHostConfig.setTrustStore(sslStoreProvider.getTrustStore());
            }
        }
        catch (Exception ex) {
            throw new WebServerException("Could not load store: " + ex.getMessage(), ex);
        }
    }

    private void configureSslKeyStore(SSLHostConfigCertificate certificate, Ssl ssl) {
        try {
            certificate.setCertificateKeystoreFile(ResourceUtils.getURL((String)ssl.getKeyStore()).toString());
        }
        catch (Exception ex) {
            throw new WebServerException("Could not load key store '" + ssl.getKeyStore() + "'", ex);
        }
        if (ssl.getKeyStoreType() != null) {
            certificate.setCertificateKeystoreType(ssl.getKeyStoreType());
        }
        if (ssl.getKeyStoreProvider() != null) {
            certificate.setCertificateKeystoreProvider(ssl.getKeyStoreProvider());
        }
    }

    private void configureSslTrustStore(SSLHostConfig sslHostConfig, Ssl ssl) {
        if (ssl.getTrustStore() != null) {
            try {
                sslHostConfig.setTruststoreFile(ResourceUtils.getURL((String)ssl.getTrustStore()).toString());
            }
            catch (FileNotFoundException ex) {
                throw new WebServerException("Could not load trust store: " + ex.getMessage(), ex);
            }
        }
        if (ssl.getTrustStorePassword() != null) {
            sslHostConfig.setTruststorePassword(ssl.getTrustStorePassword());
        }
        if (ssl.getTrustStoreType() != null) {
            sslHostConfig.setTruststoreType(ssl.getTrustStoreType());
        }
        if (ssl.getTrustStoreProvider() != null) {
            sslHostConfig.setTruststoreProvider(ssl.getTrustStoreProvider());
        }
    }
}

