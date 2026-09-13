/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.jcajce.provider.asymmetric.dsa.KeyPairGeneratorSpi
 *  org.bouncycastle.jcajce.provider.asymmetric.ec.KeyPairGeneratorSpi$EC
 *  org.bouncycastle.jcajce.provider.asymmetric.rsa.KeyPairGeneratorSpi
 *  org.bouncycastle.jce.spec.ECNamedCurveGenParameterSpec
 */
package org.cryptacular.generator;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import org.bouncycastle.jcajce.provider.asymmetric.ec.KeyPairGeneratorSpi;
import org.bouncycastle.jcajce.provider.asymmetric.rsa.KeyPairGeneratorSpi;
import org.bouncycastle.jce.spec.ECNamedCurveGenParameterSpec;

public final class KeyPairGenerator {
    private KeyPairGenerator() {
    }

    public static KeyPair generateDSA(SecureRandom random, int bitLength) {
        org.bouncycastle.jcajce.provider.asymmetric.dsa.KeyPairGeneratorSpi generator = new org.bouncycastle.jcajce.provider.asymmetric.dsa.KeyPairGeneratorSpi();
        generator.initialize(bitLength, random);
        return generator.generateKeyPair();
    }

    public static KeyPair generateRSA(SecureRandom random, int bitLength) {
        KeyPairGeneratorSpi generator = new KeyPairGeneratorSpi();
        generator.initialize(bitLength, random);
        return generator.generateKeyPair();
    }

    public static KeyPair generateEC(SecureRandom random, int bitLength) {
        KeyPairGeneratorSpi.EC generator = new KeyPairGeneratorSpi.EC();
        generator.initialize(bitLength, random);
        return generator.generateKeyPair();
    }

    public static KeyPair generateEC(SecureRandom random, String namedCurve) {
        KeyPairGeneratorSpi.EC generator = new KeyPairGeneratorSpi.EC();
        try {
            generator.initialize((AlgorithmParameterSpec)new ECNamedCurveGenParameterSpec(namedCurve), random);
        }
        catch (InvalidAlgorithmParameterException e) {
            throw new IllegalArgumentException("Invalid EC curve " + namedCurve, e);
        }
        return generator.generateKeyPair();
    }
}

