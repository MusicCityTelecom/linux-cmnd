/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.asn1;

import org.bouncycastle.asn1.ASN1NumericString;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1TaggedObject;

public class DERNumericString
extends ASN1NumericString {
    public static DERNumericString getInstance(Object object) {
        if (object == null || object instanceof DERNumericString) {
            return (DERNumericString)object;
        }
        if (object instanceof ASN1NumericString) {
            return new DERNumericString(((ASN1NumericString)object).contents, false);
        }
        if (object instanceof byte[]) {
            try {
                return (DERNumericString)DERNumericString.fromByteArray((byte[])object);
            }
            catch (Exception exception) {
                throw new IllegalArgumentException("encoding error in getInstance: " + exception.toString());
            }
        }
        throw new IllegalArgumentException("illegal object in getInstance: " + object.getClass().getName());
    }

    public static DERNumericString getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        ASN1Primitive aSN1Primitive = aSN1TaggedObject.getObject();
        if (bl || aSN1Primitive instanceof DERNumericString) {
            return DERNumericString.getInstance(aSN1Primitive);
        }
        return new DERNumericString(ASN1OctetString.getInstance(aSN1Primitive).getOctets(), true);
    }

    public DERNumericString(String string) {
        this(string, false);
    }

    public DERNumericString(String string, boolean bl) {
        super(string, bl);
    }

    DERNumericString(byte[] byArray, boolean bl) {
        super(byArray, bl);
    }
}

