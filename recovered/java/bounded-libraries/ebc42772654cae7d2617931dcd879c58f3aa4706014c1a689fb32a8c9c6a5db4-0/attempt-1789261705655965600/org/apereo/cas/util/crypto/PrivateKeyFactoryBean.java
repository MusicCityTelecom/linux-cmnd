/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.bouncycastle.asn1.pkcs.PrivateKeyInfo
 *  org.bouncycastle.jce.provider.BouncyCastleProvider
 *  org.bouncycastle.openssl.PEMKeyPair
 *  org.bouncycastle.openssl.PEMParser
 *  org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.config.AbstractFactoryBean
 *  org.springframework.core.io.Resource
 */
package org.apereo.cas.util.crypto;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;
import lombok.Generated;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.core.io.Resource;

public class PrivateKeyFactoryBean
extends AbstractFactoryBean<PrivateKey> {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(PrivateKeyFactoryBean.class);
    private Resource location;
    private String algorithm;

    public Class getObjectType() {
        return PrivateKey.class;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private PrivateKey readPemPrivateKey() {
        LOGGER.trace("Attempting to read as PEM [{}]", (Object)this.location);
        try (InputStreamReader in = new InputStreamReader(this.location.getInputStream(), StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(in);
             PEMParser pp = new PEMParser((Reader)br);){
            Object object = pp.readObject();
            if (object instanceof PrivateKeyInfo) {
                PrivateKey privateKey = new JcaPEMKeyConverter().getPrivateKey((PrivateKeyInfo)object);
                return privateKey;
            }
            if (!(object instanceof PEMKeyPair)) return null;
            PEMKeyPair pemKeyPair = (PEMKeyPair)object;
            KeyPair kp = new JcaPEMKeyConverter().getKeyPair(pemKeyPair);
            PrivateKey privateKey = kp.getPrivate();
            return privateKey;
        }
        catch (Exception e) {
            LOGGER.debug("Unable to read key", (Throwable)e);
        }
        return null;
    }

    private PrivateKey readDERPrivateKey() {
        PrivateKey privateKey;
        block8: {
            LOGGER.debug("Attempting to read key as DER [{}]", (Object)this.location);
            InputStream privKey = this.location.getInputStream();
            try {
                byte[] bytes = new byte[(int)this.location.contentLength()];
                privKey.read(bytes);
                PKCS8EncodedKeySpec privSpec = new PKCS8EncodedKeySpec(bytes);
                KeyFactory factory = KeyFactory.getInstance(this.algorithm);
                privateKey = factory.generatePrivate(privSpec);
                if (privKey == null) break block8;
            }
            catch (Throwable throwable) {
                try {
                    if (privKey != null) {
                        try {
                            privKey.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                catch (Exception e) {
                    LOGGER.debug("Unable to read key", (Throwable)e);
                    return null;
                }
            }
            privKey.close();
        }
        return privateKey;
    }

    protected PrivateKey createInstance() {
        PrivateKey key = this.readPemPrivateKey();
        if (key == null) {
            LOGGER.debug("Key [{}] is not in PEM format. Trying next...", (Object)this.location);
            key = this.readDERPrivateKey();
        }
        return key;
    }

    @Generated
    public Resource getLocation() {
        return this.location;
    }

    @Generated
    public String getAlgorithm() {
        return this.algorithm;
    }

    @Generated
    public void setLocation(Resource location) {
        this.location = location;
    }

    @Generated
    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    static {
        Security.addProvider((Provider)new BouncyCastleProvider());
    }
}

