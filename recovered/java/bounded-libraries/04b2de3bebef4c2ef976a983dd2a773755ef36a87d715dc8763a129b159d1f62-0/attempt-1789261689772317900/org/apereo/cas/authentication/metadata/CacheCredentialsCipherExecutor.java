/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.util.cipher.BaseStringCipherExecutor
 */
package org.apereo.cas.authentication.metadata;

import org.apereo.cas.util.cipher.BaseStringCipherExecutor;

public class CacheCredentialsCipherExecutor
extends BaseStringCipherExecutor {
    public CacheCredentialsCipherExecutor(String secretKeyEncryption, String secretKeySigning, String alg, int signingKeySize, int encryptionKeySize) {
        super(secretKeyEncryption, secretKeySigning, alg, signingKeySize, encryptionKeySize);
    }

    public String getName() {
        return "Credential Caching & Clearpass";
    }

    protected String getEncryptionKeySetting() {
        return "cas.clearpass.crypto.encryption.key";
    }

    protected String getSigningKeySetting() {
        return "cas.clearpass.crypto.signing.key";
    }
}

