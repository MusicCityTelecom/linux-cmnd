/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 */
package org.apereo.cas.util.cipher;

import java.io.Serializable;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.util.cipher.BaseStringCipherExecutor;

public class RsaKeyPairCipherExecutor
extends BaseStringCipherExecutor {
    private final PrivateKey privateKeySigning;
    private final PublicKey publicKeySigning;
    private final PrivateKey privateKeyEncryption;
    private final PublicKey publicKeyEncryption;

    public RsaKeyPairCipherExecutor(KeyPair signing, KeyPair encryption) {
        this(signing.getPrivate(), signing.getPublic(), encryption.getPrivate(), encryption.getPublic());
    }

    public RsaKeyPairCipherExecutor(KeyPair signing) {
        this(signing.getPrivate(), signing.getPublic(), null, null);
    }

    public RsaKeyPairCipherExecutor(String privateKeySigning, String publicKeySigning, String privateKeyEncryption, String publicKeyEncryption) {
        this.privateKeySigning = RsaKeyPairCipherExecutor.extractPrivateKeyFromResource(privateKeySigning);
        this.publicKeySigning = RsaKeyPairCipherExecutor.extractPublicKeyFromResource(publicKeySigning);
        this.privateKeyEncryption = StringUtils.isNotBlank((CharSequence)privateKeyEncryption) ? RsaKeyPairCipherExecutor.extractPrivateKeyFromResource(privateKeyEncryption) : null;
        this.publicKeyEncryption = StringUtils.isNotBlank((CharSequence)publicKeyEncryption) ? RsaKeyPairCipherExecutor.extractPublicKeyFromResource(publicKeyEncryption) : null;
    }

    public RsaKeyPairCipherExecutor(String privateKeySigning, String publicKeySigning) {
        this(privateKeySigning, publicKeySigning, null, null);
    }

    @Override
    public String encode(Serializable value, Object[] parameters) {
        this.configureSigningParametersForEncoding();
        this.configureEncryptionParametersForEncoding();
        return super.encode(value, parameters);
    }

    @Override
    public String decode(Serializable value, Object[] parameters) {
        this.configureSigningParametersForDecoding();
        this.configureEncryptionParametersForDecoding();
        return super.decode(value, parameters);
    }

    private void configureSigningParametersForDecoding() {
        this.setSigningKey(this.publicKeySigning);
    }

    private void configureEncryptionParametersForDecoding() {
        this.setEncryptionKey(this.privateKeyEncryption);
        this.setContentEncryptionAlgorithmIdentifier("A128CBC-HS256");
        this.setEncryptionAlgorithm("RSA-OAEP-256");
    }

    private void configureEncryptionParametersForEncoding() {
        this.setEncryptionKey(this.publicKeyEncryption);
        this.setContentEncryptionAlgorithmIdentifier("A128CBC-HS256");
        this.setEncryptionAlgorithm("RSA-OAEP-256");
    }

    private void configureSigningParametersForEncoding() {
        this.setSigningKey(this.privateKeySigning);
    }

    @Generated
    public PrivateKey getPrivateKeySigning() {
        return this.privateKeySigning;
    }

    @Generated
    public PublicKey getPublicKeySigning() {
        return this.publicKeySigning;
    }

    @Generated
    public PrivateKey getPrivateKeyEncryption() {
        return this.privateKeyEncryption;
    }

    @Generated
    public PublicKey getPublicKeyEncryption() {
        return this.publicKeyEncryption;
    }

    @Generated
    public RsaKeyPairCipherExecutor(PrivateKey privateKeySigning, PublicKey publicKeySigning, PrivateKey privateKeyEncryption, PublicKey publicKeyEncryption) {
        this.privateKeySigning = privateKeySigning;
        this.publicKeySigning = publicKeySigning;
        this.privateKeyEncryption = privateKeyEncryption;
        this.publicKeyEncryption = publicKeyEncryption;
    }

    @Generated
    public RsaKeyPairCipherExecutor() {
        this.privateKeySigning = null;
        this.publicKeySigning = null;
        this.privateKeyEncryption = null;
        this.publicKeyEncryption = null;
    }
}

