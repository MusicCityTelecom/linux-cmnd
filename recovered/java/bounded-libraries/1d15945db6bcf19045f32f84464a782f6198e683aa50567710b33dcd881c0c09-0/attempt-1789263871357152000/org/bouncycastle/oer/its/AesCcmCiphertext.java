/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1Object
 *  org.bouncycastle.asn1.ASN1OctetString
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1Sequence
 */
package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.oer.its.SequenceOfOctetString;
import org.bouncycastle.oer.its.Utils;

public class AesCcmCiphertext
extends ASN1Object {
    private final ASN1OctetString nonce;
    private final SequenceOfOctetString opaque;

    public AesCcmCiphertext(ASN1OctetString aSN1OctetString, SequenceOfOctetString sequenceOfOctetString) {
        this.nonce = aSN1OctetString;
        this.opaque = sequenceOfOctetString;
    }

    public static AesCcmCiphertext getInstance(Object object) {
        if (object instanceof AesCcmCiphertext) {
            return (AesCcmCiphertext)((Object)object);
        }
        ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance((Object)object);
        return new Builder().setNonce(ASN1OctetString.getInstance((Object)aSN1Sequence.getObjectAt(0))).setOpaque(SequenceOfOctetString.getInstance(aSN1Sequence.getObjectAt(1))).createAesCcmCiphertext();
    }

    public ASN1Primitive toASN1Primitive() {
        return Utils.toSequence(new ASN1Encodable[]{this.nonce, this.opaque});
    }

    public static class Builder {
        private ASN1OctetString nonce;
        private SequenceOfOctetString opaque;

        public Builder setNonce(ASN1OctetString aSN1OctetString) {
            this.nonce = aSN1OctetString;
            return this;
        }

        public Builder setOpaque(SequenceOfOctetString sequenceOfOctetString) {
            this.opaque = sequenceOfOctetString;
            return this;
        }

        public AesCcmCiphertext createAesCcmCiphertext() {
            return new AesCcmCiphertext(this.nonce, this.opaque);
        }
    }
}

