/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.http.conn.ssl.DefaultHostnameVerifier
 *  org.apache.http.conn.ssl.NoopHostnameVerifier
 *  org.apache.http.ssl.SSLContexts
 *  org.jooq.lambda.Unchecked
 */
package org.apereo.cas.authentication;

import java.security.KeyStore;
import java.security.Provider;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.ManagerFactoryParameters;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.TrustManagerFactorySpi;
import javax.net.ssl.X509TrustManager;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.ssl.SSLContexts;
import org.jooq.lambda.Unchecked;

public interface CasSSLContext {
    public static final String BEAN_NAME = "casSslContext";

    public static CasSSLContext system() {
        return new CasSSLContext(){

            @Override
            public KeyManagerFactory getKeyManagerFactory() {
                return (KeyManagerFactory)Unchecked.supplier(() -> {
                    KeyManagerFactory factory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
                    factory.init(null, null);
                    return factory;
                }).get();
            }

            @Override
            public SSLContext getSslContext() {
                return SSLContexts.createSystemDefault();
            }

            @Override
            public TrustManagerFactory getTrustManagerFactory() {
                return (TrustManagerFactory)Unchecked.supplier(() -> {
                    TrustManagerFactory factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                    factory.init((KeyStore)null);
                    return factory;
                }).get();
            }

            @Override
            public TrustManager[] getTrustManagers() {
                return this.getTrustManagerFactory().getTrustManagers();
            }

            @Override
            public HostnameVerifier getHostnameVerifier() {
                return new DefaultHostnameVerifier();
            }

            @Override
            public KeyManager[] getKeyManagers() {
                return (KeyManager[])Unchecked.supplier(() -> {
                    KeyManagerFactory factory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
                    factory.init(null, null);
                    return factory.getKeyManagers();
                }).get();
            }
        };
    }

    public static CasSSLContext disabled() {
        return new DisabledCasSslContext();
    }

    public SSLContext getSslContext();

    public TrustManager[] getTrustManagers();

    public KeyManager[] getKeyManagers();

    public HostnameVerifier getHostnameVerifier();

    public TrustManagerFactory getTrustManagerFactory();

    public KeyManagerFactory getKeyManagerFactory();

    public static class DisabledCasSslContext
    implements CasSSLContext {
        private static final X509Certificate[] ACCEPTED_ISSUERS = new X509Certificate[0];

        private static X509TrustManager getDisabledTrustedManager() {
            return new X509TrustManager(){

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return ACCEPTED_ISSUERS;
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            };
        }

        @Override
        public SSLContext getSslContext() {
            return (SSLContext)Unchecked.supplier(() -> {
                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(this.getKeyManagers(), this.getTrustManagers(), null);
                return sc;
            }).get();
        }

        @Override
        public KeyManagerFactory getKeyManagerFactory() {
            return (KeyManagerFactory)Unchecked.supplier(() -> {
                KeyManagerFactory factory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
                factory.init(null, null);
                return factory;
            }).get();
        }

        @Override
        public TrustManagerFactory getTrustManagerFactory() {
            Provider provider = new Provider("", "0.0", ""){
                private static final long serialVersionUID = -2680540247105807895L;
            };
            TrustManagerFactorySpi spi = new TrustManagerFactorySpi(){

                @Override
                protected void engineInit(KeyStore keyStore) {
                }

                @Override
                protected void engineInit(ManagerFactoryParameters managerFactoryParameters) {
                }

                @Override
                protected TrustManager[] engineGetTrustManagers() {
                    return this.getTrustManagers();
                }
            };
            return new TrustManagerFactory(spi, provider, ""){};
        }

        @Override
        public TrustManager[] getTrustManagers() {
            return new TrustManager[]{DisabledCasSslContext.getDisabledTrustedManager()};
        }

        @Override
        public KeyManager[] getKeyManagers() {
            return new KeyManager[0];
        }

        @Override
        public HostnameVerifier getHostnameVerifier() {
            return NoopHostnameVerifier.INSTANCE;
        }
    }
}

