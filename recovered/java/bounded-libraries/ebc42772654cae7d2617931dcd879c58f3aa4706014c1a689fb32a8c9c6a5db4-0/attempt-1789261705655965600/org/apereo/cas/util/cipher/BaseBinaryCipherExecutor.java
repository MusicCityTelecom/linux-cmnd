/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.jose4j.jwk.JsonWebKey$OutputControlLevel
 *  org.jose4j.jwk.OctJwkGenerator
 *  org.jose4j.jwk.OctetSequenceJsonWebKey
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.util.cipher;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import lombok.Generated;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.util.EncodingUtils;
import org.apereo.cas.util.cipher.AbstractCipherExecutor;
import org.apereo.cas.util.crypto.DecryptionException;
import org.apereo.cas.util.function.FunctionUtils;
import org.apereo.cas.util.gen.Base64RandomStringGenerator;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.OctJwkGenerator;
import org.jose4j.jwk.OctetSequenceJsonWebKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseBinaryCipherExecutor
extends AbstractCipherExecutor<byte[], byte[]> {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseBinaryCipherExecutor.class);
    private static final int GCM_TAG_LENGTH = 128;
    private static final int MINIMUM_ENCRYPTION_KEY_LENGTH = 32;
    private static final String CIPHER_ALGORITHM = "AES/GCM/NoPadding";
    protected final String cipherName;
    private final SecretKeySpec encryptionKey;
    private final AlgorithmParameterSpec parameterSpec;
    private String secretKeyAlgorithm = "AES";
    private byte[] encryptionSecretKey;

    protected BaseBinaryCipherExecutor(String encryptionSecretKey, String signingSecretKey, int signingKeySize, int encryptionKeySize, String cipherName) {
        this.cipherName = cipherName;
        this.ensureSigningKeyExists(signingSecretKey, signingKeySize);
        this.ensureEncryptionKeyExists(encryptionSecretKey, encryptionKeySize);
        this.encryptionKey = new SecretKeySpec(this.encryptionSecretKey, this.secretKeyAlgorithm);
        this.parameterSpec = this.buildParameterSpec(encryptionKeySize);
    }

    private static String generateOctetJsonWebKeyOfSize(int size) {
        OctetSequenceJsonWebKey octetKey = OctJwkGenerator.generateJwk((int)size);
        Map params = octetKey.toParams(JsonWebKey.OutputControlLevel.INCLUDE_SYMMETRIC);
        return params.get("k").toString();
    }

    public byte[] encode(byte[] value, Object[] parameters) {
        return (byte[])FunctionUtils.doUnchecked(() -> {
            Cipher aesCipher = Cipher.getInstance(CIPHER_ALGORITHM);
            aesCipher.init(1, (Key)this.encryptionKey, this.parameterSpec);
            byte[] result = aesCipher.doFinal(value);
            return this.sign(result, this.getSigningKey());
        });
    }

    public byte[] decode(byte[] value, Object[] parameters) {
        try {
            byte[] verifiedValue = this.verifySignature(value, this.getSigningKey());
            Cipher aesCipher = Cipher.getInstance(CIPHER_ALGORITHM);
            aesCipher.init(2, (Key)this.encryptionKey, this.parameterSpec);
            return aesCipher.doFinal(verifiedValue);
        }
        catch (Exception e) {
            throw LOGGER.isTraceEnabled() ? new DecryptionException(e) : new DecryptionException();
        }
    }

    protected abstract String getEncryptionKeySetting();

    protected abstract String getSigningKeySetting();

    private AlgorithmParameterSpec buildParameterSpec(int encryptionKeySize) {
        byte[] iv = new byte[this.encryptionSecretKey.length];
        if (encryptionKeySize > 32) {
            System.arraycopy(this.encryptionSecretKey, 0, iv, 0, this.encryptionSecretKey.length);
        }
        return encryptionKeySize <= 32 ? new IvParameterSpec(iv) : new GCMParameterSpec(128, iv);
    }

    private void ensureEncryptionKeyExists(String encryptionSecretKey, int encryptionKeySize) {
        byte[] genEncryptionKey;
        if (StringUtils.isBlank((CharSequence)encryptionSecretKey)) {
            LOGGER.warn("Secret key for encryption is not defined under [{}]. CAS will attempt to auto-generate the encryption key", (Object)this.getEncryptionKeySetting());
            if (encryptionKeySize <= 32) {
                String key = new Base64RandomStringGenerator(encryptionKeySize).getNewString();
                String prop = String.format("%s=%s", this.getEncryptionKeySetting(), key);
                LOGGER.warn("Generated encryption key [{}] of size [{}]. The generated key MUST be added to CAS settings:\n\n\t{}\n\n", new Object[]{key, encryptionKeySize, prop});
                genEncryptionKey = EncodingUtils.decodeBase64(key);
            } else {
                KeyGenerator keyGenerator = (KeyGenerator)FunctionUtils.doUnchecked(() -> KeyGenerator.getInstance(this.secretKeyAlgorithm));
                keyGenerator.init(encryptionKeySize);
                SecretKey secretKey = keyGenerator.generateKey();
                genEncryptionKey = secretKey.getEncoded();
                String encodedKey = EncodingUtils.encodeBase64(genEncryptionKey);
                String prop = String.format("%s=%s", this.getEncryptionKeySetting(), encodedKey);
                LOGGER.warn("Generated encryption key [{}] of size [{}]. The generated key MUST be added to CAS settings:\n\n\t{}\n\n", new Object[]{encodedKey, encryptionKeySize, prop});
            }
        } else if (encryptionKeySize <= 32) {
            byte[] key;
            boolean base64 = EncodingUtils.isBase64(encryptionSecretKey);
            byte[] byArray = key = base64 ? EncodingUtils.decodeBase64(encryptionSecretKey) : ArrayUtils.EMPTY_BYTE_ARRAY;
            if (base64 && key.length == encryptionKeySize) {
                LOGGER.trace("Secret key for encryption defined under [{}] is Base64 encoded.", (Object)this.getEncryptionKeySetting());
                genEncryptionKey = key;
            } else if (encryptionSecretKey.length() != encryptionKeySize) {
                LOGGER.warn("Secret key for encryption defined under [{}] is Base64 encoded but the size does not match the key size [{}].", (Object)this.getEncryptionKeySetting(), (Object)encryptionKeySize);
                genEncryptionKey = encryptionSecretKey.getBytes(StandardCharsets.UTF_8);
            } else {
                LOGGER.warn("Secret key for encryption defined under [{}] is not Base64 encoded. Clear the setting to regenerate (Recommended) or replace with [{}].", (Object)this.getEncryptionKeySetting(), (Object)EncodingUtils.encodeBase64(encryptionSecretKey));
                genEncryptionKey = encryptionSecretKey.getBytes(StandardCharsets.UTF_8);
            }
        } else {
            genEncryptionKey = EncodingUtils.decodeBase64(encryptionSecretKey);
        }
        this.encryptionSecretKey = genEncryptionKey;
    }

    private void ensureSigningKeyExists(String signingSecretKey, int signingKeySize) {
        String signingKeyToUse = signingSecretKey;
        if (StringUtils.isBlank((CharSequence)signingKeyToUse)) {
            LOGGER.warn("Secret key for signing is not defined under [{}]. CAS will attempt to auto-generate the signing key", (Object)this.getSigningKeySetting());
            signingKeyToUse = BaseBinaryCipherExecutor.generateOctetJsonWebKeyOfSize(signingKeySize);
            String prop = String.format("%s=%s", this.getSigningKeySetting(), signingKeyToUse);
            LOGGER.warn("Generated signing key [{}] of size [{}]. The generated key MUST be added to CAS settings:\n\n\t{}\n\n", new Object[]{signingKeyToUse, signingKeySize, prop});
        }
        this.configureSigningKey(signingKeyToUse);
    }

    @Generated
    public String getCipherName() {
        return this.cipherName;
    }

    @Generated
    public SecretKeySpec getEncryptionKey() {
        return this.encryptionKey;
    }

    @Generated
    public AlgorithmParameterSpec getParameterSpec() {
        return this.parameterSpec;
    }

    @Generated
    public String getSecretKeyAlgorithm() {
        return this.secretKeyAlgorithm;
    }

    @Generated
    public byte[] getEncryptionSecretKey() {
        return this.encryptionSecretKey;
    }

    @Generated
    public void setSecretKeyAlgorithm(String secretKeyAlgorithm) {
        this.secretKeyAlgorithm = secretKeyAlgorithm;
    }

    @Generated
    public void setEncryptionSecretKey(byte[] encryptionSecretKey) {
        this.encryptionSecretKey = encryptionSecretKey;
    }
}

