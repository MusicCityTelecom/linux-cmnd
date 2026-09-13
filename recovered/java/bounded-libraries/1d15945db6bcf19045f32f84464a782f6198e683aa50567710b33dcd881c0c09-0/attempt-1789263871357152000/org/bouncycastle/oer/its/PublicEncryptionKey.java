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
import org.bouncycastle.oer.its.BasePublicEncryptionKey;
import org.bouncycastle.oer.its.SymmAlgorithm;
import org.bouncycastle.oer.its.Utils;

public class PublicEncryptionKey
extends ASN1Object {
    private final SymmAlgorithm supportedSymmAlg;
    private final BasePublicEncryptionKey basePublicEncryptionKey;

    public PublicEncryptionKey(SymmAlgorithm symmAlgorithm, BasePublicEncryptionKey basePublicEncryptionKey) {
        this.supportedSymmAlg = symmAlgorithm;
        this.basePublicEncryptionKey = basePublicEncryptionKey;
    }

    public static PublicEncryptionKey getInstance(Object object) {
        if (object instanceof PublicEncryptionKey) {
            return (PublicEncryptionKey)((Object)object);
        }
        ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance((Object)object);
        return new PublicEncryptionKey(SymmAlgorithm.getInstance(aSN1Sequence.getObjectAt(0)), BasePublicEncryptionKey.getInstance(aSN1Sequence.getObjectAt(1)));
    }

    public SymmAlgorithm getSupportedSymmAlg() {
        return this.supportedSymmAlg;
    }

    public BasePublicEncryptionKey getBasePublicEncryptionKey() {
        return this.basePublicEncryptionKey;
    }

    public ASN1Primitive toASN1Primitive() {
        return Utils.toSequence(new ASN1Encodable[]{this.supportedSymmAlg, this.basePublicEncryptionKey});
    }
}

