/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.util.cipher;

import org.apereo.cas.util.cipher.BaseBinaryCipherExecutor;

public class WebflowConversationStateCipherExecutor
extends BaseBinaryCipherExecutor {
    public WebflowConversationStateCipherExecutor(String secretKeyEncryption, String secretKeySigning, String secretKeyAlg, int signingKeySize, int encryptionKeySize, String cipherName) {
        super(secretKeyEncryption, secretKeySigning, signingKeySize, encryptionKeySize, cipherName);
        this.setSecretKeyAlgorithm(secretKeyAlg);
    }

    public WebflowConversationStateCipherExecutor(String encryptionSecretKey, String signingSecretKey, String secretKeyAlg, int signingKeySize, int encryptionKeySize) {
        this(encryptionSecretKey, signingSecretKey, secretKeyAlg, signingKeySize, encryptionKeySize, "webflow");
    }

    public String getName() {
        return "Spring Webflow Session State Management";
    }

    @Override
    protected String getEncryptionKeySetting() {
        return "cas." + this.cipherName + ".crypto.encryption.key";
    }

    @Override
    protected String getSigningKeySetting() {
        return "cas." + this.cipherName + ".crypto.signing.key";
    }
}

