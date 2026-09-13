/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.crypto.params.DSAPrivateKeyParameters
 */
package org.cryptacular.adapter;

import java.math.BigInteger;
import java.security.interfaces.DSAPrivateKey;
import org.bouncycastle.crypto.params.DSAPrivateKeyParameters;
import org.cryptacular.adapter.AbstractWrappedDSAKey;

public class WrappedDSAPrivateKey
extends AbstractWrappedDSAKey<DSAPrivateKeyParameters>
implements DSAPrivateKey {
    public WrappedDSAPrivateKey(DSAPrivateKeyParameters parameters) {
        super(parameters);
    }

    @Override
    public BigInteger getX() {
        return ((DSAPrivateKeyParameters)this.delegate).getX();
    }
}

