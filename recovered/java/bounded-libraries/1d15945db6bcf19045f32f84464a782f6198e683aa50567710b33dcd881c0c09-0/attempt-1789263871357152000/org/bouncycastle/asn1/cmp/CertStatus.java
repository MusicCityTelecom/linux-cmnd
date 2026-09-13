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
 */
package org.bouncycastle.asn1.cmp;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.cmp.PKIStatusInfo;

public class CertStatus
extends ASN1Object {
    private ASN1OctetString certHash;
    private ASN1Integer certReqId;
    private PKIStatusInfo statusInfo;

    private CertStatus(ASN1Sequence aSN1Sequence) {
        this.certHash = ASN1OctetString.getInstance((Object)aSN1Sequence.getObjectAt(0));
        this.certReqId = ASN1Integer.getInstance((Object)aSN1Sequence.getObjectAt(1));
        if (aSN1Sequence.size() > 2) {
            this.statusInfo = PKIStatusInfo.getInstance(aSN1Sequence.getObjectAt(2));
        }
    }

    public CertStatus(byte[] byArray, BigInteger bigInteger) {
        this.certHash = new DEROctetString(byArray);
        this.certReqId = new ASN1Integer(bigInteger);
    }

    public CertStatus(byte[] byArray, BigInteger bigInteger, PKIStatusInfo pKIStatusInfo) {
        this.certHash = new DEROctetString(byArray);
        this.certReqId = new ASN1Integer(bigInteger);
        this.statusInfo = pKIStatusInfo;
    }

    public static CertStatus getInstance(Object object) {
        if (object instanceof CertStatus) {
            return (CertStatus)((Object)object);
        }
        if (object != null) {
            return new CertStatus(ASN1Sequence.getInstance((Object)object));
        }
        return null;
    }

    public ASN1OctetString getCertHash() {
        return this.certHash;
    }

    public ASN1Integer getCertReqId() {
        return this.certReqId;
    }

    public PKIStatusInfo getStatusInfo() {
        return this.statusInfo;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(3);
        aSN1EncodableVector.add((ASN1Encodable)this.certHash);
        aSN1EncodableVector.add((ASN1Encodable)this.certReqId);
        if (this.statusInfo != null) {
            aSN1EncodableVector.add((ASN1Encodable)this.statusInfo);
        }
        return new DERSequence(aSN1EncodableVector);
    }
}

