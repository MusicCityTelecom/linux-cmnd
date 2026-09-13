/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Enumerated
 */
package org.bouncycastle.oer.its;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1Enumerated;

public class CertificateType
extends ASN1Enumerated {
    public static final CertificateType Explicit = new CertificateType(0);
    public static final CertificateType Implicit = new CertificateType(1);

    protected CertificateType(int n) {
        super(n);
    }

    public static CertificateType getInstance(Object object) {
        if (object instanceof CertificateType) {
            return (CertificateType)((Object)object);
        }
        BigInteger bigInteger = ASN1Enumerated.getInstance((Object)object).getValue();
        switch (bigInteger.intValue()) {
            case 0: {
                return Explicit;
            }
            case 1: {
                return Implicit;
            }
        }
        throw new IllegalArgumentException("unaccounted enum value " + bigInteger);
    }
}

