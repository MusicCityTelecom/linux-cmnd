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
import org.bouncycastle.oer.its.EncryptedDataEncryptionKey;
import org.bouncycastle.oer.its.HashedId;
import org.bouncycastle.oer.its.Utils;

public class PKRecipientInfo
extends ASN1Object {
    private final HashedId recipientId;
    private final EncryptedDataEncryptionKey encKey;

    public PKRecipientInfo(HashedId hashedId, EncryptedDataEncryptionKey encryptedDataEncryptionKey) {
        this.recipientId = hashedId;
        this.encKey = encryptedDataEncryptionKey;
    }

    public static PKRecipientInfo getInstance(Object object) {
        if (object instanceof PKRecipientInfo) {
            return (PKRecipientInfo)((Object)object);
        }
        ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance((Object)object);
        return new PKRecipientInfo(HashedId.getInstance(aSN1Sequence.getObjectAt(0)), EncryptedDataEncryptionKey.getInstance(aSN1Sequence.getObjectAt(0)));
    }

    public HashedId getRecipientId() {
        return this.recipientId;
    }

    public EncryptedDataEncryptionKey getEncKey() {
        return this.encKey;
    }

    public ASN1Primitive toASN1Primitive() {
        return Utils.toSequence(new ASN1Encodable[]{this.recipientId, this.encKey});
    }

    public static class Builder {
        private HashedId recipientId;
        private EncryptedDataEncryptionKey encKey;

        public Builder setRecipientId(HashedId hashedId) {
            this.recipientId = hashedId;
            return this;
        }

        public Builder setEncKey(EncryptedDataEncryptionKey encryptedDataEncryptionKey) {
            this.encKey = encryptedDataEncryptionKey;
            return this;
        }

        public PKRecipientInfo createPKRecipientInfo() {
            return new PKRecipientInfo(this.recipientId, this.encKey);
        }
    }
}

