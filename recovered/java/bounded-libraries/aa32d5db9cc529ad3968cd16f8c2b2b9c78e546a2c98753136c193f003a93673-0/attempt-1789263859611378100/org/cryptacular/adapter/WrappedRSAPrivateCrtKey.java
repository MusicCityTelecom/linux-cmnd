/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters
 */
package org.cryptacular.adapter;

import java.math.BigInteger;
import java.security.interfaces.RSAPrivateCrtKey;
import org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;
import org.cryptacular.adapter.AbstractWrappedRSAKey;

public class WrappedRSAPrivateCrtKey
extends AbstractWrappedRSAKey<RSAPrivateCrtKeyParameters>
implements RSAPrivateCrtKey {
    public WrappedRSAPrivateCrtKey(RSAPrivateCrtKeyParameters parameters) {
        super(parameters);
    }

    @Override
    public BigInteger getPublicExponent() {
        return ((RSAPrivateCrtKeyParameters)this.delegate).getPublicExponent();
    }

    @Override
    public BigInteger getPrimeP() {
        return ((RSAPrivateCrtKeyParameters)this.delegate).getP();
    }

    @Override
    public BigInteger getPrimeQ() {
        return ((RSAPrivateCrtKeyParameters)this.delegate).getQ();
    }

    @Override
    public BigInteger getPrimeExponentP() {
        return ((RSAPrivateCrtKeyParameters)this.delegate).getDP();
    }

    @Override
    public BigInteger getPrimeExponentQ() {
        return ((RSAPrivateCrtKeyParameters)this.delegate).getDQ();
    }

    @Override
    public BigInteger getCrtCoefficient() {
        return ((RSAPrivateCrtKeyParameters)this.delegate).getQInv();
    }

    @Override
    public BigInteger getPrivateExponent() {
        return ((RSAPrivateCrtKeyParameters)this.delegate).getExponent();
    }
}

