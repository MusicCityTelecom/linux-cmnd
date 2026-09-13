/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.util.cipher;

import org.apereo.cas.util.cipher.BaseStringCipherExecutor;

public class TicketGrantingCookieCipherExecutor
extends BaseStringCipherExecutor {
    public TicketGrantingCookieCipherExecutor(String secretKeyEncryption, String secretKeySigning, String alg, int signingKeySize, int encryptionKeySize) {
        super(secretKeyEncryption, secretKeySigning, alg, signingKeySize, encryptionKeySize);
    }

    public TicketGrantingCookieCipherExecutor(String secretKeyEncryption, String secretKeySigning, int signingKeySize, int encryptionKeySize) {
        super(secretKeyEncryption, secretKeySigning, signingKeySize, encryptionKeySize);
    }

    public TicketGrantingCookieCipherExecutor() {
        super(null, null, 0, 0);
    }

    public String getName() {
        return "Ticket-granting Cookie";
    }

    @Override
    protected String getEncryptionKeySetting() {
        return "cas.tgc.crypto.encryption.key";
    }

    @Override
    protected String getSigningKeySetting() {
        return "cas.tgc.crypto.signing.key";
    }
}

