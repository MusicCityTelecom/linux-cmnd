/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1BitString
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1EncodableVector
 *  org.bouncycastle.asn1.ASN1Object
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1Sequence
 *  org.bouncycastle.asn1.ASN1TaggedObject
 *  org.bouncycastle.asn1.DERBitString
 *  org.bouncycastle.asn1.DERSequence
 *  org.bouncycastle.asn1.DERTaggedObject
 *  org.bouncycastle.asn1.x509.AlgorithmIdentifier
 */
package org.bouncycastle.asn1.cmp;

import org.bouncycastle.asn1.ASN1BitString;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.crmf.CertId;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class OOBCertHash
extends ASN1Object {
    private AlgorithmIdentifier hashAlg;
    private CertId certId;
    private ASN1BitString hashVal;

    private OOBCertHash(ASN1Sequence aSN1Sequence) {
        int n = aSN1Sequence.size() - 1;
        this.hashVal = ASN1BitString.getInstance((Object)aSN1Sequence.getObjectAt(n--));
        for (int i = n; i >= 0; --i) {
            ASN1TaggedObject aSN1TaggedObject = (ASN1TaggedObject)aSN1Sequence.getObjectAt(i);
            if (aSN1TaggedObject.getTagNo() == 0) {
                this.hashAlg = AlgorithmIdentifier.getInstance((ASN1TaggedObject)aSN1TaggedObject, (boolean)true);
                continue;
            }
            this.certId = CertId.getInstance(aSN1TaggedObject, true);
        }
    }

    public static OOBCertHash getInstance(Object object) {
        if (object instanceof OOBCertHash) {
            return (OOBCertHash)((Object)object);
        }
        if (object != null) {
            return new OOBCertHash(ASN1Sequence.getInstance((Object)object));
        }
        return null;
    }

    public OOBCertHash(AlgorithmIdentifier algorithmIdentifier, CertId certId, byte[] byArray) {
        this(algorithmIdentifier, certId, new DERBitString(byArray));
    }

    public OOBCertHash(AlgorithmIdentifier algorithmIdentifier, CertId certId, DERBitString dERBitString) {
        this.hashAlg = algorithmIdentifier;
        this.certId = certId;
        this.hashVal = dERBitString;
    }

    public AlgorithmIdentifier getHashAlg() {
        return this.hashAlg;
    }

    public CertId getCertId() {
        return this.certId;
    }

    public ASN1BitString getHashVal() {
        return this.hashVal;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(3);
        this.addOptional(aSN1EncodableVector, 0, (ASN1Encodable)this.hashAlg);
        this.addOptional(aSN1EncodableVector, 1, (ASN1Encodable)this.certId);
        aSN1EncodableVector.add((ASN1Encodable)this.hashVal);
        return new DERSequence(aSN1EncodableVector);
    }

    private void addOptional(ASN1EncodableVector aSN1EncodableVector, int n, ASN1Encodable aSN1Encodable) {
        if (aSN1Encodable != null) {
            aSN1EncodableVector.add((ASN1Encodable)new DERTaggedObject(true, n, aSN1Encodable));
        }
    }
}

