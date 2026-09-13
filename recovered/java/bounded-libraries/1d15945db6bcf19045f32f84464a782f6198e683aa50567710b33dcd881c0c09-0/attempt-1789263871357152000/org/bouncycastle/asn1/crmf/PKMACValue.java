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
 *  org.bouncycastle.asn1.x509.AlgorithmIdentifier
 */
package org.bouncycastle.asn1.crmf;

import org.bouncycastle.asn1.ASN1BitString;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.cmp.CMPObjectIdentifiers;
import org.bouncycastle.asn1.cmp.PBMParameter;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class PKMACValue
extends ASN1Object {
    private AlgorithmIdentifier algId;
    private ASN1BitString value;

    private PKMACValue(ASN1Sequence aSN1Sequence) {
        this.algId = AlgorithmIdentifier.getInstance((Object)aSN1Sequence.getObjectAt(0));
        this.value = ASN1BitString.getInstance((Object)aSN1Sequence.getObjectAt(1));
    }

    public static PKMACValue getInstance(Object object) {
        if (object instanceof PKMACValue) {
            return (PKMACValue)((Object)object);
        }
        if (object != null) {
            return new PKMACValue(ASN1Sequence.getInstance((Object)object));
        }
        return null;
    }

    public static PKMACValue getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        return PKMACValue.getInstance(ASN1Sequence.getInstance((ASN1TaggedObject)aSN1TaggedObject, (boolean)bl));
    }

    public PKMACValue(PBMParameter pBMParameter, DERBitString dERBitString) {
        this(new AlgorithmIdentifier(CMPObjectIdentifiers.passwordBasedMac, (ASN1Encodable)pBMParameter), dERBitString);
    }

    public PKMACValue(AlgorithmIdentifier algorithmIdentifier, DERBitString dERBitString) {
        this.algId = algorithmIdentifier;
        this.value = dERBitString;
    }

    public AlgorithmIdentifier getAlgId() {
        return this.algId;
    }

    public ASN1BitString getValue() {
        return this.value;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(2);
        aSN1EncodableVector.add((ASN1Encodable)this.algId);
        aSN1EncodableVector.add((ASN1Encodable)this.value);
        return new DERSequence(aSN1EncodableVector);
    }
}

