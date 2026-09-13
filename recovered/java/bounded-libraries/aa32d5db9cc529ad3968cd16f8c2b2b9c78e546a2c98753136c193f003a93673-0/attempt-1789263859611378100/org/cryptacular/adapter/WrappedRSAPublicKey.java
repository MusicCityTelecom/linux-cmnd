/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.crypto.params.RSAKeyParameters
 */
package org.cryptacular.adapter;

import java.math.BigInteger;
import java.security.interfaces.RSAPublicKey;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.cryptacular.adapter.AbstractWrappedRSAKey;

public class WrappedRSAPublicKey
extends AbstractWrappedRSAKey<RSAKeyParameters>
implements RSAPublicKey {
    public WrappedRSAPublicKey(RSAKeyParameters wrappedKey) {
        super(wrappedKey);
    }

    @Override
    public BigInteger getPublicExponent() {
        return ((RSAKeyParameters)this.delegate).getExponent();
    }
}

