/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1BitString
 *  org.bouncycastle.asn1.ASN1Object
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.DERBitString
 */
package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1BitString;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERBitString;

public class EndEntityType
extends ASN1Object {
    public static final int app = 128;
    public static final int enrol = 64;
    private final ASN1BitString type;

    public EndEntityType(int n) {
        this(new DERBitString(n));
    }

    public EndEntityType(DERBitString dERBitString) {
        this.type = dERBitString;
    }

    public static EndEntityType getInstance(Object object) {
        if (object instanceof EndEntityType) {
            return (EndEntityType)((Object)object);
        }
        if (object != null) {
            return new EndEntityType(DERBitString.getInstance((Object)object));
        }
        return null;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.type;
    }
}

