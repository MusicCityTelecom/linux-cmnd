/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.crypto.Digest
 */
package org.cryptacular.bean;

import org.bouncycastle.crypto.Digest;
import org.cryptacular.CryptoException;
import org.cryptacular.StreamException;
import org.cryptacular.bean.AbstractHashBean;
import org.cryptacular.bean.HashBean;
import org.cryptacular.spec.Spec;

public class SimpleHashBean
extends AbstractHashBean
implements HashBean<byte[]> {
    public SimpleHashBean() {
    }

    public SimpleHashBean(Spec<Digest> digestSpec, int iterations) {
        super(digestSpec, iterations);
    }

    @Override
    public byte[] hash(Object ... data) throws CryptoException, StreamException {
        return this.hashInternal(data);
    }

    @Override
    public boolean compare(byte[] hash, Object ... data) throws CryptoException, StreamException {
        return this.compareInternal(hash, data);
    }
}

