/*
 * Decompiled with CFR 0.152.
 */
package org.cryptacular.bean;

import java.security.PublicKey;
import org.cryptacular.EncodingException;
import org.cryptacular.bean.FactoryBean;
import org.cryptacular.util.ByteUtil;
import org.cryptacular.util.KeyPairUtil;
import org.cryptacular.util.PemUtil;

public class PemBasedPublicKeyFactoryBean
implements FactoryBean<PublicKey> {
    private String encodedKey;

    public PemBasedPublicKeyFactoryBean() {
    }

    public PemBasedPublicKeyFactoryBean(String pemEncodedKey) {
        this.setEncodedKey(pemEncodedKey);
    }

    public String getEncodedKey() {
        return this.encodedKey;
    }

    public void setEncodedKey(String pemEncodedKey) {
        if (!PemUtil.isPem(ByteUtil.toBytes(pemEncodedKey))) {
            throw new IllegalArgumentException("Data is not PEM encoded.");
        }
        this.encodedKey = pemEncodedKey;
    }

    @Override
    public PublicKey newInstance() throws EncodingException {
        return KeyPairUtil.decodePublicKey(PemUtil.decode(this.encodedKey));
    }
}

