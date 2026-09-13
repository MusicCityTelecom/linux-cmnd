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
package org.bouncycastle.asn1.cmc;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.cmc.BodyPartReference;

public class ControlsProcessed
extends ASN1Object {
    private final ASN1Sequence bodyPartReferences;

    public ControlsProcessed(BodyPartReference bodyPartReference) {
        this.bodyPartReferences = new DERSequence((ASN1Encodable)bodyPartReference);
    }

    public ControlsProcessed(BodyPartReference[] bodyPartReferenceArray) {
        this.bodyPartReferences = new DERSequence((ASN1Encodable[])bodyPartReferenceArray);
    }

    public static ControlsProcessed getInstance(Object object) {
        if (object instanceof ControlsProcessed) {
            return (ControlsProcessed)((Object)object);
        }
        if (object != null) {
            return new ControlsProcessed(ASN1Sequence.getInstance((Object)object));
        }
        return null;
    }

    private ControlsProcessed(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() != 1) {
            throw new IllegalArgumentException("incorrect sequence size");
        }
        this.bodyPartReferences = ASN1Sequence.getInstance((Object)aSN1Sequence.getObjectAt(0));
    }

    public BodyPartReference[] getBodyList() {
        BodyPartReference[] bodyPartReferenceArray = new BodyPartReference[this.bodyPartReferences.size()];
        for (int i = 0; i != this.bodyPartReferences.size(); ++i) {
            bodyPartReferenceArray[i] = BodyPartReference.getInstance(this.bodyPartReferences.getObjectAt(i));
        }
        return bodyPartReferenceArray;
    }

    public ASN1Primitive toASN1Primitive() {
        return new DERSequence((ASN1Encodable)this.bodyPartReferences);
    }
}

