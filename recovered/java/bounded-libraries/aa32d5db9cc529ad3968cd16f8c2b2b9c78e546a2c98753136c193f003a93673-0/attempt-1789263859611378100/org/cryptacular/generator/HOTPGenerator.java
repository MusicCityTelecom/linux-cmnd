/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.crypto.Digest
 *  org.bouncycastle.crypto.digests.SHA1Digest
 */
package org.cryptacular.generator;

import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.cryptacular.generator.AbstractOTPGenerator;

public class HOTPGenerator
extends AbstractOTPGenerator {
    public int generate(byte[] key, long count) {
        return this.generateInternal(key, count);
    }

    @Override
    protected Digest getDigest() {
        return new SHA1Digest();
    }
}

