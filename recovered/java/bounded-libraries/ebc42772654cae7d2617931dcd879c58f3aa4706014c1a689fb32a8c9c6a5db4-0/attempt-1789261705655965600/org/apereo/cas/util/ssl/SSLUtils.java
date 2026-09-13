/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.configuration.model.core.util.ClientCertificateProperties
 */
package org.apereo.cas.util.ssl;

import java.io.InputStream;
import java.security.KeyStore;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.util.ClientCertificateProperties;
import org.apereo.cas.util.RandomUtils;
import org.apereo.cas.util.function.FunctionUtils;

public final class SSLUtils {
    public static KeyManagerFactory buildKeystore(ClientCertificateProperties properties) {
        return (KeyManagerFactory)FunctionUtils.doUnchecked(() -> {
            try (InputStream keyInput = properties.getCertificate().getLocation().getInputStream();){
                KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
                KeyStore keyStore = KeyStore.getInstance("PKCS12");
                keyStore.load(keyInput, properties.getPassphrase().toCharArray());
                keyManagerFactory.init(keyStore, properties.getPassphrase().toCharArray());
                KeyManagerFactory keyManagerFactory2 = keyManagerFactory;
                return keyManagerFactory2;
            }
        });
    }

    public static SSLContext buildSSLContext(ClientCertificateProperties clientCertificate) {
        return (SSLContext)FunctionUtils.doUnchecked(() -> {
            KeyManagerFactory keyManagerFactory = SSLUtils.buildKeystore(clientCertificate);
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(keyManagerFactory.getKeyManagers(), null, RandomUtils.getNativeInstance());
            return context;
        });
    }

    @Generated
    private SSLUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

