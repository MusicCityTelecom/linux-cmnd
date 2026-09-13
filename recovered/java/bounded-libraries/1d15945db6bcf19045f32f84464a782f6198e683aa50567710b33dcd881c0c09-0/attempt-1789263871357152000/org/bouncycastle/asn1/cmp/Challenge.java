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
 *  org.bouncycastle.asn1.x509.AlgorithmIdentifier
 */
package org.bouncycastle.asn1.cmp;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class Challenge
extends ASN1Object {
    private AlgorithmIdentifier owf;
    private ASN1OctetString witness;
    private ASN1OctetString challenge;

    private Challenge(ASN1Sequence aSN1Sequence) {
        int n = 0;
        if (aSN1Sequence.size() == 3) {
            this.owf = AlgorithmIdentifier.getInstance((Object)aSN1Sequence.getObjectAt(n++));
        }
        this.witness = ASN1OctetString.getInstance((Object)aSN1Sequence.getObjectAt(n++));
        this.challenge = ASN1OctetString.getInstance((Object)aSN1Sequence.getObjectAt(n));
    }

    public static Challenge getInstance(Object object) {
        if (object instanceof Challenge) {
            return (Challenge)((Object)object);
        }
        if (object != null) {
            return new Challenge(ASN1Sequence.getInstance((Object)object));
        }
        return null;
    }

    public Challenge(byte[] byArray, byte[] byArray2) {
        this(null, byArray, byArray2);
    }

    public Challenge(AlgorithmIdentifier algorithmIdentifier, byte[] byArray, byte[] byArray2) {
        this.owf = algorithmIdentifier;
        this.witness = new DEROctetString(byArray);
        this.challenge = new DEROctetString(byArray2);
    }

    public AlgorithmIdentifier getOwf() {
        return this.owf;
    }

    public byte[] getWitness() {
        return this.witness.getOctets();
    }

    public byte[] getChallenge() {
        return this.challenge.getOctets();
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(3);
        this.addOptional(aSN1EncodableVector, (ASN1Encodable)this.owf);
        aSN1EncodableVector.add((ASN1Encodable)this.witness);
        aSN1EncodableVector.add((ASN1Encodable)this.challenge);
        return new DERSequence(aSN1EncodableVector);
    }

    private void addOptional(ASN1EncodableVector aSN1EncodableVector, ASN1Encodable aSN1Encodable) {
        if (aSN1Encodable != null) {
            aSN1EncodableVector.add(aSN1Encodable);
        }
    }
}

