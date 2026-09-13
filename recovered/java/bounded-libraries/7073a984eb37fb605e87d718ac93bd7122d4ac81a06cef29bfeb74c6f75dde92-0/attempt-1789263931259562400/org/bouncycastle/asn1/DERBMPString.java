/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.asn1;

import org.bouncycastle.asn1.ASN1BMPString;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1TaggedObject;

public class DERBMPString
extends ASN1BMPString {
    public static DERBMPString getInstance(Object object) {
        if (object == null || object instanceof DERBMPString) {
            return (DERBMPString)object;
        }
        if (object instanceof ASN1BMPString) {
            return new DERBMPString(((ASN1BMPString)object).string);
        }
        if (object instanceof byte[]) {
            try {
                return (DERBMPString)DERBMPString.fromByteArray((byte[])object);
            }
            catch (Exception exception) {
                throw new IllegalArgumentException("encoding error in getInstance: " + exception.toString());
            }
        }
        throw new IllegalArgumentException("illegal object in getInstance: " + object.getClass().getName());
    }

    public static DERBMPString getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        ASN1Primitive aSN1Primitive = aSN1TaggedObject.getObject();
        if (bl || aSN1Primitive instanceof DERBMPString) {
            return DERBMPString.getInstance(aSN1Primitive);
        }
        return new DERBMPString(ASN1OctetString.getInstance(aSN1Primitive).getOctets());
    }

    public DERBMPString(String string) {
        super(string);
    }

    DERBMPString(byte[] byArray) {
        super(byArray);
    }

    DERBMPString(char[] cArray) {
        super(cArray);
    }
}

