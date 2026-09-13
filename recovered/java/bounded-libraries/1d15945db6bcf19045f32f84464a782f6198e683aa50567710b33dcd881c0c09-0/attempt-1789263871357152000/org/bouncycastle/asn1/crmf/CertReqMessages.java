/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1Object
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1Sequence
 *  org.bouncycastle.asn1.DERSequence
 */
package org.bouncycastle.asn1.crmf;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.crmf.CertReqMsg;

public class CertReqMessages
extends ASN1Object {
    private ASN1Sequence content;

    private CertReqMessages(ASN1Sequence aSN1Sequence) {
        this.content = aSN1Sequence;
    }

    public static CertReqMessages getInstance(Object object) {
        if (object instanceof CertReqMessages) {
            return (CertReqMessages)((Object)object);
        }
        if (object != null) {
            return new CertReqMessages(ASN1Sequence.getInstance((Object)object));
        }
        return null;
    }

    public CertReqMessages(CertReqMsg certReqMsg) {
        this.content = new DERSequence((ASN1Encodable)certReqMsg);
    }

    public CertReqMessages(CertReqMsg[] certReqMsgArray) {
        this.content = new DERSequence((ASN1Encodable[])certReqMsgArray);
    }

    public CertReqMsg[] toCertReqMsgArray() {
        CertReqMsg[] certReqMsgArray = new CertReqMsg[this.content.size()];
        for (int i = 0; i != certReqMsgArray.length; ++i) {
            certReqMsgArray[i] = CertReqMsg.getInstance(this.content.getObjectAt(i));
        }
        return certReqMsgArray;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.content;
    }
}

