/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.crypto.params;

import java.math.BigInteger;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.math.Primes;
import org.bouncycastle.util.BigIntegers;
import org.bouncycastle.util.Properties;

public class RSAKeyParameters
extends AsymmetricKeyParameter {
    private static final BigIntegers.Cache validated = new BigIntegers.Cache();
    private static final BigInteger SMALL_PRIMES_PRODUCT = new BigInteger("8138e8a0fcf3a4e84a771d40fd305d7f4aa59306d7251de54d98af8fe95729a1f73d893fa424cd2edc8636a6c3285e022b0e3866a565ae8108eed8591cd4fe8d2ce86165a978d719ebf647f362d33fca29cd179fb42401cbaf3df0c614056f9c8f3cfd51e474afb6bc6974f78db8aba8e9e517fded658591ab7502bd41849462f", 16);
    private static final BigInteger ONE = BigInteger.valueOf(1L);
    private BigInteger modulus;
    private BigInteger exponent;

    public RSAKeyParameters(boolean bl, BigInteger bigInteger, BigInteger bigInteger2) {
        this(bl, bigInteger, bigInteger2, false);
    }

    public RSAKeyParameters(boolean bl, BigInteger bigInteger, BigInteger bigInteger2, boolean bl2) {
        super(bl);
        if (!bl && (bigInteger2.intValue() & 1) == 0) {
            throw new IllegalArgumentException("RSA publicExponent is even");
        }
        this.modulus = validated.contains(bigInteger) ? bigInteger : this.validate(bigInteger, bl2);
        this.exponent = bigInteger2;
    }

    private BigInteger validate(BigInteger bigInteger, boolean bl) {
        int n;
        if (bl) {
            validated.add(bigInteger);
            return bigInteger;
        }
        if ((bigInteger.intValue() & 1) == 0) {
            throw new IllegalArgumentException("RSA modulus is even");
        }
        if (Properties.isOverrideSet("org.bouncycastle.rsa.allow_unsafe_mod")) {
            return bigInteger;
        }
        int n2 = Properties.asInteger("org.bouncycastle.rsa.max_size", 15360);
        if (n2 < (n = bigInteger.bitLength())) {
            throw new IllegalArgumentException("modulus value out of range");
        }
        if (!bigInteger.gcd(SMALL_PRIMES_PRODUCT).equals(ONE)) {
            throw new IllegalArgumentException("RSA modulus has a small prime factor");
        }
        int n3 = bigInteger.bitLength() / 2;
        int n4 = n3 >= 1536 ? 3 : (n3 >= 1024 ? 4 : (n3 >= 512 ? 7 : 50));
        Primes.MROutput mROutput = Primes.enhancedMRProbablePrimeTest(bigInteger, CryptoServicesRegistrar.getSecureRandom(), n4);
        if (!mROutput.isProvablyComposite()) {
            throw new IllegalArgumentException("RSA modulus is not composite");
        }
        validated.add(bigInteger);
        return bigInteger;
    }

    public BigInteger getModulus() {
        return this.modulus;
    }

    public BigInteger getExponent() {
        return this.exponent;
    }
}

