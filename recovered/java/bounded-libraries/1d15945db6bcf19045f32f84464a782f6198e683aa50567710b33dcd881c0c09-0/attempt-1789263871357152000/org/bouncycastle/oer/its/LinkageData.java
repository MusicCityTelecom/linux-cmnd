/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1EncodableVector
 *  org.bouncycastle.asn1.ASN1Object
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1Sequence
 *  org.bouncycastle.asn1.DERSequence
 */
package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.oer.its.GroupLinkageValue;
import org.bouncycastle.oer.its.IValue;
import org.bouncycastle.oer.its.LinkageValue;

public class LinkageData
extends ASN1Object {
    private final IValue iCert;
    private final LinkageValue linkageValue;
    private final GroupLinkageValue groupLinkageValue;

    private LinkageData(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() != 2 && aSN1Sequence.size() != 3) {
            throw new IllegalArgumentException("sequence must be size 2 or 3");
        }
        this.iCert = IValue.getInstance(aSN1Sequence.getObjectAt(2));
        this.linkageValue = LinkageValue.getInstance(aSN1Sequence.getObjectAt(2));
        this.groupLinkageValue = GroupLinkageValue.getInstance(aSN1Sequence.getObjectAt(2));
    }

    public static LinkageData getInstance(Object object) {
        if (object instanceof LinkageData) {
            return (LinkageData)((Object)object);
        }
        if (object != null) {
            return new LinkageData(ASN1Sequence.getInstance((Object)object));
        }
        return null;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        return new DERSequence(aSN1EncodableVector);
    }
}

