/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.AccessController;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.Logger;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import org.glassfish.jersey.internal.LocalizationMessages;
import org.glassfish.jersey.internal.util.PropertiesHelper;

public final class SslConfigurator {
    public static final String TRUST_STORE_PROVIDER = "javax.net.ssl.trustStoreProvider";
    public static final String KEY_STORE_PROVIDER = "javax.net.ssl.keyStoreProvider";
    public static final String TRUST_STORE_FILE = "javax.net.ssl.trustStore";
    public static final String KEY_STORE_FILE = "javax.net.ssl.keyStore";
    public static final String TRUST_STORE_PASSWORD = "javax.net.ssl.trustStorePassword";
    public static final String KEY_STORE_PASSWORD = "javax.net.ssl.keyStorePassword";
    public static final String TRUST_STORE_TYPE = "javax.net.ssl.trustStoreType";
    public static final String KEY_STORE_TYPE = "javax.net.ssl.keyStoreType";
    public static final String KEY_MANAGER_FACTORY_ALGORITHM = "ssl.keyManagerFactory.algorithm";
    public static final String KEY_MANAGER_FACTORY_PROVIDER = "ssl.keyManagerFactory.provider";
    public static final String TRUST_MANAGER_FACTORY_ALGORITHM = "ssl.trustManagerFactory.algorithm";
    public static final String TRUST_MANAGER_FACTORY_PROVIDER = "ssl.trustManagerFactory.provider";
    private static final SslConfigurator DEFAULT_CONFIG_NO_PROPS = new SslConfigurator(false);
    private static final Logger LOGGER = Logger.getLogger(SslConfigurator.class.getName());
    private KeyStore keyStore;
    private KeyStore trustStore;
    private String trustStoreProvider;
    private String keyStoreProvider;
    private String trustStoreType;
    private String keyStoreType;
    private char[] trustStorePass;
    private char[] keyStorePass;
    private char[] keyPass;
    private String trustStoreFile;
    private String keyStoreFile;
    private byte[] trustStoreBytes;
    private byte[] keyStoreBytes;
    private String trustManagerFactoryAlgorithm;
    private String keyManagerFactoryAlgorithm;
    private String trustManagerFactoryProvider;
    private String keyManagerFactoryProvider;
    private String securityProtocol = "TLS";

    public static SSLContext getDefaultContext() {
        return SslConfigurator.getDefaultContext(true);
    }

    public static SSLContext getDefaultContext(boolean readSystemProperties) {
        if (readSystemProperties) {
            return new SslConfigurator(true).createSSLContext();
        }
        return DEFAULT_CONFIG_NO_PROPS.createSSLContext();
    }

    public static SslConfigurator newInstance() {
        return new SslConfigurator(false);
    }

    public static SslConfigurator newInstance(boolean readSystemProperties) {
        return new SslConfigurator(readSystemProperties);
    }

    private SslConfigurator(boolean readSystemProperties) {
        if (readSystemProperties) {
            this.retrieve();
        }
    }

    private SslConfigurator(SslConfigurator that) {
        this.keyStore = that.keyStore;
        this.trustStore = that.trustStore;
        this.trustStoreProvider = that.trustStoreProvider;
        this.keyStoreProvider = that.keyStoreProvider;
        this.trustStoreType = that.trustStoreType;
        this.keyStoreType = that.keyStoreType;
        this.trustStorePass = that.trustStorePass;
        this.keyStorePass = that.keyStorePass;
        this.keyPass = that.keyPass;
        this.trustStoreFile = that.trustStoreFile;
        this.keyStoreFile = that.keyStoreFile;
        this.trustStoreBytes = that.trustStoreBytes;
        this.keyStoreBytes = that.keyStoreBytes;
        this.trustManagerFactoryAlgorithm = that.trustManagerFactoryAlgorithm;
        this.keyManagerFactoryAlgorithm = that.keyManagerFactoryAlgorithm;
        this.trustManagerFactoryProvider = that.trustManagerFactoryProvider;
        this.keyManagerFactoryProvider = that.keyManagerFactoryProvider;
        this.securityProtocol = that.securityProtocol;
    }

