/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Integer
 */
package org.bouncycastle.oer.its;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1Integer;

public class Psid
extends ASN1Integer {
    public Psid(long l) {
        super(l);
        this.validate();
    }

    public Psid(BigInteger bigInteger) {
        super(bigInteger);
        this.validate();
    }

    public Psid(byte[] byArray) {
        super(byArray);
        this.validate();
    }

    public static Psid getInstance(Object object) {
        if (object instanceof Psid) {
            return (Psid)((Object)object);
        }
        return new Psid(ASN1Integer.getInstance((Object)object).getValue());
    }

    private void validate() {
        if (BigInteger.ZERO.compareTo(this.getValue()) >= 0) {
            throw new IllegalStateException("psid must be greater than zero");
        }
    }
}

