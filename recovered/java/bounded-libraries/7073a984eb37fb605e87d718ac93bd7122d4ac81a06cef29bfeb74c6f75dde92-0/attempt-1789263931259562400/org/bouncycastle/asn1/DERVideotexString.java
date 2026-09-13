/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.asn1;

import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.ASN1VideotexString;

public class DERVideotexString
extends ASN1VideotexString {
    public static DERVideotexString getInstance(Object object) {
        if (object == null || object instanceof DERVideotexString) {
            return (DERVideotexString)object;
        }
        if (object instanceof ASN1VideotexString) {
            return new DERVideotexString(((ASN1VideotexString)object).contents, false);
        }
        if (object instanceof byte[]) {
            try {
                return (DERVideotexString)DERVideotexString.fromByteArray((byte[])object);
            }
            catch (Exception exception) {
                throw new IllegalArgumentException("encoding error in getInstance: " + exception.toString());
            }
        }
        throw new IllegalArgumentException("illegal object in getInstance: " + object.getClass().getName());
    }

    public static DERVideotexString getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        ASN1Primitive aSN1Primitive = aSN1TaggedObject.getObject();
        if (bl || aSN1Primitive instanceof DERVideotexString) {
            return DERVideotexString.getInstance(aSN1Primitive);
        }
        return new DERVideotexString(ASN1OctetString.getInstance(aSN1Primitive).getOctets());
    }

    public DERVideotexString(byte[] byArray) {
        this(byArray, true);
    }

    DERVideotexString(byte[] byArray, boolean bl) {
        super(byArray, bl);
    }
}

