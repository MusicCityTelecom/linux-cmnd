/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.crypto.sphincsplus;

import org.bouncycastle.pqc.crypto.sphincsplus.PK;
import org.bouncycastle.pqc.crypto.sphincsplus.SK;
import org.bouncycastle.pqc.crypto.sphincsplus.SPHINCSPlusKeyParameters;
import org.bouncycastle.pqc.crypto.sphincsplus.SPHINCSPlusParameters;
import org.bouncycastle.util.Arrays;

public class SPHINCSPlusPrivateKeyParameters
extends SPHINCSPlusKeyParameters {
    final SK sk;
    final PK pk;

    public SPHINCSPlusPrivateKeyParameters(SPHINCSPlusParameters sPHINCSPlusParameters, byte[] byArray) {
        super(true, sPHINCSPlusParameters);
        int n = sPHINCSPlusParameters.getEngine().N;
        if (byArray.length != 4 * n) {
            throw new IllegalArgumentException("private key encoding does not match parameters");
        }
        this.sk = new SK(Arrays.copyOfRange(byArray, 0, n), Arrays.copyOfRange(byArray, n, 2 * n));
        this.pk = new PK(Arrays.copyOfRange(byArray, 2 * n, 3 * n), Arrays.copyOfRange(byArray, 3 * n, 4 * n));
    }

    SPHINCSPlusPrivateKeyParameters(SPHINCSPlusParameters sPHINCSPlusParameters, SK sK, PK pK) {
        super(true, sPHINCSPlusParameters);
        this.sk = sK;
        this.pk = pK;
    }

    public byte[] getSeed() {
        return Arrays.clone(this.sk.seed);
    }

    public byte[] getPrf() {
        return Arrays.clone(this.sk.prf);
    }

    public byte[] getPublicSeed() {
        return Arrays.clone(this.pk.seed);
    }

    public byte[] getPublicKey() {
        return Arrays.concatenate(this.pk.seed, this.pk.root);
    }

    public byte[] getEncoded() {
        return Arrays.concatenate(this.sk.seed, this.sk.prf, this.pk.seed, this.pk.root);
    }
}

