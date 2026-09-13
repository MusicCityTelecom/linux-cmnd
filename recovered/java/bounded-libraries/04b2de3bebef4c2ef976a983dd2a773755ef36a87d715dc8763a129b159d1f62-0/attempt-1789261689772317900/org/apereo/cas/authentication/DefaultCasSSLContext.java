/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.http.ssl.SSLContexts
 *  org.apereo.cas.configuration.model.core.authentication.HttpClientProperties
 *  org.apereo.cas.util.CollectionUtils
 *  org.apereo.cas.util.ssl.CompositeX509KeyManager
 *  org.apereo.cas.util.ssl.CompositeX509TrustManager
 *  org.jooq.lambda.Unchecked
 *  org.springframework.core.io.Resource
 */
package org.apereo.cas.authentication;

import java.io.InputStream;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;
import lombok.Generated;
import org.apache.http.ssl.SSLContexts;
import org.apereo.cas.authentication.CasSSLContext;
import org.apereo.cas.configuration.model.core.authentication.HttpClientProperties;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.util.ssl.CompositeX509KeyManager;
import org.apereo.cas.util.ssl.CompositeX509TrustManager;
import org.jooq.lambda.Unchecked;
import org.springframework.core.io.Resource;

public class DefaultCasSSLContext
implements CasSSLContext {
    private static final String ALG_NAME_PKIX = "PKIX";
    private final SSLContext sslContext;
    private final TrustManager[] trustManagers;
    private final KeyManager[] keyManagers;
    private final HostnameVerifier hostnameVerifier;
    private final KeyStore casTrustStore;
    private final KeyManagerFactory keyManagerFactory;

    public DefaultCasSSLContext(Resource trustStoreFile, String trustStorePassword, String trustStoreType, HttpClientProperties httpClientProperties, HostnameVerifier hostnameVerifier) throws Exception {
        boolean disabled = httpClientProperties.getHostNameVerifier().equalsIgnoreCase("none");
        if (disabled) {
            this.trustManagers = CasSSLContext.disabled().getTrustManagers();
            this.keyManagerFactory = CasSSLContext.disabled().getKeyManagerFactory();
            this.casTrustStore = null;
            this.keyManagers = CasSSLContext.disabled().getKeyManagers();
        } else {
            this.casTrustStore = KeyStore.getInstance(trustStoreType);
            char[] trustStorePasswordCharArray = trustStorePassword.toCharArray();
            try (InputStream casStream = trustStoreFile.getInputStream();){
                this.casTrustStore.load(casStream, trustStorePasswordCharArray);
            }
            this.keyManagerFactory = DefaultCasSSLContext.getKeyManagerFactory(ALG_NAME_PKIX, this.casTrustStore, trustStorePasswordCharArray);
            X509KeyManager customKeyManager = (X509KeyManager)this.keyManagerFactory.getKeyManagers()[0];
            String defaultAlgorithm = KeyManagerFactory.getDefaultAlgorithm();
            KeyManagerFactory jvmKeyManagerFactory = DefaultCasSSLContext.getKeyManagerFactory(defaultAlgorithm, null, null);
            X509KeyManager jvmKeyManager = (X509KeyManager)jvmKeyManagerFactory.getKeyManagers()[0];
            String defaultTrustAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            Collection<X509TrustManager> customTrustManager = DefaultCasSSLContext.getTrustManager(ALG_NAME_PKIX, this.casTrustStore);
            Collection<X509TrustManager> jvmTrustManagers = DefaultCasSSLContext.getTrustManager(defaultTrustAlgorithm, null);
            ArrayList<X509TrustManager> allManagers = new ArrayList<X509TrustManager>(customTrustManager);
            allManagers.addAll(jvmTrustManagers);
            this.trustManagers = new TrustManager[]{new CompositeX509TrustManager(allManagers)};
            this.keyManagers = new KeyManager[]{new CompositeX509KeyManager(CollectionUtils.wrapList((Object[])new X509KeyManager[]{jvmKeyManager, customKeyManager}))};
        }
        this.sslContext = SSLContexts.custom().setProtocol("SSL").build();
        this.sslContext.init(this.keyManagers, this.trustManagers, null);
        this.hostnameVerifier = hostnameVerifier;
    }

    private static KeyManagerFactory getKeyManagerFactory(String algorithm, KeyStore keystore, char[] password) throws Exception {
        KeyManagerFactory factory = KeyManagerFactory.getInstance(algorithm);
        factory.init(keystore, password);
        return factory;
    }

    private static Collection<X509TrustManager> getTrustManager(String algorithm, KeyStore keystore) throws Exception {
        TrustManagerFactory factory = TrustManagerFactory.getInstance(algorithm);
        factory.init(keystore);
        return Arrays.stream(factory.getTrustManagers()).filter(e -> e instanceof X509TrustManager).map(X509TrustManager.class::cast).collect(Collectors.toList());
    }

    @Override
    public TrustManagerFactory getTrustManagerFactory() {
        return (TrustManagerFactory)Unchecked.supplier(() -> {
            TrustManagerFactory factory = TrustManagerFactory.getInstance(ALG_NAME_PKIX);
            factory.init(this.casTrustStore);
            return factory;
        }).get();
    }

    @Override
    @Generated
    public SSLContext getSslContext() {
        return this.sslContext;
    }

    @Override
    @Generated
    public TrustManager[] getTrustManagers() {
        return this.trustManagers;
    }

    @Override
    @Generated
    public KeyManager[] getKeyManagers() {
        return this.keyManagers;
    }

    @Override
    @Generated
    public HostnameVerifier getHostnameVerifier() {
        return this.hostnameVerifier;
    }

    @Generated
    public KeyStore getCasTrustStore() {
        return this.casTrustStore;
    }

    @Override
    @Generated
    public KeyManagerFactory getKeyManagerFactory() {
        return this.keyManagerFactory;
    }
}

