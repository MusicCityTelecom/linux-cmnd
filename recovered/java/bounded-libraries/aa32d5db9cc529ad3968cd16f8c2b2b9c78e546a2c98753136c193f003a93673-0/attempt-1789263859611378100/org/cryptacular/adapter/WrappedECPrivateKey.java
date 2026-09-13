/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.crypto.params.ECPrivateKeyParameters
 */
package org.cryptacular.adapter;

import java.math.BigInteger;
import java.security.interfaces.ECPrivateKey;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.cryptacular.adapter.AbstractWrappedECKey;

public class WrappedECPrivateKey
extends AbstractWrappedECKey<ECPrivateKeyParameters>
implements ECPrivateKey {
    public WrappedECPrivateKey(ECPrivateKeyParameters wrappedKey) {
        super(wrappedKey);
    }

    @Override
    public BigInteger getS() {
        return ((ECPrivateKeyParameters)this.delegate).getD();
    }
}

