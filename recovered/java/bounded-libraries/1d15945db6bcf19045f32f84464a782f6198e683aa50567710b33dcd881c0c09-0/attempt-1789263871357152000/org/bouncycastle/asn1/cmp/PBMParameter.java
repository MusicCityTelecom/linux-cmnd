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
 *  org.bouncycastle.asn1.x509.AlgorithmIdentifier
 */
package org.bouncycastle.asn1.cmp;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class PBMParameter
extends ASN1Object {
    private ASN1OctetString salt;
    private AlgorithmIdentifier owf;
    private ASN1Integer iterationCount;
    private AlgorithmIdentifier mac;

    private PBMParameter(ASN1Sequence aSN1Sequence) {
        this.salt = ASN1OctetString.getInstance((Object)aSN1Sequence.getObjectAt(0));
        this.owf = AlgorithmIdentifier.getInstance((Object)aSN1Sequence.getObjectAt(1));
        this.iterationCount = ASN1Integer.getInstance((Object)aSN1Sequence.getObjectAt(2));
        this.mac = AlgorithmIdentifier.getInstance((Object)aSN1Sequence.getObjectAt(3));
    }

    public static PBMParameter getInstance(Object object) {
        if (object instanceof PBMParameter) {
            return (PBMParameter)((Object)object);
        }
        if (object != null) {
            return new PBMParameter(ASN1Sequence.getInstance((Object)object));
        }
        return null;
    }

    public PBMParameter(byte[] byArray, AlgorithmIdentifier algorithmIdentifier, int n, AlgorithmIdentifier algorithmIdentifier2) {
        this((ASN1OctetString)new DEROctetString(byArray), algorithmIdentifier, new ASN1Integer((long)n), algorithmIdentifier2);
    }

    public PBMParameter(ASN1OctetString aSN1OctetString, AlgorithmIdentifier algorithmIdentifier, ASN1Integer aSN1Integer, AlgorithmIdentifier algorithmIdentifier2) {
        this.salt = aSN1OctetString;
        this.owf = algorithmIdentifier;
        this.iterationCount = aSN1Integer;
        this.mac = algorithmIdentifier2;
    }

    public ASN1OctetString getSalt() {
        return this.salt;
    }

    public AlgorithmIdentifier getOwf() {
        return this.owf;
    }

    public ASN1Integer getIterationCount() {
        return this.iterationCount;
    }

    public AlgorithmIdentifier getMac() {
        return this.mac;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(4);
        aSN1EncodableVector.add((ASN1Encodable)this.salt);
        aSN1EncodableVector.add((ASN1Encodable)this.owf);
        aSN1EncodableVector.add((ASN1Encodable)this.iterationCount);
        aSN1EncodableVector.add((ASN1Encodable)this.mac);
        return new DERSequence(aSN1EncodableVector);
    }
}

