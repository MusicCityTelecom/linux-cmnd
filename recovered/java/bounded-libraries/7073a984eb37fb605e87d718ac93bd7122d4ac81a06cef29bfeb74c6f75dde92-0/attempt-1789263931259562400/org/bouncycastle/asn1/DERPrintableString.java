/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.asn1;

import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1PrintableString;
import org.bouncycastle.asn1.ASN1TaggedObject;

public class DERPrintableString
extends ASN1PrintableString {
    public static DERPrintableString getInstance(Object object) {
        if (object == null || object instanceof DERPrintableString) {
            return (DERPrintableString)object;
        }
        if (object instanceof ASN1PrintableString) {
            return new DERPrintableString(((ASN1PrintableString)object).contents, false);
        }
        if (object instanceof byte[]) {
            try {
                return (DERPrintableString)DERPrintableString.fromByteArray((byte[])object);
            }
            catch (Exception exception) {
                throw new IllegalArgumentException("encoding error in getInstance: " + exception.toString());
            }
        }
        throw new IllegalArgumentException("illegal object in getInstance: " + object.getClass().getName());
    }

    public static DERPrintableString getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        ASN1Primitive aSN1Primitive = aSN1TaggedObject.getObject();
        if (bl || aSN1Primitive instanceof DERPrintableString) {
            return DERPrintableString.getInstance(aSN1Primitive);
        }
        return new DERPrintableString(ASN1OctetString.getInstance(aSN1Primitive).getOctets(), true);
    }

    public DERPrintableString(String string) {
        this(string, false);
    }

    public DERPrintableString(String string, boolean bl) {
        super(string, bl);
    }

    DERPrintableString(byte[] byArray, boolean bl) {
        super(byArray, bl);
    }
}

