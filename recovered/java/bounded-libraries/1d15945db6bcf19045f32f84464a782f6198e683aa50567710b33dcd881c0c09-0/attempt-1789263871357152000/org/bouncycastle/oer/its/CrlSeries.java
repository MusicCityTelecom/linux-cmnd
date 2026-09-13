/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Integer
 */
package org.bouncycastle.oer.its;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.oer.its.Uint16;

public class CrlSeries
extends Uint16 {
    public CrlSeries(int n) {
        super(n);
    }

    public CrlSeries(BigInteger bigInteger) {
        super(bigInteger);
    }

    public static CrlSeries getInstance(Object object) {
        if (object instanceof CrlSeries) {
            return (CrlSeries)((Object)object);
        }
        return new CrlSeries(ASN1Integer.getInstance((Object)object).getValue());
    }
}

