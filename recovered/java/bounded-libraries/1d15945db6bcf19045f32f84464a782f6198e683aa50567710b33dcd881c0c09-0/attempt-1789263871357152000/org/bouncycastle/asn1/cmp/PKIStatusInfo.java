/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1BitString
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1EncodableVector
 *  org.bouncycastle.asn1.ASN1Integer
 *  org.bouncycastle.asn1.ASN1Object
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1Sequence
 *  org.bouncycastle.asn1.ASN1TaggedObject
 *  org.bouncycastle.asn1.DERBitString
 *  org.bouncycastle.asn1.DERSequence
 */
package org.bouncycastle.asn1.cmp;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1BitString;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.cmp.PKIFailureInfo;
import org.bouncycastle.asn1.cmp.PKIFreeText;
import org.bouncycastle.asn1.cmp.PKIStatus;

public class PKIStatusInfo
extends ASN1Object {
    ASN1Integer status;
    PKIFreeText statusString;
    ASN1BitString failInfo;

    public static PKIStatusInfo getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        return PKIStatusInfo.getInstance(ASN1Sequence.getInstance((ASN1TaggedObject)aSN1TaggedObject, (boolean)bl));
    }

    public static PKIStatusInfo getInstance(Object object) {
        if (object instanceof PKIStatusInfo) {
            return (PKIStatusInfo)((Object)object);
        }
        if (object != null) {
            return new PKIStatusInfo(ASN1Sequence.getInstance((Object)object));
        }
        return null;
    }

    private PKIStatusInfo(ASN1Sequence aSN1Sequence) {
        this.status = ASN1Integer.getInstance((Object)aSN1Sequence.getObjectAt(0));
        this.statusString = null;
        this.failInfo = null;
        if (aSN1Sequence.size() > 2) {
            this.statusString = PKIFreeText.getInstance(aSN1Sequence.getObjectAt(1));
            this.failInfo = DERBitString.getInstance((Object)aSN1Sequence.getObjectAt(2));
        } else if (aSN1Sequence.size() > 1) {
            ASN1Encodable aSN1Encodable = aSN1Sequence.getObjectAt(1);
            if (aSN1Encodable instanceof ASN1BitString) {
                this.failInfo = ASN1BitString.getInstance((Object)aSN1Encodable);
            } else {
                this.statusString = PKIFreeText.getInstance(aSN1Encodable);
            }
        }
    }

    public PKIStatusInfo(PKIStatus pKIStatus) {
        this.status = ASN1Integer.getInstance((Object)pKIStatus.toASN1Primitive());
    }

    public PKIStatusInfo(PKIStatus pKIStatus, PKIFreeText pKIFreeText) {
        this.status = ASN1Integer.getInstance((Object)pKIStatus.toASN1Primitive());
        this.statusString = pKIFreeText;
    }

    public PKIStatusInfo(PKIStatus pKIStatus, PKIFreeText pKIFreeText, PKIFailureInfo pKIFailureInfo) {
        this.status = ASN1Integer.getInstance((Object)pKIStatus.toASN1Primitive());
        this.statusString = pKIFreeText;
        this.failInfo = pKIFailureInfo;
    }

    public BigInteger getStatus() {
        return this.status.getValue();
    }

    public PKIFreeText getStatusString() {
        return this.statusString;
    }

    public ASN1BitString getFailInfo() {
        return this.failInfo;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(3);
        aSN1EncodableVector.add((ASN1Encodable)this.status);
        if (this.statusString != null) {
            aSN1EncodableVector.add((ASN1Encodable)this.statusString);
        }
        if (this.failInfo != null) {
            aSN1EncodableVector.add((ASN1Encodable)this.failInfo);
        }
        return new DERSequence(aSN1EncodableVector);
    }
}

