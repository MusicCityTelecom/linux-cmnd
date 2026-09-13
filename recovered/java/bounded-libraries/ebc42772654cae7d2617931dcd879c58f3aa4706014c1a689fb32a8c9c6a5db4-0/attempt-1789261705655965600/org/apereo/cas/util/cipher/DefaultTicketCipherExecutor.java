/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.util.cipher;

import org.apereo.cas.util.cipher.BaseBinaryCipherExecutor;

public class DefaultTicketCipherExecutor
extends BaseBinaryCipherExecutor {
    public DefaultTicketCipherExecutor(String encryptionSecretKey, String signingSecretKey, String secretKeyAlg, int signingKeySize, int encryptionKeySize, String cipherName) {
        super(encryptionSecretKey, signingSecretKey, signingKeySize, encryptionKeySize, cipherName);
        this.setSecretKeyAlgorithm(secretKeyAlg);
    }

    public String getName() {
        return "Ticketing";
    }

    @Override
    protected String getEncryptionKeySetting() {
        return "cas.ticket.registry." + this.cipherName + ".crypto.encryption.key";
    }

    @Override
    protected String getSigningKeySetting() {
        return "cas.ticket.registry." + this.cipherName + ".crypto.signing.key";
    }
}

