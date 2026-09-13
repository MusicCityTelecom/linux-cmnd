/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Integer
 */
package org.bouncycastle.oer.its;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1Integer;

public class OneEightyDegreeInt
extends ASN1Integer {
    private static final BigInteger loweBound = new BigInteger("-1799999999");
    private static final BigInteger upperBound = new BigInteger("1800000000");
    private static final BigInteger unknown = new BigInteger("1800000001");

    public OneEightyDegreeInt(long l) {
        super(l);
        this.assertValue();
    }

    public OneEightyDegreeInt(BigInteger bigInteger) {
        super(bigInteger);
        this.assertValue();
    }

    public OneEightyDegreeInt(byte[] byArray) {
        super(byArray);
        this.assertValue();
    }

    public static OneEightyDegreeInt getInstance(Object object) {
        if (object instanceof OneEightyDegreeInt) {
            return (OneEightyDegreeInt)((Object)object);
        }
        return new OneEightyDegreeInt(ASN1Integer.getInstance((Object)object).getValue());
    }

    public void assertValue() {
        BigInteger bigInteger = this.getValue();
        if (bigInteger.compareTo(loweBound) < 0) {
            throw new IllegalStateException("one eighty degree int cannot be less than -1799999999");
        }
        if (bigInteger.equals(unknown)) {
            return;
        }
        if (bigInteger.compareTo(upperBound) > 0) {
            throw new IllegalStateException("one eighty degree int cannot be greater than 1800000000");
        }
    }
}

