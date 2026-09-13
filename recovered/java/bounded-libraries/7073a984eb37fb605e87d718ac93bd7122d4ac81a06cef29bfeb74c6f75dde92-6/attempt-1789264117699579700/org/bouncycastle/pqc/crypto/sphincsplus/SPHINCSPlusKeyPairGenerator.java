/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.crypto.sphincsplus;

import java.security.SecureRandom;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.pqc.crypto.sphincsplus.HT;
import org.bouncycastle.pqc.crypto.sphincsplus.PK;
import org.bouncycastle.pqc.crypto.sphincsplus.SK;
import org.bouncycastle.pqc.crypto.sphincsplus.SPHINCSPlusEngine;
import org.bouncycastle.pqc.crypto.sphincsplus.SPHINCSPlusKeyGenerationParameters;
import org.bouncycastle.pqc.crypto.sphincsplus.SPHINCSPlusParameters;
import org.bouncycastle.pqc.crypto.sphincsplus.SPHINCSPlusPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.sphincsplus.SPHINCSPlusPublicKeyParameters;

public class SPHINCSPlusKeyPairGenerator
implements AsymmetricCipherKeyPairGenerator {
    private SecureRandom random;
    private SPHINCSPlusParameters parameters;

    public void init(KeyGenerationParameters keyGenerationParameters) {
        this.random = keyGenerationParameters.getRandom();
        this.parameters = ((SPHINCSPlusKeyGenerationParameters)keyGenerationParameters).getParameters();
    }

    public AsymmetricCipherKeyPair generateKeyPair() {
        SPHINCSPlusEngine sPHINCSPlusEngine = this.parameters.getEngine();
        SK sK = new SK(this.sec_rand(sPHINCSPlusEngine.N), this.sec_rand(sPHINCSPlusEngine.N));
        byte[] byArray = this.sec_rand(sPHINCSPlusEngine.N);
        PK pK = new PK(byArray, new HT((SPHINCSPlusEngine)sPHINCSPlusEngine, (byte[])sK.seed, (byte[])byArray).htPubKey);
        return new AsymmetricCipherKeyPair(new SPHINCSPlusPublicKeyParameters(this.parameters, pK), new SPHINCSPlusPrivateKeyParameters(this.parameters, sK, pK));
    }

    private byte[] sec_rand(int n) {
        byte[] byArray = new byte[n];
        this.random.nextBytes(byArray);
        return byArray;
    }
}

