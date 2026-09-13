/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.crypto.Digest
 *  org.bouncycastle.util.Arrays
 */
package org.cryptacular.bean;

import org.bouncycastle.crypto.Digest;
import org.bouncycastle.util.Arrays;
import org.cryptacular.CryptoException;
import org.cryptacular.EncodingException;
import org.cryptacular.StreamException;
import org.cryptacular.bean.AbstractHashBean;
import org.cryptacular.bean.HashBean;
import org.cryptacular.codec.Codec;
import org.cryptacular.spec.Spec;
import org.cryptacular.util.CodecUtil;

public class EncodingHashBean
extends AbstractHashBean
implements HashBean<String> {
    private Spec<Codec> codecSpec;
    private boolean salted;

    public EncodingHashBean() {
    }

    public EncodingHashBean(Spec<Codec> codecSpec, Spec<Digest> digestSpec) {
        this(codecSpec, digestSpec, 1, false);
    }

    public EncodingHashBean(Spec<Codec> codecSpec, Spec<Digest> digestSpec, int iterations) {
        this(codecSpec, digestSpec, iterations, false);
    }

    public EncodingHashBean(Spec<Codec> codecSpec, Spec<Digest> digestSpec, int iterations, boolean salted) {
        super(digestSpec, iterations);
        this.setCodecSpec(codecSpec);
        this.setSalted(salted);
    }

    public Spec<Codec> getCodecSpec() {
        return this.codecSpec;
    }

    public void setCodecSpec(Spec<Codec> codecSpec) {
        this.codecSpec = codecSpec;
    }

    public boolean isSalted() {
        return this.salted;
    }

    public void setSalted(boolean salted) {
        this.salted = salted;
    }

    @Override
    public String hash(Object ... data) throws CryptoException, EncodingException, StreamException {
        if (this.salted) {
            if (data.length < 2 || !(data[data.length - 1] instanceof byte[])) {
                throw new IllegalArgumentException("Last parameter must be a salt of type byte[]");
            }
            byte[] hashSalt = (byte[])data[data.length - 1];
            return CodecUtil.encode(this.codecSpec.newInstance().getEncoder(), Arrays.concatenate((byte[])this.hashInternal(data), (byte[])hashSalt));
        }
        return CodecUtil.encode(this.codecSpec.newInstance().getEncoder(), this.hashInternal(data));
    }

    @Override
    public boolean compare(String hash, Object ... data) throws CryptoException, EncodingException, StreamException {
        return this.compareInternal(CodecUtil.decode(this.codecSpec.newInstance().getDecoder(), hash), data);
    }
}

