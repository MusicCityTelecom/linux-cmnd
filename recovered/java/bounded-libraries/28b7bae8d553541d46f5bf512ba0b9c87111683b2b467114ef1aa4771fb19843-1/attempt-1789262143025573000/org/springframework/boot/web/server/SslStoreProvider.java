/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.web.server;

import java.security.KeyStore;

public interface SslStoreProvider {
    public KeyStore getKeyStore() throws Exception;

    public KeyStore getTrustStore() throws Exception;

    default public String getKeyPassword() {
        return null;
    }
}

