/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.util.crypto;

import java.io.Serializable;
import java.security.Key;
import org.apereo.cas.util.crypto.DecodableCipher;
import org.apereo.cas.util.crypto.EncodableCipher;
import org.apereo.cas.util.crypto.NoOpCipherExecutor;

public interface CipherExecutor<I, O>
extends EncodableCipher<I, O>,
DecodableCipher<I, O> {
    public static final String DEFAULT_CONTENT_ENCRYPTION_ALGORITHM = "A128CBC-HS256";
    public static final int DEFAULT_STRINGABLE_ENCRYPTION_KEY_SIZE = 256;
    public static final int DEFAULT_STRINGABLE_SIGNING_KEY_SIZE = 512;

    public static CipherExecutor<Serializable, Serializable> noOp() {
        return NoOpCipherExecutor.INSTANCE;
    }

    public static CipherExecutor<String, String> noOpOfStringToString() {
        return NoOpCipherExecutor.INSTANCE;
    }

    public static CipherExecutor<Number, Number> noOpOfNumberToNumber() {
        return NoOpCipherExecutor.INSTANCE;
    }

    public static CipherExecutor<Serializable, String> noOpOfSerializableToString() {
        return NoOpCipherExecutor.INSTANCE;
    }

    default public boolean isEnabled() {
        return true;
    }

    default public String getName() {
        return this.getClass().getSimpleName();
    }

    default public Key getSigningKey() {
        return null;
    }
}

