/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1BitString
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1EncodableVector
 *  org.bouncycastle.asn1.ASN1GeneralizedTime
 *  org.bouncycastle.asn1.ASN1Object
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1Sequence
 *  org.bouncycastle.asn1.ASN1TaggedObject
 *  org.bouncycastle.asn1.DERSequence
 *  org.bouncycastle.asn1.x500.X500Name
 *  org.bouncycastle.asn1.x509.GeneralName
 *  org.bouncycastle.asn1.x509.ReasonFlags
 */
package org.bouncycastle.asn1.cmc;

import org.bouncycastle.asn1.ASN1BitString;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1GeneralizedTime;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.ReasonFlags;

public class GetCRL
extends ASN1Object {
    private final X500Name issuerName;
    private GeneralName cRLName;
    private ASN1GeneralizedTime time;
    private ReasonFlags reasons;

    public GetCRL(X500Name x500Name, GeneralName generalName, ASN1GeneralizedTime aSN1GeneralizedTime, ReasonFlags reasonFlags) {
        this.issuerName = x500Name;
        this.cRLName = generalName;
        this.time = aSN1GeneralizedTime;
        this.reasons = reasonFlags;
    }

    private GetCRL(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() < 1 || aSN1Sequence.size() > 4) {
            throw new IllegalArgumentException("incorrect sequence size");
        }
        this.issuerName = X500Name.getInstance((Object)aSN1Sequence.getObjectAt(0));
        int n = 1;
        if (aSN1Sequence.size() > n && aSN1Sequence.getObjectAt(n).toASN1Primitive() instanceof ASN1TaggedObject) {
            this.cRLName = GeneralName.getInstance((Object)aSN1Sequence.getObjectAt(n++));
        }
        if (aSN1Sequence.size() > n && aSN1Sequence.getObjectAt(n).toASN1Primitive() instanceof ASN1GeneralizedTime) {
            this.time = ASN1GeneralizedTime.getInstance((Object)aSN1Sequence.getObjectAt(n++));
        }
        if (aSN1Sequence.size() > n && aSN1Sequence.getObjectAt(n).toASN1Primitive() instanceof ASN1BitString) {
            this.reasons = new ReasonFlags(ASN1BitString.getInstance((Object)aSN1Sequence.getObjectAt(n)));
        }
    }

    public static GetCRL getInstance(Object object) {
        if (object instanceof GetCRL) {
            return (GetCRL)((Object)object);
        }
        if (object != null) {
            return new GetCRL(ASN1Sequence.getInstance((Object)object));
        }
        return null;
    }

    public X500Name getIssuerName() {
        return this.issuerName;
    }

    public GeneralName getcRLName() {
        return this.cRLName;
    }

    public ASN1GeneralizedTime getTime() {
        return this.time;
    }

    public ReasonFlags getReasons() {
        return this.reasons;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(4);
        aSN1EncodableVector.add((ASN1Encodable)this.issuerName);
        if (this.cRLName != null) {
            aSN1EncodableVector.add((ASN1Encodable)this.cRLName);
        }
        if (this.time != null) {
            aSN1EncodableVector.add((ASN1Encodable)this.time);
        }
        if (this.reasons != null) {
            aSN1EncodableVector.add((ASN1Encodable)this.reasons);
        }
        return new DERSequence(aSN1EncodableVector);
    }
}

