/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1Object
 *  org.bouncycastle.asn1.ASN1Primitive
 */
package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.oer.its.HashedId;
import org.bouncycastle.oer.its.SymmetricCiphertext;
import org.bouncycastle.oer.its.Utils;

public class SymmRecipientInfo
extends ASN1Object {
    private final HashedId recipientId;
    private final SymmetricCiphertext encKey;

    public SymmRecipientInfo(HashedId hashedId, SymmetricCiphertext symmetricCiphertext) {
        this.recipientId = hashedId;
        this.encKey = symmetricCiphertext;
    }

    public HashedId getRecipientId() {
        return this.recipientId;
    }

    public SymmetricCiphertext getEncKey() {
        return this.encKey;
    }

    public ASN1Primitive toASN1Primitive() {
        return Utils.toSequence(new ASN1Encodable[]{this.recipientId, this.encKey});
    }
}

