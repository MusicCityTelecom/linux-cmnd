/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1EncodableVector
 *  org.bouncycastle.asn1.ASN1Object
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1Sequence
 *  org.bouncycastle.asn1.ASN1TaggedObject
 *  org.bouncycastle.asn1.ASN1UTF8String
 *  org.bouncycastle.asn1.DERSequence
 *  org.bouncycastle.asn1.DERUTF8String
 */
package org.bouncycastle.asn1.cmp;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.ASN1UTF8String;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERUTF8String;

public class PKIFreeText
extends ASN1Object {
    ASN1Sequence strings;

    public static PKIFreeText getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        return PKIFreeText.getInstance(ASN1Sequence.getInstance((ASN1TaggedObject)aSN1TaggedObject, (boolean)bl));
    }

    public static PKIFreeText getInstance(Object object) {
        if (object instanceof PKIFreeText) {
            return (PKIFreeText)((Object)object);
        }
        if (object != null) {
            return new PKIFreeText(ASN1Sequence.getInstance((Object)object));
        }
        return null;
    }

    private PKIFreeText(ASN1Sequence aSN1Sequence) {
        Enumeration enumeration = aSN1Sequence.getObjects();
        while (enumeration.hasMoreElements()) {
            if (enumeration.nextElement() instanceof ASN1UTF8String) continue;
            throw new IllegalArgumentException("attempt to insert non UTF8 STRING into PKIFreeText");
        }
        this.strings = aSN1Sequence;
    }

    public PKIFreeText(ASN1UTF8String aSN1UTF8String) {
        this.strings = new DERSequence((ASN1Encodable)aSN1UTF8String);
    }

    public PKIFreeText(String string) {
        this((ASN1UTF8String)new DERUTF8String(string));
    }

    public PKIFreeText(ASN1UTF8String[] aSN1UTF8StringArray) {
        this.strings = new DERSequence((ASN1Encodable[])aSN1UTF8StringArray);
    }

    public PKIFreeText(String[] stringArray) {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(stringArray.length);
        for (int i = 0; i < stringArray.length; ++i) {
            aSN1EncodableVector.add((ASN1Encodable)new DERUTF8String(stringArray[i]));
        }
        this.strings = new DERSequence(aSN1EncodableVector);
    }

    public int size() {
        return this.strings.size();
    }

    public DERUTF8String getStringAt(int n) {
        ASN1UTF8String aSN1UTF8String = this.getStringAtUTF8(n);
        return null == aSN1UTF8String || aSN1UTF8String instanceof DERUTF8String ? (DERUTF8String)aSN1UTF8String : new DERUTF8String(aSN1UTF8String.getString());
    }

    public ASN1UTF8String getStringAtUTF8(int n) {
        return (ASN1UTF8String)this.strings.getObjectAt(n);
    }

    public ASN1Primitive toASN1Primitive() {
        return this.strings;
    }
}

