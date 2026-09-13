/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.web.server;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import org.springframework.boot.web.server.CertificateParser;
import org.springframework.boot.web.server.PrivateKeyParser;
import org.springframework.boot.web.server.Ssl;
import org.springframework.boot.web.server.SslStoreProvider;

public final class CertificateFileSslStoreProvider
implements SslStoreProvider {
    private static final String KEY_PASSWORD = "";
    private static final String DEFAULT_KEY_ALIAS = "spring-boot-web";
    private final Ssl ssl;

    private CertificateFileSslStoreProvider(Ssl ssl) {
        this.ssl = ssl;
    }

    @Override
    public KeyStore getKeyStore() throws Exception {
        return this.createKeyStore(this.ssl.getCertificate(), this.ssl.getCertificatePrivateKey(), this.ssl.getKeyStoreType(), this.ssl.getKeyAlias());
    }

    @Override
    public KeyStore getTrustStore() throws Exception {
        if (this.ssl.getTrustCertificate() == null) {
            return null;
        }
        return this.createKeyStore(this.ssl.getTrustCertificate(), this.ssl.getTrustCertificatePrivateKey(), this.ssl.getTrustStoreType(), this.ssl.getKeyAlias());
    }

    @Override
    public String getKeyPassword() {
        return KEY_PASSWORD;
    }

    private KeyStore createKeyStore(String certPath, String keyPath, String storeType, String keyAlias) {
        try {
            KeyStore keyStore = KeyStore.getInstance(storeType != null ? storeType : KeyStore.getDefaultType());
            keyStore.load(null);
            X509Certificate[] certificates = CertificateParser.parse(certPath);
            PrivateKey privateKey = keyPath != null ? PrivateKeyParser.parse(keyPath) : null;
            try {
                this.addCertificates(keyStore, certificates, privateKey, keyAlias);
            }
            catch (KeyStoreException ex) {
                throw new IllegalStateException("Error adding certificates to KeyStore: " + ex.getMessage(), ex);
            }
            return keyStore;
        }
        catch (IOException | GeneralSecurityException ex) {
            throw new IllegalStateException("Error creating KeyStore: " + ex.getMessage(), ex);
        }
    }

    private void addCertificates(KeyStore keyStore, X509Certificate[] certificates, PrivateKey privateKey, String keyAlias) throws KeyStoreException {
        String alias;
        String string = alias = keyAlias != null ? keyAlias : DEFAULT_KEY_ALIAS;
        if (privateKey != null) {
            keyStore.setKeyEntry(alias, privateKey, KEY_PASSWORD.toCharArray(), certificates);
        } else {
            for (int index = 0; index < certificates.length; ++index) {
                keyStore.setCertificateEntry(alias + "-" + index, certificates[index]);
            }
        }
    }

    public static SslStoreProvider from(Ssl ssl) {
        if (ssl != null && ssl.isEnabled() && ssl.getCertificate() != null && ssl.getCertificatePrivateKey() != null) {
            return new CertificateFileSslStoreProvider(ssl);
        }
        return null;
    }
}

