/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1BitString
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1EncodableVector
 *  org.bouncycastle.asn1.ASN1Object
 *  org.bouncycastle.asn1.ASN1OctetString
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1Sequence
 *  org.bouncycastle.asn1.ASN1TaggedObject
 *  org.bouncycastle.asn1.DERSequence
 *  org.bouncycastle.asn1.DERTaggedObject
 *  org.bouncycastle.asn1.x509.AlgorithmIdentifier
 */
package org.bouncycastle.asn1.crmf;

import org.bouncycastle.asn1.ASN1BitString;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class EncryptedValue
extends ASN1Object {
    private AlgorithmIdentifier intendedAlg;
    private AlgorithmIdentifier symmAlg;
    private ASN1BitString encSymmKey;
    private AlgorithmIdentifier keyAlg;
    private ASN1OctetString valueHint;
    private ASN1BitString encValue;

    private EncryptedValue(ASN1Sequence aSN1Sequence) {
        int n = 0;
        while (aSN1Sequence.getObjectAt(n) instanceof ASN1TaggedObject) {
            ASN1TaggedObject aSN1TaggedObject = (ASN1TaggedObject)aSN1Sequence.getObjectAt(n);
            switch (aSN1TaggedObject.getTagNo()) {
                case 0: {
                    this.intendedAlg = AlgorithmIdentifier.getInstance((ASN1TaggedObject)aSN1TaggedObject, (boolean)false);
                    break;
                }
                case 1: {
                    this.symmAlg = AlgorithmIdentifier.getInstance((ASN1TaggedObject)aSN1TaggedObject, (boolean)false);
                    break;
                }
                case 2: {
                    this.encSymmKey = ASN1BitString.getInstance((ASN1TaggedObject)aSN1TaggedObject, (boolean)false);
                    break;
                }
                case 3: {
                    this.keyAlg = AlgorithmIdentifier.getInstance((ASN1TaggedObject)aSN1TaggedObject, (boolean)false);
                    break;
                }
                case 4: {
                    this.valueHint = ASN1OctetString.getInstance((ASN1TaggedObject)aSN1TaggedObject, (boolean)false);
                    break;
                }
                default: {
                    throw new IllegalArgumentException("Unknown tag encountered: " + aSN1TaggedObject.getTagNo());
                }
            }
            ++n;
        }
        this.encValue = ASN1BitString.getInstance((Object)aSN1Sequence.getObjectAt(n));
    }

    public static EncryptedValue getInstance(Object object) {
        if (object instanceof EncryptedValue) {
            return (EncryptedValue)((Object)object);
        }
        if (object != null) {
            return new EncryptedValue(ASN1Sequence.getInstance((Object)object));
        }
        return null;
    }

    public EncryptedValue(AlgorithmIdentifier algorithmIdentifier, AlgorithmIdentifier algorithmIdentifier2, ASN1BitString aSN1BitString, AlgorithmIdentifier algorithmIdentifier3, ASN1OctetString aSN1OctetString, ASN1BitString aSN1BitString2) {
        if (aSN1BitString2 == null) {
            throw new IllegalArgumentException("'encValue' cannot be null");
        }
        this.intendedAlg = algorithmIdentifier;
        this.symmAlg = algorithmIdentifier2;
        this.encSymmKey = aSN1BitString;
        this.keyAlg = algorithmIdentifier3;
        this.valueHint = aSN1OctetString;
        this.encValue = aSN1BitString2;
    }

    public AlgorithmIdentifier getIntendedAlg() {
        return this.intendedAlg;
    }

    public AlgorithmIdentifier getSymmAlg() {
        return this.symmAlg;
    }

    public ASN1BitString getEncSymmKey() {
        return this.encSymmKey;
    }

    public AlgorithmIdentifier getKeyAlg() {
        return this.keyAlg;
    }

    public ASN1OctetString getValueHint() {
        return this.valueHint;
    }

    public ASN1BitString getEncValue() {
        return this.encValue;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(6);
        this.addOptional(aSN1EncodableVector, 0, (ASN1Encodable)this.intendedAlg);
        this.addOptional(aSN1EncodableVector, 1, (ASN1Encodable)this.symmAlg);
        this.addOptional(aSN1EncodableVector, 2, (ASN1Encodable)this.encSymmKey);
        this.addOptional(aSN1EncodableVector, 3, (ASN1Encodable)this.keyAlg);
        this.addOptional(aSN1EncodableVector, 4, (ASN1Encodable)this.valueHint);
        aSN1EncodableVector.add((ASN1Encodable)this.encValue);
        return new DERSequence(aSN1EncodableVector);
    }

    private void addOptional(ASN1EncodableVector aSN1EncodableVector, int n, ASN1Encodable aSN1Encodable) {
        if (aSN1Encodable != null) {
            aSN1EncodableVector.add((ASN1Encodable)new DERTaggedObject(false, n, aSN1Encodable));
        }
    }
}

