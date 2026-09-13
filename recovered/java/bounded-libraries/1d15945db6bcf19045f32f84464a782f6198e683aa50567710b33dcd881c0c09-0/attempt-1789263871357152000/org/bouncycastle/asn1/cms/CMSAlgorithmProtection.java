/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1EncodableVector
 *  org.bouncycastle.asn1.ASN1Object
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1Sequence
 *  org.bouncycastle.asn1.ASN1TaggedObject
 *  org.bouncycastle.asn1.DERSequence
 *  org.bouncycastle.asn1.DERTaggedObject
 *  org.bouncycastle.asn1.x509.AlgorithmIdentifier
 */
package org.bouncycastle.asn1.cms;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class CMSAlgorithmProtection
extends ASN1Object {
    public static final int SIGNATURE = 1;
    public static final int MAC = 2;
    private final AlgorithmIdentifier digestAlgorithm;
    private final AlgorithmIdentifier signatureAlgorithm;
    private final AlgorithmIdentifier macAlgorithm;

    public CMSAlgorithmProtection(AlgorithmIdentifier algorithmIdentifier, int n, AlgorithmIdentifier algorithmIdentifier2) {
        if (algorithmIdentifier == null || algorithmIdentifier2 == null) {
            throw new NullPointerException("AlgorithmIdentifiers cannot be null");
        }
        this.digestAlgorithm = algorithmIdentifier;
        if (n == 1) {
            this.signatureAlgorithm = algorithmIdentifier2;
            this.macAlgorithm = null;
        } else if (n == 2) {
            this.signatureAlgorithm = null;
            this.macAlgorithm = algorithmIdentifier2;
        } else {
            throw new IllegalArgumentException("Unknown type: " + n);
        }
    }

    private CMSAlgorithmProtection(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() != 2) {
            throw new IllegalArgumentException("Sequence wrong size: One of signatureAlgorithm or macAlgorithm must be present");
        }
        this.digestAlgorithm = AlgorithmIdentifier.getInstance((Object)aSN1Sequence.getObjectAt(0));
        ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance((Object)aSN1Sequence.getObjectAt(1));
        if (aSN1TaggedObject.getTagNo() == 1) {
            this.signatureAlgorithm = AlgorithmIdentifier.getInstance((ASN1TaggedObject)aSN1TaggedObject, (boolean)false);
            this.macAlgorithm = null;
        } else if (aSN1TaggedObject.getTagNo() == 2) {
            this.signatureAlgorithm = null;
            this.macAlgorithm = AlgorithmIdentifier.getInstance((ASN1TaggedObject)aSN1TaggedObject, (boolean)false);
        } else {
            throw new IllegalArgumentException("Unknown tag found: " + aSN1TaggedObject.getTagNo());
        }
    }

    public static CMSAlgorithmProtection getInstance(Object object) {
        if (object instanceof CMSAlgorithmProtection) {
            return (CMSAlgorithmProtection)((Object)object);
        }
        if (object != null) {
            return new CMSAlgorithmProtection(ASN1Sequence.getInstance((Object)object));
        }
        return null;
    }

    public AlgorithmIdentifier getDigestAlgorithm() {
        return this.digestAlgorithm;
    }

    public AlgorithmIdentifier getMacAlgorithm() {
        return this.macAlgorithm;
    }

    public AlgorithmIdentifier getSignatureAlgorithm() {
        return this.signatureAlgorithm;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(3);
        aSN1EncodableVector.add((ASN1Encodable)this.digestAlgorithm);
        if (this.signatureAlgorithm != null) {
            aSN1EncodableVector.add((ASN1Encodable)new DERTaggedObject(false, 1, (ASN1Encodable)this.signatureAlgorithm));
        }
        if (this.macAlgorithm != null) {
            aSN1EncodableVector.add((ASN1Encodable)new DERTaggedObject(false, 2, (ASN1Encodable)this.macAlgorithm));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}

