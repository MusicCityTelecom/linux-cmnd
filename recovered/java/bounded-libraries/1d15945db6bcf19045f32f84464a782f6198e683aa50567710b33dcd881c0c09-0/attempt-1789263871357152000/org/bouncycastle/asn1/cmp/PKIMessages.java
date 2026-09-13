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
package org.bouncycastle.asn1.cmp;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.cmp.PKIMessage;

public class PKIMessages
extends ASN1Object {
    private ASN1Sequence content;

    private PKIMessages(ASN1Sequence aSN1Sequence) {
        this.content = aSN1Sequence;
    }

    public static PKIMessages getInstance(Object object) {
        if (object instanceof PKIMessages) {
            return (PKIMessages)((Object)object);
        }
        if (object != null) {
            return new PKIMessages(ASN1Sequence.getInstance((Object)object));
        }
        return null;
    }

    public PKIMessages(PKIMessage pKIMessage) {
        this.content = new DERSequence((ASN1Encodable)pKIMessage);
    }

    public PKIMessages(PKIMessage[] pKIMessageArray) {
        this.content = new DERSequence((ASN1Encodable[])pKIMessageArray);
    }

    public PKIMessage[] toPKIMessageArray() {
        PKIMessage[] pKIMessageArray = new PKIMessage[this.content.size()];
        for (int i = 0; i != pKIMessageArray.length; ++i) {
            pKIMessageArray[i] = PKIMessage.getInstance(this.content.getObjectAt(i));
        }
        return pKIMessageArray;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.content;
    }
}

