/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.crypto.params.ECDomainParameters
 *  org.bouncycastle.crypto.params.ECKeyParameters
 *  org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util
 *  org.bouncycastle.math.ec.ECCurve
 */
package org.cryptacular.adapter;

import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECKeyParameters;
import org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.bouncycastle.math.ec.ECCurve;
import org.cryptacular.adapter.AbstractWrappedKey;

public abstract class AbstractWrappedECKey<T extends ECKeyParameters>
extends AbstractWrappedKey<T> {
    private static final String ALGORITHM = "EC";

    public AbstractWrappedECKey(T wrappedKey) {
        super(wrappedKey);
    }

    public ECParameterSpec getParams() {
        ECDomainParameters params = ((ECKeyParameters)this.delegate).getParameters();
        return new ECParameterSpec(EC5Util.convertCurve((ECCurve)params.getCurve(), (byte[])params.getSeed()), new ECPoint(params.getG().normalize().getXCoord().toBigInteger(), params.getG().normalize().getYCoord().toBigInteger()), params.getN(), params.getH().intValue());
    }

    @Override
    public String getAlgorithm() {
        return ALGORITHM;
    }
}

