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
import org.bouncycastle.oer.its.HashedData;
import org.bouncycastle.oer.its.Ieee1609Dot2Data;

public class SignedDataPayload
extends ASN1Object {
    private final Ieee1609Dot2Data data;
    private final HashedData extDataHash;

    public SignedDataPayload(Ieee1609Dot2Data ieee1609Dot2Data, HashedData hashedData) {
        this.data = ieee1609Dot2Data;
        this.extDataHash = hashedData;
    }

    public static SignedDataPayload getInstance(Object object) {
        if (object instanceof SignedDataPayload) {
            return (SignedDataPayload)((Object)object);
        }
        ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance((Object)object);
        return new SignedDataPayload(Ieee1609Dot2Data.getInstance(aSN1Sequence.getObjectAt(0)), HashedData.getInstance(aSN1Sequence.getObjectAt(1)));
    }

    public static Builder builder() {
        return new Builder();
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        return new DERSequence(aSN1EncodableVector);
    }

    public Ieee1609Dot2Data getData() {
        return this.data;
    }

    public HashedData getExtDataHash() {
        return this.extDataHash;
    }

    public static class Builder {
        private Ieee1609Dot2Data data;
        private HashedData extDataHash;

        public Builder setData(Ieee1609Dot2Data ieee1609Dot2Data) {
            this.data = ieee1609Dot2Data;
            return this;
        }

        public Builder setExtDataHash(HashedData hashedData) {
            this.extDataHash = hashedData;
            return this;
        }

        public SignedDataPayload build() {
            return new SignedDataPayload(this.data, this.extDataHash);
        }
    }
}

