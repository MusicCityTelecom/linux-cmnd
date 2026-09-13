/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Integer
 *  org.bouncycastle.asn1.ASN1Object
 *  org.bouncycastle.asn1.ASN1Primitive
 */
package org.bouncycastle.oer.its;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;

public class Uint8
extends ASN1Object {
    private final int value;

    public Uint8(int n) {
        this.value = this.verify(n);
    }

    public Uint8(BigInteger bigInteger) {
        this.value = bigInteger.intValue();
    }

    public static Uint8 getInstance(Object object) {
        if (object instanceof Uint8) {
            return (Uint8)((Object)object);
        }
        return new Uint8(ASN1Integer.getInstance((Object)object).getValue());
    }

    protected int verify(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Uint16 must be >= 0");
        }
        if (n > 255) {
            throw new IllegalArgumentException("Uint16 must be <= 0xFF");
        }
        return n;
    }

    public ASN1Primitive toASN1Primitive() {
        return new ASN1Integer((long)this.value);
    }
}

