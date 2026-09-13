/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.crypto.Digest
 */
package org.cryptacular.generator;

import org.bouncycastle.crypto.Digest;
import org.cryptacular.generator.AbstractOTPGenerator;
import org.cryptacular.spec.DigestSpec;
import org.cryptacular.spec.Spec;

public class TOTPGenerator
extends AbstractOTPGenerator {
    private Spec<Digest> digestSpecification = new DigestSpec("SHA1");
    private long currentTime = -1L;
    private int startTime;
    private int timeStep = 30;

    public Spec<Digest> getDigestSpecification() {
        return this.digestSpecification;
    }

    public void setDigestSpecification(Spec<Digest> specification) {
        if ("SHA1".equalsIgnoreCase(specification.getAlgorithm()) || "SHA-1".equalsIgnoreCase(specification.getAlgorithm()) || "SHA256".equalsIgnoreCase(specification.getAlgorithm()) || "SHA-256".equalsIgnoreCase(specification.getAlgorithm()) || "SHA512".equalsIgnoreCase(specification.getAlgorithm()) || "SHA-512".equalsIgnoreCase(specification.getAlgorithm())) {
            this.digestSpecification = specification;
            return;
        }
        throw new IllegalArgumentException("Unsupported digest algorithm " + specification);
    }

    public int getStartTime() {
        return this.startTime;
    }

    public void setStartTime(int seconds) {
        this.startTime = seconds;
    }

    public int getTimeStep() {
        return this.timeStep;
    }

    public void setTimeStep(int seconds) {
        this.timeStep = seconds;
    }

    public int generate(byte[] key) {
        long t = (this.currentTime() - (long)this.startTime) / (long)this.timeStep;
        return this.generateInternal(key, t);
    }

    @Override
    protected Digest getDigest() {
        return this.digestSpecification.newInstance();
    }

    protected void setCurrentTime(long epochSeconds) {
        this.currentTime = epochSeconds;
    }

    protected long currentTime() {
        if (this.currentTime >= 0L) {
            return this.currentTime;
        }
        return System.currentTimeMillis() / 1000L;
    }
}

