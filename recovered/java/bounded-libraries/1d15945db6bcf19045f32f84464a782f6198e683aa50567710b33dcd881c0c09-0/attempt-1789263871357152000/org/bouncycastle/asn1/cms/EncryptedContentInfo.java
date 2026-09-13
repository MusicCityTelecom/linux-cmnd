/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1EncodableVector
 *  org.bouncycastle.asn1.ASN1Object
 *  org.bouncycastle.asn1.ASN1ObjectIdentifier
 *  org.bouncycastle.asn1.ASN1OctetString
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1Sequence
 *  org.bouncycastle.asn1.ASN1TaggedObject
 *  org.bouncycastle.asn1.BERSequence
 *  org.bouncycastle.asn1.BERTaggedObject
 *  org.bouncycastle.asn1.x509.AlgorithmIdentifier
 */
package org.bouncycastle.asn1.cms;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.BERSequence;
import org.bouncycastle.asn1.BERTaggedObject;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class EncryptedContentInfo
extends ASN1Object {
    private ASN1ObjectIdentifier contentType;
    private AlgorithmIdentifier contentEncryptionAlgorithm;
    private ASN1OctetString encryptedContent;

    public EncryptedContentInfo(ASN1ObjectIdentifier aSN1ObjectIdentifier, AlgorithmIdentifier algorithmIdentifier, ASN1OctetString aSN1OctetString) {
        this.contentType = aSN1ObjectIdentifier;
        this.contentEncryptionAlgorithm = algorithmIdentifier;
        this.encryptedContent = aSN1OctetString;
    }

    private EncryptedContentInfo(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() < 2) {
            throw new IllegalArgumentException("Truncated Sequence Found");
        }
        this.contentType = (ASN1ObjectIdentifier)aSN1Sequence.getObjectAt(0);
        this.contentEncryptionAlgorithm = AlgorithmIdentifier.getInstance((Object)aSN1Sequence.getObjectAt(1));
        if (aSN1Sequence.size() > 2) {
            this.encryptedContent = ASN1OctetString.getInstance((ASN1TaggedObject)((ASN1TaggedObject)aSN1Sequence.getObjectAt(2)), (boolean)false);
        }
    }

    public static EncryptedContentInfo getInstance(Object object) {
        if (object instanceof EncryptedContentInfo) {
            return (EncryptedContentInfo)((Object)object);
        }
        if (object != null) {
            return new EncryptedContentInfo(ASN1Sequence.getInstance((Object)object));
        }
        return null;
    }

    public ASN1ObjectIdentifier getContentType() {
        return this.contentType;
    }

    public AlgorithmIdentifier getContentEncryptionAlgorithm() {
        return this.contentEncryptionAlgorithm;
    }

    public ASN1OctetString getEncryptedContent() {
        return this.encryptedContent;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(3);
        aSN1EncodableVector.add((ASN1Encodable)this.contentType);
        aSN1EncodableVector.add((ASN1Encodable)this.contentEncryptionAlgorithm);
        if (this.encryptedContent != null) {
            aSN1EncodableVector.add((ASN1Encodable)new BERTaggedObject(false, 0, (ASN1Encodable)this.encryptedContent));
        }
        return new BERSequence(aSN1EncodableVector);
    }
}

