/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.crypto.params.ECPublicKeyParameters
 */
package org.cryptacular.adapter;

import java.security.interfaces.ECPublicKey;
import java.security.spec.ECPoint;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.cryptacular.adapter.AbstractWrappedECKey;

public class WrappedECPublicKey
extends AbstractWrappedECKey<ECPublicKeyParameters>
implements ECPublicKey {
    public WrappedECPublicKey(ECPublicKeyParameters wrappedKey) {
        super(wrappedKey);
    }

    @Override
    public ECPoint getW() {
        return new ECPoint(((ECPublicKeyParameters)this.delegate).getQ().normalize().getXCoord().toBigInteger(), ((ECPublicKeyParameters)this.delegate).getQ().normalize().getYCoord().toBigInteger());
    }
}

