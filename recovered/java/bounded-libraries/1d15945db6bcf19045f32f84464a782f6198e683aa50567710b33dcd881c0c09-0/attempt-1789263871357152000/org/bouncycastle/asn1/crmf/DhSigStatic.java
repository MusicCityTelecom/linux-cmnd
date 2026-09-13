/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1EncodableVector
 *  org.bouncycastle.asn1.ASN1Object
 *  org.bouncycastle.asn1.ASN1OctetString
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1Sequence
 *  org.bouncycastle.asn1.DEROctetString
 *  org.bouncycastle.asn1.DERSequence
 *  org.bouncycastle.util.Arrays
 */
package org.bouncycastle.asn1.crmf;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.cms.IssuerAndSerialNumber;
import org.bouncycastle.util.Arrays;

public class DhSigStatic
extends ASN1Object {
    private final IssuerAndSerialNumber issuerAndSerial;
    private final ASN1OctetString hashValue;

    public DhSigStatic(byte[] byArray) {
        this(null, byArray);
    }

    public DhSigStatic(IssuerAndSerialNumber issuerAndSerialNumber, byte[] byArray) {
        this.issuerAndSerial = issuerAndSerialNumber;
        this.hashValue = new DEROctetString(Arrays.clone((byte[])byArray));
    }

    public static DhSigStatic getInstance(Object object) {
        if (object instanceof DhSigStatic) {
            return (DhSigStatic)((Object)object);
        }
        if (object != null) {
            return new DhSigStatic(ASN1Sequence.getInstance((Object)object));
        }
        return null;
    }

    private DhSigStatic(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() == 1) {
            this.issuerAndSerial = null;
            this.hashValue = ASN1OctetString.getInstance((Object)aSN1Sequence.getObjectAt(0));
        } else if (aSN1Sequence.size() == 2) {
            this.issuerAndSerial = IssuerAndSerialNumber.getInstance(aSN1Sequence.getObjectAt(0));
            this.hashValue = ASN1OctetString.getInstance((Object)aSN1Sequence.getObjectAt(1));
        } else {
            throw new IllegalArgumentException("sequence wrong length for DhSigStatic");
        }
    }

    public IssuerAndSerialNumber getIssuerAndSerial() {
        return this.issuerAndSerial;
    }

    public byte[] getHashValue() {
        return Arrays.clone((byte[])this.hashValue.getOctets());
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(2);
        if (this.issuerAndSerial != null) {
            aSN1EncodableVector.add((ASN1Encodable)this.issuerAndSerial);
        }
        aSN1EncodableVector.add((ASN1Encodable)this.hashValue);
        return new DERSequence(aSN1EncodableVector);
    }
}

