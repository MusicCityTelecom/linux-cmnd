/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Integer
 */
package org.bouncycastle.oer.its;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.oer.its.RegionInterface;
import org.bouncycastle.oer.its.Uint16;

public class CountryOnly
extends Uint16
implements RegionInterface {
    public CountryOnly(int n) {
        super(n);
    }

    public CountryOnly(BigInteger bigInteger) {
        super(bigInteger);
    }

    public static CountryOnly getInstance(Object object) {
        if (object instanceof CountryOnly) {
            return (CountryOnly)object;
        }
        return new CountryOnly(ASN1Integer.getInstance((Object)object).getValue());
    }
}

