/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.jcajce.spec;

import java.security.spec.AlgorithmParameterSpec;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.security.auth.Destroyable;
import org.bouncycastle.util.Arrays;

public class HybridValueParameterSpec
implements AlgorithmParameterSpec,
Destroyable {
    private final AtomicBoolean hasBeenDestroyed = new AtomicBoolean(false);
    private volatile byte[] t;
    private volatile AlgorithmParameterSpec baseSpec;

    public HybridValueParameterSpec(byte[] byArray, AlgorithmParameterSpec algorithmParameterSpec) {
        this.t = byArray;
        this.baseSpec = algorithmParameterSpec;
    }

    public byte[] getT() {
        this.checkDestroyed();
        return this.t;
    }

    public AlgorithmParameterSpec getBaseParameterSpec() {
        this.checkDestroyed();
        return this.baseSpec;
    }

    @Override
    public boolean isDestroyed() {
        return this.hasBeenDestroyed.get();
    }

    @Override
    public void destroy() {
        if (!this.hasBeenDestroyed.getAndSet(true)) {
            Arrays.clear(this.t);
            this.t = null;
            this.baseSpec = null;
        }
    }

    private void checkDestroyed() {
        if (this.isDestroyed()) {
            throw new IllegalStateException("spec has been destroyed");
        }
    }
}

