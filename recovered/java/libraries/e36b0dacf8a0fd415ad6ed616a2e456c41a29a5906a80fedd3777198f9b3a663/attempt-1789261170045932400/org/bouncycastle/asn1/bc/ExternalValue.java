/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.asn1.bc;

import org.bouncycastle.asn1.ASN1BitString;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.GeneralName;

public class ExternalValue
extends ASN1Object {
    private final GeneralName location;
    private final AlgorithmIdentifier hashAlg;
    private final ASN1BitString hashVal;

    public ExternalValue(GeneralName generalName, AlgorithmIdentifier algorithmIdentifier, byte[] byArray) {
        this.location = generalName;
        this.hashAlg = algorithmIdentifier;
        this.hashVal = new DERBitString(byArray);
    }

    private ExternalValue(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() != 3) {
            throw new IllegalArgumentException("unknown sequence");
        }
        this.location = GeneralName.getInstance(aSN1Sequence.getObjectAt(0));
        this.hashAlg = AlgorithmIdentifier.getInstance(aSN1Sequence.getObjectAt(1));
        this.hashVal = ASN1BitString.getInstance(aSN1Sequence.getObjectAt(2));
    }

    public static ExternalValue getInstance(Object object) {
        if (object instanceof ExternalValue) {
            return (ExternalValue)object;
        }
        if (object != null) {
            return new ExternalValue(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    public GeneralName getLocation() {
        return this.location;
    }

    public AlgorithmIdentifier getHashAlg() {
        return this.hashAlg;
    }

    public ASN1BitString getHashVal() {
        return this.hashVal;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.location);
        aSN1EncodableVector.add(this.hashAlg);
        aSN1EncodableVector.add(this.hashVal);
        return new DERSequence(aSN1EncodableVector);
    }
}

