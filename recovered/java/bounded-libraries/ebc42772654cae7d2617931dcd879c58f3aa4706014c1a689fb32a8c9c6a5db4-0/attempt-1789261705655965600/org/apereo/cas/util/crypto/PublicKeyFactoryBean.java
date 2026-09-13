/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.bouncycastle.util.io.pem.PemObject
 *  org.bouncycastle.util.io.pem.PemReader
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.config.AbstractFactoryBean
 *  org.springframework.core.io.Resource
 */
package org.apereo.cas.util.crypto;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import lombok.Generated;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.core.io.Resource;

public class PublicKeyFactoryBean
extends AbstractFactoryBean<PublicKey> {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(PublicKeyFactoryBean.class);
    private final Resource resource;
    private final String algorithm;

    public Cipher toCipher() {
        try {
            PublicKey publicKey = (PublicKey)this.getObject();
            if (publicKey != null) {
                Cipher cipher = Cipher.getInstance(this.algorithm);
                cipher.init(1, publicKey);
                LOGGER.trace("Initialized cipher in encrypt-mode via the public key algorithm [{}]", (Object)this.algorithm);
                return cipher;
            }
        }
        catch (Exception e) {
            LOGGER.warn("Cipher could not be initialized. Error [{}]", (Object)e.getMessage());
        }
        return null;
    }

    public Class getObjectType() {
        return PublicKey.class;
    }

    protected PublicKey createInstance() throws Exception {
        PublicKey key = this.readPemPublicKey();
        if (key == null) {
            LOGGER.debug("Key [{}] is not in PEM format. Trying next...", (Object)this.resource);
            key = this.readDERPublicKey();
        }
        return key;
    }

    protected PublicKey readPemPublicKey() throws Exception {
        try (PemReader reader = new PemReader((Reader)new InputStreamReader(this.resource.getInputStream(), StandardCharsets.UTF_8));){
            PemObject pemObject = reader.readPemObject();
            if (pemObject != null) {
                byte[] content = pemObject.getContent();
                X509EncodedKeySpec pubSpec = new X509EncodedKeySpec(content);
                KeyFactory factory = KeyFactory.getInstance(this.algorithm);
                PublicKey publicKey = factory.generatePublic(pubSpec);
                return publicKey;
            }
        }
        return null;
    }

    protected PublicKey readDERPublicKey() throws Exception {
        LOGGER.debug("Creating public key instance from [{}] using [{}]", (Object)this.resource.getFilename(), (Object)this.algorithm);
        try (InputStream pubKey = this.resource.getInputStream();){
            byte[] bytes = new byte[(int)this.resource.contentLength()];
            pubKey.read(bytes);
            X509EncodedKeySpec pubSpec = new X509EncodedKeySpec(bytes);
            KeyFactory factory = KeyFactory.getInstance(this.algorithm);
            PublicKey publicKey = factory.generatePublic(pubSpec);
            return publicKey;
        }
    }

    @Generated
    public String toString() {
        return "PublicKeyFactoryBean(super=" + super.toString() + ", resource=" + this.resource + ", algorithm=" + this.algorithm + ")";
    }

    @Generated
    public Resource getResource() {
        return this.resource;
    }

    @Generated
    public String getAlgorithm() {
        return this.algorithm;
    }

    @Generated
    public PublicKeyFactoryBean(Resource resource, String algorithm) {
        this.resource = resource;
        this.algorithm = algorithm;
    }
}

