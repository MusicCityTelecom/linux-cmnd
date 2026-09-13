/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.crypto.params.RSAKeyParameters
 */
package org.cryptacular.adapter;

import java.math.BigInteger;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.cryptacular.adapter.AbstractWrappedKey;

public abstract class AbstractWrappedRSAKey<T extends RSAKeyParameters>
extends AbstractWrappedKey<T> {
    private static final String ALGORITHM = "RSA";

    public AbstractWrappedRSAKey(T wrappedKey) {
        super(wrappedKey);
    }

    public BigInteger getModulus() {
        return ((RSAKeyParameters)this.delegate).getModulus();
    }

    @Override
    public String getAlgorithm() {
        return ALGORITHM;
    }
}

