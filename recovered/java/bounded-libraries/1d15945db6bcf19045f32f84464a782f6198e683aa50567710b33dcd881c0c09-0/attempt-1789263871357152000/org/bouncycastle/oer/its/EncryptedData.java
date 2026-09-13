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

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.oer.its.SequenceOfRecipientInfo;
import org.bouncycastle.oer.its.SymmetricCiphertext;
import org.bouncycastle.oer.its.Utils;

public class EncryptedData
extends ASN1Object {
    private final SequenceOfRecipientInfo recipients;
    private final SymmetricCiphertext ciphertext;

    public EncryptedData(SequenceOfRecipientInfo sequenceOfRecipientInfo, SymmetricCiphertext symmetricCiphertext) {
        this.recipients = sequenceOfRecipientInfo;
        this.ciphertext = symmetricCiphertext;
    }

    public static EncryptedData getInstance(Object object) {
        if (object == null || object instanceof EncryptedData) {
            return (EncryptedData)((Object)object);
        }
        ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance((Object)object);
        return new EncryptedData(SequenceOfRecipientInfo.getInstance(aSN1Sequence.getObjectAt(0)), SymmetricCiphertext.getInstance(aSN1Sequence.getObjectAt(1)));
    }

    public ASN1Primitive toASN1Primitive() {
        return Utils.toSequence(new ASN1Encodable[]{this.recipients, this.ciphertext});
    }

    public SequenceOfRecipientInfo getRecipients() {
        return this.recipients;
    }

    public SymmetricCiphertext getCiphertext() {
        return this.ciphertext;
    }

    public static class Builder {
        private SequenceOfRecipientInfo recipients;
        private SymmetricCiphertext ciphertext;

        public Builder setRecipients(SequenceOfRecipientInfo sequenceOfRecipientInfo) {
            this.recipients = sequenceOfRecipientInfo;
            return this;
        }

        public Builder setCiphertext(SymmetricCiphertext symmetricCiphertext) {
            this.ciphertext = symmetricCiphertext;
            return this;
        }

        public EncryptedData createEncryptedData() {
            return new EncryptedData(this.recipients, this.ciphertext);
        }
    }
}

