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
import org.bouncycastle.asn1.cmp.RevDetails;

public class RevReqContent
extends ASN1Object {
    private ASN1Sequence content;

    private RevReqContent(ASN1Sequence aSN1Sequence) {
        this.content = aSN1Sequence;
    }

    public static RevReqContent getInstance(Object object) {
        if (object instanceof RevReqContent) {
            return (RevReqContent)((Object)object);
        }
        if (object != null) {
            return new RevReqContent(ASN1Sequence.getInstance((Object)object));
        }
        return null;
    }

    public RevReqContent(RevDetails revDetails) {
        this.content = new DERSequence((ASN1Encodable)revDetails);
    }

    public RevReqContent(RevDetails[] revDetailsArray) {
        this.content = new DERSequence((ASN1Encodable[])revDetailsArray);
    }

    public RevDetails[] toRevDetailsArray() {
        RevDetails[] revDetailsArray = new RevDetails[this.content.size()];
        for (int i = 0; i != revDetailsArray.length; ++i) {
            revDetailsArray[i] = RevDetails.getInstance(this.content.getObjectAt(i));
        }
        return revDetailsArray;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.content;
    }
}

