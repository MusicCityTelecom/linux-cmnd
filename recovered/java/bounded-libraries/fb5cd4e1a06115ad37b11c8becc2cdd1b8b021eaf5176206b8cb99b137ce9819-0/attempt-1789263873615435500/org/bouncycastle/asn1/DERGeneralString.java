/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.asn1;

import org.bouncycastle.asn1.ASN1GeneralString;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1TaggedObject;

public class DERGeneralString
extends ASN1GeneralString {
    public static DERGeneralString getInstance(Object object) {
        if (object == null || object instanceof DERGeneralString) {
            return (DERGeneralString)object;
        }
        if (object instanceof ASN1GeneralString) {
            return new DERGeneralString(((ASN1GeneralString)object).contents, false);
        }
        if (object instanceof byte[]) {
            try {
                return (DERGeneralString)DERGeneralString.fromByteArray((byte[])object);
            }
            catch (Exception exception) {
                throw new IllegalArgumentException("encoding error in getInstance: " + exception.toString());
            }
        }
        throw new IllegalArgumentException("illegal object in getInstance: " + object.getClass().getName());
    }

    public static DERGeneralString getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        ASN1Primitive aSN1Primitive = aSN1TaggedObject.getObject();
        if (bl || aSN1Primitive instanceof DERGeneralString) {
            return DERGeneralString.getInstance(aSN1Primitive);
        }
        return new DERGeneralString(ASN1OctetString.getInstance(aSN1Primitive).getOctets(), true);
    }

    public DERGeneralString(String string) {
        super(string);
    }

    DERGeneralString(byte[] byArray, boolean bl) {
        super(byArray, bl);
    }
}

