/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.util.crypto.CipherExecutor
 *  org.bouncycastle.jce.provider.BouncyCastleProvider
 *  org.jose4j.keys.AesKey
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.core.io.AbstractResource
 *  org.springframework.core.io.Resource
 */
package org.apereo.cas.util.cipher;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.util.EncodingUtils;
import org.apereo.cas.util.ResourceUtils;
import org.apereo.cas.util.crypto.CipherExecutor;
import org.apereo.cas.util.crypto.PrivateKeyFactoryBean;
import org.apereo.cas.util.crypto.PublicKeyFactoryBean;
import org.apereo.cas.util.function.FunctionUtils;
import org.apereo.cas.util.jwt.JsonWebTokenSigner;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jose4j.keys.AesKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.Resource;

public abstract class AbstractCipherExecutor<T, R>
implements CipherExecutor<T, R> {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCipherExecutor.class);
    private static final BigInteger RSA_PUBLIC_KEY_EXPONENT = BigInteger.valueOf(65537L);
    private Key signingKey;
    private Map<String, Object> customHeaders = new LinkedHashMap<String, Object>();

    public static PrivateKey extractPrivateKeyFromResource(String signingSecretKey) {
        return (PrivateKey)FunctionUtils.doUnchecked(() -> {
            LOGGER.debug("Attempting to extract private key...");
            AbstractResource resource = ResourceUtils.getResourceFrom(signingSecretKey);
            PrivateKeyFactoryBean factory = new PrivateKeyFactoryBean();
            factory.setAlgorithm("RSA");
            factory.setLocation((Resource)resource);
            factory.setSingleton(false);
            return (PrivateKey)factory.getObject();
        });
    }

    public static PublicKey extractPublicKeyFromResource(String secretKeyToUse) {
        return (PublicKey)FunctionUtils.doUnchecked(() -> {
            LOGGER.debug("Attempting to extract public key from [{}]...", (Object)secretKeyToUse);
            AbstractResource resource = ResourceUtils.getResourceFrom(secretKeyToUse);
            PublicKeyFactoryBean factory = new PublicKeyFactoryBean((Resource)resource, "RSA");
            factory.setSingleton(false);
            return (PublicKey)factory.getObject();
        });
    }

    public boolean isEnabled() {
        return this.signingKey != null;
    }

    protected byte[] sign(byte[] value, Key signingKey) {
        if (signingKey == null) {
            return value;
        }
        return this.signWith(value, this.getSigningAlgorithmFor(signingKey));
    }

    protected byte[] signWith(byte[] value, String algHeaderValue) {
        return this.signWith(value, algHeaderValue, this.signingKey);
    }

    protected byte[] signWith(byte[] value, String algHeaderValue, Key key) {
        return ((JsonWebTokenSigner)((JsonWebTokenSigner.JsonWebTokenSignerBuilder)((JsonWebTokenSigner.JsonWebTokenSignerBuilder)((JsonWebTokenSigner.JsonWebTokenSignerBuilder)JsonWebTokenSigner.builder().key(key)).headers(this.customHeaders)).algorithm(algHeaderValue)).build()).sign(value);
    }

    protected void configureSigningKey(String signingSecretKey) {
        try {
            if (ResourceUtils.doesResourceExist(signingSecretKey)) {
                this.configureSigningKeyFromPrivateKeyResource(signingSecretKey);
            }
        }
        finally {
            if (this.signingKey == null) {
                this.setSigningKey((Key)new AesKey(signingSecretKey.getBytes(StandardCharsets.UTF_8)));
                LOGGER.trace("Created signing key instance [{}] based on provided secret key", (Object)this.signingKey.getClass().getSimpleName());
            }
        }
    }

    protected void configureSigningKeyFromPrivateKeyResource(String signingSecretKey) {
        PrivateKey object = AbstractCipherExecutor.extractPrivateKeyFromResource(signingSecretKey);
        LOGGER.trace("Located signing key resource [{}]", (Object)signingSecretKey);
        this.setSigningKey(object);
    }

    protected byte[] verifySignature(byte[] value, Key activeSigningKey) {
        if (activeSigningKey == null) {
            return value;
        }
        try {
            if (activeSigningKey instanceof RSAPrivateKey) {
                RSAPrivateKey privKey = (RSAPrivateKey)RSAPrivateKey.class.cast(activeSigningKey);
                RSAPublicKeySpec keySpec = new RSAPublicKeySpec(privKey.getModulus(), RSA_PUBLIC_KEY_EXPONENT);
                PublicKey pubKey = KeyFactory.getInstance("RSA").generatePublic(keySpec);
                return EncodingUtils.verifyJwsSignature((Key)pubKey, value);
            }
            return EncodingUtils.verifyJwsSignature(activeSigningKey, value);
        }
        catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    protected String getSigningAlgorithmFor(Key signingKey) {
        return "RSA".equalsIgnoreCase(signingKey.getAlgorithm()) ? "RS512" : "HS512";
    }

    @Generated
    public void setSigningKey(Key signingKey) {
        this.signingKey = signingKey;
    }

    @Generated
    public void setCustomHeaders(Map<String, Object> customHeaders) {
        this.customHeaders = customHeaders;
    }

    @Generated
    protected AbstractCipherExecutor() {
    }

    @Generated
    public Key getSigningKey() {
        return this.signingKey;
    }

    @Generated
    public Map<String, Object> getCustomHeaders() {
        return this.customHeaders;
    }

    static {
        Security.addProvider((Provider)new BouncyCastleProvider());
    }
}