    public SslConfigurator copy() {
        return new SslConfigurator(this);
    }

    public SslConfigurator trustStoreProvider(String trustStoreProvider) {
        this.trustStoreProvider = trustStoreProvider;
        return this;
    }

    public SslConfigurator keyStoreProvider(String keyStoreProvider) {
        this.keyStoreProvider = keyStoreProvider;
        return this;
    }

    public SslConfigurator trustStoreType(String trustStoreType) {
        this.trustStoreType = trustStoreType;
        return this;
    }

    public SslConfigurator keyStoreType(String keyStoreType) {
        this.keyStoreType = keyStoreType;
        return this;
    }

    public SslConfigurator trustStorePassword(String password) {
        this.trustStorePass = password.toCharArray();
        return this;
    }

    public SslConfigurator keyStorePassword(String password) {
        this.keyStorePass = password.toCharArray();
        return this;
    }

    public SslConfigurator keyStorePassword(char[] password) {
        this.keyStorePass = (char[])password.clone();
        return this;
    }

    public SslConfigurator keyPassword(String password) {
        this.keyPass = password.toCharArray();
        return this;
    }

    public SslConfigurator keyPassword(char[] password) {
        this.keyPass = (char[])password.clone();
        return this;
    }

    public SslConfigurator trustStoreFile(String fileName) {
        this.trustStoreFile = fileName;
        this.trustStoreBytes = null;
        this.trustStore = null;
        return this;
    }

    public SslConfigurator trustStoreBytes(byte[] payload) {
        this.trustStoreBytes = (byte[])payload.clone();
        this.trustStoreFile = null;
        this.trustStore = null;
        return this;
    }

    public SslConfigurator keyStoreFile(String fileName) {
        this.keyStoreFile = fileName;
        this.keyStoreBytes = null;
        this.keyStore = null;
        return this;
    }

    public SslConfigurator keyStoreBytes(byte[] payload) {
        this.keyStoreBytes = (byte[])payload.clone();
        this.keyStoreFile = null;
        this.keyStore = null;
        return this;
    }

    public SslConfigurator trustManagerFactoryAlgorithm(String algorithm) {
        this.trustManagerFactoryAlgorithm = algorithm;
        return this;
    }

    public SslConfigurator keyManagerFactoryAlgorithm(String algorithm) {
        this.keyManagerFactoryAlgorithm = algorithm;
        return this;
    }

    public SslConfigurator trustManagerFactoryProvider(String provider) {
        this.trustManagerFactoryProvider = provider;
        return this;
    }

    public SslConfigurator keyManagerFactoryProvider(String provider) {
        this.keyManagerFactoryProvider = provider;
        return this;
    }

    public SslConfigurator securityProtocol(String protocol) {
        this.securityProtocol = protocol;
        return this;
    }

    KeyStore getKeyStore() {
        return this.keyStore;
    }

    public SslConfigurator keyStore(KeyStore keyStore) {
        this.keyStore = keyStore;
        this.keyStoreFile = null;
        this.keyStoreBytes = null;
        return this;
    }

    KeyStore getTrustStore() {
        return this.trustStore;
    }

