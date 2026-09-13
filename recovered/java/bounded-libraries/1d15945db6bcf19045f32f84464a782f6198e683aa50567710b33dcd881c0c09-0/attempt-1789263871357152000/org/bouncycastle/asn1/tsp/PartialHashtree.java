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
package org.bouncycastle.asn1.tsp;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.util.Arrays;

public class PartialHashtree
extends ASN1Object {
    private final ASN1Sequence values;

    public static PartialHashtree getInstance(Object object) {
        if (object instanceof PartialHashtree) {
            return (PartialHashtree)((Object)object);
        }
        if (object != null) {
            return new PartialHashtree(ASN1Sequence.getInstance((Object)object));
        }
        return null;
    }

    private PartialHashtree(ASN1Sequence aSN1Sequence) {
        for (int i = 0; i != aSN1Sequence.size(); ++i) {
            if (aSN1Sequence.getObjectAt(i) instanceof DEROctetString) continue;
            throw new IllegalArgumentException("unknown object in constructor: " + aSN1Sequence.getObjectAt(i).getClass().getName());
        }
        this.values = aSN1Sequence;
    }

    public PartialHashtree(byte[] byArray) {
        this(new byte[][]{byArray});
    }

    public PartialHashtree(byte[][] byArray) {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(byArray.length);
        for (int i = 0; i != byArray.length; ++i) {
            aSN1EncodableVector.add((ASN1Encodable)new DEROctetString(Arrays.clone((byte[])byArray[i])));
        }
        this.values = new DERSequence(aSN1EncodableVector);
    }

    public int getValueCount() {
        return this.values.size();
    }

    public byte[][] getValues() {
        byte[][] byArrayArray = new byte[this.values.size()][];
        for (int i = 0; i != byArrayArray.length; ++i) {
            byArrayArray[i] = Arrays.clone((byte[])ASN1OctetString.getInstance((Object)this.values.getObjectAt(i)).getOctets());
        }
        return byArrayArray;
    }

    public boolean containsHash(byte[] byArray) {
        Enumeration enumeration = this.values.getObjects();
        while (enumeration.hasMoreElements()) {
            byte[] byArray2 = ASN1OctetString.getInstance(enumeration.nextElement()).getOctets();
            if (!Arrays.constantTimeAreEqual((byte[])byArray, (byte[])byArray2)) continue;
            return true;
        }
        return false;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.values;
    }
}

