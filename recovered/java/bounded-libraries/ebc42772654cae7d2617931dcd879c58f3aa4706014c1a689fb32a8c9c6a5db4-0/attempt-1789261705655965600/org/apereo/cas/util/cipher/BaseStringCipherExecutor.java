/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.jose4j.json.JsonUtil
 *  org.jose4j.jwk.PublicJsonWebKey
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.util.cipher;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.PublicKey;
import java.util.Map;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.util.EncodingUtils;
import org.apereo.cas.util.LoggingUtils;
import org.apereo.cas.util.ResourceUtils;
import org.apereo.cas.util.cipher.AbstractCipherExecutor;
import org.apereo.cas.util.function.FunctionUtils;
import org.apereo.cas.util.jwt.JsonWebTokenEncryptor;
import org.jose4j.json.JsonUtil;
import org.jose4j.jwk.PublicJsonWebKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseStringCipherExecutor
extends AbstractCipherExecutor<Serializable, String> {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseStringCipherExecutor.class);
    private CipherOperationsStrategyType strategyType = CipherOperationsStrategyType.ENCRYPT_AND_SIGN;
    private String encryptionAlgorithm = "dir";
    private String contentEncryptionAlgorithmIdentifier;
    private Key encryptionKey;
    private boolean encryptionEnabled = true;
    private boolean signingEnabled = true;
    private int encryptionKeySize = 256;
    private int signingKeySize = 512;

    protected BaseStringCipherExecutor(String secretKeyEncryption, String secretKeySigning, boolean encryptionEnabled, boolean signingEnabled, int signingKeySize, int encryptionKeySize) {
        this(secretKeyEncryption, secretKeySigning, "A128CBC-HS256", encryptionEnabled, signingEnabled, signingKeySize, encryptionKeySize);
    }

    protected BaseStringCipherExecutor(String secretKeyEncryption, String secretKeySigning, boolean encryptionEnabled, int signingKeySize, int encryptionKeySize) {
        this(secretKeyEncryption, secretKeySigning, encryptionEnabled, true, signingKeySize, encryptionKeySize);
    }

    protected BaseStringCipherExecutor(String secretKeyEncryption, String secretKeySigning, String contentEncryptionAlgorithmIdentifier, int signingKeySize, int encryptionKeySize) {
        this(secretKeyEncryption, secretKeySigning, contentEncryptionAlgorithmIdentifier, true, true, signingKeySize, encryptionKeySize);
    }

    protected BaseStringCipherExecutor(String secretKeyEncryption, String secretKeySigning, int signingKeySize, int encryptionKeySize) {
        this(secretKeyEncryption, secretKeySigning, "A128CBC-HS256", true, true, signingKeySize, encryptionKeySize);
    }

    protected BaseStringCipherExecutor(String secretKeyEncryption, String secretKeySigning, String contentEncryptionAlgorithmIdentifier, boolean encryptionEnabled, boolean signingEnabled, int signingKeyLength, int encryptionKeyLength) {
        this.signingEnabled = signingEnabled || StringUtils.isNotBlank((CharSequence)secretKeySigning);
        this.encryptionEnabled = this.signingEnabled && (encryptionEnabled || StringUtils.isNotBlank((CharSequence)secretKeyEncryption));
        this.signingKeySize = signingKeyLength <= 0 ? 512 : signingKeyLength;
        int n = this.encryptionKeySize = encryptionKeyLength <= 0 ? 256 : encryptionKeyLength;
        if (this.encryptionEnabled) {
            this.configureEncryptionParameters(secretKeyEncryption, contentEncryptionAlgorithmIdentifier);
        } else {
            LOGGER.info("Encryption is not enabled for [{}]. The cipher [{}] will only attempt to produce signed objects", (Object)this.getName(), (Object)this.getClass().getSimpleName());
        }
        if (this.signingEnabled) {
            this.configureSigningParameters(secretKeySigning);
        } else {
            LOGGER.info("Signing is not enabled for [{}]. The cipher [{}] will attempt to produce plain objects", (Object)this.getName(), (Object)this.getClass().getSimpleName());
        }
    }

    public String encode(Serializable value, Object[] parameters) {
        if (this.strategyType == CipherOperationsStrategyType.ENCRYPT_AND_SIGN) {
            return this.encryptAndSign(value, this.getEncryptionKey(), this.getSigningKey());
        }
        return this.signAndEncrypt(value, this.getEncryptionKey(), this.getSigningKey());
    }

    public String decode(Serializable value, Object[] parameters) {
        return this.decode(value, parameters, this.getEncryptionKey(), this.getSigningKey());
    }

    protected String decode(Serializable value, Object[] parameters, Key encryptionKey, Key signingKey) {
        if (this.strategyType == CipherOperationsStrategyType.ENCRYPT_AND_SIGN) {
            return this.verifyAndDecrypt(value, encryptionKey, signingKey);
        }
        return this.decryptAndVerify(value, encryptionKey, signingKey);
    }

    protected void configureEncryptionKeyFromPublicKeyResource(String secretKeyToUse) {
        PublicKey object = BaseStringCipherExecutor.extractPublicKeyFromResource(secretKeyToUse);
        LOGGER.debug("Located encryption key resource [{}]", (Object)secretKeyToUse);
        this.setEncryptionKey(object);
        this.setEncryptionAlgorithm("RSA-OAEP-256");
    }

    protected boolean isEncryptionPossible(Key key) {
        return this.encryptionEnabled && key != null;
    }

    protected String getEncryptionKeySetting() {
        return "N/A";
    }

    protected String getSigningKeySetting() {
        return "N/A";
    }

    private void configureSigningParameters(String secretKeySigning) {
        String signingKeyToUse = secretKeySigning;
        if (StringUtils.isBlank((CharSequence)signingKeyToUse)) {
            LOGGER.warn("Secret key for signing is not defined for [{}]. CAS will attempt to auto-generate the signing key", (Object)this.getName());
            signingKeyToUse = EncodingUtils.generateJsonWebKey(this.signingKeySize);
            String prop = String.format("%s=%s", this.getSigningKeySetting(), signingKeyToUse);
            LOGGER.warn("Generated signing key [{}] of size [{}] for [{}]. The generated key MUST be added to CAS settings:\n\n\t{}\n\n", new Object[]{signingKeyToUse, this.signingKeySize, this.getName(), prop});
        } else {
            try {
                PublicJsonWebKey jwk = (PublicJsonWebKey)EncodingUtils.newJsonWebKey(signingKeyToUse);
                LOGGER.trace("Parsed signing key as a JSON web key for [{}] with kid [{}]", (Object)this.getName(), (Object)jwk.getKeyId());
                if (jwk.getPrivateKey() == null) {
                    String msg = "Provided signing key as a JSON web key does not carry a private key";
                    LOGGER.error("Provided signing key as a JSON web key does not carry a private key");
                    throw new RuntimeException("Provided signing key as a JSON web key does not carry a private key");
                }
                this.setSigningKey(jwk.getPrivateKey());
            }
            catch (Exception e) {
                LOGGER.trace("Unable to recognize signing key for [{}] as a JSON web key: [{}].", (Object)this.getSigningKeySetting(), (Object)e.getMessage());
                LOGGER.debug("Using pre-defined signing key to use for [{}]", (Object)this.getSigningKeySetting());
            }
        }
        this.configureSigningKey(signingKeyToUse);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void configureEncryptionParameters(String secretKeyEncryption, String contentEncryptionAlgorithmIdentifier) {
        String secretKeyToUse = secretKeyEncryption;
        if (StringUtils.isBlank((CharSequence)secretKeyToUse)) {
            LOGGER.warn("Secret key for encryption is not defined for [{}]; CAS will attempt to auto-generate the encryption key", (Object)this.getName());
            secretKeyToUse = EncodingUtils.generateJsonWebKey(this.encryptionKeySize);
            String prop = String.format("%s=%s", this.getEncryptionKeySetting(), secretKeyToUse);
            LOGGER.warn("Generated encryption key [{}] of size [{}] for [{}]. The generated key MUST be added to CAS settings:\n\n\t{}\n\n", new Object[]{secretKeyToUse, this.encryptionKeySize, this.getName(), prop});
        } else {
            try {
                Map results = JsonUtil.parseJson((String)secretKeyToUse);
                LOGGER.trace("Parsed encryption key as a JSON web key for [{}] as [{}]", (Object)this.getName(), (Object)results);
                this.setEncryptionKey(EncodingUtils.generateJsonWebKey(results));
            }
            catch (Exception e) {
                LOGGER.trace("Unable to recognize encryption key [{}] as a JSON web key: [{}].", (Object)this.getEncryptionKeySetting(), (Object)e.getMessage());
                LOGGER.debug("Using pre-defined encryption key to use for [{}]", (Object)this.getEncryptionKeySetting());
            }
        }
        try {
            if (ResourceUtils.doesResourceExist(secretKeyToUse)) {
                this.configureEncryptionKeyFromPublicKeyResource(secretKeyToUse);
            }
        }
        catch (Exception e) {
            LoggingUtils.error(LOGGER, e);
        }
        finally {
            if (this.encryptionKey == null) {
                LOGGER.trace("Creating encryption key instance based on provided secret key");
                this.setEncryptionKey(EncodingUtils.generateJsonWebKey(secretKeyToUse));
            }
            if (StringUtils.isBlank((CharSequence)contentEncryptionAlgorithmIdentifier)) {
                this.setContentEncryptionAlgorithmIdentifier("A128CBC-HS256");
            } else {
                this.setContentEncryptionAlgorithmIdentifier(contentEncryptionAlgorithmIdentifier);
            }
            LOGGER.trace("Initialized cipher encryption sequence via content encryption [{}] and algorithm [{}]", (Object)this.contentEncryptionAlgorithmIdentifier, (Object)this.encryptionAlgorithm);
        }
    }

    private String decryptAndVerify(Serializable value, Key encryptionKey, Key signingKey) {
        String encodedObj = value.toString();
        if (this.isEncryptionPossible(encryptionKey)) {
            LOGGER.trace("Attempting to decrypt value based on encryption key defined by [{}]", (Object)this.getEncryptionKeySetting());
            encodedObj = EncodingUtils.decryptJwtValue(encryptionKey, encodedObj);
        }
        byte[] currentValue = encodedObj.getBytes(StandardCharsets.UTF_8);
        byte[] encoded = FunctionUtils.doIf(this.signingEnabled, () -> {
            LOGGER.trace("Attempting to verify signature based on signing key defined by [{}]", (Object)this.getSigningKeySetting());
            return this.verifySignature(currentValue, signingKey);
        }, () -> currentValue).get();
        return new String(encoded, StandardCharsets.UTF_8);
    }

    private String verifyAndDecrypt(Serializable value, Key encryptionKey, Key signingKey) {
        byte[] currentValue = value.toString().getBytes(StandardCharsets.UTF_8);
        byte[] encoded = FunctionUtils.doIf(this.signingEnabled, () -> {
            LOGGER.trace("Attempting to verify signature based on signing key defined by [{}]", (Object)this.getSigningKeySetting());
            return this.verifySignature(currentValue, signingKey);
        }, () -> currentValue).get();
        if (encoded != null && encoded.length > 0) {
            String encodedObj = new String(encoded, StandardCharsets.UTF_8);
            if (this.isEncryptionPossible(encryptionKey)) {
                LOGGER.trace("Attempting to decrypt value based on encryption key defined by [{}]", (Object)this.getEncryptionKeySetting());
                return EncodingUtils.decryptJwtValue(encryptionKey, encodedObj);
            }
            return encodedObj;
        }
        return null;
    }

    private String encryptAndSign(Serializable value, Key encryptionKey, Key signingKey) {
        String encoded = FunctionUtils.doIf(this.isEncryptionPossible(encryptionKey), () -> {
            LOGGER.trace("Attempting to encrypt value based on encryption key defined by [{}]", (Object)this.getEncryptionKeySetting());
            return this.encryptValueAsJwt(encryptionKey, value);
        }, value::toString).get();
        if (this.signingEnabled) {
            LOGGER.trace("Attempting to sign value based on signing key defined by [{}]", (Object)this.getSigningKeySetting());
            byte[] signed = this.sign(encoded.getBytes(StandardCharsets.UTF_8), signingKey);
            return new String(signed, StandardCharsets.UTF_8);
        }
        return encoded;
    }

    private String signAndEncrypt(Serializable value, Key encryptionKey, Key signingKey) {
        String encoded = FunctionUtils.doIf(this.signingEnabled, () -> {
            LOGGER.trace("Attempting to sign value based on signing key defined by [{}]", (Object)this.getSigningKeySetting());
            byte[] signed = this.sign(value.toString().getBytes(StandardCharsets.UTF_8), signingKey);
            return new String(signed, StandardCharsets.UTF_8);
        }, value::toString).get();
        return FunctionUtils.doIf(this.isEncryptionPossible(encryptionKey), () -> {
            LOGGER.trace("Attempting to encrypt value based on encryption key defined by [{}]", (Object)this.getEncryptionKeySetting());
            return this.encryptValueAsJwt(encryptionKey, (Serializable)((Object)encoded));
        }, () -> encoded).get();
    }

    private String encryptValueAsJwt(Key encryptionKey, Serializable value) {
        return ((JsonWebTokenEncryptor)((JsonWebTokenEncryptor.JsonWebTokenEncryptorBuilder)((JsonWebTokenEncryptor.JsonWebTokenEncryptorBuilder)((JsonWebTokenEncryptor.JsonWebTokenEncryptorBuilder)((JsonWebTokenEncryptor.JsonWebTokenEncryptorBuilder)JsonWebTokenEncryptor.builder().key(encryptionKey)).algorithm(this.encryptionAlgorithm)).encryptionMethod(this.contentEncryptionAlgorithmIdentifier)).headers(this.getCustomHeaders())).build()).encrypt(value);
    }

    @Generated
    protected BaseStringCipherExecutor() {
    }

    @Generated
    public void setStrategyType(CipherOperationsStrategyType strategyType) {
        this.strategyType = strategyType;
    }

    @Generated
    public void setEncryptionAlgorithm(String encryptionAlgorithm) {
        this.encryptionAlgorithm = encryptionAlgorithm;
    }

    @Generated
    public void setContentEncryptionAlgorithmIdentifier(String contentEncryptionAlgorithmIdentifier) {
        this.contentEncryptionAlgorithmIdentifier = contentEncryptionAlgorithmIdentifier;
    }

    @Generated
    public void setEncryptionKey(Key encryptionKey) {
        this.encryptionKey = encryptionKey;
    }

    @Generated
    public void setEncryptionEnabled(boolean encryptionEnabled) {
        this.encryptionEnabled = encryptionEnabled;
    }

    @Generated
    public void setSigningEnabled(boolean signingEnabled) {
        this.signingEnabled = signingEnabled;
    }

    @Generated
    public void setEncryptionKeySize(int encryptionKeySize) {
        this.encryptionKeySize = encryptionKeySize;
    }

    @Generated
    public void setSigningKeySize(int signingKeySize) {
        this.signingKeySize = signingKeySize;
    }

    @Generated
    public CipherOperationsStrategyType getStrategyType() {
        return this.strategyType;
    }

    @Generated
    public String getEncryptionAlgorithm() {
        return this.encryptionAlgorithm;
    }

    @Generated
    public String getContentEncryptionAlgorithmIdentifier() {
        return this.contentEncryptionAlgorithmIdentifier;
    }

    @Generated
    public Key getEncryptionKey() {
        return this.encryptionKey;
    }

    @Generated
    public boolean isEncryptionEnabled() {
        return this.encryptionEnabled;
    }

    @Generated
    public boolean isSigningEnabled() {
        return this.signingEnabled;
    }

    @Generated
    public int getEncryptionKeySize() {
        return this.encryptionKeySize;
    }

    @Generated
    public int getSigningKeySize() {
        return this.signingKeySize;
    }

    public static enum CipherOperationsStrategyType {
        ENCRYPT_AND_SIGN,
        SIGN_AND_ENCRYPT;

    }
}

