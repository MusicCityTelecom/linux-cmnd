/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.crypto.BufferedBlockCipher
 *  org.bouncycastle.crypto.CipherParameters
 *  org.bouncycastle.crypto.params.KeyParameter
 *  org.bouncycastle.crypto.params.ParametersWithIV
 */
package org.cryptacular.bean;

import java.security.KeyStore;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.cryptacular.CiphertextHeader;
import org.cryptacular.adapter.BufferedBlockCipherAdapter;
import org.cryptacular.bean.AbstractBlockCipherBean;
import org.cryptacular.generator.Nonce;
import org.cryptacular.spec.Spec;

public class BufferedBlockCipherBean
extends AbstractBlockCipherBean {
    private Spec<BufferedBlockCipher> blockCipherSpec;

    public BufferedBlockCipherBean() {
    }

    public BufferedBlockCipherBean(Spec<BufferedBlockCipher> blockCipherSpec, KeyStore keyStore, String keyAlias, String keyPassword, Nonce nonce) {
        super(keyStore, keyAlias, keyPassword, nonce);
        this.setBlockCipherSpec(blockCipherSpec);
    }

    public Spec<BufferedBlockCipher> getBlockCipherSpec() {
        return this.blockCipherSpec;
    }

    public void setBlockCipherSpec(Spec<BufferedBlockCipher> blockCipherSpec) {
        this.blockCipherSpec = blockCipherSpec;
    }

    @Override
    protected BufferedBlockCipherAdapter newCipher(CiphertextHeader header, boolean mode) {
        BufferedBlockCipher cipher = this.blockCipherSpec.newInstance();
        KeyParameter params = new KeyParameter(this.lookupKey(header.getKeyName()).getEncoded());
        String algName = cipher.getUnderlyingCipher().getAlgorithmName();
        if (algName.endsWith("CBC") || algName.endsWith("OFB") || algName.endsWith("CFB")) {
            params = new ParametersWithIV((CipherParameters)params, header.getNonce());
        }
        cipher.init(mode, (CipherParameters)params);
        return new BufferedBlockCipherAdapter(cipher);
    }
}

