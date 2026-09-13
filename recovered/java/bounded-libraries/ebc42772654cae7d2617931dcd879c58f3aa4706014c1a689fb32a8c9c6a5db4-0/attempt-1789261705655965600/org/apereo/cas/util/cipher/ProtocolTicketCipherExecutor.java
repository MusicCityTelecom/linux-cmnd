/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.util.cipher;

import org.apereo.cas.util.cipher.BaseStringCipherExecutor;

public class ProtocolTicketCipherExecutor
extends BaseStringCipherExecutor {
    public ProtocolTicketCipherExecutor() {
        super(null, null, "A128CBC-HS256", 0, 0);
    }

    public ProtocolTicketCipherExecutor(String secretKeyEncryption, String secretKeySigning, String contentEncryptionAlgorithmIdentifier, int signingKeySize, int encryptionKeySize) {
        super(secretKeyEncryption, secretKeySigning, contentEncryptionAlgorithmIdentifier, signingKeySize, encryptionKeySize);
    }

    public ProtocolTicketCipherExecutor(String secretKeyEncryption, String secretKeySigning, int signingKeySize, int encryptionKeySize) {
        super(secretKeyEncryption, secretKeySigning, "A128CBC-HS256", signingKeySize, encryptionKeySize);
    }

    public String getName() {
        return "CAS Protocol Tickets";
    }

    @Override
    protected String getEncryptionKeySetting() {
        return "cas.ticket.crypto.encryption.key";
    }

    @Override
    protected String getSigningKeySetting() {
        return "cas.ticket.crypto.signing.key";
    }
}

