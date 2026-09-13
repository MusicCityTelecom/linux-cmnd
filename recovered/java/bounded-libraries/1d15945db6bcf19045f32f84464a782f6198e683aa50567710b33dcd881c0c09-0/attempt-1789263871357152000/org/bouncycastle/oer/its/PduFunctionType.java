/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Integer
 */
package org.bouncycastle.oer.its;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1Integer;

public class PduFunctionType
extends ASN1Integer {
    public static final PduFunctionType tlsHandshake = new PduFunctionType(1L);
    public static final PduFunctionType iso21177ExtendedAuth = new PduFunctionType(2L);

    public PduFunctionType(long l) {
        super(l);
    }

    public PduFunctionType(BigInteger bigInteger) {
        super(bigInteger);
    }

    public PduFunctionType(byte[] byArray) {
        super(byArray);
    }

    public static PduFunctionType getInstance(Object object) {
        if (object instanceof PduFunctionType) {
            return (PduFunctionType)((Object)object);
        }
        if (object instanceof ASN1Integer) {
            return new PduFunctionType(((ASN1Integer)object).getValue());
        }
        ASN1Integer aSN1Integer = ASN1Integer.getInstance((Object)object);
        return PduFunctionType.getInstance(aSN1Integer);
    }
}

