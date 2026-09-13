/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.asn1;

import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.ASN1UniversalString;

public class DERUniversalString
extends ASN1UniversalString {
    public static DERUniversalString getInstance(Object object) {
        if (object == null || object instanceof DERUniversalString) {
            return (DERUniversalString)object;
        }
        if (object instanceof ASN1UniversalString) {
            return new DERUniversalString(((ASN1UniversalString)object).contents, false);
        }
        if (object instanceof byte[]) {
            try {
                return (DERUniversalString)DERUniversalString.fromByteArray((byte[])object);
            }
            catch (Exception exception) {
                throw new IllegalArgumentException("encoding error getInstance: " + exception.toString());
            }
        }
        throw new IllegalArgumentException("illegal object in getInstance: " + object.getClass().getName());
    }

    public static DERUniversalString getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        ASN1Primitive aSN1Primitive = aSN1TaggedObject.getObject();
        if (bl || aSN1Primitive instanceof DERUniversalString) {
            return DERUniversalString.getInstance(aSN1Primitive);
        }
        return new DERUniversalString(ASN1OctetString.getInstance(aSN1Primitive).getOctets(), true);
    }

    public DERUniversalString(byte[] byArray) {
        this(byArray, true);
    }

    DERUniversalString(byte[] byArray, boolean bl) {
        super(byArray, bl);
    }
}

