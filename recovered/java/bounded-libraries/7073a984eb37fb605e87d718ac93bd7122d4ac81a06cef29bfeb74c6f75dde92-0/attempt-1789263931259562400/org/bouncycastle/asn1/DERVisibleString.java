/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.asn1;

import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.ASN1VisibleString;

public class DERVisibleString
extends ASN1VisibleString {
    public static DERVisibleString getInstance(Object object) {
        if (object == null || object instanceof DERVisibleString) {
            return (DERVisibleString)object;
        }
        if (object instanceof ASN1VisibleString) {
            return new DERVisibleString(((ASN1VisibleString)object).contents, false);
        }
        if (object instanceof byte[]) {
            try {
                return (DERVisibleString)DERVisibleString.fromByteArray((byte[])object);
            }
            catch (Exception exception) {
                throw new IllegalArgumentException("encoding error in getInstance: " + exception.toString());
            }
        }
        throw new IllegalArgumentException("illegal object in getInstance: " + object.getClass().getName());
    }

    public static DERVisibleString getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        ASN1Primitive aSN1Primitive = aSN1TaggedObject.getObject();
        if (bl || aSN1Primitive instanceof DERVisibleString) {
            return DERVisibleString.getInstance(aSN1Primitive);
        }
        return new DERVisibleString(ASN1OctetString.getInstance(aSN1Primitive).getOctets(), true);
    }

    public DERVisibleString(String string) {
        super(string);
    }

    DERVisibleString(byte[] byArray, boolean bl) {
        super(byArray, bl);
    }
}

