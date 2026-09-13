/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.crypto.prng.drbg.SP80090DRBG
 */
package org.cryptacular.generator.sp80038a;

import org.bouncycastle.crypto.prng.drbg.SP80090DRBG;
import org.cryptacular.generator.LimitException;
import org.cryptacular.generator.Nonce;
import org.cryptacular.util.NonceUtil;

public class RBGNonce
implements Nonce {
    private final int length;
    private final SP80090DRBG rbg;

    public RBGNonce() {
        this(16);
    }

    public RBGNonce(int length) {
        if (length < 1) {
            throw new IllegalArgumentException("Length must be positive");
        }
        this.length = length;
        this.rbg = NonceUtil.newRBG(length);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public byte[] generate() throws LimitException {
        byte[] random = new byte[this.length];
        SP80090DRBG sP80090DRBG = this.rbg;
        synchronized (sP80090DRBG) {
            this.rbg.generate(random, null, false);
        }
        return random;
    }

    @Override
    public int getLength() {
        return this.length;
    }
}

