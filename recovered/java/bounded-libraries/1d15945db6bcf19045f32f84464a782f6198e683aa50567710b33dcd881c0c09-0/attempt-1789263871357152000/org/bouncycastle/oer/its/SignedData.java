/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1Object
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1Sequence
 */
package org.bouncycastle.oer.its;

import java.util.Iterator;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.oer.its.HashAlgorithm;
import org.bouncycastle.oer.its.Signature;
import org.bouncycastle.oer.its.SignerIdentifier;
import org.bouncycastle.oer.its.ToBeSignedData;
import org.bouncycastle.oer.its.Utils;

public class SignedData
extends ASN1Object {
    private final HashAlgorithm hashId;
    private final ToBeSignedData tbsData;
    private final SignerIdentifier signer;
    private final Signature signature;

    public SignedData(HashAlgorithm hashAlgorithm, ToBeSignedData toBeSignedData, SignerIdentifier signerIdentifier, Signature signature) {
        this.hashId = hashAlgorithm;
        this.tbsData = toBeSignedData;
        this.signer = signerIdentifier;
        this.signature = signature;
    }

    public static SignedData getInstance(Object object) {
        if (object instanceof SignedData) {
            return (SignedData)((Object)object);
        }
        Iterator iterator = ASN1Sequence.getInstance((Object)object).iterator();
        return new SignedData(HashAlgorithm.getInstance(iterator.next()), ToBeSignedData.getInstance(iterator.next()), SignerIdentifier.getInstance(iterator.next()), Signature.getInstance(iterator.next()));
    }

    public ASN1Primitive toASN1Primitive() {
        return Utils.toSequence(new ASN1Encodable[]{this.hashId, this.tbsData, this.signer, this.signature});
    }

    public HashAlgorithm getHashId() {
        return this.hashId;
    }

    public ToBeSignedData getTbsData() {
        return this.tbsData;
    }

    public SignerIdentifier getSigner() {
        return this.signer;
    }

    public Signature getSignature() {
        return this.signature;
    }

    public Builder builder() {
        return new Builder();
    }

    public class Builder {
        private HashAlgorithm hashId;
        private ToBeSignedData tbsData;
        private SignerIdentifier signer;
        private Signature signature;

        public Builder setHashId(HashAlgorithm hashAlgorithm) {
            this.hashId = hashAlgorithm;
            return this;
        }

        public Builder setTbsData(ToBeSignedData toBeSignedData) {
            this.tbsData = toBeSignedData;
            return this;
        }

        public Builder setSigner(SignerIdentifier signerIdentifier) {
            this.signer = signerIdentifier;
            return this;
        }

        public Builder setSignature(Signature signature) {
            this.signature = signature;
            return this;
        }

        public SignedData build() {
            return new SignedData(this.hashId, this.tbsData, this.signer, this.signature);
        }
    }
}

