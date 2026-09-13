/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.crypto.StreamCipher
 *  org.bouncycastle.crypto.engines.Grain128Engine
 *  org.bouncycastle.crypto.engines.HC128Engine
 *  org.bouncycastle.crypto.engines.HC256Engine
 *  org.bouncycastle.crypto.engines.ISAACEngine
 *  org.bouncycastle.crypto.engines.RC4Engine
 *  org.bouncycastle.crypto.engines.Salsa20Engine
 *  org.bouncycastle.crypto.engines.VMPCEngine
 */
package org.cryptacular.spec;

import org.bouncycastle.crypto.StreamCipher;
import org.bouncycastle.crypto.engines.Grain128Engine;
import org.bouncycastle.crypto.engines.HC128Engine;
import org.bouncycastle.crypto.engines.HC256Engine;
import org.bouncycastle.crypto.engines.ISAACEngine;
import org.bouncycastle.crypto.engines.RC4Engine;
import org.bouncycastle.crypto.engines.Salsa20Engine;
import org.bouncycastle.crypto.engines.VMPCEngine;
import org.cryptacular.spec.Spec;

public class StreamCipherSpec
implements Spec<StreamCipher> {
    private final String algorithm;

    public StreamCipherSpec(String algName) {
        this.algorithm = algName;
    }

    @Override
    public String getAlgorithm() {
        return this.algorithm;
    }

    @Override
    public StreamCipher newInstance() {
        ISAACEngine cipher;
        if ("Grainv1".equalsIgnoreCase(this.algorithm) || "Grain-v1".equalsIgnoreCase(this.algorithm)) {
            cipher = new ISAACEngine();
        } else if ("Grain128".equalsIgnoreCase(this.algorithm) || "Grain-128".equalsIgnoreCase(this.algorithm)) {
            cipher = new Grain128Engine();
        } else if ("ISAAC".equalsIgnoreCase(this.algorithm)) {
            cipher = new ISAACEngine();
        } else if ("HC128".equalsIgnoreCase(this.algorithm)) {
            cipher = new HC128Engine();
        } else if ("HC256".equalsIgnoreCase(this.algorithm)) {
            cipher = new HC256Engine();
        } else if ("RC4".equalsIgnoreCase(this.algorithm)) {
            cipher = new RC4Engine();
        } else if ("Salsa20".equalsIgnoreCase(this.algorithm)) {
            cipher = new Salsa20Engine();
        } else if ("VMPC".equalsIgnoreCase(this.algorithm)) {
            cipher = new VMPCEngine();
        } else {
            throw new IllegalStateException("Unsupported cipher algorithm " + this.algorithm);
        }
        return cipher;
    }

    public String toString() {
        return this.algorithm;
    }
}

