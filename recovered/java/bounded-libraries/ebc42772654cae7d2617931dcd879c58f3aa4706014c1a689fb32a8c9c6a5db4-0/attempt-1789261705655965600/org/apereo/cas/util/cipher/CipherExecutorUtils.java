/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.configuration.model.core.util.EncryptionJwtSigningJwtCryptographyProperties
 *  org.apereo.cas.configuration.model.core.util.EncryptionOptionalSigningOptionalJwtCryptographyProperties
 *  org.jooq.lambda.Unchecked
 */
package org.apereo.cas.util.cipher;

import java.lang.reflect.Constructor;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.util.EncryptionJwtSigningJwtCryptographyProperties;
import org.apereo.cas.configuration.model.core.util.EncryptionOptionalSigningOptionalJwtCryptographyProperties;
import org.apereo.cas.util.cipher.BaseStringCipherExecutor;
import org.jooq.lambda.Unchecked;

public final class CipherExecutorUtils {
    public static <T extends BaseStringCipherExecutor> T newStringCipherExecutor(EncryptionJwtSigningJwtCryptographyProperties crypto, Class<T> cipherClass) {
        return (T)((BaseStringCipherExecutor)Unchecked.supplier(() -> {
            Constructor ctor = cipherClass.getDeclaredConstructor(String.class, String.class, String.class, Integer.TYPE, Integer.TYPE);
            BaseStringCipherExecutor cipher = (BaseStringCipherExecutor)ctor.newInstance(crypto.getEncryption().getKey(), crypto.getSigning().getKey(), crypto.getAlg(), crypto.getSigning().getKeySize(), crypto.getEncryption().getKeySize());
            cipher.setStrategyType(BaseStringCipherExecutor.CipherOperationsStrategyType.valueOf(crypto.getStrategyType()));
            return cipher;
        }).get());
    }

    public static <T extends BaseStringCipherExecutor> T newStringCipherExecutor(EncryptionOptionalSigningOptionalJwtCryptographyProperties crypto, Class<T> cipherClass) {
        return (T)((BaseStringCipherExecutor)Unchecked.supplier(() -> {
            Constructor ctor = cipherClass.getDeclaredConstructor(String.class, String.class, String.class, Boolean.TYPE, Boolean.TYPE, Integer.TYPE, Integer.TYPE);
            BaseStringCipherExecutor cipher = (BaseStringCipherExecutor)ctor.newInstance(crypto.getEncryption().getKey(), crypto.getSigning().getKey(), crypto.getAlg(), crypto.isEncryptionEnabled(), crypto.isSigningEnabled(), crypto.getSigning().getKeySize(), crypto.getEncryption().getKeySize());
            cipher.setStrategyType(BaseStringCipherExecutor.CipherOperationsStrategyType.valueOf(crypto.getStrategyType()));
            return cipher;
        }).get());
    }

    @Generated
    private CipherExecutorUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

