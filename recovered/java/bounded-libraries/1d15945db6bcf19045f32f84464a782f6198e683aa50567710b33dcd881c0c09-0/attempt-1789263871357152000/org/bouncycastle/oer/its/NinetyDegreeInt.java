/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Integer
 */
package org.bouncycastle.oer.its;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1Integer;

public class NinetyDegreeInt
extends ASN1Integer {
    private static final BigInteger loweBound = new BigInteger("-900000000");
    private static final BigInteger upperBound = new BigInteger("900000000");
    private static final BigInteger unknown = new BigInteger("900000001");

    public NinetyDegreeInt(long l) {
        super(l);
        this.assertValue();
    }

    public NinetyDegreeInt(BigInteger bigInteger) {
        super(bigInteger);
        this.assertValue();
    }

    public NinetyDegreeInt(byte[] byArray) {
        super(byArray);
        this.assertValue();
    }

    public static NinetyDegreeInt getInstance(Object object) {
        if (object instanceof NinetyDegreeInt) {
            return (NinetyDegreeInt)((Object)object);
        }
        return new NinetyDegreeInt(ASN1Integer.getInstance((Object)object).getValue());
    }

    public void assertValue() {
        BigInteger bigInteger = this.getValue();
        if (bigInteger.compareTo(loweBound) < 0) {
            throw new IllegalStateException("ninety degree int cannot be less than -900000000");
        }
        if (bigInteger.equals(unknown)) {
            return;
        }
        if (bigInteger.compareTo(upperBound) > 0) {
            throw new IllegalStateException("ninety degree int cannot be greater than 900000000");
        }
    }
}

