/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.crypto.params.DSAKeyParameters
 */
package org.cryptacular.adapter;

import java.math.BigInteger;
import java.security.interfaces.DSAParams;
import org.bouncycastle.crypto.params.DSAKeyParameters;
import org.cryptacular.adapter.AbstractWrappedKey;

public abstract class AbstractWrappedDSAKey<T extends DSAKeyParameters>
extends AbstractWrappedKey<T> {
    private static final String ALGORITHM = "DSA";

    public AbstractWrappedDSAKey(T wrappedKey) {
        super(wrappedKey);
    }

    public DSAParams getParams() {
        return new DSAParams(){

            @Override
            public BigInteger getP() {
                return ((DSAKeyParameters)AbstractWrappedDSAKey.this.delegate).getParameters().getP();
            }

            @Override
            public BigInteger getQ() {
                return ((DSAKeyParameters)AbstractWrappedDSAKey.this.delegate).getParameters().getQ();
            }

            @Override
            public BigInteger getG() {
                return ((DSAKeyParameters)AbstractWrappedDSAKey.this.delegate).getParameters().getG();
            }
        };
    }

    @Override
    public String getAlgorithm() {
        return ALGORITHM;
    }
}

