/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1EncodableVector
 *  org.bouncycastle.asn1.ASN1Integer
 *  org.bouncycastle.asn1.ASN1Object
 *  org.bouncycastle.asn1.ASN1OctetString
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1Sequence
 *  org.bouncycastle.asn1.DEROctetString
 *  org.bouncycastle.asn1.DERSequence
 *  org.bouncycastle.util.Arrays
 */
package org.bouncycastle.asn1.cms;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.util.Arrays;

public class CCMParameters
extends ASN1Object {
    private byte[] nonce;
    private int icvLen;

    public static CCMParameters getInstance(Object object) {
        if (object instanceof CCMParameters) {
            return (CCMParameters)((Object)object);
        }
        if (object != null) {
            return new CCMParameters(ASN1Sequence.getInstance((Object)object));
        }
        return null;
    }

    private CCMParameters(ASN1Sequence aSN1Sequence) {
        this.nonce = ASN1OctetString.getInstance((Object)aSN1Sequence.getObjectAt(0)).getOctets();
        this.icvLen = aSN1Sequence.size() == 2 ? ASN1Integer.getInstance((Object)aSN1Sequence.getObjectAt(1)).intValueExact() : 12;
    }

    public CCMParameters(byte[] byArray, int n) {
        this.nonce = Arrays.clone((byte[])byArray);
        this.icvLen = n;
    }

    public byte[] getNonce() {
        return Arrays.clone((byte[])this.nonce);
    }

    public int getIcvLen() {
        return this.icvLen;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(2);
        aSN1EncodableVector.add((ASN1Encodable)new DEROctetString(this.nonce));
        if (this.icvLen != 12) {
            aSN1EncodableVector.add((ASN1Encodable)new ASN1Integer((long)this.icvLen));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}