    public SslConfigurator trustStore(KeyStore trustStore) {
        this.trustStore = trustStore;
        this.trustStoreFile = null;
        this.trustStoreBytes = null;
        return this;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public SSLContext createSSLContext() {
        KeyStore _trustStore;
        TrustManagerFactory trustManagerFactory = null;
        KeyManagerFactory keyManagerFactory = null;
        KeyStore _keyStore = this.keyStore;
        if (_keyStore == null && (this.keyStoreBytes != null || this.keyStoreFile != null)) {
            try {
                _keyStore = this.keyStoreProvider != null ? KeyStore.getInstance(this.keyStoreType != null ? this.keyStoreType : KeyStore.getDefaultType(), this.keyStoreProvider) : KeyStore.getInstance(this.keyStoreType != null ? this.keyStoreType : KeyStore.getDefaultType());
                InputStream keyStoreInputStream = null;
                try {
                    if (this.keyStoreBytes != null) {
                        keyStoreInputStream = new ByteArrayInputStream(this.keyStoreBytes);
                    } else if (!this.keyStoreFile.equals("NONE")) {
                        keyStoreInputStream = new FileInputStream(this.keyStoreFile);
                    }
                    _keyStore.load(keyStoreInputStream, this.keyStorePass);
                }
                finally {
                    try {
                        if (keyStoreInputStream != null) {
                            keyStoreInputStream.close();
                        }
                    }
                    catch (IOException iOException) {}
                }
            }
            catch (KeyStoreException e) {
                throw new IllegalStateException(LocalizationMessages.SSL_KS_IMPL_NOT_FOUND(), e);
            }
            catch (CertificateException e) {
                throw new IllegalStateException(LocalizationMessages.SSL_KS_CERT_LOAD_ERROR(), e);
            }
            catch (FileNotFoundException e) {
                throw new IllegalStateException(LocalizationMessages.SSL_KS_FILE_NOT_FOUND(this.keyStoreFile), e);
            }
            catch (IOException e) {
                throw new IllegalStateException(LocalizationMessages.SSL_KS_LOAD_ERROR(this.keyStoreFile), e);
            }
            catch (NoSuchProviderException e) {
                throw new IllegalStateException(LocalizationMessages.SSL_KS_PROVIDERS_NOT_REGISTERED(), e);
            }
            catch (NoSuchAlgorithmException e) {
                throw new IllegalStateException(LocalizationMessages.SSL_KS_INTEGRITY_ALGORITHM_NOT_FOUND(), e);
            }
        }
        if (_keyStore != null) {
            String kmfAlgorithm = this.keyManagerFactoryAlgorithm;
            if (kmfAlgorithm == null) {
                kmfAlgorithm = AccessController.doPrivileged(PropertiesHelper.getSystemProperty(KEY_MANAGER_FACTORY_ALGORITHM, KeyManagerFactory.getDefaultAlgorithm()));
            }
            try {
                char[] password;
                keyManagerFactory = this.keyManagerFactoryProvider != null ? KeyManagerFactory.getInstance(kmfAlgorithm, this.keyManagerFactoryProvider) : KeyManagerFactory.getInstance(kmfAlgorithm);
                char[] cArray = password = this.keyPass != null ? this.keyPass : this.keyStorePass;
                if (password != null) {
                    keyManagerFactory.init(_keyStore, password);
                } else {
                    String ksName = this.keyStoreProvider != null ? LocalizationMessages.SSL_KMF_NO_PASSWORD_FOR_PROVIDER_BASED_KS() : (this.keyStoreBytes != null ? LocalizationMessages.SSL_KMF_NO_PASSWORD_FOR_BYTE_BASED_KS() : this.keyStoreFile);
                    LOGGER.config(LocalizationMessages.SSL_KMF_NO_PASSWORD_SET(ksName));
                    keyManagerFactory = null;
                }
            }
            catch (KeyStoreException e) {
                throw new IllegalStateException(LocalizationMessages.SSL_KMF_INIT_FAILED(), e);
            }
            catch (UnrecoverableKeyException e) {
                throw new IllegalStateException(LocalizationMessages.SSL_KMF_UNRECOVERABLE_KEY(), e);
            }
            catch (NoSuchAlgorithmException e) {
                throw new IllegalStateException(LocalizationMessages.SSL_KMF_ALGORITHM_NOT_SUPPORTED(), e);
            }
            catch (NoSuchProviderException e) {
                throw new IllegalStateException(LocalizationMessages.SSL_KMF_PROVIDER_NOT_REGISTERED(), e);
            }
        }
        if ((_trustStore = this.trustStore) == null && (this.trustStoreBytes != null || this.trustStoreFile != null)) {
            try {
                _trustStore = this.trustStoreProvider != null ? KeyStore.getInstance(this.trustStoreType != null ? this.trustStoreType : KeyStore.getDefaultType(), this.trustStoreProvider) : KeyStore.getInstance(this.trustStoreType != null ? this.trustStoreType : KeyStore.getDefaultType());
                InputStream trustStoreInputStream = null;
                try {
                    if (this.trustStoreBytes != null) {
                        trustStoreInputStream = new ByteArrayInputStream(this.trustStoreBytes);
                    } else if (!this.trustStoreFile.equals("NONE")) {
                        trustStoreInputStream = new FileInputStream(this.trustStoreFile);
                    }
                    _trustStore.load(trustStoreInputStream, this.trustStorePass);
                }
                finally {
                    try {
                        if (trustStoreInputStream != null) {
                            trustStoreInputStream.close();
                        }
                    }
                    catch (IOException ksName) {}
                }
            }
            catch (KeyStoreException e) {
                throw new IllegalStateException(LocalizationMessages.SSL_TS_IMPL_NOT_FOUND(), e);
            }
            catch (CertificateException e) {
                throw new IllegalStateException(LocalizationMessages.SSL_TS_CERT_LOAD_ERROR(), e);
            }
            catch (FileNotFoundException e) {
                throw new IllegalStateException(LocalizationMessages.SSL_TS_FILE_NOT_FOUND(this.trustStoreFile), e);
            }
            catch (IOException e) {
                throw new IllegalStateException(LocalizationMessages.SSL_TS_LOAD_ERROR(this.trustStoreFile), e);
            }
            catch (NoSuchProviderException e) {
                throw new IllegalStateException(LocalizationMessages.SSL_TS_PROVIDERS_NOT_REGISTERED(), e);
            }
            catch (NoSuchAlgorithmException e) {
                throw new IllegalStateException(LocalizationMessages.SSL_TS_INTEGRITY_ALGORITHM_NOT_FOUND(), e);
            }
        }
        if (_trustStore != null) {
            String tmfAlgorithm = this.trustManagerFactoryAlgorithm;
            if (tmfAlgorithm == null) {
                tmfAlgorithm = AccessController.doPrivileged(PropertiesHelper.getSystemProperty(TRUST_MANAGER_FACTORY_ALGORITHM, TrustManagerFactory.getDefaultAlgorithm()));
            }
            try {
                trustManagerFactory = this.trustManagerFactoryProvider != null ? TrustManagerFactory.getInstance(tmfAlgorithm, this.trustManagerFactoryProvider) : TrustManagerFactory.getInstance(tmfAlgorithm);
                trustManagerFactory.init(_trustStore);
            }
            catch (KeyStoreException e) {
                throw new IllegalStateException(LocalizationMessages.SSL_TMF_INIT_FAILED(), e);
            }
            catch (NoSuchAlgorithmException e) {
                throw new IllegalStateException(LocalizationMessages.SSL_TMF_ALGORITHM_NOT_SUPPORTED(), e);
            }
            catch (NoSuchProviderException e) {
                throw new IllegalStateException(LocalizationMessages.SSL_TMF_PROVIDER_NOT_REGISTERED(), e);
            }
        }
        try {
            String secProtocol = "TLS";
            if (this.securityProtocol != null) {
                secProtocol = this.securityProtocol;
            }
            SSLContext sslContext = SSLContext.getInstance(secProtocol);
            sslContext.init(keyManagerFactory != null ? keyManagerFactory.getKeyManagers() : null, trustManagerFactory != null ? trustManagerFactory.getTrustManagers() : null, null);
            return sslContext;
        }
        catch (KeyManagementException e) {
            throw new IllegalStateException(LocalizationMessages.SSL_CTX_INIT_FAILED(), e);
        }
        catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(LocalizationMessages.SSL_CTX_ALGORITHM_NOT_SUPPORTED(), e);
        }
    }

