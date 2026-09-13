/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.crypto.Digest
 */
package org.cryptacular.bean;

import org.bouncycastle.crypto.Digest;
import org.cryptacular.spec.Spec;
import org.cryptacular.util.HashUtil;

public abstract class AbstractHashBean {
    private Spec<Digest> digestSpec;
    private int iterations = 1;

    public AbstractHashBean() {
    }

    public AbstractHashBean(Spec<Digest> digestSpec, int iterations) {
        this.setDigestSpec(digestSpec);
        this.setIterations(iterations);
    }

    public Spec<Digest> getDigestSpec() {
        return this.digestSpec;
    }

    public void setDigestSpec(Spec<Digest> digestSpec) {
        this.digestSpec = digestSpec;
    }

    public int getIterations() {
        return this.iterations;
    }

    public void setIterations(int iterations) {
        if (iterations < 1) {
            throw new IllegalArgumentException("Iterations must be positive");
        }
        this.iterations = iterations;
    }

    protected byte[] hashInternal(Object ... data) {
        return HashUtil.hash(this.digestSpec.newInstance(), this.iterations, data);
    }

    protected boolean compareInternal(byte[] hash, Object ... data) {
        return HashUtil.compareHash(this.digestSpec.newInstance(), hash, this.iterations, data);
    }
}

