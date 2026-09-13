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

public class Region
extends Uint16 {
    public Region(int n) {
        super(n);
    }

    public Region(BigInteger bigInteger) {
        super(bigInteger);
    }

    public static Region getInstance(Object object) {
        if (object instanceof Region) {
            return (Region)((Object)object);
        }
        return new Region(ASN1Integer.getInstance((Object)object).getValue());
    }
}

