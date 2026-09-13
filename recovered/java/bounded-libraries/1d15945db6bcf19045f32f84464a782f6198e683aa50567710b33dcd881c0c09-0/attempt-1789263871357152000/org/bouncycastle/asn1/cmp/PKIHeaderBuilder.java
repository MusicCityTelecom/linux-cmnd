/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1EncodableVector
 *  org.bouncycastle.asn1.ASN1GeneralizedTime
 *  org.bouncycastle.asn1.ASN1Integer
 *  org.bouncycastle.asn1.ASN1OctetString
 *  org.bouncycastle.asn1.ASN1Sequence
 *  org.bouncycastle.asn1.DEROctetString
 *  org.bouncycastle.asn1.DERSequence
 *  org.bouncycastle.asn1.DERTaggedObject
 *  org.bouncycastle.asn1.x509.AlgorithmIdentifier
 *  org.bouncycastle.asn1.x509.GeneralName
 */
package org.bouncycastle.asn1.cmp;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1GeneralizedTime;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.cmp.InfoTypeAndValue;
import org.bouncycastle.asn1.cmp.PKIFreeText;
import org.bouncycastle.asn1.cmp.PKIHeader;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.GeneralName;

public class PKIHeaderBuilder {
    private ASN1Integer pvno;
    private GeneralName sender;
    private GeneralName recipient;
    private ASN1GeneralizedTime messageTime;
    private AlgorithmIdentifier protectionAlg;
    private ASN1OctetString senderKID;
    private ASN1OctetString recipKID;
    private ASN1OctetString transactionID;
    private ASN1OctetString senderNonce;
    private ASN1OctetString recipNonce;
    private PKIFreeText freeText;
    private ASN1Sequence generalInfo;

    public PKIHeaderBuilder(int n, GeneralName generalName, GeneralName generalName2) {
        this(new ASN1Integer((long)n), generalName, generalName2);
    }

    private PKIHeaderBuilder(ASN1Integer aSN1Integer, GeneralName generalName, GeneralName generalName2) {
        this.pvno = aSN1Integer;
        this.sender = generalName;
        this.recipient = generalName2;
    }

    public PKIHeaderBuilder setMessageTime(ASN1GeneralizedTime aSN1GeneralizedTime) {
        this.messageTime = aSN1GeneralizedTime;
        return this;
    }

    public PKIHeaderBuilder setProtectionAlg(AlgorithmIdentifier algorithmIdentifier) {
        this.protectionAlg = algorithmIdentifier;
        return this;
    }

    public PKIHeaderBuilder setSenderKID(byte[] byArray) {
        return this.setSenderKID((ASN1OctetString)(byArray == null ? null : new DEROctetString(byArray)));
    }

    public PKIHeaderBuilder setSenderKID(ASN1OctetString aSN1OctetString) {
        this.senderKID = aSN1OctetString;
        return this;
    }

    public PKIHeaderBuilder setRecipKID(byte[] byArray) {
        return this.setRecipKID((ASN1OctetString)(byArray == null ? null : new DEROctetString(byArray)));
    }

    public PKIHeaderBuilder setRecipKID(ASN1OctetString aSN1OctetString) {
        this.recipKID = aSN1OctetString;
        return this;
    }

    public PKIHeaderBuilder setTransactionID(byte[] byArray) {
        return this.setTransactionID((ASN1OctetString)(byArray == null ? null : new DEROctetString(byArray)));
    }

    public PKIHeaderBuilder setTransactionID(ASN1OctetString aSN1OctetString) {
        this.transactionID = aSN1OctetString;
        return this;
    }

    public PKIHeaderBuilder setSenderNonce(byte[] byArray) {
        return this.setSenderNonce((ASN1OctetString)(byArray == null ? null : new DEROctetString(byArray)));
    }

    public PKIHeaderBuilder setSenderNonce(ASN1OctetString aSN1OctetString) {
        this.senderNonce = aSN1OctetString;
        return this;
    }

    public PKIHeaderBuilder setRecipNonce(byte[] byArray) {
        return this.setRecipNonce((ASN1OctetString)(byArray == null ? null : new DEROctetString(byArray)));
    }

    public PKIHeaderBuilder setRecipNonce(ASN1OctetString aSN1OctetString) {
        this.recipNonce = aSN1OctetString;
        return this;
    }

    public PKIHeaderBuilder setFreeText(PKIFreeText pKIFreeText) {
        this.freeText = pKIFreeText;
        return this;
    }

    public PKIHeaderBuilder setGeneralInfo(InfoTypeAndValue infoTypeAndValue) {
        return this.setGeneralInfo(PKIHeaderBuilder.makeGeneralInfoSeq(infoTypeAndValue));
    }

    public PKIHeaderBuilder setGeneralInfo(InfoTypeAndValue[] infoTypeAndValueArray) {
        return this.setGeneralInfo(PKIHeaderBuilder.makeGeneralInfoSeq(infoTypeAndValueArray));
    }

    public PKIHeaderBuilder setGeneralInfo(ASN1Sequence aSN1Sequence) {
        this.generalInfo = aSN1Sequence;
        return this;
    }

    private static ASN1Sequence makeGeneralInfoSeq(InfoTypeAndValue infoTypeAndValue) {
        return new DERSequence((ASN1Encodable)infoTypeAndValue);
    }

    private static ASN1Sequence makeGeneralInfoSeq(InfoTypeAndValue[] infoTypeAndValueArray) {
        DERSequence dERSequence = null;
        if (infoTypeAndValueArray != null) {
            dERSequence = new DERSequence((ASN1Encodable[])infoTypeAndValueArray);
        }
        return dERSequence;
    }

    public PKIHeader build() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(12);
        aSN1EncodableVector.add((ASN1Encodable)this.pvno);
        aSN1EncodableVector.add((ASN1Encodable)this.sender);
        aSN1EncodableVector.add((ASN1Encodable)this.recipient);
        this.addOptional(aSN1EncodableVector, 0, (ASN1Encodable)this.messageTime);
        this.addOptional(aSN1EncodableVector, 1, (ASN1Encodable)this.protectionAlg);
        this.addOptional(aSN1EncodableVector, 2, (ASN1Encodable)this.senderKID);
        this.addOptional(aSN1EncodableVector, 3, (ASN1Encodable)this.recipKID);
        this.addOptional(aSN1EncodableVector, 4, (ASN1Encodable)this.transactionID);
        this.addOptional(aSN1EncodableVector, 5, (ASN1Encodable)this.senderNonce);
        this.addOptional(aSN1EncodableVector, 6, (ASN1Encodable)this.recipNonce);
        this.addOptional(aSN1EncodableVector, 7, (ASN1Encodable)this.freeText);
        this.addOptional(aSN1EncodableVector, 8, (ASN1Encodable)this.generalInfo);
        this.messageTime = null;
        this.protectionAlg = null;
        this.senderKID = null;
        this.recipKID = null;
        this.transactionID = null;
        this.senderNonce = null;
        this.recipNonce = null;
        this.freeText = null;
        this.generalInfo = null;
        return PKIHeader.getInstance(new DERSequence(aSN1EncodableVector));
    }

    private void addOptional(ASN1EncodableVector aSN1EncodableVector, int n, ASN1Encodable aSN1Encodable) {
        if (aSN1Encodable != null) {
            aSN1EncodableVector.add((ASN1Encodable)new DERTaggedObject(true, n, aSN1Encodable));
        }
    }
}