    public SslConfigurator retrieve(Properties props) {
        this.trustStoreProvider = props.getProperty(TRUST_STORE_PROVIDER);
        this.keyStoreProvider = props.getProperty(KEY_STORE_PROVIDER);
        this.trustManagerFactoryProvider = props.getProperty(TRUST_MANAGER_FACTORY_PROVIDER);
        this.keyManagerFactoryProvider = props.getProperty(KEY_MANAGER_FACTORY_PROVIDER);
        this.trustStoreType = props.getProperty(TRUST_STORE_TYPE);
        this.keyStoreType = props.getProperty(KEY_STORE_TYPE);
        this.trustStorePass = (char[])(props.getProperty(TRUST_STORE_PASSWORD) != null ? props.getProperty(TRUST_STORE_PASSWORD).toCharArray() : null);
        this.keyStorePass = (char[])(props.getProperty(KEY_STORE_PASSWORD) != null ? props.getProperty(KEY_STORE_PASSWORD).toCharArray() : null);
        this.trustStoreFile = props.getProperty(TRUST_STORE_FILE);
        this.keyStoreFile = props.getProperty(KEY_STORE_FILE);
        this.trustStoreBytes = null;
        this.keyStoreBytes = null;
        this.trustStore = null;
        this.keyStore = null;
        this.securityProtocol = "TLS";
        return this;
    }

