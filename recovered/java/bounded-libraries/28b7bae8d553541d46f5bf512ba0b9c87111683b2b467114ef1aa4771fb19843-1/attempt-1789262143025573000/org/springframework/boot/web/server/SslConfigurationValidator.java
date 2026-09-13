/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.web.server;

import java.security.KeyStore;
import java.security.KeyStoreException;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public final class SslConfigurationValidator {
    private SslConfigurationValidator() {
    }

    public static void validateKeyAlias(KeyStore keyStore, String keyAlias) {
        if (StringUtils.hasLength((String)keyAlias)) {
            try {
                Assert.state((boolean)keyStore.containsAlias(keyAlias), () -> String.format("Keystore does not contain specified alias '%s'", keyAlias));
            }
            catch (KeyStoreException ex) {
                throw new IllegalStateException(String.format("Could not determine if keystore contains alias '%s'", keyAlias), ex);
            }
        }
    }
}

