/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.asn1;

import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.ASN1UTF8String;

public class DERUTF8String
extends ASN1UTF8String {
    public static DERUTF8String getInstance(Object object) {
        if (object == null || object instanceof DERUTF8String) {
            return (DERUTF8String)object;
        }
        if (object instanceof ASN1UTF8String) {
            return new DERUTF8String(((ASN1UTF8String)object).contents, false);
        }
        if (object instanceof byte[]) {
            try {
                return (DERUTF8String)DERUTF8String.fromByteArray((byte[])object);
            }
            catch (Exception exception) {
                throw new IllegalArgumentException("encoding error in getInstance: " + exception.toString());
            }
        }
        throw new IllegalArgumentException("illegal object in getInstance: " + object.getClass().getName());
    }

    public static DERUTF8String getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        ASN1Primitive aSN1Primitive = aSN1TaggedObject.getObject();
        if (bl || aSN1Primitive instanceof DERUTF8String) {
            return DERUTF8String.getInstance(aSN1Primitive);
        }
        return new DERUTF8String(ASN1OctetString.getInstance(aSN1Primitive).getOctets(), true);
    }

    public DERUTF8String(String string) {
        super(string);
    }

    DERUTF8String(byte[] byArray, boolean bl) {
        super(byArray, bl);
    }
}