    public SslConfigurator retrieve() {
        this.trustStoreProvider = AccessController.doPrivileged(PropertiesHelper.getSystemProperty(TRUST_STORE_PROVIDER));
        this.keyStoreProvider = AccessController.doPrivileged(PropertiesHelper.getSystemProperty(KEY_STORE_PROVIDER));
        this.trustManagerFactoryProvider = AccessController.doPrivileged(PropertiesHelper.getSystemProperty(TRUST_MANAGER_FACTORY_PROVIDER));
        this.keyManagerFactoryProvider = AccessController.doPrivileged(PropertiesHelper.getSystemProperty(KEY_MANAGER_FACTORY_PROVIDER));
        this.trustStoreType = AccessController.doPrivileged(PropertiesHelper.getSystemProperty(TRUST_STORE_TYPE));
        this.keyStoreType = AccessController.doPrivileged(PropertiesHelper.getSystemProperty(KEY_STORE_TYPE));
        String trustStorePassword = AccessController.doPrivileged(PropertiesHelper.getSystemProperty(TRUST_STORE_PASSWORD));
        this.trustStorePass = (char[])(trustStorePassword != null ? trustStorePassword.toCharArray() : null);
        String keyStorePassword = AccessController.doPrivileged(PropertiesHelper.getSystemProperty(KEY_STORE_PASSWORD));
        this.keyStorePass = (char[])(keyStorePassword != null ? keyStorePassword.toCharArray() : null);
        this.trustStoreFile = AccessController.doPrivileged(PropertiesHelper.getSystemProperty(TRUST_STORE_FILE));
        this.keyStoreFile = AccessController.doPrivileged(PropertiesHelper.getSystemProperty(KEY_STORE_FILE));
        this.trustStoreBytes = null;
        this.keyStoreBytes = null;
        this.trustStore = null;
        this.keyStore = null;
        this.securityProtocol = "TLS";
        return this;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        SslConfigurator that = (SslConfigurator)o;
        if (this.keyManagerFactoryAlgorithm != null ? !this.keyManagerFactoryAlgorithm.equals(that.keyManagerFactoryAlgorithm) : that.keyManagerFactoryAlgorithm != null) {
            return false;
        }
        if (this.keyManagerFactoryProvider != null ? !this.keyManagerFactoryProvider.equals(that.keyManagerFactoryProvider) : that.keyManagerFactoryProvider != null) {
            return false;
        }
        if (!Arrays.equals(this.keyPass, that.keyPass)) {
            return false;
        }
        if (this.keyStore != null ? !this.keyStore.equals(that.keyStore) : that.keyStore != null) {
            return false;
        }
        if (!Arrays.equals(this.keyStoreBytes, that.keyStoreBytes)) {
            return false;
        }
        if (this.keyStoreFile != null ? !this.keyStoreFile.equals(that.keyStoreFile) : that.keyStoreFile != null) {
            return false;
        }
        if (!Arrays.equals(this.keyStorePass, that.keyStorePass)) {
            return false;
        }
        if (this.keyStoreProvider != null ? !this.keyStoreProvider.equals(that.keyStoreProvider) : that.keyStoreProvider != null) {
            return false;
        }
        if (this.keyStoreType != null ? !this.keyStoreType.equals(that.keyStoreType) : that.keyStoreType != null) {
            return false;
        }
        if (this.securityProtocol != null ? !this.securityProtocol.equals(that.securityProtocol) : that.securityProtocol != null) {
            return false;
        }
        if (this.trustManagerFactoryAlgorithm != null ? !this.trustManagerFactoryAlgorithm.equals(that.trustManagerFactoryAlgorithm) : that.trustManagerFactoryAlgorithm != null) {
            return false;
        }
        if (this.trustManagerFactoryProvider != null ? !this.trustManagerFactoryProvider.equals(that.trustManagerFactoryProvider) : that.trustManagerFactoryProvider != null) {
            return false;
        }
        if (this.trustStore != null ? !this.trustStore.equals(that.trustStore) : that.trustStore != null) {
            return false;
        }
        if (!Arrays.equals(this.trustStoreBytes, that.trustStoreBytes)) {
            return false;
        }
        if (this.trustStoreFile != null ? !this.trustStoreFile.equals(that.trustStoreFile) : that.trustStoreFile != null) {
            return false;
        }
        if (!Arrays.equals(this.trustStorePass, that.trustStorePass)) {
            return false;
        }
        if (this.trustStoreProvider != null ? !this.trustStoreProvider.equals(that.trustStoreProvider) : that.trustStoreProvider != null) {
            return false;
        }
        return !(this.trustStoreType != null ? !this.trustStoreType.equals(that.trustStoreType) : that.trustStoreType != null);
    }

