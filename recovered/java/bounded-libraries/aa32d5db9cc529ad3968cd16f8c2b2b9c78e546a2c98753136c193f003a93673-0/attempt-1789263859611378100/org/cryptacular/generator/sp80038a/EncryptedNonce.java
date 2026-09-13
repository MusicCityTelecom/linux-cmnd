/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.crypto.BlockCipher
 *  org.bouncycastle.crypto.CipherParameters
 *  org.bouncycastle.crypto.params.KeyParameter
 */
package org.cryptacular.generator.sp80038a;

import javax.crypto.SecretKey;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.cryptacular.generator.LimitException;
import org.cryptacular.generator.Nonce;
import org.cryptacular.spec.Spec;
import org.cryptacular.util.NonceUtil;

public class EncryptedNonce
implements Nonce {
    private final BlockCipher cipher;
    private final SecretKey key;

    public EncryptedNonce(Spec<BlockCipher> cipherSpec, SecretKey key) {
        this(cipherSpec.newInstance(), key);
    }

    public EncryptedNonce(BlockCipher cipher, SecretKey key) {
        this.cipher = cipher;
        this.key = key;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public byte[] generate() throws LimitException {
        byte[] result = new byte[this.cipher.getBlockSize()];
        byte[] nonce = NonceUtil.randomNonce(result.length);
        BlockCipher blockCipher = this.cipher;
        synchronized (blockCipher) {
            this.cipher.init(true, (CipherParameters)new KeyParameter(this.key.getEncoded()));
            this.cipher.processBlock(nonce, 0, result, 0);
            this.cipher.reset();
        }
        return result;
    }

    @Override
    public int getLength() {
        return this.cipher.getBlockSize();
    }
}

