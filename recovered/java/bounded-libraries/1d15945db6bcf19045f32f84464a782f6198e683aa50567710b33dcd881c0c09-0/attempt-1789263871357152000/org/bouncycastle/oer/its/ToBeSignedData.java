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
package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.oer.its.HeaderInfo;
import org.bouncycastle.oer.its.SignedDataPayload;

public class ToBeSignedData
extends ASN1Object {
    private final SignedDataPayload payload;
    private final HeaderInfo headerInfo;

    public ToBeSignedData(SignedDataPayload signedDataPayload, HeaderInfo headerInfo) {
        this.payload = signedDataPayload;
        this.headerInfo = headerInfo;
    }

    public static ToBeSignedData getInstance(Object object) {
        if (object instanceof ToBeSignedData) {
            return (ToBeSignedData)((Object)object);
        }
        ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance((Object)object);
        return new Builder().setPayload(SignedDataPayload.getInstance(aSN1Sequence.getObjectAt(0))).setHeaderInfo(HeaderInfo.getInstance(aSN1Sequence.getObjectAt(1))).createToBeSignedData();
    }

    public SignedDataPayload getPayload() {
        return this.payload;
    }

    public HeaderInfo getHeaderInfo() {
        return this.headerInfo;
    }

    public ASN1Primitive toASN1Primitive() {
        return new DERSequence(new ASN1Encodable[]{this.payload, this.headerInfo});
    }

    public static class Builder {
        private SignedDataPayload payload;
        private HeaderInfo headerInfo;

        public Builder setPayload(SignedDataPayload signedDataPayload) {
            this.payload = signedDataPayload;
            return this;
        }

        public Builder setHeaderInfo(HeaderInfo headerInfo) {
            this.headerInfo = headerInfo;
            return this;
        }

        public ToBeSignedData createToBeSignedData() {
            return new ToBeSignedData(this.payload, this.headerInfo);
        }
    }
}

