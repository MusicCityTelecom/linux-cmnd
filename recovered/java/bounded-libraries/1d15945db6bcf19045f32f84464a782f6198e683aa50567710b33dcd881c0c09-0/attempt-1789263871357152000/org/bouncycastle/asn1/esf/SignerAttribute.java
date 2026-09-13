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
 *  org.bouncycastle.asn1.DERSequence
 *  org.bouncycastle.asn1.DERTaggedObject
 *  org.bouncycastle.asn1.x509.Attribute
 *  org.bouncycastle.asn1.x509.AttributeCertificate
 */
package org.bouncycastle.asn1.esf;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.x509.Attribute;
import org.bouncycastle.asn1.x509.AttributeCertificate;

public class SignerAttribute
extends ASN1Object {
    private Object[] values;

    public static SignerAttribute getInstance(Object object) {
        if (object instanceof SignerAttribute) {
            return (SignerAttribute)((Object)object);
        }
        if (object != null) {
            return new SignerAttribute(ASN1Sequence.getInstance((Object)object));
        }
        return null;
    }

    private SignerAttribute(ASN1Sequence aSN1Sequence) {
        int n = 0;
        this.values = new Object[aSN1Sequence.size()];
        Enumeration enumeration = aSN1Sequence.getObjects();
        while (enumeration.hasMoreElements()) {
            ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance(enumeration.nextElement());
            if (aSN1TaggedObject.getTagNo() == 0) {
                ASN1Sequence aSN1Sequence2 = ASN1Sequence.getInstance((ASN1TaggedObject)aSN1TaggedObject, (boolean)true);
                Attribute[] attributeArray = new Attribute[aSN1Sequence2.size()];
                for (int i = 0; i != attributeArray.length; ++i) {
                    attributeArray[i] = Attribute.getInstance((Object)aSN1Sequence2.getObjectAt(i));
                }
                this.values[n] = attributeArray;
            } else if (aSN1TaggedObject.getTagNo() == 1) {
                this.values[n] = AttributeCertificate.getInstance((Object)ASN1Sequence.getInstance((ASN1TaggedObject)aSN1TaggedObject, (boolean)true));
            } else {
                throw new IllegalArgumentException("illegal tag: " + aSN1TaggedObject.getTagNo());
            }
            ++n;
        }
    }

    public SignerAttribute(Attribute[] attributeArray) {
        this.values = new Object[1];
        this.values[0] = attributeArray;
    }

    public SignerAttribute(AttributeCertificate attributeCertificate) {
        this.values = new Object[1];
        this.values[0] = attributeCertificate;
    }

    public Object[] getValues() {
        Object[] objectArray = new Object[this.values.length];
        System.arraycopy(this.values, 0, objectArray, 0, objectArray.length);
        return objectArray;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(this.values.length);
        for (int i = 0; i != this.values.length; ++i) {
            if (this.values[i] instanceof Attribute[]) {
                aSN1EncodableVector.add((ASN1Encodable)new DERTaggedObject(0, (ASN1Encodable)new DERSequence((ASN1Encodable[])((Attribute[])this.values[i]))));
                continue;
            }
            aSN1EncodableVector.add((ASN1Encodable)new DERTaggedObject(1, (ASN1Encodable)((AttributeCertificate)this.values[i])));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}

