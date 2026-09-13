/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1EncodableVector
 *  org.bouncycastle.asn1.ASN1Integer
 *  org.bouncycastle.asn1.ASN1Object
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1Sequence
 *  org.bouncycastle.asn1.DERSequence
 */
package org.bouncycastle.asn1.cmp;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.cmp.PKIFreeText;

public class PollRepContent
extends ASN1Object {
    private ASN1Integer[] certReqId;
    private ASN1Integer[] checkAfter;
    private PKIFreeText[] reason;

    private PollRepContent(ASN1Sequence aSN1Sequence) {
        this.certReqId = new ASN1Integer[aSN1Sequence.size()];
        this.checkAfter = new ASN1Integer[aSN1Sequence.size()];
        this.reason = new PKIFreeText[aSN1Sequence.size()];
        for (int i = 0; i != aSN1Sequence.size(); ++i) {
            ASN1Sequence aSN1Sequence2 = ASN1Sequence.getInstance((Object)aSN1Sequence.getObjectAt(i));
            this.certReqId[i] = ASN1Integer.getInstance((Object)aSN1Sequence2.getObjectAt(0));
            this.checkAfter[i] = ASN1Integer.getInstance((Object)aSN1Sequence2.getObjectAt(1));
            if (aSN1Sequence2.size() <= 2) continue;
            this.reason[i] = PKIFreeText.getInstance(aSN1Sequence2.getObjectAt(2));
        }
    }

    public static PollRepContent getInstance(Object object) {
        if (object instanceof PollRepContent) {
            return (PollRepContent)((Object)object);
        }
        if (object != null) {
            return new PollRepContent(ASN1Sequence.getInstance((Object)object));
        }
        return null;
    }

    public PollRepContent(ASN1Integer aSN1Integer, ASN1Integer aSN1Integer2) {
        this(aSN1Integer, aSN1Integer2, null);
    }

    public PollRepContent(ASN1Integer aSN1Integer, ASN1Integer aSN1Integer2, PKIFreeText pKIFreeText) {
        this.certReqId = new ASN1Integer[1];
        this.checkAfter = new ASN1Integer[1];
        this.reason = new PKIFreeText[1];
        this.certReqId[0] = aSN1Integer;
        this.checkAfter[0] = aSN1Integer2;
        this.reason[0] = pKIFreeText;
    }

    public int size() {
        return this.certReqId.length;
    }

    public ASN1Integer getCertReqId(int n) {
        return this.certReqId[n];
    }

    public ASN1Integer getCheckAfter(int n) {
        return this.checkAfter[n];
    }

    public PKIFreeText getReason(int n) {
        return this.reason[n];
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(this.certReqId.length);
        for (int i = 0; i != this.certReqId.length; ++i) {
            ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector(3);
            aSN1EncodableVector2.add((ASN1Encodable)this.certReqId[i]);
            aSN1EncodableVector2.add((ASN1Encodable)this.checkAfter[i]);
            if (this.reason[i] != null) {
                aSN1EncodableVector2.add((ASN1Encodable)this.reason[i]);
            }
            aSN1EncodableVector.add((ASN1Encodable)new DERSequence(aSN1EncodableVector2));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}

