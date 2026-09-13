/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.asn1;

import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1T61String;
import org.bouncycastle.asn1.ASN1TaggedObject;

public class DERT61String
extends ASN1T61String {
    public static DERT61String getInstance(Object object) {
        if (object == null || object instanceof DERT61String) {
            return (DERT61String)object;
        }
        if (object instanceof ASN1T61String) {
            return new DERT61String(((ASN1T61String)object).contents, false);
        }
        if (object instanceof byte[]) {
            try {
                return (DERT61String)DERT61String.fromByteArray((byte[])object);
            }
            catch (Exception exception) {
                throw new IllegalArgumentException("encoding error in getInstance: " + exception.toString());
            }
        }
        throw new IllegalArgumentException("illegal object in getInstance: " + object.getClass().getName());
    }

    public static DERT61String getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        ASN1Primitive aSN1Primitive = aSN1TaggedObject.getObject();
        if (bl || aSN1Primitive instanceof DERT61String) {
            return DERT61String.getInstance(aSN1Primitive);
        }
        return new DERT61String(ASN1OctetString.getInstance(aSN1Primitive).getOctets(), true);
    }

    public DERT61String(String string) {
        super(string);
    }

    public DERT61String(byte[] byArray) {
        this(byArray, true);
    }

    DERT61String(byte[] byArray, boolean bl) {
        super(byArray, bl);
    }
}

