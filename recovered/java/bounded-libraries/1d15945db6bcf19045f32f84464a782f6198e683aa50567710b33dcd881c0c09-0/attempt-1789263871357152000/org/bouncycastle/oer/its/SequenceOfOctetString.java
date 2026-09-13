/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1EncodableVector
 *  org.bouncycastle.asn1.ASN1Object
 *  org.bouncycastle.asn1.ASN1OctetString
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1Sequence
 *  org.bouncycastle.asn1.DEROctetString
 *  org.bouncycastle.asn1.DERSequence
 *  org.bouncycastle.util.Arrays
 */
package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.util.Arrays;

public class SequenceOfOctetString
extends ASN1Object {
    private byte[][] octetStrings;

    private SequenceOfOctetString(ASN1Sequence aSN1Sequence) {
        this.octetStrings = SequenceOfOctetString.toByteArrays(aSN1Sequence);
    }

    public static SequenceOfOctetString getInstance(Object object) {
        if (object instanceof SequenceOfOctetString) {
            return (SequenceOfOctetString)((Object)object);
        }
        if (object != null) {
            return new SequenceOfOctetString(ASN1Sequence.getInstance((Object)object));
        }
        return null;
    }

    static byte[][] toByteArrays(ASN1Sequence aSN1Sequence) {
        byte[][] byArrayArray = new byte[aSN1Sequence.size()][];
        for (int i = 0; i != aSN1Sequence.size(); ++i) {
            byArrayArray[i] = ASN1OctetString.getInstance((Object)aSN1Sequence.getObjectAt(i)).getOctets();
        }
        return byArrayArray;
    }

    public int size() {
        return this.octetStrings.length;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        for (int i = 0; i != this.octetStrings.length; ++i) {
            aSN1EncodableVector.add((ASN1Encodable)new DEROctetString(Arrays.clone((byte[])this.octetStrings[i])));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}

