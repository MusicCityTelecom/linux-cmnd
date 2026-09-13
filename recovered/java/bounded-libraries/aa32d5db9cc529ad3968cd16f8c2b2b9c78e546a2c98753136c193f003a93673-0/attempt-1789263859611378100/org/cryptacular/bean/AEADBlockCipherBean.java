/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.crypto.CipherParameters
 *  org.bouncycastle.crypto.modes.AEADBlockCipher
 *  org.bouncycastle.crypto.params.AEADParameters
 *  org.bouncycastle.crypto.params.KeyParameter
 */
package org.cryptacular.bean;

import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import javax.crypto.SecretKey;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.modes.AEADBlockCipher;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.cryptacular.CiphertextHeader;
import org.cryptacular.adapter.AEADBlockCipherAdapter;
import org.cryptacular.bean.AbstractBlockCipherBean;
import org.cryptacular.generator.Nonce;
import org.cryptacular.spec.Spec;

public class AEADBlockCipherBean
extends AbstractBlockCipherBean {
    public static final int MAC_SIZE_BITS = 128;
    private Spec<AEADBlockCipher> blockCipherSpec;

    public AEADBlockCipherBean() {
    }

    public AEADBlockCipherBean(Spec<AEADBlockCipher> blockCipherSpec, KeyStore keyStore, String keyAlias, String keyPassword, Nonce nonce) {
        super(keyStore, keyAlias, keyPassword, nonce);
        this.setBlockCipherSpec(blockCipherSpec);
    }

    public Spec<AEADBlockCipher> getBlockCipherSpec() {
        return this.blockCipherSpec;
    }

    public void setBlockCipherSpec(Spec<AEADBlockCipher> blockCipherSpec) {
        this.blockCipherSpec = blockCipherSpec;
    }

    @Override
    public void encrypt(InputStream input, OutputStream output) {
        if (this.blockCipherSpec.toString().endsWith("CCM")) {
            throw new UnsupportedOperationException("CCM mode ciphers do not support chunked encryption.");
        }
        super.encrypt(input, output);
    }

    @Override
    public void decrypt(InputStream input, OutputStream output) {
        if (this.blockCipherSpec.toString().endsWith("CCM")) {
            throw new UnsupportedOperationException("CCM mode ciphers do not support chunked decryption.");
        }
        super.decrypt(input, output);
    }

    @Override
    protected AEADBlockCipherAdapter newCipher(CiphertextHeader header, boolean mode) {
        AEADBlockCipher cipher = this.blockCipherSpec.newInstance();
        SecretKey key = this.lookupKey(header.getKeyName());
        AEADParameters params = new AEADParameters(new KeyParameter(key.getEncoded()), 128, header.getNonce(), header.encode());
        cipher.init(mode, (CipherParameters)params);
        return new AEADBlockCipherAdapter(cipher);
    }
}

