/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.asn1;

import org.bouncycastle.asn1.ASN1IA5String;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1TaggedObject;

public class DERIA5String
extends ASN1IA5String {
    public static DERIA5String getInstance(Object object) {
        if (object == null || object instanceof DERIA5String) {
            return (DERIA5String)object;
        }
        if (object instanceof ASN1IA5String) {
            return new DERIA5String(((ASN1IA5String)object).contents, false);
        }
        if (object instanceof byte[]) {
            try {
                return (DERIA5String)DERIA5String.fromByteArray((byte[])object);
            }
            catch (Exception exception) {
                throw new IllegalArgumentException("encoding error in getInstance: " + exception.toString());
            }
        }
        throw new IllegalArgumentException("illegal object in getInstance: " + object.getClass().getName());
    }

    public static DERIA5String getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        ASN1Primitive aSN1Primitive = aSN1TaggedObject.getObject();
        if (bl || aSN1Primitive instanceof DERIA5String) {
            return DERIA5String.getInstance(aSN1Primitive);
        }
        return new DERIA5String(ASN1OctetString.getInstance(aSN1Primitive).getOctets(), true);
    }

    public DERIA5String(String string) {
        this(string, false);
    }

    public DERIA5String(String string, boolean bl) {
        super(string, bl);
    }

    DERIA5String(byte[] byArray, boolean bl) {
        super(byArray, bl);
    }
}