    public int hashCode() {
        int result = this.keyStore != null ? this.keyStore.hashCode() : 0;
        result = 31 * result + (this.trustStore != null ? this.trustStore.hashCode() : 0);
        result = 31 * result + (this.trustStoreProvider != null ? this.trustStoreProvider.hashCode() : 0);
        result = 31 * result + (this.keyStoreProvider != null ? this.keyStoreProvider.hashCode() : 0);
        result = 31 * result + (this.trustStoreType != null ? this.trustStoreType.hashCode() : 0);
        result = 31 * result + (this.keyStoreType != null ? this.keyStoreType.hashCode() : 0);
        result = 31 * result + (this.trustStorePass != null ? Arrays.hashCode(this.trustStorePass) : 0);
        result = 31 * result + (this.keyStorePass != null ? Arrays.hashCode(this.keyStorePass) : 0);
        result = 31 * result + (this.keyPass != null ? Arrays.hashCode(this.keyPass) : 0);
        result = 31 * result + (this.trustStoreFile != null ? this.trustStoreFile.hashCode() : 0);
        result = 31 * result + (this.keyStoreFile != null ? this.keyStoreFile.hashCode() : 0);
        result = 31 * result + (this.trustStoreBytes != null ? Arrays.hashCode(this.trustStoreBytes) : 0);
        result = 31 * result + (this.keyStoreBytes != null ? Arrays.hashCode(this.keyStoreBytes) : 0);
        result = 31 * result + (this.trustManagerFactoryAlgorithm != null ? this.trustManagerFactoryAlgorithm.hashCode() : 0);
        result = 31 * result + (this.keyManagerFactoryAlgorithm != null ? this.keyManagerFactoryAlgorithm.hashCode() : 0);
        result = 31 * result + (this.trustManagerFactoryProvider != null ? this.trustManagerFactoryProvider.hashCode() : 0);
        result = 31 * result + (this.keyManagerFactoryProvider != null ? this.keyManagerFactoryProvider.hashCode() : 0);
        result = 31 * result + (this.securityProtocol != null ? this.securityProtocol.hashCode() : 0);
        return result;
    }
}

