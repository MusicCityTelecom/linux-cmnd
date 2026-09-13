/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.asn1;

import org.bouncycastle.asn1.ASN1GraphicString;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1TaggedObject;

public class DERGraphicString
extends ASN1GraphicString {
    public static DERGraphicString getInstance(Object object) {
        if (object == null || object instanceof DERGraphicString) {
            return (DERGraphicString)object;
        }
        if (object instanceof ASN1GraphicString) {
            return new DERGraphicString(((ASN1GraphicString)object).contents, false);
        }
        if (object instanceof byte[]) {
            try {
                return (DERGraphicString)DERGraphicString.fromByteArray((byte[])object);
            }
            catch (Exception exception) {
                throw new IllegalArgumentException("encoding error in getInstance: " + exception.toString());
            }
        }
        throw new IllegalArgumentException("illegal object in getInstance: " + object.getClass().getName());
    }

    public static DERGraphicString getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        ASN1Primitive aSN1Primitive = aSN1TaggedObject.getObject();
        if (bl || aSN1Primitive instanceof DERGraphicString) {
            return DERGraphicString.getInstance(aSN1Primitive);
        }
        return new DERGraphicString(ASN1OctetString.getInstance(aSN1Primitive).getOctets());
    }

    public DERGraphicString(byte[] byArray) {
        this(byArray, true);
    }

    DERGraphicString(byte[] byArray, boolean bl) {
        super(byArray, bl);
    }
}

