/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.crypto.params.DSAPublicKeyParameters
 */
package org.cryptacular.adapter;

import java.math.BigInteger;
import java.security.interfaces.DSAPublicKey;
import org.bouncycastle.crypto.params.DSAPublicKeyParameters;
import org.cryptacular.adapter.AbstractWrappedDSAKey;

public class WrappedDSAPublicKey
extends AbstractWrappedDSAKey<DSAPublicKeyParameters>
implements DSAPublicKey {
    public WrappedDSAPublicKey(DSAPublicKeyParameters wrappedKey) {
        super(wrappedKey);
    }

    @Override
    public BigInteger getY() {
        return ((DSAPublicKeyParameters)this.delegate).getY();
    }
}

